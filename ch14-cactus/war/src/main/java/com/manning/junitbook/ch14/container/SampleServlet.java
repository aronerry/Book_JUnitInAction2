/* 
 * ========================================================================
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * ========================================================================
 */
package com.manning.junitbook.ch14.container;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * A sample servlet to demonstrate how to unit test servlets with Cactus.
 */
public class SampleServlet extends HttpServlet {
	
	/**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
	 * Checks to see if the authenticated attribute has been setup
	 * with value of true. 
	 * 
	 * @param request
	 * @return
	 */
	public boolean isAuthenticated(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null) {
			return false;
		}

		String authenticationAttribute = (String) session
				.getAttribute("authenticated");

		return Boolean.valueOf(authenticationAttribute).booleanValue();
	}
}
