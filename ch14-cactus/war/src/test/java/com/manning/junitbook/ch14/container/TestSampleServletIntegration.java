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

import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;

/**
 * A sample cactus test-case to jumpstart showing servlet testing.
 */
public class TestSampleServletIntegration extends ServletTestCase {

	/**
	 * The sample servlet object.
	 */
	private SampleServlet servlet;

	/**
	 * A setUp method to be executed on the server-side.
	 */
	protected void setUp() {
		servlet = new SampleServlet();
	}

	/**
	 * Test method to be executed on the server-side. Tests to see whether the
	 * authenticated attribute is taken into consideration.
	 */
	public void testIsAuthenticatedAuthenticated() {
		session.setAttribute("authenticated", "true");

		assertTrue(servlet.isAuthenticated(request));
	}

	/**
	 * A test for testing whether the servlet is authenticated by default.
	 */
	public void testIsAuthenticatedNotAuthenticated() {
		assertFalse(servlet.isAuthenticated(request));
	}

	/**
	 * A begin method for the isAuthenticatedNoSession method. This method will
	 * be executed on the client-side and willset the automaticSession of the
	 * request to be false
	 * 
	 * @param request
	 */
	public void beginIsAuthenticatedNoSession(WebRequest request) {
		request.setAutomaticSession(false);
	}

	/**
	 * A test to be executed in the server JVM. This test tests the
	 * isAuthenticated method of the SampleServlet.
	 */
	public void testIsAuthenticatedNoSession() {
		assertFalse(servlet.isAuthenticated(request));
	}
}
