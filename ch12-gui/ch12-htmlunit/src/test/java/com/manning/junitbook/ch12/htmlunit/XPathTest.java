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
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Demonstrates testing with XPath.
 * 
 * It turns out that packages and classes on
 * http://java.sun.com/javase/6/docs/api/index.html are not in alphabetical
 * order because of the way Javadoc sorts the underscore character.
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id: XPathTest.java 580 2010-04-11 02:41:57Z garydgregory $
 */
public class XPathTest extends ManagedWebClient {

    private static final String USCORE = "_";

    private HtmlPage mainPage;

    private void assertAlphabeticalOrder(List<DomNode> nodes) {
        if (nodes.size() <= 0) {
            return;
        }
        DomNode prevNode = nodes.get(0);
        for (DomNode currNode : nodes) {
            final String prevText = prevNode.getTextContent();
            final String currText = currNode.getTextContent();
            // Compare ignoring case.
            boolean inOrder = prevText.toLowerCase().compareTo(currText.toLowerCase()) <= 0;
            if (!inOrder && (prevText.contains(USCORE) || currText.contains(USCORE))) {
                // We know _ are trouble, ignore for the purpose of this demo.
                continue;
            }
            Assert.assertTrue("previous=" + prevText + ", current=" + currText, inOrder);
            prevNode = currNode;
        }
    }

    /**
     * Make logging pretty quiet, do not log warnings.
     */
    @BeforeClass
    public static void setUpLogging() {
        Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.SEVERE);
    }

    @Before
    public void initWebClient() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        this.webClient.setThrowExceptionOnScriptError(false);
        this.mainPage = (HtmlPage) this.webClient.getPage("http://java.sun.com/javase/6/docs/api/index.html");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAllClassesList() {
        HtmlPage classListPage = (HtmlPage) this.mainPage.getFrameByName("packageFrame").getEnclosedPage();
        List<DomNode> classes = (List<DomNode>) classListPage.getByXPath("//a");
        this.assertAlphabeticalOrder(classes);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAllPackagesList() {
        HtmlPage packageListPage = (HtmlPage) this.mainPage.getFrameByName("packageListFrame").getEnclosedPage();
        List<DomNode> anchors = (List<DomNode>) packageListPage
                .getByXPath("//a[contains(@href, 'package-frame.html') and @target='packageFrame']");
        this.assertAlphabeticalOrder(anchors);
    }
}
