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
package com.manning.junitbook.ch15.beans;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.jsfunit.jsfsession.JSFServerSession;
import org.jboss.jsfunit.jsfsession.JSFSession;
import org.xml.sax.SAXException;

import com.manning.junitbook.ch15.model.Album;

/**
 * Test the ListAvailableAlbumsBean.
 * 
 * @version $Id: TestListAvailableAlbumsBean.java 530 2009-08-16 19:01:19Z paranoid12 $
 */
public class TestListAvailableAlbumsBean
    extends org.apache.cactus.ServletTestCase
{
    public static Test suite()
    {
        return new TestSuite( TestListAvailableAlbumsBean.class );
    }

    public void testInitialPage()
        throws IOException, SAXException
    {
        // Send an HTTP request for the initial page
        JSFSession jsfSession = new JSFSession( "/" );

        // A JSFClientSession emulates the browser and lets you test HTML
        // JSFClientSession client = jsfSession.getJSFClientSession();

        // A JSFServerSession gives you access to JSF state
        JSFServerSession server = jsfSession.getJSFServerSession();

        // Test navigation to initial viewID
        assertEquals( "/list_albums.jsp", server.getCurrentViewID() );

        // Assert that the prompt component is in the component tree and rendered
        UIComponent label = server.findComponent( "list_albums_label" );
        assertEquals( label.getParent().getId(), "list_albums" );

        // Test a managed bean
        assertEquals( 5, ( (List<Album>) server.getManagedBeanValue( "#{listAlbumsBean.albums}" ) ).size() );
    }
}
    