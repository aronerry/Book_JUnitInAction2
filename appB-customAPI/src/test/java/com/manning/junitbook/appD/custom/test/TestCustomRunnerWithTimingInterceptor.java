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
 * A sample test-case to demonstrate the timing interceptor.
 * 
 * @version $Id: TestCustomRunnerWithTimingInterceptor.java 201 2009-02-15 19:18:09Z paranoid12 $
 */
@RunWith( InterceptorRunner.class )
@InterceptorClasses( SampleTimingInterceptor.class )
public class TestCustomRunnerWithTimingInterceptor
{
    @Before
    public void longSetUp() throws InterruptedException
    {
        Thread.sleep( 1000 );
    }

    @Test
    public void testDummy() throws InterruptedException
    {
        Thread.sleep( 2000 );
        assertTrue( true );
    }

    @After
    public void longTearDown() throws InterruptedException
    {
        Thread.sleep( 1000 );
    }
}
