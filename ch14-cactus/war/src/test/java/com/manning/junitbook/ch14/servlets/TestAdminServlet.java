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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;

import com.sun.xml.internal.ws.util.ByteArrayBuffer;

/**
 * A Cactus test-case for the AdminServlet.
 */
public class TestAdminServlet extends ServletTestCase {

	/**
	 * Begin methods are executed on the client side. This one sets the command
	 * parameter
	 * 
	 * @param request
	 */
	public void beginGetCommandOk(WebRequest request) {
		request.addParameter("command", "SELECT...");
	}

	/**
	 * Test method to be executed inside the JVM of the container. This test
	 * tests to see if the command parameter is a select query.
	 * 
	 * @throws Exception
	 */
	public void testGetCommandOk() throws Exception {
		AdminServlet servlet = new AdminServlet();
		String command = servlet.getCommand(request);

		assertEquals("SELECT...", command);
	}

	/**
	 * Tests what would happen if the command parameter is not specified.
	 */
	public void testGetCommandNotDefined() {
		AdminServlet servlet = new AdminServlet();

		try {
			servlet.getCommand(request);
			fail("Command should not have existed");
		} catch (ServletException expected) {
			assertTrue(true);
		}
	}

	/**
	 * Creates a java.util.Collection of DynaBean objects to show in the JSP.
	 * 
	 * @return
	 * @throws Exception
	 */
	private Collection<DynaBean> createCommandResult() throws Exception {
		List<DynaBean> results = new ArrayList<DynaBean>();

		DynaProperty[] props = new DynaProperty[] {
				new DynaProperty("id", String.class),
				new DynaProperty("responsetime", Long.class) };
		BasicDynaClass dynaClass = new BasicDynaClass("requesttime", null,
				props);

		DynaBean request1 = dynaClass.newInstance();
		request1.set("id", "12345");
		request1.set("responsetime", new Long(500));
		results.add(request1);

		DynaBean request2 = dynaClass.newInstance();
		request1.set("id", "56789");
		request1.set("responsetime", new Long(430));
		results.add(request2);

		return results;
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void testCallView() throws Exception {
		AdminServlet servlet = new AdminServlet();

		// Set the result of the exection of the command in the
		// HTTP request so that the JSP page can get the data to
		// display
		request.setAttribute("result", createCommandResult());

		servlet.callView(request, response);
	}
	
	/**
	 * 
	 * @param response
	 * @throws Exception
	 */
	public void endCallView(com.meterware.httpunit.WebResponse response) throws Exception {
		
		InputStreamReader reader = new InputStreamReader(response.getInputStream());
		BufferedReader bufReader = new BufferedReader(reader);
		
		String content = "";
		String line = "";
		System.out.println("before");
		while ((line = bufReader.readLine()) != null) {
			content +=line;
			System.out.println("here: " + line);
		}
		System.out.println("after");
		
		
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n " + response.getResponseCode() + "\n\n"+content + "\n\n\n\n\n\n");
		
		assertTrue(response.isHTML());
		assertEquals("12345",
				response.getTables()[0].getCellAsText(1, 0));
		assertEquals("500", response.getTables()[0].getCellAsText(1, 1));
		assertEquals("56789",
				response.getTables()[0].getCellAsText(2, 0));
		assertEquals("430",
				response.getTables()[0].getCellAsText(2, 1));
	}


	/**
	 * A begin method for the doGet servlet's method. This begin method will be
	 * executed on the client side and will set the command parameter in the
	 * request
	 * 
	 * @param request
	 */
	public void beginDoGet(WebRequest request) {
		request.addParameter("command", "SELECT...");
	}

	/**
	 * A test for the doGet method of the servlet.
	 * @throws Exception
	 */
	public void testDoGet() throws Exception {
		AdminServlet servlet = new AdminServlet() {
			public Collection<DynaBean> executeCommand(String command) throws Exception {
				return createCommandResult();
			}
		};

		servlet.doGet(request, response);

		// Verify that the result of executing the command has been
		// stored in the HTTP request as an attribute that will be
		// passed to the JSP page.
		Collection<DynaBean> results = (Collection<DynaBean>) request.getAttribute("result");
		assertNotNull("Failed to get execution results from the " + "request",
				results);
		assertEquals(2, results.size());
	}

}
