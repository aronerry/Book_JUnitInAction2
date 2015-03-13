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

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.manning.junitbook.ch19.dao.UserDao;
import com.manning.junitbook.ch19.model.User;
import com.manning.junitbook.ch19.model.UserDto;
import com.mycila.testing.core.TestSetup;
import com.mycila.testing.plugin.jmock.Mock;
import com.mycila.testing.plugin.jmock.MockContext;

public class UserFacadeImplMycilaJMockTest {
  
  private UserFacadeImpl facade;
  
  @MockContext
  private Mockery context;
  
  @Mock
  private UserDao dao;
  
  @Before
  public void setFixtures() {
    facade = new UserFacadeImpl();
    TestSetup.setup(this);
    facade.setUserDao(dao);
  }

  @Test
  public void testGetUserById() {
    final int id = 666;
    final User user = newUserWithTelephones();
    context.checking(new Expectations() {{
      one(dao).getUserById(id);
      will(returnValue(user));
    }});
    UserDto dto = facade.getUserById(id);
    assertUser(dto);
    context.assertIsSatisfied();
  }

}
