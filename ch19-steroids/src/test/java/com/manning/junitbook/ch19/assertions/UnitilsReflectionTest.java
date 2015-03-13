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
package com.manning.junitbook.ch19.assertions;

import static org.unitils.reflectionassert.ReflectionComparatorMode.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.reflectionassert.ReflectionAssert;

import com.manning.junitbook.ch19.model.EntitiesHelper;
import com.manning.junitbook.ch19.model.User;

@RunWith(AssertionErrorLoggerRunner.class)
public class UnitilsReflectionTest {
  
  @Test
  public void testSameUser() {
    User user1 = EntitiesHelper.newUser();
    User user2 = EntitiesHelper.newUser();
    ReflectionAssert.assertLenientEquals(user1, user2);
    // JUnit assertion would fail
    Assert.assertEquals(user1, user2);
  }

  // used only to show differences
  @Test
  public void testDifferentUser() {
    User user1 = EntitiesHelper.newUser();
    user1.setUsername(null);
    User user2 = EntitiesHelper.newUser();
//    ReflectionAssert.assertReflectionEquals(user1, user2);
//    ReflectionAssert.assertReflectionEquals(user1, user2, IGNORE_DEFAULTS);
    ReflectionAssert.assertReflectionEquals(user2, user1, IGNORE_DEFAULTS);
  }

  @Test
  public void testAllPropertiesEquals() {
    ReflectionAssert.assertLenientEquals(allFields, allFields);
    Assert.fail("Did not fail because all properties are equal");
  }
  
  @Test
  public void testNullProperty() {
    ReflectionAssert.assertReflectionEquals(noLastName, allFields);
  }
  
  @Test
  public void testNullPropertyOk() {
    ReflectionAssert.assertReflectionEquals(noLastName, allFields,IGNORE_DEFAULTS);
    Assert.fail("Did not fail because one property is null");
  }
  
  @Test
  public void testDifferentDates() {
    ReflectionAssert.assertReflectionEquals(allFields,otherBirthday);
    Assert.fail("Should fail, but it doesn't due to a Unitls bug (to be filed by authors)");
  }
  
  @Test
  public void testDifferentDatesOk() {
    ReflectionAssert.assertReflectionEquals(allFields,otherBirthday,LENIENT_DATES);
    Assert.fail("Should fail, but it doesn't due to a Unitls bug (to be filed by authors)");
  }
  
  @Test
  public void testOneFieldOnly() {
    ReflectionAssert.assertPropertyReflectionEquals("firstName", "dude", allFields);
  }
  
  @Test
  public void testOneFieldOnlyManyObjects() {
    String lastName = "Lebowsky";
    List<String> expected = Arrays.asList(null, lastName, lastName, lastName);
    ReflectionAssert.assertPropertyLenientEquals("lastName", expected, population );
//    ReflectionAssert.assertPropertyReflectionEquals("lastName", expected, population );
//    Date epoch = new Date(0);
//    List<Date> expected = Arrays.asList(epoch, epoch, epoch, epoch);
//    ReflectionAssert.assertPropertyReflectionEquals("birthday", expected, population );
  }
  
  final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
  
  private class Person {
    String firstName;
    String lastName;
    Date birthday;
    Person(String firstName, String lastName, String birthday) {
      this.firstName = firstName;
      this.lastName = lastName;
      if ( birthday != null ) {
        try {
          this.birthday = dateFormat.parse(birthday);
        } catch (ParseException e) {
          Assert.fail("Invalid date: " + birthday);
        }
      }
    }
  }

  Person allFields = new Person("Dude", "Lebowsky", "01-01-1970");
  Person noLastName = new Person("Dude", null, "01-01-1970");
  Person otherBirthday = new Person("Dude", "Lebowsky", "11-11-1999");
  Person otherBirthdayAndNoLastName = new Person("Dude", null, "11-11-1999");
  List<Person> population = Arrays.asList(allFields, noLastName, otherBirthday, otherBirthdayAndNoLastName);

}
