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

import org.apache.cactus.ServletTestCase;
import org.jboss.jsfunit.jsfsession.JSFClientSession;
import org.jboss.jsfunit.jsfsession.JSFSession;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

/**
 * Demonstrate how to test the ListAvailableAlbums bean by using the
 * HTMLUnit.
 * 
 * @version $Id: TestListAvailableAlbumsWithHTMLUnit.java 530 2009-08-16 19:01:19Z paranoid12 $
 */
public class TestListAvailableAlbumsWithHTMLUnit extends ServletTestCase
{

    public void testIntialPage() throws IOException {
        JSFSession jsfSession = new JSFSession("/");
        JSFClientSession client = jsfSession.getJSFClientSession();
        HtmlPage page = (HtmlPage)client.getContentPage();
        HtmlTable table = (HtmlTable) page.getFirstByXPath("/html/body/form/table");
        
        assertNotNull("table should not be null",table);
        //Five albums + one header
        assertEquals( 6, table.getRowCount() );
        
        HtmlAnchor link  = table.getRow( 1 ).getCell( 0 ).getFirstByXPath( "a" );
        assertNotNull("link should not be null", link);
        
        HtmlPage newPage = link.click();
        
        //we should be now on the next page
        assertEquals(newPage.getTitleText(), "Album details");
    }
}
