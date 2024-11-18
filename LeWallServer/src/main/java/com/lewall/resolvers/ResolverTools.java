package com.lewall.resolvers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import java.util.HashMap;
import java.util.UUID;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.lewall.api.Request;
import com.lewall.api.Response;
import com.lewall.api.Request.EHeader;
import com.lewall.api.Request.EMethod;
import com.lewall.api.Response.EStatus;
import com.lewall.api.ServerException;
import com.lewall.services.AuthService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ResolverTools {
    private static final Logger logger = LogManager.getLogger(ResolverTools.class);

    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();

    private static final HashMap<String, EndpointRef> endpointMap = new HashMap<>();

    private static BaseResolver[] resolvers = new BaseResolver[0];

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Resolver {
        String basePath();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AuthGuard {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Endpoint {
        String endpoint();

        EMethod method();

        Class<?> requestBodyType() default String.class;

        Class<?> responseBodyType() default String.class;
    }

    private static class EndpointRef {
        private final BaseResolver resolver;
        private final Method method;

        public EndpointRef(BaseResolver resolver, Method method) {
            this.resolver = resolver;
            this.method = method;
        }

        public BaseResolver getResolver() {
            return resolver;
        }

        public Method getMethod() {
            return method;
        }
    }

    public static void init(BaseResolver... resolvers) {
        ResolverTools.resolvers = resolvers;

        predetermineEndpoints();
    }

    private static void predetermineEndpoints() {
        for (BaseResolver resolver : resolvers) {
            String basePath = resolver.getClass().getAnnotation(Resolver.class).basePath();
            for (Method m : resolver.getClass().getMethods()) {
                if (m.isAnnotationPresent(Endpoint.class)) {
                    Endpoint endpointAnnotation = m.getAnnotation(Endpoint.class);
                    String endpoint = endpointAnnotation.endpoint();
                    String fullPath = combinePaths(basePath, endpoint);

                    // Check for duplicate endpoints
                    if (endpointMap.containsKey(fullPath)) {
                        throw new RuntimeException(String.format(
                                "Duplicate endpoint: %s on %s exists on %s",
                                fullPath, resolver.getClass(), endpointMap.get(fullPath).getResolver().getClass()));
                    }

                    // Validate method return type
                    boolean defaultVoidResponse = m.getReturnType().equals(Void.TYPE)
                            && endpointAnnotation.responseBodyType().equals(String.class);
                    if (!m.getReturnType().equals(endpointAnnotation.responseBodyType()) && !defaultVoidResponse) {
                        throw new RuntimeException(String.format(
                                "Endpoint %s on %s does not return the correct type: %s != %s",
                                fullPath, resolver.getClass(), m.getReturnType(),
                                endpointAnnotation.responseBodyType()));
                    }

                    endpointMap.put(fullPath, new EndpointRef(resolver, m));
                    logger.debug("Registered Endpoint: " + fullPath);
                }
            }
        }
    }

    private static String combinePaths(String basePath, String endpoint) {
        if (basePath.equals("/") && endpoint.startsWith("/")) {
            endpoint = endpoint.substring(1);
        } else if (endpoint.equals("/")) {
            return basePath;
        }

        return basePath + endpoint;
    }

    public interface BaseResolver {
    }

    private static String resolveEndpoint(EndpointRef ref, Request<?> request, String rawRequest)
            throws IllegalAccessException, IllegalArgumentException {
        BaseResolver resolver = ref.getResolver();
        Method m = ref.getMethod();
        UUID userId = null;

        // Validates request and attaches userId to request if AuthGuard is present
        if (m.isAnnotationPresent(AuthGuard.class)) {
            String accessToken = request.getHeaders().get(EHeader.ACCESS_TOKEN);
            if (accessToken == null) {
                Response<String> response = new Response<String>(request.getMethod(), request.getEndpoint(),
                        "No Access Token Provided.", EStatus.UNAUTHORIZED, request.getRequestId());
                return gson.toJson(response);
            }

            try {
                userId = AuthService.validateAccessToken(accessToken);

                if (userId == null) {
                    Response<String> response = new Response<String>(request.getMethod(), request.getEndpoint(),
                            "Invalid Access Token.", EStatus.UNAUTHORIZED, request.getRequestId());
                    return gson.toJson(response);
                }
            } catch (ExpiredJwtException e) {
                Response<String> response = new Response<String>(request.getMethod(), request.getEndpoint(),
                        "Access Token Expired.", EStatus.ACCESS_EXPIRED, request.getRequestId());
                return gson.toJson(response);
            } catch (JwtException e) {
                Response<String> response = new Response<String>(request.getMethod(), request.getEndpoint(),
                        "Invalid Access Token.", EStatus.UNAUTHORIZED, request.getRequestId());
                return gson.toJson(response);
            }
        }

        Endpoint endpointAnnotation = m.getAnnotation(Endpoint.class);
        Class<?> requestBodyType = endpointAnnotation.requestBodyType();
        Type requestType = TypeToken.getParameterized(Request.class, requestBodyType).getType();

        // Ensuring request contains a valid body
        try {
            request = gson.fromJson(rawRequest, requestType);
        } catch (Exception e) {
            Response<String> response = new Response<String>(request.getMethod(), request.getEndpoint(),
                    "Bad Request", EStatus.BAD_REQUEST, request.getRequestId());
            return gson.toJson(response);
        }

        request.setUserId(userId);

        try {
            // Handle `void` endpoint return type
            if (m.getReturnType().equals(Void.TYPE)) {
                m.invoke(resolver, request);
                Response<String> response = new Response<String>(request.getMethod(), request.getEndpoint(),
                        "Success", EStatus.OK, request.getRequestId());
                return gson.toJson(response);
            }

            // Handle non-void endpoint return type
            Object methodResponse = m.invoke(resolver, request);

            Type responseType = TypeToken.getParameterized(Response.class,
                    endpointAnnotation.responseBodyType()).getType();

            Response<Object> response = new Response<>(request.getMethod(),
                    request.getEndpoint(),
                    methodResponse, EStatus.OK, request.getRequestId());
            return gson.toJson(response, responseType);
        } catch (InvocationTargetException e) {
            Exception cause = (Exception) e.getCause();

            if (cause instanceof ServerException) {
                Response<String> response = new Response<String>(request.getMethod(), request.getEndpoint(),
                        cause.getMessage(), ((ServerException) cause).getStatus(), request.getRequestId());
                return gson.toJson(response);
            }

            Response<String> response = new Response<String>(request.getMethod(), request.getEndpoint(),
                    cause.getMessage(), EStatus.SERVER_ERROR, request.getRequestId());
            return gson.toJson(response);
        }
    }

    /**
     * 
     * @param request
     * @param rawRequest
     * @return response JSON in String format
     */
    public static String resolve(Request<?> request, String rawRequest) {
        String path = request.getEndpoint();
        EndpointRef ref = endpointMap.get(path);

        if (ref == null || ref.getMethod().getAnnotation(Endpoint.class).method() != request.getMethod()) {
            Response<String> response = new Response<String>(request.getMethod(), request.getEndpoint(),
                    "Unknown Endpoint", EStatus.UNKNOWN_ENDPOINT, request.getRequestId());
            return gson.toJson(response);
        }

        try {
            return resolveEndpoint(ref, request, rawRequest);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            logger.error("Internal Server Error", e);

            Response<String> response = new Response<String>(request.getMethod(), request.getEndpoint(),
                    "Internal Server Error", EStatus.INTERNAL_SERVER_ERROR, request.getRequestId());
            return gson.toJson(response);
        }
    }
}
