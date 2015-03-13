/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.manning.junitbook.ch12.htmlunit;

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.MockWebConnection;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Demonstrates using in-line HTML fixtures in test methods.
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id: InLineHtmlFixtureTest.java 392 2009-04-28 23:38:33Z
 *          garydgregory $
 */
public class InLineHtmlFixtureTest extends ManagedWebClient {

    @Test
    public void testInLineHtmlFixture() throws Exception {
        final String expectedTitle = "Hello 1!";
        String html = "<html><head><title>" + expectedTitle + "</title></head></html>";
        MockWebConnection conn = new MockWebConnection();
        conn.setDefaultResponse(html);
        this.webClient.setWebConnection(conn);
        HtmlPage page = this.webClient.getPage("http://page");
        Assert.assertEquals(expectedTitle, page.getTitleText());
    }

    @Test
    public void testInLineHtmlFixtures() throws Exception {
        final URL page1Url = new URL("http://Page1/");
        final URL page2Url = new URL("http://Page2/");
        final URL page3Url = new URL("http://Page3/");

        MockWebConnection conn = new MockWebConnection();
        conn.setResponse(page1Url, "<html><head><title>Hello 1!</title></head></html>");
        conn.setResponse(page2Url, "<html><head><title>Hello 2!</title></head></html>");
        conn.setResponse(page3Url, "<html><head><title>Hello 3!</title></head></html>");
        this.webClient.setWebConnection(conn);

        HtmlPage page1 = this.webClient.getPage(page1Url);
        Assert.assertEquals("Hello 1!", page1.getTitleText());

        HtmlPage page2 = this.webClient.getPage(page2Url);
        Assert.assertEquals("Hello 2!", page2.getTitleText());

        HtmlPage page3 = this.webClient.getPage(page3Url);
        Assert.assertEquals("Hello 3!", page3.getTitleText());
    }

}
