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
package com.manning.junitbook.ch06.stubs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.HttpHeaders;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.util.ByteArrayISO8859Writer;

/**
 * A sample test case that demonstrates how to stub an HTTP server using Jetty as an embedded server.
 * 
 * @version $Id$
 */
public class TestWebClient
{

    private WebClient client = new WebClient();

    @BeforeClass
    public static void setUp()
        throws Exception
    {
        Server server = new Server( 8080 );

        TestWebClient t = new TestWebClient();

        Context contentOkContext = new Context( server, "/testGetContentOk" );
        contentOkContext.setHandler( t.new TestGetContentOkHandler() );

        Context contentErrorContext = new Context( server, "/testGetContentError" );
        contentErrorContext.setHandler( t.new TestGetContentServerErrorHandler() );

        Context contentNotFoundContext = new Context( server, "/testGetContentNotFound" );
        contentNotFoundContext.setHandler( t.new TestGetContentNotFoundHandler() );

        server.setStopAtShutdown( true );
        server.start();
    }

    @Test
    public void testGetContentOk()
        throws Exception
    {
        String result = client.getContent( new URL( "http://localhost:8080/testGetContentOk" ) );
        assertEquals( "It works", result );
    }

    @Test
    public void testGetContentError()
        throws Exception
    {
        String result = client.getContent( new URL( "http://localhost:8080/testGetContentError/" ) );
        assertNull( result );
    }

    @Test
    public void testGetContentNotFound()
        throws Exception
    {
        String result = client.getContent( new URL( "http://localhost:8080/testGetContentNotFound" ) );
        assertNull( result );
    }

    @AfterClass
    public static void tearDown()
    {
        // Do nothing becuase the Jetty server is configured
        // to stop at shutdown.
    }

    /**
     * Handler to handle the good requests to the server.
     */
    private class TestGetContentOkHandler
        extends AbstractHandler
    {
        public void handle( String target, HttpServletRequest request, HttpServletResponse response, int dispatch )
            throws IOException, ServletException
        {

            OutputStream out = response.getOutputStream();
            ByteArrayISO8859Writer writer = new ByteArrayISO8859Writer();
            writer.write( "It works" );
            writer.flush();
            response.setIntHeader( HttpHeaders.CONTENT_LENGTH, writer.size() );
            writer.writeTo( out );
            out.flush();
        }
    }

    /**
     * Handler to handle bad requests to the server
     */
    private class TestGetContentServerErrorHandler
        extends AbstractHandler
    {

        public void handle( String target, HttpServletRequest request, HttpServletResponse response, int dispatch )
            throws IOException, ServletException
        {
            response.sendError( HttpServletResponse.SC_SERVICE_UNAVAILABLE );
        }
    }

    /**
     * Handler to handle requests that request unavailable content.
     */
    private class TestGetContentNotFoundHandler
        extends AbstractHandler
    {

        public void handle( String target, HttpServletRequest request, HttpServletResponse response, int dispatch )
            throws IOException, ServletException
        {
            response.sendError( HttpServletResponse.SC_NOT_FOUND );
        }
    }
}
