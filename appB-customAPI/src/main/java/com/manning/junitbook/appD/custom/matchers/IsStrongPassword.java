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
package com.manning.junitbook.appD.custom.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

/**
 * A custom hamcrest matcher that matches to true if a java.lang.String is null or an empty literal.
 * 
 * @version $Id: IsStrongPassword.java 201 2009-02-15 19:18:09Z paranoid12 $
 */
public class IsStrongPassword
    extends TypeSafeMatcher<String>
{
    /**
     * Checks the condition of this matcher against the given parameter. In this case we check if the parameter is not
     * null and is not ''.
     * 
     * @param str parameter to evaluate
     * @return true if the parameter is not null and not an empty string, return false otherwise
     */
    @Override
    public boolean matchesSafely( String str )
    {
        return containsSpecialSymbol( str ) && containsDigit( str ) && str.length() >= 6;
    }

    /**
     * Helper method to check if the password contains a digit.
     * 
     * @param str the password
     * @return true if the password contains a digit, false otherwise
     */
    private boolean containsDigit( String str )
    {
        for ( char ch : str.toCharArray() )
        {
            if ( Character.isDigit( ch ) )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to check if the password contains a special symbol. Special symbols are considered one of the
     * following: !, ", #, $, %, &, ', (, ), *, +, -, ., /
     * 
     * @param str the password
     * @return true if the password contains a special symbol, false otherwise
     */
    private boolean containsSpecialSymbol( String str )
    {
        for ( char ch : str.toCharArray() )
        {
            if ( ( (int) ch ) <= 47 && ( (int) ch ) >= 33 )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Description to show when the matcher fails.
     */
    public void describeTo( Description description )
    {
        description.appendText( "string that contains a digit, a special character and is at least 6 symbols" );
    }
    
    /**
     * What is the relationship that we check for?
     * 
     * @return
     */
    protected String relationship()
    {
        return "containing";
    }

    /**
     * Another factory method to create a new instance of the IsEmptyOrNull matcher.
     * 
     * @return a new instance of the matcher
     */
    @Factory
    public static <T> Matcher<String> isStrongPassword()
    {
        return new IsStrongPassword();
    }

    /**
     * Another factory method to create a new instance of the IsEmptyOrNull matcher.
     * 
     * @return a new instance of the matcher
     */
    @Factory
    public static <T> Matcher<String> strongPassword()
    {
        return new IsStrongPassword();
    }
}
