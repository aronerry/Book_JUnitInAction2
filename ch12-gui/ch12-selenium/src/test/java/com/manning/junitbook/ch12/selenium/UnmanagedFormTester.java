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

import java.io.IOException;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

/**
 * Tests a form. The Selenium server must be managed elsewhere.
 * <p>
 * Maven will pick up this test so it must be excluded in pom.xml.
 * </p>
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id$
 */
public class UnmanagedFormTester {
    /*
     * The test URL will be accessed from the Selenium server home directory, so
     * we specify an absolute URL to find our project resource.
     */
    private static final String TEST_URL = new java.io.File("").toURI() + "src/main/webapp/formtest.html";

    private static Selenium selenium;

    @BeforeClass
    public static void setUpOnce() throws Exception {
        selenium = new DefaultSelenium("localhost", 4444, "*iexplore", TEST_URL);
        selenium.start();
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
        if (selenium != null) {
            selenium.stop();
        }
        selenium = null;
    }

    @Test
    public void testForm() throws IOException {
        selenium.open(TEST_URL);
        selenium.type("id=in_text", "typing...");
        selenium.click("id=submit");
        SeleneseTestCase.assertEquals("Result", selenium.getTitle());
    }

    @Test
    public void testFormAlert() throws IOException {
        selenium.open(TEST_URL);
        String title = selenium.getTitle();
        selenium.click("id=submit");
        SeleneseTestCase.assertEquals("Please enter a value.", selenium.getAlert());
        SeleneseTestCase.assertEquals(title, selenium.getTitle());
    }

    @Test
    public void testFormAlertNotRead() throws IOException {
        selenium.open(TEST_URL);
        String title = selenium.getTitle();
        selenium.click("id=submit");
        // Do NOT call getAlert()
        try {
            selenium.open(TEST_URL);
            Assert.fail("Expected a " + SeleniumException.class.getName());
        } catch (SeleniumException e) {
            SeleneseTestCase.assertEquals("ERROR: There was an unexpected Alert! [Please enter a value.]", e.getMessage());
            SeleneseTestCase.assertEquals(title, selenium.getTitle());
        }
    }

    @Test
    public void testFormNoAlert() throws IOException {
        selenium.open(TEST_URL);
        selenium.type("id=in_text", "typing...");
        selenium.click("id=submit");
        try {
            selenium.getAlert();
            Assert.fail("Expected a " + SeleniumException.class.getName());
        } catch (SeleniumException e) {
            SeleneseTestCase.assertEquals("ERROR: There were no alerts", e.getMessage());
        }
        SeleneseTestCase.assertEquals("Result", selenium.getTitle());
    }

}
