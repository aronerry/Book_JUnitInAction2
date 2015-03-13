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

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.CollectingAlertHandler;
import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * Demonstrates testing a form.
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id: FormTest.java 410 2009-05-20 21:37:55Z garydgregory $
 */
public class FormTest extends ManagedWebClient {

    @Test
    public void testForm() throws IOException {
        this.webClient.setThrowExceptionOnScriptError(false);
        HtmlPage page = (HtmlPage) this.webClient.getPage("file:src/main/webapp/formtest.html");
        HtmlForm form = page.getFormByName("validated_form");
        HtmlTextInput input = (HtmlTextInput) form.getInputByName("in_text");
        input.setValueAttribute("typing...");
        HtmlSubmitInput submitButton = (HtmlSubmitInput) form.getInputByName("submit");
        HtmlPage resultPage = (HtmlPage) submitButton.click();
        WebAssert.assertTitleEquals(resultPage, "Result");
    }

    @Test
    public void testFormAlert() throws IOException {
        this.webClient.setThrowExceptionOnScriptError(false);
        CollectingAlertHandler alertHandler = new CollectingAlertHandler();
        this.webClient.setAlertHandler(alertHandler);
        HtmlPage page = (HtmlPage) this.webClient.getPage("file:src/main/webapp/formtest.html");
        HtmlForm form = page.getFormByName("validated_form");
        HtmlSubmitInput submitButton = (HtmlSubmitInput) form.getInputByName("submit");
        HtmlPage resultPage = (HtmlPage) submitButton.click();
        Assert.assertEquals(resultPage.getTitleText(), page.getTitleText());
        Assert.assertEquals(resultPage, page);
        List<String> collectedAlerts = alertHandler.getCollectedAlerts();
        List<String> expectedAlerts = Collections.singletonList("Please enter a value.");
        Assert.assertEquals(expectedAlerts, collectedAlerts);
    }

    @Test
    public void testFormNoAlert() throws IOException {
        WebClient webClient = new WebClient();
        webClient.setThrowExceptionOnScriptError(false);
        CollectingAlertHandler alertHandler = new CollectingAlertHandler();
        webClient.setAlertHandler(alertHandler);
        HtmlPage page = (HtmlPage) webClient.getPage("file:src/main/webapp/formtest.html");
        HtmlForm form = page.getFormByName("validated_form");
        HtmlTextInput input = (HtmlTextInput) form.getInputByName("in_text");
        input.setValueAttribute("typing...");
        HtmlSubmitInput submitButton = (HtmlSubmitInput) form.getInputByName("submit");
        HtmlPage resultPage = (HtmlPage) submitButton.click();
        WebAssert.assertTitleEquals(resultPage, "Result");
        Assert.assertTrue("No alerts expected", alertHandler.getCollectedAlerts().isEmpty());
    }

}
