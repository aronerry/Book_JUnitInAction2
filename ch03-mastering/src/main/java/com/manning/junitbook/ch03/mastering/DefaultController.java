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
package com.manning.junitbook.ch03.mastering;

import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of the controller.
 * 
 * @version $Id: DefaultController.java 553 2010-03-06 12:29:58Z paranoid12 $
 */
public class DefaultController implements Controller {
	private Map<String, RequestHandler> requestHandlers = new HashMap<String, RequestHandler>();

	protected RequestHandler getHandler(Request request) {
		if (!this.requestHandlers.containsKey(request.getName())) {
			String message = "Cannot find handler for request name " + "["
					+ request.getName() + "]";
			throw new RuntimeException(message);
		}
		return this.requestHandlers.get(request.getName());
	}

	public Response processRequest(Request request) {
		Response response;
		try {
			response = getHandler(request).process(request);
		} catch (Exception exception) {
			response = new ErrorResponse(request, exception);
		}
		return response;
	}

	public void addHandler(Request request, RequestHandler requestHandler) {
		if (this.requestHandlers.containsKey(request.getName())) {
			throw new RuntimeException("A request handler has "
					+ "already been registered for request name " + "["
					+ request.getName() + "]");
		} else {
			this.requestHandlers.put(request.getName(), requestHandler);
		}
	}
}
