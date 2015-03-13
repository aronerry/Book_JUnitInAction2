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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * A sample SecurityFilter used to intercept requests and check whether the
 * command parameter is allowed SQL (i.e. only UPDATE queries).
 */
public class SecurityFilter implements Filter {

	/**
	 * The error page to redirect to, in case not allowed SQL was set.
	 */
	private String securityErrorPage;

	/**
	 * Filter's init method.
	 */
	public void init(FilterConfig theConfig) throws ServletException {
		this.securityErrorPage = theConfig
				.getInitParameter("securityErrorPage");
	}

	/**
	 * Filter's doFilter method. Checks whether the command parameter was a
	 * valid SQL.
	 */
	public void doFilter(ServletRequest theRequest,
			ServletResponse theResponse, FilterChain theChain)
			throws IOException, ServletException {
		String sqlCommand = theRequest.getParameter(AdminServlet.COMMAND_PARAM);

		if (!sqlCommand.startsWith("SELECT")) {
			// Forward to an error page
			RequestDispatcher dispatcher = theRequest
					.getRequestDispatcher(this.securityErrorPage);
			dispatcher.forward(theRequest, theResponse);
		} else {
			theChain.doFilter(theRequest, theResponse);
		}
	}

	/**
	 * Filter's destroy() method.
	 */
	public void destroy() {
	}
}
