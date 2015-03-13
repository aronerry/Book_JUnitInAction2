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

import java.util.List;

import com.manning.junitbook.ch18.dao.UserDao;
import com.manning.junitbook.ch18.model.Telephone;
import com.manning.junitbook.ch18.model.User;
import com.manning.junitbook.ch18.model.UserDto;

public class UserFacadeImpl implements UserFacade {

  private static final String TELEPHONE_STRING_FORMAT = "%s (%s)";
  private UserDao userDao;

  void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  UserDao getUserDao() {
    return userDao;
  }
  
  public UserDto getUserById(long id) {
    User user = userDao.getUserById(id);
    if ( user == null ) {
      return null;
    }
    UserDto dto = new UserDto();
    dto.setFirstName(user.getFirstName());
    dto.setLastName(user.getLastName());
    dto.setUsername(user.getUsername());
    List<String> telephoneDtos = dto.getTelephones();
    for ( Telephone telephone : user.getTelephones() ) {
      String telephoneDto = String.format(TELEPHONE_STRING_FORMAT, telephone.getNumber(), telephone.getType());
      telephoneDtos.add(telephoneDto);
    }
    return dto;
  }
  
}
