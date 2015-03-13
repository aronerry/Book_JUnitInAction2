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
package com.manning.junitbook.ch14.servlets;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.cactus.FilterTestCase;
import org.apache.cactus.WebRequest;
import org.apache.cactus.WebResponse;

/**
 * A Cactus test-case for the SecurityFilter object.
 */
public class TestSecurityFilter extends FilterTestCase {

	/**
	 * A begin method for the doFilterAllowedSQL method. This begin method will
	 * be executed on the client-side and will setup the command parameter in
	 * the request.
	 * 
	 * @param request
	 */
	public void beginDoFilterAllowedSQL(WebRequest request) {
		request.addParameter("command", "SELECT [...]");
	}

	/**
	 * A test to be executed on the server-side.
	 * 
	 * @throws Exception
	 */
	public void testDoFilterAllowedSQL() throws Exception {
		SecurityFilter filter = new SecurityFilter();

		FilterChain mockFilterChain = new FilterChain() {
			public void doFilter(ServletRequest theRequest,
					ServletResponse theResponse) throws IOException,
					ServletException {
			}

			public void init(FilterConfig theConfig) {
			}

			public void destroy() {
			}
		};

		filter.doFilter(request, response, mockFilterChain);
	}

	/**
	 * A begin method for the doFilterForbiddenSQL test-method. This begin
	 * method will be executed on the client-side and will try to setup the
	 * command parameter with an UPDATE value to see if the filter handles it
	 * correctly.
	 * 
	 * @param request
	 */
	public void beginDoFilterForbiddenSQL(WebRequest request) {
		request.addParameter("command", "UPDATE [...]");
	}

	/**
	 * A test-method to be executed on the server-side. Tests that in case of a
	 * forbidden SQL arrives on the server-side the filter handles it correctly.
	 * 
	 * @throws Exception
	 */
	public void testDoFilterForbiddenSQL() throws Exception {
		config.setInitParameter("securityErrorPage", "/securityError.jsp");
		SecurityFilter filter = new SecurityFilter();
		filter.init(config);

		filter.doFilter(request, response, filterChain);
	}

	/**
	 * End method for the doFilterForbiddenSQL. It will be executed again on the
	 * client side to assert that the forbidden SQL was redirected to a security
	 * error page.
	 * 
	 * @param response
	 */
	public void endDoFilterForbiddenSQL(WebResponse response) {
		assertTrue("Bad response page", response.getText().indexOf(
				"<title>Security Error Page</title>") > 0);
	}
}
