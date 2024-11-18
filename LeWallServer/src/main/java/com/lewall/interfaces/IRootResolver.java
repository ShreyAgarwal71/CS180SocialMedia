package com.lewall.interfaces;

import com.lewall.api.Request;

/**
 * Interface implemented only by the RootResolver class
 *
 * @author Ates Isfendiyaroglu
 * @version 17 November 2024
 */
public interface IRootResolver {

	/**
	 * Defines the root
	 */
    public String root(Request<String> request);
}
