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
package com.manning.junitbook.ch15.mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.servlet.http.HttpServletRequest;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import com.manning.junitbook.ch15.beans.AlbumDetailsBean;

/**
 * Test the AlbumDetailsBean by using the JMock mocking framework.
 * 
 * @version $Id: TestAlbumDetailsBeanMock.java 530 2009-08-16 19:01:19Z paranoid12 $
 */
public class TestAlbumDetailsBeanMock
{

    /**
     * The mockery context that we use to create our mocks.
     */
    private Mockery context = new JUnit4Mockery();

    /**
     * The mock instance of the AccountManager to use.
     */
    private HttpServletRequest mockRequest;

    @Before
    public void setUp()
    {
        mockRequest = context.mock( HttpServletRequest.class );
    }

    @Test
    public void testShowAlbumDetailsRealAlbum()
    {
        context.checking( new Expectations()
        {
            {
                oneOf( mockRequest ).getParameter( "albumName" );
                will( returnValue( "Achtung Baby" ) );
            }
        } );

        AlbumDetailsBean albumDetailsBean = new AlbumDetailsBean();
        albumDetailsBean.setRequest( mockRequest );

        String forwardString = albumDetailsBean.showAlbumDetails();
        assertEquals( "The return string must match 'showAlbumDetails'", forwardString, "showAlbumDetails" );

        assertNotNull( "The album must not be null", albumDetailsBean.getAlbum() );
        assertEquals( "The author must be U2", albumDetailsBean.getAlbum().getAuthor(), "U2" );
        assertEquals( "The year of the album must be 1991", albumDetailsBean.getAlbum().getYear(), 1991 );
    }

    @Test
    public void testShowAlbumDetailsNoParameterAlbum()
    {
        context.checking( new Expectations()
        {
            {
                oneOf( mockRequest ).getParameter( "albumName" );
                will( returnValue( null ) );
            }
        } );

        AlbumDetailsBean albumDetailsBean = new AlbumDetailsBean();
        albumDetailsBean.setRequest( mockRequest );

        String forwardString = albumDetailsBean.showAlbumDetails();
        assertEquals( forwardString, "" );
        assertNull( "The album must be null", albumDetailsBean.getAlbum() );
    }

    @Test
    public void testShowAlbumDetailsNoRealAlbum()
    {
        context.checking( new Expectations()
        {
            {
                oneOf( mockRequest ).getParameter( "albumName" );
                will( returnValue( "No-real-album" ) );
            }
        } );

        AlbumDetailsBean albumDetailsBean = new AlbumDetailsBean();
        albumDetailsBean.setRequest( mockRequest );

        String forwardString = albumDetailsBean.showAlbumDetails();
        assertEquals( "The return string must match 'showAlbumDetails'", forwardString, "showAlbumDetails" );
        assertNull( "The album must be null", albumDetailsBean.getAlbum() );
    }
}
