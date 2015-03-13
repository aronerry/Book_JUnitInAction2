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
import static org.junit.Assert.assertThat;

import static org.hamcrest.core.Is.is;
import static com.manning.junitbook.appD.custom.matchers.IsStrongPassword.isStrongPassword;
import static com.manning.junitbook.appD.custom.matchers.IsStrongPassword.strongPassword;

/**
 * A test-case for the custom hamcrest matcher we made.
 * 
 * @version $Id: TestIsPasswordStrongUsingMatcher.java 201 2009-02-15 19:18:09Z paranoid12 $
 */
public class TestIsPasswordStrongUsingMatcher
{
    @Test
    public void testIsPasswordStrongIsStrong()
    {
        final String pass = "!abcde0";

        assertThat( pass, isStrongPassword() );
        assertThat( pass, is( strongPassword() ) );
    }

    @Test( expected = java.lang.AssertionError.class )
    public void testIsPasswordStrongTooShort()
    {
        final String shortPass = "abcde";

        assertThat( shortPass, isStrongPassword() );
        assertThat( shortPass, is( strongPassword() ) );
    }

    @Test( expected = java.lang.AssertionError.class )
    public void testIsPasswordStrongContainsNoSpecialCharacter()
    {
        final String noSpecialCharacterPass = "abcdef0";

        assertThat( noSpecialCharacterPass, isStrongPassword() );
        assertThat( noSpecialCharacterPass, is( strongPassword() ) );
    }

    @Test( expected = java.lang.AssertionError.class )
    public void testIsPasswordStrongContainsNoDigit()
    {
        final String noDigitPass = "abcdef!";

        assertThat( noDigitPass, isStrongPassword() );
        assertThat( noDigitPass, is( strongPassword() ) );
    }
    
    @Test( expected = java.lang.AssertionError.class )
    public void testIsPasswordStrongIsNull()
    {
        final String nullPass = null;

        assertThat( nullPass, isStrongPassword() );
        assertThat( nullPass, is( strongPassword() ) );
    }
}
