package com.lewall.resolvers;

import com.lewall.api.Request;
import com.lewall.api.Request.EMethod;
import com.lewall.interfaces.IRootResolver;
import com.lewall.resolvers.ResolverTools.BaseResolver;
import com.lewall.resolvers.ResolverTools.Endpoint;
import com.lewall.resolvers.ResolverTools.Resolver;

/**
 * A class to resolve the base path
 *
 * @author Shrey Agarwal
 * @version 14 November 2024
 */
@Resolver(basePath = "/")
public class RootResolver implements BaseResolver, IRootResolver {

    /**
     * Defines the root
     * 
     * @param request
     *            {@link Request} with {@link String} body
     * @return {@link String}
     *
     */
    @Endpoint(endpoint = "/", method = EMethod.GET)
    public String root(Request<String> request) {
        return "All Systems Operational.";
    }
}
