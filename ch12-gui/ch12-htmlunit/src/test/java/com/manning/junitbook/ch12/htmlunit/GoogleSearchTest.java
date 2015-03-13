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

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * Tests the Google search page.
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id: GoogleSearchTest.java 410 2009-05-20 21:37:55Z garydgregory $
 */
public class GoogleSearchTest extends ManagedWebClient {

    @Test
    public void testSearchPage() throws Exception {
        this.webClient.setThrowExceptionOnScriptError(false);
        HtmlPage page = (HtmlPage) this.webClient.getPage("http://www.google.com");
        HtmlForm form = page.getForms().get(0);
        HtmlTextInput queryText = (HtmlTextInput) form.getInputByName("q");
        queryText.setValueAttribute("Manning Publications Co.");
        HtmlSubmitInput searchButton = (HtmlSubmitInput) form.getInputByName("btnG");
        HtmlPage resultPage = (HtmlPage) searchButton.click();
        HtmlAnchor link = resultPage.getAnchorByHref("http://www.manning.com/");
        HtmlPage page3 = (HtmlPage) link.click();
        WebAssert.assertTitleEquals(page3, "Manning Publications Co.");
    }

    @Test
    public void testSearchPageEnter() throws Exception {
        this.webClient.setThrowExceptionOnScriptError(false);
        HtmlPage page = (HtmlPage) this.webClient.getPage("http://www.google.com");
        HtmlForm form = page.getForms().get(0);
        HtmlTextInput queryText = (HtmlTextInput) form.getInputByName("q");
        queryText.setValueAttribute("Manning Publications Co.");
        HtmlPage resultPage = (HtmlPage) queryText.type('\n');
        HtmlAnchor link = resultPage.getAnchorByHref("http://www.manning.com/");
        HtmlPage page3 = (HtmlPage) link.click();
        WebAssert.assertTitleEquals(page3, "Manning Publications Co.");
    }

}
