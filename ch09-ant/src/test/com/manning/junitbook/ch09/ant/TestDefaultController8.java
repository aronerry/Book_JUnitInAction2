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

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * A test-case for the DefaultController8.
 * 
 * @version $Id$
 */
public class TestDefaultController8
{
    private DefaultController controller;

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
        private static final String DEFAULT_NAME = "Test";

        private String name;

        public TestRequest( String name )
        {
            this.name = name;
        }

        public TestRequest()
        {
            this( DEFAULT_NAME );
        }

        public String getName()
        {
            return this.name;
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

    @Test( expected = RuntimeException.class )
    public void testGetHandlerNotDefined()
    {
        TestRequest request = new TestRequest( "testNotDefined" );
        controller.getHandler( request );
        
        fail( "An exception should be raised if the requested " + "handler has not been registered" );
    }

    @Test( expected = RuntimeException.class )
    public void testAddRequestDuplicateName()
    {
        TestRequest request = new TestRequest();
        TestHandler handler = new TestHandler();
        controller.addHandler( request, handler );
        
        fail( "An exception should be raised if the default " + "TestRequest has already been registered" );
    }
}
