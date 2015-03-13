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
package com.manning.junitbook.ch07.mocks.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test the WebClient class using the JMock mocking library.
 * 
 * @version $Id: TestWebClientJMock.java 505 2009-08-16 17:58:38Z paranoid12 $
 */
@RunWith( JMock.class )
public class TestWebClientJMock
{
    private Mockery context = new JUnit4Mockery()
    {
        {
            setImposteriser( ClassImposteriser.INSTANCE );
        }
    };

    @Test
    public void testGetContentOk()
        throws Exception
    {
        final ConnectionFactory factory = context.mock( ConnectionFactory.class );
        final InputStream mockStream = context.mock( InputStream.class );

        context.checking( new Expectations()
        {
            {
                oneOf( factory ).getData();
                will( returnValue( mockStream ) );

                oneOf( mockStream ).read();
                will( onConsecutiveCalls( returnValue( new Integer( (byte) 'W' ) ),
                                          returnValue( new Integer( (byte) 'o' ) ),
                                          returnValue( new Integer( (byte) 'r' ) ),
                                          returnValue( new Integer( (byte) 'k' ) ),
                                          returnValue( new Integer( (byte) 's' ) ),
                                          returnValue( new Integer( (byte) '!' ) ), 
                                          returnValue( -1 ) ) );

                oneOf( mockStream ).close();
            }
        } );

        WebClient2 client = new WebClient2();

        String result = client.getContent( factory );

        assertEquals( "Works!", result );
    }

    @Test
    public void testGetContentCannotCloseInputStream()
        throws Exception
    {

        final ConnectionFactory factory = context.mock( ConnectionFactory.class );
        final InputStream mockStream = context.mock( InputStream.class );

        context.checking( new Expectations()
        {
            {
                oneOf( factory ).getData();
                will( returnValue( mockStream ) );
                oneOf( mockStream ).read();
                will( returnValue( -1 ) );
                oneOf( mockStream ).close();
                will( throwException( new IOException( "cannot close" ) ) );
            }
        } );

        WebClient2 client = new WebClient2();

        String result = client.getContent( factory );

        assertNull( result );
    }
}
