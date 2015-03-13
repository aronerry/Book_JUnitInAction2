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

package com.manning.junitbook.ch13.htmlunit;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.manning.junitbook.ch12.htmlunit.ManagedWebClient;

/**
 * Tests a form.
 * <p>
 * To test the form in IE, create a virtual directory in IIS, use the permission
 * wizard to grant default rights, and point your browser and tests to
 * http://localhost/webapp/formtest.html
 * </p>
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id$
 */
public class AjaxFormTest extends ManagedWebClient {

    private static final String EXPECTED_MSG = "Hello World";

    /**
     * The directory /ch13-ajax/src/main/webapp has been configured as an IIS
     * virtual directory for this test.
     */
    private static final String TEST_URL = "http://localhost/webapp/formtest.html";

    @Test
    public void testAjaxForm() throws IOException {
        this.webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        HtmlPage page = (HtmlPage) this.webClient.getPage(TEST_URL);
        HtmlButtonInput button = (HtmlButtonInput) page.getFirstByXPath("/html/body/form/input[1]");
        HtmlPage newPage = button.click();
        HtmlInput reply = (HtmlInput) newPage.getFirstByXPath("/html/body/form/input[2]");
        Assert.assertTrue(EXPECTED_MSG.equals(reply.asText()));
    }

    @Test
    public void testAjaxFormOutOfSync() throws IOException {
        HtmlPage page = (HtmlPage) this.webClient.getPage(TEST_URL);
        HtmlButtonInput button = (HtmlButtonInput) page.getFirstByXPath("/html/body/form/input[1]");
        HtmlPage newPage = button.click();
        HtmlInput reply = (HtmlInput) newPage.getFirstByXPath("/html/body/form/input[2]");
        Assert.assertFalse(EXPECTED_MSG.equals(reply.asText()));
    }

    @Test
    public void testAjaxFormWaitForBackgroundJavaScript() throws IOException {
        HtmlPage page = (HtmlPage) this.webClient.getPage(TEST_URL);
        HtmlButtonInput button = (HtmlButtonInput) page.getFirstByXPath("/html/body/form/input[1]");
        HtmlPage newPage = button.click();
        this.webClient.waitForBackgroundJavaScript(1000);
        HtmlInput reply = (HtmlInput) newPage.getFirstByXPath("/html/body/form/input[2]");
        Assert.assertTrue(EXPECTED_MSG.equals(reply.asText()));
    }
}
