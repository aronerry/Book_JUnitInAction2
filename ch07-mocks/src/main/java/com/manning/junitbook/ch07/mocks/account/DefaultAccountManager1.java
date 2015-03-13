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

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Default account manager implementation before refactoring.
 * 
 * @version $Id: DefaultAccountManager1.java 503 2009-08-16 17:47:12Z paranoid12 $
 */
public class DefaultAccountManager1
    implements AccountManager
{
    /**
     * Logger instance.
     */
    private static final Log LOGGER = LogFactory.getLog( DefaultAccountManager1.class );

    /**
     * Finds an account for user with the given userID.
     * 
     * @param
     */
    public Account findAccountForUser( String userId )
    {
        LOGGER.debug( "Getting account for user [" + userId + "]" );
        ResourceBundle bundle = PropertyResourceBundle.getBundle( "technical" );
        String sql = bundle.getString( "FIND_ACCOUNT_FOR_USER" );

        // Some code logic to load a user account using JDBC
        // […]
        return null;
    }

    /**
     * Updates the given account.
     * 
     * @param
     */
    public void updateAccount( Account account )
    {
        // Perform database access here
    }
}
