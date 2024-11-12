package com.cs180.resolvers;

import com.cs180.api.Request;
import com.cs180.api.Request.EMethod;
import com.cs180.resolvers.ResolverTools.BaseResolver;
import com.cs180.resolvers.ResolverTools.Endpoint;
import com.cs180.resolvers.ResolverTools.Resolver;

@Resolver(basePath = "/")
public class RootResolver implements BaseResolver {

    @Endpoint(endpoint = "/", method = EMethod.GET)
    public String root(Request<String> request) {
        return "All Systems Operational.";
    }
}
