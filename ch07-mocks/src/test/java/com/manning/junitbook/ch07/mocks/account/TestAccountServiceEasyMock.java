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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A test-case to test the AccountService class by means of the EasyMock framework.
 * 
 * @version $Id: TestAccountServiceEasyMock.java 505 2009-08-16 17:58:38Z paranoid12 $
 */
public class TestAccountServiceEasyMock
{
    private AccountManager mockAccountManager;

    @Before
    public void setUp()
    {
        mockAccountManager = createMock("mockAccountManager", AccountManager.class );
    }

    @Test
    public void testTransferOk()
    {
        Account senderAccount = new Account( "1", 200 );
        Account beneficiaryAccount = new Account( "2", 100 );

        mockAccountManager.updateAccount( senderAccount );
        mockAccountManager.updateAccount( beneficiaryAccount );

        expect( mockAccountManager.findAccountForUser( "1" ) ).andReturn( senderAccount );
        expect( mockAccountManager.findAccountForUser( "2" ) ).andReturn( beneficiaryAccount );
        replay( mockAccountManager );

        AccountService accountService = new AccountService();
        accountService.setAccountManager( mockAccountManager );
        accountService.transfer( "1", "2", 50 );

        assertEquals( 150, senderAccount.getBalance() );
        assertEquals( 150, beneficiaryAccount.getBalance() );
    }

    @After
    public void tearDown()
    {
        verify( mockAccountManager );
    }
}
