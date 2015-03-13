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
import static org.unitils.easymock.EasyMockUnitils.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.easymock.annotation.Mock;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;

import com.manning.junitbook.ch19.dao.UserDao;
import com.manning.junitbook.ch19.model.User;
import com.manning.junitbook.ch19.model.UserDto;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class UserFacadeImplUnitilsTest {
//public class UserFacadeImplUnitilsTest extends UnitilsJUnit4 {
  
  @TestedObject
  private UserFacadeImpl facade;
  
  @Mock
  @InjectIntoByType
  private UserDao dao;

  @Test
  public void testGetUserById() {
    int id = 666;
    User user = newUserWithTelephones();
    expect(dao.getUserById(id)).andReturn(user);
    replay(); // this is Unitils' replay
    UserDto dto = facade.getUserById(id);
    assertUser(dto);
//    verify(); // not necessary - Unitils automatically does it (by default)
  }

}
