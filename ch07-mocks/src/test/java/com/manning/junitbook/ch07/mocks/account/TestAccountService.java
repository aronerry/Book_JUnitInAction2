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
import org.junit.Test;

/**
 * A test-case to test the AccountService class. 
 * We use the custom mock AccountManager that we 
 * implemented.
 * 
 * @version $Id: TestAccountService.java 505 2009-08-16 17:58:38Z paranoid12 $
 */
public class TestAccountService
{
    @Test
    public void testTransferOk()
    {
        MockAccountManager mockAccountManager = new MockAccountManager();
        Account senderAccount = new Account( "1", 200 );
        
        Account beneficiaryAccount = new Account( "2", 100 );
        mockAccountManager.addAccount( "1", senderAccount );
        mockAccountManager.addAccount( "2", beneficiaryAccount );
        
        AccountService accountService = new AccountService();
        accountService.setAccountManager( mockAccountManager );
        accountService.transfer( "1", "2", 50 );
        
        assertEquals( 150, senderAccount.getBalance() );
        assertEquals( 150, beneficiaryAccount.getBalance() );
    }
}
