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

import org.junit.Test;

import com.thoughtworks.selenium.SeleneseTestCase;

/**
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id$
 */
public class ManagedTestJUnit4v2 extends ManagedSeleniumServer {

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
