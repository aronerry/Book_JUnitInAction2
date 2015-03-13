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
package com.manning.junitbook.ch17.dbunit;

import static com.manning.junitbook.ch17.dbunit.EntitiesHelper.*;
import static org.junit.Assert.*;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Ignore;
import org.junit.Test;

public class UserDaoJdbcImplTest extends AbstractDbUnitTestCase {
  
  @Test
  public void testGetUserById() throws Exception {
    IDataSet setupDataSet = getDataSet("/user.xml");
    DatabaseOperation.CLEAN_INSERT.execute(dbunitConnection, setupDataSet);
    User user = dao.getUserById(1);
    assertNotNull( user);
    assertEquals( "Jeffrey", user.getFirstName() );
    assertEquals( "Lebowsky", user.getLastName() );
    assertEquals( "ElDuderino", user.getUsername() );
  }
  
  @Test @Ignore("fails if run together with others")
  public void testAddUser() throws Exception {
    User user = newUser();
    long id = dao.addUser(user);
    assertTrue(id>0);
    IDataSet expectedDataSet = getDataSet("/user.xml");
    IDataSet actualDataSet = dbunitConnection.createDataSet();
    Assertion.assertEquals( expectedDataSet, actualDataSet );
  }
  
  @Test
  public void testAddUseIgnoringId() throws Exception {
    IDataSet setupDataSet = getDataSet("/user.xml");
    DatabaseOperation.DELETE_ALL.execute(dbunitConnection, setupDataSet);
    User user = newUser();
    long id = dao.addUser(user);
    assertTrue(id>0);
    IDataSet expectedDataSet = getDataSet("/user.xml");
    IDataSet actualDataSet = dbunitConnection.createDataSet();
    Assertion.assertEqualsIgnoreCols( expectedDataSet, actualDataSet, "users", new String[] { "id" } );
  }
  
  @Test
  public void testGetUserByIdReplacingIds() throws Exception {
    long id = 42;
    IDataSet setupDataset = getReplacedDataSet("/user-token.xml", id );
    DatabaseOperation.INSERT.execute(dbunitConnection, setupDataset);
    User user = dao.getUserById(id);
    assertUser(user);
  }
  
  @Test
  public void testAddUserReplacingIds() throws Exception {
    IDataSet setupDataSet = getDataSet("/user-token.xml");
    DatabaseOperation.DELETE_ALL.execute(dbunitConnection, setupDataSet);
    User user = newUser();
    long id = dao.addUser(user);
    assertTrue(id>0);
    IDataSet expectedDataSet = getReplacedDataSet(setupDataSet, id );
    IDataSet actualDataSet = dbunitConnection.createDataSet();
    Assertion.assertEquals( expectedDataSet, actualDataSet );
  }
  
}
