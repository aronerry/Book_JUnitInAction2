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
package com.manning.junitbook.ch19.dao;

import static com.manning.junitbook.ch19.model.EntitiesHelper.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.database.annotations.TestDataSource;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;

import com.manning.junitbook.ch19.model.User;

//@RunWith(UnitilsJUnit4TestClassRunner.class)
//public class UserDaoJdbcImplTest {
public class UserDaoJdbcImplTest extends UnitilsJUnit4 {
  
  private UserDaoJdbcImpl dao = new UserDaoJdbcImpl();
  
  @TestDataSource
  @SuppressWarnings("unused") // used by Unitils
  private void setDataSource(DataSource ds) throws SQLException {
    Connection connection = ds.getConnection();
    dao.setConnection(connection);
    dao.createTables();
  }

  @Test
  @DataSet("user.xml")
  public void testGetUserById() throws Exception {
    long id = 1;
    User user = dao.getUserById(id);
    assertUser(user);
  }
  
  @Test
  @DataSet("user.xml")
  public void testGetUserByIdUnknowId() throws Exception {
    long id = 2;
    User user = dao.getUserById(id);
    assertNull(user);
  }

  @Test
  @ExpectedDataSet("user.xml")
  public void testAddUser() throws Exception {
    User user = newUser();
    dao.addUser(user);
    long id = user.getId();
    assertTrue(id>0);
  }
  
  @Test
  // NOTE: ideally, we should start with a 1-user table and end up with an empty one.
  // But unitils would always pass the test in this situation (see http://jira.unitils.org/browse/UNI-123)
//  @DataSet("user.xml")
//  @ExpectedDataSet("empty.xml")
  @DataSet("users.xml")
  @ExpectedDataSet("user.xml")
  public void testDeleteUser() throws Exception {
//    long id = 1;
    long id = 2;
    dao.deleteUser(id);
  }
  
}
