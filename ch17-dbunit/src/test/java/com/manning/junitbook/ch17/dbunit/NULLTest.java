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
package com.manning.junitbook.ch17.dbunit;

import org.dbunit.Assertion;
import static org.junit.Assert.*;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.SortedDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Ignore;
import org.junit.Test;

public class NULLTest extends AbstractDbUnitTestCase {

  @Ignore("this is exactly the situation where the problem arises")
  @Test
  public void testNULLIssue() throws Exception {
    IDataSet okDataSet = getDataSet("/user-ok.xml");
    DatabaseOperation.CLEAN_INSERT.execute(dbunitConnection, okDataSet);
    User user = dao.getUserById(2);
    assertNull(user.getFirstName());
    assertNull(user.getLastName());
    IDataSet actualDataSet = dbunitConnection.createDataSet();
    Assertion.assertEquals(okDataSet, actualDataSet);
    IDataSet revertedDataSet = getDataSet("/user-reverted.xml");
//    IDataSet revertedDataSet = getDataSet("/user-DTD.xml");
    IDataSet sortedDataSet = new SortedDataSet(revertedDataSet);
    Assertion.assertEquals(sortedDataSet, actualDataSet);
  }
  
  @Test
  public void testNULLReplacementDataset() throws Exception {
    IDataSet okDataSet = getDataSet("/user-ok.xml");
    DatabaseOperation.CLEAN_INSERT.execute(dbunitConnection, okDataSet);
    User user = dao.getUserById(2);
    assertNull(user.getFirstName());
    assertNull(user.getLastName());
    IDataSet actualDataSet = dbunitConnection.createDataSet();
    Assertion.assertEquals( okDataSet, actualDataSet );
    IDataSet revertedDataSet = getReplacedDataSet("/user-replacement.xml", -1);
    IDataSet sortedDataSet = new SortedDataSet(revertedDataSet);
    Assertion.assertEquals(sortedDataSet, actualDataSet);
  }
  
  @Test
  public void testNULLDTD() throws Exception {
    IDataSet okDataSet = getDataSet("/user-ok.xml");
    DatabaseOperation.CLEAN_INSERT.execute(dbunitConnection, okDataSet);
    User user = dao.getUserById(2);
    assertNull(user.getFirstName());
    assertNull(user.getLastName());
    IDataSet actualDataSet = dbunitConnection.createDataSet();
    Assertion.assertEquals( okDataSet, actualDataSet );
    IDataSet revertedDataSet = getDataSet("/user-DTD.xml");
    IDataSet sortedDataSet = new SortedDataSet(revertedDataSet);
    Assertion.assertEquals(sortedDataSet, actualDataSet);
  }
  
}
