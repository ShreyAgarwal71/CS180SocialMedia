package com.cs180.resolvers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.cs180.api.Request;
import com.cs180.api.Response;
import com.cs180.api.Request.EMethod;
import com.cs180.api.Response.EStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ResolverTools {
    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Resolver {
        String basePath();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Endpoint {
        String endpoint();

        EMethod method();

        Class<?> requestBodyType();

        Class<?> responseBodyType() default String.class;
    }

    public class BaseResolver {

    }

    private static final BaseResolver[] resolvers = new BaseResolver[] {
            new AuthResolver()
    };

    public static BaseResolver getResolver(String path) {
        if (path == null) {
            return null;
        }

        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        for (BaseResolver resolver : resolvers) {
            Resolver resolverAnnotation = resolver.getClass().getAnnotation(Resolver.class);
            if (resolverAnnotation.basePath().equals(path)) {
                return resolver;
            }
        }

        return null;
    }

    /**
     * 
     * @param request
     * @param rawRequest
     * @return response JSON in String format
     */
    public static String resolve(Request<?> request, String rawRequest) {
        String path = request.getEndpoint();

        String[] pathParts = null;
        String[] pathPartsInitial = path.split("/");

        if (path.equals("/")) {
            pathParts = new String[] { "/" };
        } else if (pathPartsInitial.length > 1 && pathPartsInitial[0].equals("")) {
            pathParts = new String[pathPartsInitial.length - 1];
            System.arraycopy(pathPartsInitial, 1, pathParts, 0, pathPartsInitial.length -
                    1);
        }

        if (pathParts == null) {
            pathParts = pathPartsInitial;
        }

        String endpoint = null;

        BaseResolver resolver = getResolver(pathParts[0]);
        if (resolver == null) {
            if (pathParts.length == 1) {
                resolver = getResolver("/");
            }

            if (resolver == null) {
                Response<String> response = new Response<String>(request.getMethod(), request.getEndpoint(),
                        "Unknown Endpoint", EStatus.UNKNOWN_ENDPOINT);
                return gson.toJson(response);
            }

            endpoint = pathParts[0];
        } else {
            endpoint = pathParts[1];
        }

        if (!endpoint.startsWith("/"))
            endpoint = "/" + endpoint;

        for (Method m : resolver.getClass().getMethods()) {
            if (m.isAnnotationPresent(Endpoint.class)) {
                if (m.getAnnotation(Endpoint.class).endpoint().equals(endpoint) &&
                        m.getAnnotation(Endpoint.class).method().equals(request.getMethod())) {

                    try {
                        Class<?> c = m.getAnnotation(Endpoint.class).requestBodyType();
                        Type requestType = TypeToken.getParameterized(Request.class, c).getType();
                        request = gson.fromJson(rawRequest, requestType);
                    } catch (Exception e) {
                        Response<String> response = new Response<String>(request.getMethod(), request.getEndpoint(),
                                "Bad Request", EStatus.BAD_REQUEST);
                        return gson.toJson(response);
                    }

                    try {
                        if (m.getReturnType().equals(Void.TYPE)) {
                            m.invoke(resolver, request);
                            Response<String> response = new Response<String>(request.getMethod(), request.getEndpoint(),
                                    "Success", EStatus.OK);
                            return gson.toJson(response);
                        } else if (m.getReturnType().equals(m.getAnnotation(Endpoint.class).responseBodyType())) {
                            Object methodResponse = m.invoke(resolver, request);
                            String expectedResponseBodyName = m.getAnnotation(Endpoint.class).responseBodyType()
                                    .getName();
                            if (methodResponse.getClass().getName().equals(expectedResponseBodyName)) {
                                Type responseType = TypeToken.getParameterized(Response.class,
                                        m.getAnnotation(Endpoint.class).responseBodyType()).getType();

                                Response<Object> response = new Response<>(request.getMethod(),
                                        request.getEndpoint(),
                                        methodResponse, EStatus.OK);
                                return gson.toJson(response, responseType);
                            }
                        }

                        Response<String> response = new Response<String>(request.getMethod(), request.getEndpoint(),
                                "Success", EStatus.OK);
                        return gson.toJson(response);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        e.printStackTrace();

                        Response<String> response = new Response<String>(request.getMethod(), request.getEndpoint(),
                                "Internal Server Error", EStatus.INTERNAL_SERVER_ERROR);
                        return gson.toJson(response);
                    }
                }
            }
        }

        Response<String> response = new Response<String>(request.getMethod(), request.getEndpoint(),
                "Unknown Endpoint", EStatus.UNKNOWN_ENDPOINT);
        return gson.toJson(response);
    }
}
