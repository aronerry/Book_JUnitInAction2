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
 * A test-case for the defaultController5.
 * 
 * @version $Id$
 */
public class TestDefaultController5
{
    private Controller controller;

    private Request request;

    private RequestHandler handler;

    @Before
    public void setUp()
        throws Exception
    {
        controller = new DefaultController();
        request = new TestRequest();
        handler = new TestHandler();
        controller.addHandler( request, handler );
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
        private static final String NAME = "Test";

        public String getName()
        {
            return NAME;
        }

        public boolean equals( Object object )
        {
            boolean result = false;
            if ( object instanceof TestResponse )
            {
                result = ( (TestResponse) object ).getName().equals( getName() );
            }
            return result;
        }

        public int hashCode()
        {
            return NAME.hashCode();
        }
    }

    @Test
    public void testProcessRequest()
    {
        Response response = controller.processRequest( request );
        assertNotNull( "Must not return a null response", response );
        assertEquals( new TestResponse(), response );
    }
}
