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

import org.apache.commons.logging.Log;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.manning.junitbook.ch07.mocks.configurations.Configuration;

/**
 * This is another test-case for the DefaultAccountManager class. We use here the Jmock library to mock the logger and
 * the configuration.
 * 
 * @version $Id: TestDefaultAccountManagerJMock.java 508 2009-08-16 18:05:18Z paranoid12 $
 */
@RunWith( JMock.class )
public class TestDefaultAccountManagerJMock
{
    Mockery context = new JUnit4Mockery();

    private Configuration configuration;

    private Log logger;

    @Before
    public void setUp()
    {
        configuration = context.mock( Configuration.class );
        logger = context.mock( Log.class );
    }

    @Test
    public void testFindAccountByUser()
    {
        context.checking( new Expectations()
        {
            {
                oneOf (logger).debug("Getting account for user [1234]");
                
                oneOf (configuration).getSQL( "FIND_ACCOUNT_FOR_USER" );
                will( returnValue( "SELECT ..." ) );
            }
        } );
        
        DefaultAccountManager2 am = new DefaultAccountManager2( logger, configuration );
        @SuppressWarnings("unused")
        Account account = am.findAccountForUser( "1234" );

        // Perform asserts here
        // […]
    }
}
