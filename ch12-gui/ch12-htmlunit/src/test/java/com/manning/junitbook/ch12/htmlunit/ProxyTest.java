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

import junit.framework.Assert;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Demonstrates using a proxy server.
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id: ProxyTest.java 586 2010-04-12 16:29:20Z garydgregory $
 */
public class ProxyTest extends ManagedWebClient {

    @Test
    public void testGetHomePageThroughProxy() throws Exception {
        this.webClient.setProxyConfig(new ProxyConfig("forcethru.info", 80));
        // Set up proxy user name and password
        DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) this.webClient.getCredentialsProvider();
        credentialsProvider.addProxyCredentials("username", "password");
        // Get a page
        HtmlPage page = this.webClient.getPage("http://www.google.com/");
        Assert.assertNotNull(page.getTitleText());
        // Navigation through free proxies can be tricky as pages can change drastically from the original as ads are inserted...
        // Assert.assertEquals("", page.getTitleText());
    }
}
