/* 
 * ========================================================================
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * ========================================================================
 */
package com.manning.junitbook.appD.custom.test;

import org.junit.Test;

import static com.manning.junitbook.appD.custom.matchers.IsNotNullOrEmpty.isNotNullOrEmpty;
import static com.manning.junitbook.appD.custom.matchers.IsNotNullOrEmpty.notNullOrEmpty;
import static org.junit.Assert.assertThat;

import static org.hamcrest.core.Is.is;

/**
 * This test-case is used to demonstrate the hamcrest matcher that we tested.
 * 
 * @version $Id: TestStringIsNullUsingMatcher.java 201 2009-02-15 19:18:09Z paranoid12 $
 */
public class TestStringIsNullUsingMatcher
{
    @Test(expected=java.lang.AssertionError.class)
    public void testIsNotNullOrEmptyButIsNull()
    {
        String str = null;

        assertThat( str, isNotNullOrEmpty() );
        assertThat( str, is( notNullOrEmpty() ) );
    }

    @Test(expected=java.lang.AssertionError.class)
    public void testIsNotNullOrEmptyButIsEmpty()
    {
        String str = "";

        assertThat( str, isNotNullOrEmpty() );
        assertThat( str, is( notNullOrEmpty() ) );
    }

    @Test
    public void testIsNotNullOrEmptyIsNotNull()
    {
        String str = "test";
        assertThat( str, isNotNullOrEmpty() );
        assertThat( str, is( notNullOrEmpty() ) );
    }
}
