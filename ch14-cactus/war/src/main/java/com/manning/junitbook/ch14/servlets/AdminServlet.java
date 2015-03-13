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
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A sample servlet for our Admin application.
 */
public class AdminServlet extends HttpServlet {

	/**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;
    /**
	 * The command parameter that we use to pass the SQL command to execute.
	 */
	public static final String COMMAND_PARAM = "command";

	/**
	 * A helper method to extract the command parameter from the request object.
	 * 
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	public String getCommand(HttpServletRequest request)
			throws ServletException {
		String command = request.getParameter(COMMAND_PARAM);
		if (command == null) {
			throw new ServletException("Missing parameter [" + COMMAND_PARAM
					+ "]");
		}
		return command;
	}

	/**
	 * This method calls the corresponding view to display the result.
	 * 
	 * @param request
	 */
	public void callView(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("/results.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param command
	 * @return
	 * @throws Exception
	 */
	public Collection executeCommand(String command) throws Exception {
		throw new RuntimeException("not implemented");
	}

	/**
	 * Servlet's entry point.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		try {
			Collection results = executeCommand(getCommand(request));
			request.setAttribute("result", results);
		} catch (Exception e) {
			throw new ServletException("Failed to execute command", e);
		}
	}
}
