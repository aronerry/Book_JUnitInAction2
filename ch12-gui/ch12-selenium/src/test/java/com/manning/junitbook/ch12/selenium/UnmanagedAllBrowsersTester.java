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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;

/**
 * Test methods are run for IE and FF. The Selenium server is not managed from
 * this class.
 * <p>
 * Maven will pick up this test so it must be excluded in pom.xml.
 * </p>
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id$
 */
@RunWith(value = Parameterized.class)
public class UnmanagedAllBrowsersTester {
    private static Map<String, Selenium> SeleniumMap;

    @Parameters
    public static Collection<String[]> getBrowsers() {
        return Arrays.asList(new String[][] { { "*iexplore" }, { "*firefox" } });
    }

    private static Selenium getSelenium(String key) {
        Selenium s = getSeleniumMap().get(key);
        if (s != null) {
            return s;
        }
        stopDrivers(); // only let one driver run
        s = new DefaultSelenium("localhost", 4444, key, "http://www.google.com/");
        getSeleniumMap().put(key, s);
        s.start();
        return s;
    }

    private static Map<String, Selenium> getSeleniumMap() {
        if (SeleniumMap == null) {
            SeleniumMap = new HashMap<String, Selenium>();
        }
        return SeleniumMap;
    }

    @AfterClass
    public static void stopDrivers() {
        // System.out.println("@AfterClass");
        for (Selenium s : getSeleniumMap().values()) {
            s.stop();
        }
        SeleniumMap = null;
    }

    private String browserStartCommand;

    private Selenium selenium;

    public UnmanagedAllBrowsersTester(String browserStartCommand) {
        System.out.println("new AllBrowsersTest for " + browserStartCommand);
        this.browserStartCommand = browserStartCommand;
    }

    @Before
    public void setUp() throws Exception {
        this.selenium = getSelenium(this.browserStartCommand);
    }

    @Test
    public void testGG() {
        this.selenium.open("http://www.garygregory.com/");
        SeleneseTestCase.assertEquals("Gary Gregory", this.selenium.getTitle());
    }

    @Test
    public void testGoogleSearch() {
        this.selenium.open("/");
        SeleneseTestCase.assertEquals("Google", this.selenium.getTitle());
        this.selenium.type("q", "Manning Publishing Co.");
        this.selenium.click("btnG");
        this.selenium.waitForPageToLoad("30000");
        SeleneseTestCase.assertEquals("Manning Publishing Co. - Google Search", this.selenium.getTitle());
        this.selenium.click("link=Manning Publications Co.");
        this.selenium.waitForPageToLoad("30000");
        SeleneseTestCase.assertEquals("Manning Publications Co.", this.selenium.getTitle());
    }
}
