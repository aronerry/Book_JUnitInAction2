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

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * A custom hamcrest matcher that checks whether a string is null ot empty.
 * 
 * @version $Id: IsNotNullOrEmpty.java 201 2009-02-15 19:18:09Z paranoid12 $
 */
public class IsNotNullOrEmpty
    extends BaseMatcher<String>
{

    public boolean matches( Object string )
    {
        String str = (String) string;
        return ( str != null ) && !str.equals( "" );
    }

    public void describeTo( Description description )
    {
        description.appendText( "a string that is not null and not empty" );
    }

    @Factory
    public static <T> Matcher<String> isNotNullOrEmpty()
    {
        return new IsNotNullOrEmpty();
    }

    @Factory
    public static <T> Matcher<String> notNullOrEmpty()
    {
        return new IsNotNullOrEmpty();
    }

}
