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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.CollectingAlertHandler;
import com.gargoylesoftware.htmlunit.ConfirmHandler;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.MockWebConnection;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Demonstrates testing a confirmation handler.
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id: WindowConfirmTest.java 410 2009-05-20 21:37:55Z garydgregory $
 */
public class WindowConfirmTest extends ManagedWebClient {

    @Test
    public void testWindowConfirm() throws FailingHttpStatusCodeException, IOException {
        String html = "<html><head><title>Hello</title></head><body onload='confirm(\"Confirm Message\")'></body></html>";
        URL testUrl = new URL("http://Page1/");
        WebClient webClient = new WebClient();
        MockWebConnection mockConnection = new MockWebConnection();
        final List<String> confirmMessages = new ArrayList<String>();
        // set up
        webClient.setConfirmHandler(new ConfirmHandler() {
            public boolean handleConfirm(Page page, String message) {
                confirmMessages.add(message);
                return true;
            }
        });
        mockConnection.setResponse(testUrl, html);
        webClient.setWebConnection(mockConnection);
        // go
        HtmlPage firstPage = webClient.getPage(testUrl);
        Assert.assertEquals("Hello", firstPage.getTitleText());
        Assert.assertArrayEquals(new String[] { "Confirm Message" }, confirmMessages.toArray());
    }

    @Test
    public void testWindowConfirmAndAlert() throws FailingHttpStatusCodeException, IOException {
        String html = "<html><head><title>Hello</title><script>function go(){alert(confirm('Confirm Message'))}</script>\n"
                + "</head><body onload='go()'></body></html>";
        URL testUrl = new URL("http://Page1/");
        WebClient webClient = new WebClient();
        MockWebConnection mockConnection = new MockWebConnection();
        final List<String> confirmMessages = new ArrayList<String>();
        // set up
        webClient.setAlertHandler(new CollectingAlertHandler());
        webClient.setConfirmHandler(new ConfirmHandler() {
            public boolean handleConfirm(Page page, String message) {
                confirmMessages.add(message);
                return true;
            }
        });
        mockConnection.setResponse(testUrl, html);
        webClient.setWebConnection(mockConnection);
        // go
        HtmlPage firstPage = webClient.getPage(testUrl);
        Assert.assertEquals("Hello", firstPage.getTitleText());
        Assert.assertArrayEquals(new String[] { "Confirm Message" }, confirmMessages.toArray());
        Assert.assertArrayEquals(new String[] { "true" }, ((CollectingAlertHandler) webClient.getAlertHandler()).getCollectedAlerts()
                .toArray());
    }

}
