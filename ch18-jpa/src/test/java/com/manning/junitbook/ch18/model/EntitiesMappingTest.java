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
package com.manning.junitbook.ch18.model;
import static com.manning.junitbook.ch18.model.EntitiesHelper.*;

import org.junit.Test;

import com.manning.junitbook.ch18.AbstractJpaDbUnitELTemplateTestCaseJUnit44;
import com.manning.junitbook.ch18.DataSets;
import com.manning.junitbook.ch18.ELFunctionMapperImpl;

public class EntitiesMappingTest extends AbstractJpaDbUnitELTemplateTestCaseJUnit44 {
  
  @Test
  @DataSets(setUpDataSet="/user-with-telephone.xml")
  public void testLoadUserWithTelephone() {
    beginTransaction();
    long id = ELFunctionMapperImpl.getId(User.class);
    User user = em.find(User.class, id);
    commitTransaction();
    assertUserWithTelephone(user);
  }

  @Test
  @DataSets(assertDataSet="/user-with-telephone.xml")
  public void testSaveUserWithTelephoneAgain() throws Exception {
    testSaveUserWithTelephone();
  }
  
  @Test
  @DataSets(setUpDataSet="/user-with-telephone.xml")
  public void testLoadUserWithTelephoneOneMoreTime() {
    testLoadUserWithTelephone();
  }
  
  @Test
  @DataSets(assertDataSet="/user-with-telephone.xml")
  public void testSaveUserWithTelephone() throws Exception {
    User user = newUserWithTelephone();
    beginTransaction();
    em.persist(user);
    commitTransaction();
  }
  
  @Test
  @DataSets(assertDataSet="/user-with-telephone.xml")
  public void testSaveUserWithTelephoneOneMoreTime() throws Exception {
    testSaveUserWithTelephone();
  }
  
}
