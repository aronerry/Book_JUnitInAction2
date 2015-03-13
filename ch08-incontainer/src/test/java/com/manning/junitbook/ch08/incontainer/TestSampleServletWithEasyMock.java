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
package com.manning.junitbook.ch08.incontainer;

import javax.servlet.http.HttpServletRequest;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.easymock.EasyMock.eq;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This test-case tests the SampleServlet class using the EasyMock mock-objects library.
 * 
 * @version $Id$
 */
public class TestSampleServletWithEasyMock
{

    private SampleServlet servlet;

    private HttpServletRequest mockHttpServletRequest;

    private HttpSession mockHttpSession;

    @Before
    public void setUp()
    {
        servlet = new SampleServlet();
        mockHttpServletRequest = createStrictMock( HttpServletRequest.class );
        mockHttpSession = createStrictMock( HttpSession.class );
    }

    @After
    public void tearDown()
    {
        verify( mockHttpServletRequest );
        verify( mockHttpSession );
    }

    @Test
    public void testIsAuthenticatedAuthenticated()
    {
        expect( mockHttpServletRequest.getSession( eq( false ) ) ).andReturn( mockHttpSession );
        expect( mockHttpSession.getAttribute( eq( "authenticated" ) ) ).andReturn( "true" );

        replay( mockHttpServletRequest );
        replay( mockHttpSession );

        assertTrue( servlet.isAuthenticated( mockHttpServletRequest ) );
    }

    @Test
    public void testIsAuthenticatedNotAuthenticated()
    {
        expect( mockHttpSession.getAttribute( eq( "authenticated" ) ) ).andReturn( "false" );
        replay( mockHttpSession );

        expect( mockHttpServletRequest.getSession( eq( false ) ) ).andReturn( mockHttpSession );
        replay( mockHttpServletRequest );

        assertFalse( servlet.isAuthenticated( mockHttpServletRequest ) );
    }

    @Test
    public void testIsAuthenticatedNoSession()
    {
        expect( mockHttpServletRequest.getSession( eq( false ) ) ).andReturn( null );

        replay( mockHttpServletRequest );
        replay( mockHttpSession );

        assertFalse( servlet.isAuthenticated( mockHttpServletRequest ) );
    }
}
