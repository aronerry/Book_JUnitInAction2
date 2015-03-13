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

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * Tests the Google search page with all browsers supported by HtmlUnit 2.5.
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id: GoogleSearchAllBrowsersTest.java 392 2009-04-28 23:38:33Z
 *          garydgregory $
 */
@RunWith(value = Parameterized.class)
public class GoogleSearchAllBrowsersTest {

    @Parameters
    public static Collection<BrowserVersion[]> getBrowserVersions() {
        return Arrays.asList(new BrowserVersion[][] { { BrowserVersion.FIREFOX_2 }, { BrowserVersion.FIREFOX_3 },
                { BrowserVersion.INTERNET_EXPLORER_6 }, { BrowserVersion.INTERNET_EXPLORER_7 } });
    }

    private BrowserVersion browserVersion;

    public GoogleSearchAllBrowsersTest(BrowserVersion browserVersion) {
        this.browserVersion = browserVersion;
    }

    public void testSearchPage() throws Exception {
        WebClient webClient = new WebClient(this.browserVersion);
        webClient.setThrowExceptionOnScriptError(false);
        HtmlPage page = (HtmlPage) webClient.getPage("http://www.google.com");
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
    public void testSearchPageByXPath() throws Exception {
        WebClient webClient = new WebClient(this.browserVersion);
        webClient.setThrowExceptionOnScriptError(false);
        HtmlPage page = (HtmlPage) webClient.getPage("http://www.google.com");
        // HtmlTextInput queryText = (HtmlTextInput)
        // searchPage.getFirstByXPath("/html/body/center/form/table/tbody/tr/td[2]/input[2]");
        HtmlTextInput queryText = (HtmlTextInput) page.getFirstByXPath("//input[@name='q']");
        Assert.assertNotNull("Query text field not found", queryText);
        queryText.setValueAttribute("Manning Publications Co.");
        // HtmlSubmitInput searchButton = (HtmlSubmitInput)
        // searchPage.getFirstByXPath("/html/body/center/form/table/tbody/tr/td[2]/input[3]");
        HtmlSubmitInput searchButton = (HtmlSubmitInput) page.getFirstByXPath("//input[@name='btnG']");
        HtmlPage resultPage = (HtmlPage) searchButton.click();
        if (this.browserVersion.equals(BrowserVersion.FIREFOX_2) || this.browserVersion.equals(BrowserVersion.FIREFOX_3)) {
            // Google generates a different page for FF, so skip the rest for
            // now since it belong to the Ajax chapter
            return;
        }
        HtmlAnchor link = resultPage.getAnchorByHref("http://www.manning.com/");
        HtmlPage page3 = (HtmlPage) link.click();
        WebAssert.assertTitleEquals(page3, "Manning Publications Co.");
    }

    @Test(expected = ElementNotFoundException.class)
    public void testUnknownElement() throws Exception {
        WebClient webClient = new WebClient(this.browserVersion);
        webClient.setThrowExceptionOnScriptError(false);
        HtmlPage page = (HtmlPage) webClient.getPage("http://www.google.com");
        page.getFormByName("unknown_element");
        Assert.fail("Expected the previous line throws a ElementNotFoundException");
    }

    @Test(expected = UnknownHostException.class)
    public void testUnknownPage() throws Exception {
        WebClient webClient = new WebClient(this.browserVersion);
        webClient.setThrowExceptionOnScriptError(false);
        webClient.getPage("http://unknown_page");
        Assert.fail("Expected the previous line throws a ElementNotFoundException");
    }

}
