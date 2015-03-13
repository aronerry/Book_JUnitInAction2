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
package com.manning.junitbook.ch16.service.test;

import junit.framework.TestCase;

import com.manning.junitbook.ch16.service.CalculatorImpl;

/**
 * JUnit 3.8 test for the CalculatorService. We are going to include this test in the build process, so it will be
 * executed with the build. We are also going to include the test-case in the bundle so you can start it in from the
 * Felix command line.
 */
public class CalculatorServiceTest
    extends TestCase
{

    /**
     * CalculatorImpl service implementation that we want to test.
     */
    private CalculatorImpl calc = null;

    public void setUp()
    {
        calc = new CalculatorImpl();
    }

    public void testAdd()
    {
        assertEquals( "1+2 must equal 3.0", 3.0, calc.add( calc.parseUserInput( "1 2" ) ), 0 );
    }
}
