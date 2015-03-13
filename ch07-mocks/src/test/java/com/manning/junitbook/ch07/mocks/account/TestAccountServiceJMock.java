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

import static org.junit.Assert.assertEquals;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * A test-case to test the AccountService class by means of the JMock framework.
 * 
 * @version $Id: TestAccountServiceJMock.java 505 2009-08-16 17:58:38Z paranoid12 $
 */
@RunWith( JMock.class )
public class TestAccountServiceJMock
{
    /**
     * The mockery context that we use to create our mocks.
     */
    private Mockery context = new JUnit4Mockery();

    /**
     * The mock instance of the AccountManager to use.
     */
    private AccountManager mockAccountManager;

    @Before
    public void setUp()
    {
        mockAccountManager = context.mock( AccountManager.class );
    }

    @Test
    public void testTransferOk()
    {
        final Account senderAccount = new Account( "1", 200 );

        final Account beneficiaryAccount = new Account( "2", 100 );

        context.checking( new Expectations()
        {
            {
                oneOf( mockAccountManager ).findAccountForUser( "1" );
                will( returnValue( senderAccount ) );
                oneOf( mockAccountManager ).findAccountForUser( "2" );
                will( returnValue( beneficiaryAccount ) );

                oneOf( mockAccountManager ).updateAccount( senderAccount );
                oneOf( mockAccountManager ).updateAccount( beneficiaryAccount );
            }
        } );

        AccountService accountService = new AccountService();
        accountService.setAccountManager( mockAccountManager );
        accountService.transfer( "1", "2", 50 );

        assertEquals( 150, senderAccount.getBalance() );
        assertEquals( 150, beneficiaryAccount.getBalance() );
    }
}
