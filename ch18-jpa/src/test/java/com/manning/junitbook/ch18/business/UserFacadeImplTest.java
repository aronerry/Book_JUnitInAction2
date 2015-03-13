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
package com.manning.junitbook.ch18.business;

import static org.easymock.EasyMock.*;
import static com.manning.junitbook.ch18.model.EntitiesHelper.*;
import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.manning.junitbook.ch18.dao.UserDao;
import com.manning.junitbook.ch18.model.User;
import com.manning.junitbook.ch18.model.UserDto;

public class UserFacadeImplTest {
  
  private UserFacadeImpl facade;
  private UserDao dao;

  @Before
  public void setFixtures() {
    facade = new UserFacadeImpl();
    dao = createMock(UserDao.class);
    facade.setUserDao(dao);
  }
  
  @Test
  public void testGetUserByIdUnkownId() {
    int id = 666;
    expect(dao.getUserById(id)).andReturn(null);
    replay(dao);
    UserDto dto = facade.getUserById(id);
    assertNull(dto);
//    verify(dao);   // not necessary
  }

  @Test
  public void testGetUserById() {
    int id = 666;
    User user = newUserWithTelephones();
    expect(dao.getUserById(id)).andReturn(user);
    replay(dao);
    UserDto dto = facade.getUserById(id);
    assertUser(dto);
//    verify(dao);   // not necessary
  }
  
}
