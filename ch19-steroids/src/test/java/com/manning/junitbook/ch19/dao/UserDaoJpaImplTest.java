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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import com.manning.junitbook.ch19.model.User;

public class UserDaoJpaImplTest extends UnitilsJUnit4 {
  
  @JpaEntityManagerFactory(persistenceUnit="chapter-19")
  @PersistenceContext
  EntityManager em;
  
  private final UserDaoJpaImpl dao = new UserDaoJpaImpl();
  
  @Before
  public void prepareDao() {
    dao.setEntityManager(em);
  }
  
  @Test
  @DataSet("user-no-phone.xml")
  public void testGetUserById() throws Exception {
    long id = 1;
    User user = dao.getUserById(id);
    assertUser(user);
  }
  
  @Test
  @DataSet("user-no-phone.xml")
  public void testGetUserByIdUnknowId() throws Exception {
    long id = 2;
    User user = dao.getUserById(id);
    assertNull(user);
  }
  
  @Test
  @DataSet("user-no-phone.xml")
  public void testAddUser() throws Exception {
    User user = newUser();
    dao.addUser(user);
    long id = user.getId();
    assertTrue(id>0);
  }
  
  @Test
  // NOTE: ideally, we should start with a 1-user table and end up with an empty one.
  // But unitils would always pass the test in this situation (see http://jira.unitils.org/browse/UNI-123)
  //@DataSet("user-no-phone.xml")
  //@ExpectedDataSet("empty.xml")
  @DataSet("users-no-phone.xml")
  @ExpectedDataSet("user-no-phone.xml")
  public void testDeleteUser() throws Exception {
//    long id = 1;
    long id = 2;
    dao.deleteUser(id);
  }
  
  @Test
  @DataSet("user-with-telephone.xml")
  public void testGetUserByIdWithTelephone() throws Exception {
    long id = 1;
    User user = dao.getUserById(id);
    assertSame(em, dao.getEntityManager());
    clearContext();
    assertUserWithTelephone(user);
    startNewTransaction();
  }

  @Test
  @DataSet("user-with-telephone.xml")
  public void testAddUserWithTelephone() throws Exception {
    User user = newUserWithTelephone();
    dao.addUser(user);
    long id = user.getId();
    assertTrue(id>0);
  }
  
  @Test
  @DataSet("user-with-telephone.xml")
  @ExpectedDataSet("empty.xml")
  public void testDeleteUserWithTelephone() throws Exception {
    long id = 1;
    dao.deleteUser(id);
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
  @DataSet("user-no-phone.xml")
  public void testGetUserByIdUnknownId() throws Exception {
    getUserReturnsNullTest(666);
  }
  
  @Test
  @DataSet("user-with-telephones.xml")
  public void testGetUserByIdWithTelephones() throws Exception {
    long id = 1;
    User user = dao.getUserById(id);
    clearContext();
    assertUserWithTelephones(user);
    startNewTransaction();
  }
  
  private void getUserReturnsNullTest(int id) {
    User user = dao.getUserById(id);
    assertNull(user);
  }

  private void startNewTransaction() {
    em.getTransaction().begin();
  }

  private void clearContext() {
    em.getTransaction().commit();
    em.clear();
  }
  
}
