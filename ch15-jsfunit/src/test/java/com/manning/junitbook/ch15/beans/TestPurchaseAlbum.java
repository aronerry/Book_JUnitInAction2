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

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.cactus.ServletTestCase;
import org.jboss.jsfunit.jsfsession.JSFClientSession;
import org.jboss.jsfunit.jsfsession.JSFServerSession;
import org.jboss.jsfunit.jsfsession.JSFSession;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Test the AJAX support of RichFaces.
 * 
 * @version $Id: TestPurchaseAlbum.java 530 2009-08-16 19:01:19Z paranoid12 $
 */
public class TestPurchaseAlbum
    extends ServletTestCase
{
    public static Test suite()
    {
        return new TestSuite( TestPurchaseAlbum.class );
    }

    public void testCommandButton()
        throws IOException, SAXException
    {
        JSFSession jsfSession = new JSFSession( "/album_details.jsp" );
        
        JSFServerSession server = jsfSession.getJSFServerSession();
        JSFClientSession client = jsfSession.getJSFClientSession();

        client.click( "PurchaseButton" );

        Object userBeanValue = server.getManagedBeanValue( "#{albumDetailsBean.status}" );
        assertEquals( "Successfully purchased: ", userBeanValue );
        
        String spanContent =((HtmlPage)client.getContentPage()).getElementsByTagName( "span" ).item( 0 ).getTextContent(); 

        assertEquals(spanContent, "Successfully purchased:");
    }
}