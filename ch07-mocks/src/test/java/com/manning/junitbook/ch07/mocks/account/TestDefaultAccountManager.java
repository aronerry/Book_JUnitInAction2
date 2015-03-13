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
package com.manning.junitbook.ch07.mocks.account;

import org.junit.Test;

import com.manning.junitbook.ch07.mocks.configurations.MockConfiguration;

/**
 * A test-case for the DefaultAccountManager class.
 * 
 * @version $Id: TestDefaultAccountManager.java 505 2009-08-16 17:58:38Z paranoid12 $
 */
public class TestDefaultAccountManager
{

    @Test
    public void testFindAccountByUser()
    {
        MockLog logger = new MockLog();
        MockConfiguration configuration = new MockConfiguration();
        configuration.setSQL( "SELECT * [...]" );
        DefaultAccountManager2 am = new DefaultAccountManager2( logger, configuration );
        
        @SuppressWarnings("unused")
        Account account = am.findAccountForUser( "1234" );

        // Perform asserts here
        // […]
    }
}
