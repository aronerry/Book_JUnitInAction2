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
package com.manning.junitbook.ch09.ant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * A test-case for the defaultController4.
 * 
 * @version $Id$
 */
public class TestDefaultController4
{
    private DefaultController controller;

    @Before
    public void setUp()
        throws Exception
    {
        controller = new DefaultController();
    }

    private class TestRequest
        implements Request
    {
        public String getName()
        {
            return "Test";
        }
    }

    private class TestHandler
        implements RequestHandler
    {
        public Response process( Request request )
            throws Exception
        {
            return new TestResponse();
        }
    }

    private class TestResponse
        implements Response
    {
        // empty
    }

    @Test
    public void testAddAndProcess()
    {
        Request request = new TestRequest();
        RequestHandler handler = new TestHandler();
        controller.addHandler( request, handler );

        RequestHandler handler2 = controller.getHandler( request );
        assertEquals( handler2, handler );

        // DO NOT COMBINE TEST METHODS THIS WAY
        Response response = controller.processRequest( request );
        assertNotNull( "Must not return a null response", response );
        assertEquals( TestResponse.class, response.getClass() );
    }

}
