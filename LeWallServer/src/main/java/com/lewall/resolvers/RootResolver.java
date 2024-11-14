package com.lewall.resolvers;

import com.lewall.api.Request;
import com.lewall.api.Request.EMethod;
import com.lewall.resolvers.ResolverTools.BaseResolver;
import com.lewall.resolvers.ResolverTools.Endpoint;
import com.lewall.resolvers.ResolverTools.Resolver;

@Resolver(basePath = "/")
public class RootResolver implements BaseResolver {

    @Endpoint(endpoint = "/", method = EMethod.GET)
    public String root(Request<String> request) {
        return "All Systems Operational.";
    }
}
