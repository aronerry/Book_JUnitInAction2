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
package com.manning.junitbook.ch19.model;

import static org.junit.Assert.*;

import java.util.List;

import com.manning.junitbook.ch19.model.Telephone.Type;

public final class EntitiesHelper {
  
  public static final String USER_FIRST_NAME = "Jeffrey";
  public static final String USER_LAST_NAME = "Lebowsky";
  public static final String USER_USERNAME = "ElDuderino";
  public static final String PHONE_HOME_NUMBER = "481 516-2342";
  public static final String PHONE_MOBILE_NUMBER = "108 555-6666";

  private EntitiesHelper() {
    throw new UnsupportedOperationException("this class is a helper");
  }
  
  public static void assertUser(User user) {
    assertNotNull(user);
    assertEquals(USER_FIRST_NAME, user.getFirstName());
    assertEquals(USER_LAST_NAME, user.getLastName());
    assertEquals(USER_USERNAME, user.getUsername());
  }
  
  public static void assertUserWithTelephone(User user) {
    assertUserWithTelephones(user, 1);
  }
  
  public static void assertUserWithTelephones(User user) {
    assertUserWithTelephones(user, 2);
  }
  
  public static void assertUserWithTelephones(User user, int size) {
    assertUser(user);
    List<Telephone> telephones = user.getTelephones();
    assertEquals( size, telephones.size() );
    Telephone homePhone = telephones.get(0);
    assertEquals( Type.HOME, homePhone.getType() );
    assertEquals( PHONE_HOME_NUMBER, homePhone.getNumber() );
    if ( size == 2 ) {
      Telephone mobilePhone = telephones.get(1);
      assertEquals( Type.MOBILE, mobilePhone.getType() );
      assertEquals( PHONE_MOBILE_NUMBER, mobilePhone.getNumber() );
    }
  }
  
  public static void assertUser(UserDto user) {
    assertNotNull(user);
    assertEquals(USER_FIRST_NAME, user.getFirstName());
    assertEquals(USER_LAST_NAME, user.getLastName());
    assertEquals(USER_USERNAME, user.getUsername());
    List<String> telephones = user.getTelephones();
    assertEquals( 2, telephones.size() );
    String homePhone = telephones.get(0);
    String mobilePhone = telephones.get(1);
    assertTrue( "home phone does not contain type", homePhone.contains(Type.HOME.toString()) );
    assertTrue( "home phone does not contain number", homePhone.contains(PHONE_HOME_NUMBER) );
    assertTrue( "mobile phone does not contain type", mobilePhone.contains(Type.MOBILE.toString()) );
    assertTrue( "mobile phone does not contain number", mobilePhone.contains(PHONE_MOBILE_NUMBER) );
  }
  
  public static User newUser() {
    User user = new User();
    user.setFirstName(USER_FIRST_NAME);
    user.setLastName(USER_LAST_NAME);
    user.setUsername(USER_USERNAME);
    return user;
  }

  public static User newUserWithTelephone() {
    return newUserWithTelephones(1);
  }

  public static User newUserWithTelephones() {
    return newUserWithTelephones(2);
  }
  
  public static User newUserWithTelephones(int size) {
    User user = newUser();
    Telephone homeNumber = new Telephone();
    homeNumber.setType(Type.HOME);
    homeNumber.setNumber(PHONE_HOME_NUMBER);
    List<Telephone> telephones = user.getTelephones();
    telephones.add(homeNumber);
    if ( size == 2 ) {
      Telephone mobileNumber = new Telephone();
      mobileNumber.setType(Type.MOBILE);
      mobileNumber.setNumber(PHONE_MOBILE_NUMBER);
      telephones.add(mobileNumber);
    }
    return user;
  }

}
