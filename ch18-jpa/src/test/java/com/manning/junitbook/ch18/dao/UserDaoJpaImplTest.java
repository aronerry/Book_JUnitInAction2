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
package com.manning.junitbook.ch18.dao;

import static com.manning.junitbook.ch18.model.EntitiesHelper.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.manning.junitbook.ch18.AbstractJpaDbUnitELTemplateTestCaseJUnit44;
import com.manning.junitbook.ch18.DataSets;
import com.manning.junitbook.ch18.ELFunctionMapperImpl;
import com.manning.junitbook.ch18.model.User;

public class UserDaoJpaImplTest extends AbstractJpaDbUnitELTemplateTestCaseJUnit44 {
  
  UserDaoJpaImpl dao;
  
  @Before
  public void prepareDao() {
    dao = new UserDaoJpaImpl();
    dao.setEntityManager(em);
  }
  
  @Test
  @DataSets(setUpDataSet="/user.xml")
  public void testGetUserById() throws Exception {
    beginTransaction();
    long id = ELFunctionMapperImpl.getId(User.class);
    User user = dao.getUserById(id);
    commitTransaction();
    assertUser(user);
  }
  
  @Test
  @DataSets(setUpDataSet="/user.xml")
  public void testGetUserByIdUnknowId() throws Exception {
    beginTransaction();
    long id = ELFunctionMapperImpl.getId(User.class)*2;
    User user = dao.getUserById(id);
    commitTransaction();
    assertNull(user);
  }
  
  @Test
  @DataSets(assertDataSet="/user.xml")
  public void testAddUser() throws Exception {
    beginTransaction();
    User user = newUser();
    dao.addUser(user);
    commitTransaction();
    long id = user.getId();
    assertTrue(id>0);
  }
  
  @Test
  @DataSets(setUpDataSet="/user.xml",assertDataSet="/empty.xml")
  public void testDeleteUser() throws Exception {
    beginTransaction();
    long id = ELFunctionMapperImpl.getId(User.class);
    dao.deleteUser(id);
    commitTransaction();
  }
  
  @Test
  @DataSets(setUpDataSet="/user-with-telephone.xml")
  public void testGetUserByIdWithTelephone() throws Exception {
    beginTransaction();
    long id = ELFunctionMapperImpl.getId(User.class);
    User user = dao.getUserById(id);
//    commitTransaction();
    commitTransaction(true);
    assertUserWithTelephone(user);
  }
  
  @Test
  @DataSets(assertDataSet="/user-with-telephone.xml")
  public void testAddUserWithTelephone() throws Exception {
    beginTransaction();
    User user = newUserWithTelephone();
    dao.addUser(user);
    commitTransaction();
    long id = user.getId();
    assertTrue(id>0);
  }
  
  @Test
  @DataSets(setUpDataSet="/user-with-telephone.xml",assertDataSet="/empty.xml")
  public void testDeleteUserWithTelephone() throws Exception {
    beginTransaction();
    long id = ELFunctionMapperImpl.getId(User.class);
    dao.deleteUser(id);
    commitTransaction();
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void testAddNullUser() throws Exception {
    dao.addUser(null);
  }
  
  @Test
  public void testGetUserByIdOnNullDatabase() throws Exception {
    getUserReturnsNullTest(0);
  }
  
  @Test
  @DataSets(setUpDataSet="/user.xml")
  public void testGetUserByIdUnknownId() throws Exception {
    getUserReturnsNullTest(666);
  }
  
  private void getUserReturnsNullTest(int deltaId) {
    beginTransaction();
    long id = ELFunctionMapperImpl.getId(User.class)+deltaId;
    User user = dao.getUserById(id);
    commitTransaction(true);
    assertNull(user);
  }

  @Test
  @DataSets(setUpDataSet="/user-with-telephones.xml")
  public void testGetUserByIdWithTelephones() throws Exception {
    beginTransaction();
    long id = ELFunctionMapperImpl.getId(User.class);
    User user = dao.getUserById(id);
    commitTransaction(true);
    assertUserWithTelephones(user);
  }
  
}
