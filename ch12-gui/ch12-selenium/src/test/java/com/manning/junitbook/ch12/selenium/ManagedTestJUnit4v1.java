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

package com.manning.junitbook.ch12.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;

/**
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id$
 */
public class ManagedTestJUnit4v1 {
    private static Selenium selenium;

    @BeforeClass
    public static void setUpOnce() throws Exception {
        selenium = new DefaultSelenium("localhost", 4444, "*iexplore", "http://www.google.com/");
        selenium.start();
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
        if (selenium != null) {
            selenium.stop();
        }
        selenium = null;
    }

    private void captureScreenshot(Throwable t) throws Throwable {
        if (selenium != null) {
            String filename = this.getClass().getName() + ".png";
            try {
                selenium.captureScreenshot(filename);
                System.err.println("Saved screenshot " + filename + " for " + t.toString());
            } catch (Exception e) {
                System.err.println("Exception saving screenshot " + filename + " for " + t.toString() + ": " + e.toString());
                e.printStackTrace();
            }
            throw t;
        }
    }

    public void testSearch() {
        selenium.open("/");
        SeleneseTestCase.assertEquals("Google", selenium.getTitle());
        selenium.type("q", "Manning Publishing Co.");
        selenium.click("btnG");
        selenium.waitForPageToLoad("30000");
        SeleneseTestCase.assertEquals("Manning Publishing Co. - Google Search", selenium.getTitle());
        selenium.click("link=Manning Publications Co.");
        selenium.waitForPageToLoad("30000");
        SeleneseTestCase.assertEquals("Manning Publications Co.", selenium.getTitle());
    }

    @Test
    public void testSearchOnErrSaveScreen() throws Throwable {
        try {
            this.testSearch();
        } catch (Throwable t) {
            this.captureScreenshot(t);
        }
    }
}
