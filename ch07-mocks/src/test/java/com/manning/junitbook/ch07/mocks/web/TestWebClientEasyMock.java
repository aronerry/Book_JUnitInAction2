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

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the WebClient class using the EasyMock library.
 * 
 * @version $Id: TestWebClientEasyMock.java 512 2009-08-16 18:16:02Z paranoid12 $
 */
public class TestWebClientEasyMock
{
    private ConnectionFactory factory;

    private InputStream stream;

    @Before
    public void setUp()
    {
        factory = createMock("factory", ConnectionFactory.class );
        stream = createMock("stream", InputStream.class );
    }

    @Test
    public void testGetContentOk()
        throws Exception
    {
        expect( factory.getData() ).andReturn( stream );
        expect( stream.read() ).andReturn( new Integer( (byte) 'W' ) );
        expect( stream.read() ).andReturn( new Integer( (byte) 'o' ) );
        expect( stream.read() ).andReturn( new Integer( (byte) 'r' ) );
        expect( stream.read() ).andReturn( new Integer( (byte) 'k' ) );
        expect( stream.read() ).andReturn( new Integer( (byte) 's' ) );
        expect( stream.read() ).andReturn( new Integer( (byte) '!' ) );

        expect( stream.read() ).andReturn( -1 );
        stream.close();

        replay( factory );
        replay( stream );

        WebClient2 client = new WebClient2();

        String result = client.getContent( factory );

        assertEquals( "Works!", result );
    }
    
    @Test
    public void testGetContentInputStreamNull() throws Exception {
        expect( factory.getData() ).andReturn( null );

        replay( factory );
        replay( stream );

        WebClient2 client = new WebClient2();

        String result = client.getContent( factory );

        assertNull( result ); 
    }

    @Test
    public void testGetContentCannotCloseInputStream() throws Exception {
        expect( factory.getData() ).andReturn( stream );
        expect( stream.read() ).andReturn( -1 );
        stream.close();
        expectLastCall().andThrow(new IOException("cannot close"));
        
        replay( factory );
        replay( stream );

        WebClient2 client = new WebClient2();
        String result = client.getContent( factory );

        assertNull( result ); 
    }

    @After
    public void tearDown()
    {
        verify( factory );
        verify( stream );
    }
}
