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
package com.manning.junitbook.ch18;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.hsqldb.HsqldbConnection;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractJpaDbUnitTestCaseV0 extends AbstractJpaTestCase {
  
  protected static HsqldbConnection dbunitConnection;
  
  // TODO: refactor
  protected static long id = 1;
  protected static long phoneId = 1;
  
  @BeforeClass
  public static void setupDbUnit() throws Exception {
    dbunitConnection = new HsqldbConnection(connection,null);    
  }

  @AfterClass
  public static void closeDbUnit() throws Exception {
    if ( dbunitConnection != null ) {
      // connection will be close by super class
//      dbunitConnection.close();
      dbunitConnection = null;
    }
  }
  
  public static IDataSet getDataSet(String name) throws Exception {
    InputStream inputStream = getInputStream(name);
    Reader reader = new InputStreamReader(inputStream);
    FlatXmlDataSet dataset = new FlatXmlDataSet(reader);
    return dataset;
  }

  private static InputStream getInputStream(String path) {
    InputStream inputStream = AbstractJpaDbUnitTestCaseV0.class.getResourceAsStream(path);
    assertNotNull("file " + path + " not found in classpath", inputStream );
    return inputStream;
  }
  
  public static IDataSet getReplacedDataSet(String name, long id) throws Exception {
    IDataSet originalDataSet = getDataSet(name);
    return getReplacedDataSet(originalDataSet, id);
  }
  
  public static IDataSet getReplacedDataSet(IDataSet originalDataSet, long id) throws Exception {
    ReplacementDataSet replacementDataSet = new ReplacementDataSet(originalDataSet);
    replacementDataSet.addReplacementObject("[NULL]", null);
    return replacementDataSet;
  }
  
  protected static IDataSet getActualDataset() throws Exception {
    IDataSet actualDataSet = dbunitConnection.createDataSet();
    return actualDataSet;
  }
  
  protected static IDataSet getStrippedDataset(IDataSet expectedDataSet) throws Exception {
    String[] tableNames = expectedDataSet.getTableNames();
    IDataSet strippedDataSet = dbunitConnection.createDataSet(tableNames);
    return strippedDataSet;
  }
    
  public static String toString(IDataSet dataSet) throws DataSetException, IOException {
    StringWriter writer = new StringWriter();
    try {
      if ( dataSet != null ) {
        FlatXmlDataSet.write(dataSet, writer);       
      } else {
        writer.write("null");
      }
      return writer.toString();
    } finally {
      writer.close();
    }
  }
  
}
