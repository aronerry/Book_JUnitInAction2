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

import junit.framework.Assert;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Demonstrates testing frames.
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id: FrameTest.java 410 2009-05-20 21:37:55Z garydgregory $
 */
public class FrameTest extends ManagedWebClient {

    @Test
    public void testFramesByIndices() throws IOException {
        this.webClient.setThrowExceptionOnScriptError(false);
        HtmlPage mainPage = (HtmlPage) this.webClient.getPage("http://java.sun.com/javase/6/docs/api/index.html");

        // get page of the first Frame (upper left)
        HtmlPage packageListPage = (HtmlPage) mainPage.getFrames().get(0).getEnclosedPage();
        packageListPage.getAnchors().get(5).click();

        // get page of the Frame named 'packageFrame' (lower left)
        HtmlPage pakcagePage = (HtmlPage) mainPage.getFrameByName("packageFrame").getEnclosedPage();
        pakcagePage.getAnchors().get(1).click();

        // get page of the Frame named 'classFrame' (right)
        HtmlPage classPage = (HtmlPage) mainPage.getFrameByName("classFrame").getEnclosedPage();
        Assert.assertNotNull(classPage);
    }

    @Test
    public void testFramesByNames() throws IOException {
        this.webClient.setThrowExceptionOnScriptError(false);
        HtmlPage mainPage = (HtmlPage) this.webClient.getPage("http://java.sun.com/javase/6/docs/api/index.html");

        // Gets page of the first Frame (upper left)
        HtmlPage packageListPage = (HtmlPage) mainPage.getFrameByName("packageListFrame").getEnclosedPage();
        packageListPage.getAnchorByHref("java/lang/package-frame.html").click();

        // get page of the Frame named 'packageFrame' (lower left)
        HtmlPage packagePage = (HtmlPage) mainPage.getFrameByName("packageFrame").getEnclosedPage();
        packagePage.getAnchors().get(1).click();

        // get page of the Frame named 'classFrame' (right)
        HtmlPage classPage = (HtmlPage) mainPage.getFrameByName("classFrame").getEnclosedPage();
        Assert.assertNotNull(classPage);
    }
}
