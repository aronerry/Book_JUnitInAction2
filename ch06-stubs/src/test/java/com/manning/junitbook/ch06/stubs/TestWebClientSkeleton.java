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
package com.manning.junitbook.ch06.stubs;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * The first stub-skeleton of a test.
 * 
 * @version $Id$
 */
public class TestWebClientSkeleton
{

    @Before
    public void setUp()
    {
        // Start Jetty and configure it to return "It works" when
        // the http://localhost:8080/testGetContentOk URL is
        // called.
    }

    @After
    public void tearDown()
    {
        // Stop Jetty.
    }

    @Test
    @Ignore( value = "This is just initial skeleton of a test. " + "Therefore if we run it now it will fail." )
    public void testGetContentOk()
        throws Exception
    {
        WebClient client = new WebClient();
        String result = client.getContent( new URL( "http://localhost:8080/testGetContentOk" ) );

        assertEquals( "It works", result );
    }
}
