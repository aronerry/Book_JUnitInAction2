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

package com.manning.junitbook.ch13.selenium;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * Tests a form. The Selenium server must be managed elsewhere.
 * <p>
 * Maven will not pick up this test because it does not start or end with "Test"
 * or end with "TestCase".
 * </p>
 * <p>
 * To test the form in IE, create a virtual directory in IIS and point your
 * browser and tests to http://localhost/webapp/formtest.html
 * </p>
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id$
 */
public class UnmanagedFormTester {
    private static final String APP_WINDOW = "selenium.browserbot.getCurrentWindow()";

    private static final String EXPECTED_MSG = "Hello World";

    /**
     * The directory /ch13-ajax/src/main/webapp has been configured as an IIS
     * virtual directory for this test.
     */
    private static final String TEST_URL = "http://localhost/webapp/";

    private static final String TEST_PAGE = "formtest.html";

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
    public void testFormBad() throws IOException {
        selenium.open(TEST_PAGE);
        selenium.click("name=getMsgBtn");
        String actualMsg = selenium.getText("name=serverMessage");
        // The message is not there!
        Assert.assertFalse(EXPECTED_MSG.equals(actualMsg));
    }

    @Test
    public void testFormBad2() throws IOException, InterruptedException {
        selenium.open(TEST_PAGE);
        selenium.click("name=getMsgBtn");
        Thread.sleep(2000);
        String actualMsg = selenium.getText("name=serverMessage");
        // The message is not there!
        Assert.assertFalse(EXPECTED_MSG.equals(actualMsg));
    }

    @Test
    public void testFormWithJavaScript() throws IOException {
        selenium.open(TEST_PAGE);
        selenium.click("name=getMsgBtn");
        selenium.waitForCondition(APP_WINDOW + ".document.helloForm.serverMessage.value=='" + EXPECTED_MSG + "'", "1000");
    }

}
