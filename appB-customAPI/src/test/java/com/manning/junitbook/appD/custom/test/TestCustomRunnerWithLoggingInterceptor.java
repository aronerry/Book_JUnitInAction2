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

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.manning.junitbook.appD.custom.runners.InterceptorRunner;
import com.manning.junitbook.appD.custom.runners.InterceptorRunner.InterceptorClasses;

/**
 * A test-case to test our custom InterceptorRunner.
 * 
 * @version $Id: TestCustomRunnerWithLoggingInterceptor.java 201 2009-02-15 19:18:09Z paranoid12 $
 */
@RunWith( InterceptorRunner.class )
@InterceptorClasses( value = { SampleLoggingInterceptor.class } )
public class TestCustomRunnerWithLoggingInterceptor
{
    @Before
    public void setUp()
    {
        System.out.println( "Real before" );
    }

    @Test
    public void testDummy()
    {
        assertTrue( true );
        System.out.println( "Some text for test purpose" );

    }

    @After
    public void tearDown()
    {
        System.out.println( "Real after" );
    }
}
