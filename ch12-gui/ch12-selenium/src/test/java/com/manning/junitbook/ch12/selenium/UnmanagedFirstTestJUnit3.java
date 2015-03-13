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

import com.thoughtworks.selenium.SeleneseTestCase;

/**
 * A Selenium-classic test based on JUnit 3 which requires that the Selenium
 * managed elsewhere.
 * <p>
 * Maven will pick up this test so it must be excluded in pom.xml.
 * </p>
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id$
 */
public class UnmanagedFirstTestJUnit3 extends SeleneseTestCase {
    @Override
    public void setUp() throws Exception {
        this.setUp("http://www.google.com/", "*iexplore");
    }

    public void testSearch() {
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
