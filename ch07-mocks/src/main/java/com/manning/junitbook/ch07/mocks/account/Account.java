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

/**
 * Account POJO to hold the bank account object.
 * 
 * @version $Id: Account.java 503 2009-08-16 17:47:12Z paranoid12 $
 */
public class Account
{
    /**
     * The id of the account.
     */
    private String accountId;

    /**
     * The balance of the account.
     */
    private long balance;

    /**
     * A constructor.
     * 
     * @param accountId
     * @param initialBalance
     */
    public Account( String accountId, long initialBalance )
    {
        this.accountId = accountId;
        this.balance = initialBalance;
    }

    /**
     * Withdraw the amount from the account.
     * 
     * @param amount
     */
    public void debit( long amount )
    {
        this.balance -= amount;
    }

    /**
     * Add the amount of money in the account.
     * 
     * @param amount
     */
    public void credit( long amount )
    {
        this.balance += amount;
    }

    /**
     * What's the balance of the account?
     * 
     * @return
     */
    public long getBalance()
    {
        return this.balance;
    }
}
