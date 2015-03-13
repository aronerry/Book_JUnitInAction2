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
package com.manning.junitbook.ch19.business;

import static com.manning.junitbook.ch19.model.EntitiesHelper.*;
import static org.easymock.EasyMock.*;

import org.fest.mocks.EasyMockTemplate;
import org.junit.Before;
import org.junit.Test;

import com.manning.junitbook.ch19.dao.UserDao;
import com.manning.junitbook.ch19.model.User;
import com.manning.junitbook.ch19.model.UserDto;

public class UserFacadeImplFESTTest {
  
  private UserFacadeImpl facade;
  private UserDao dao;
  
  @Before
  public void setFixtures() {
    facade = new UserFacadeImpl();
    dao = createMock(UserDao.class);
    facade.setUserDao(dao);
  }

  @Test
  public void testGetUserById() {
    final int id = 666;
    final User user = newUserWithTelephones();
    new EasyMockTemplate(dao) {
      @Override
      protected void expectations() throws Throwable {
        expect(dao.getUserById(id)).andReturn(user);
      }
      @Override
      protected void codeToTest() throws Throwable {
        UserDto dto = facade.getUserById(id);
        assertUser(dto);
      }
    }.run();
//    verify(); // not necessary - FEST automatically does it (by default)
  }

}
