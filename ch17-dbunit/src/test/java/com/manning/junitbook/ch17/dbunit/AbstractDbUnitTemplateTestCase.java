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

import java.lang.reflect.Method;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class AbstractDbUnitTemplateTestCase extends AbstractDbUnitTestCase {

  protected static long id;
  
  public static class DataSetsTemplateRunner extends BlockJUnit4ClassRunner {

    public DataSetsTemplateRunner(Class<?> klass) throws InitializationError {
      super(klass);
    }
    
    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
      return new AssertDataSetStatement(super.methodInvoker(method, test), method);
    }
        
    private class AssertDataSetStatement extends Statement {

      private final Statement invoker;
      private FrameworkMethod method;

      public AssertDataSetStatement(Statement invoker, FrameworkMethod method) {
        this.invoker = invoker;
        this.method = method;
      }

      @Override
      public void evaluate() throws Throwable {
        setupDataSet(method);
        invoker.evaluate();
        assertDataSet(method);
      }
      
    }

    private void setupDataSet(FrameworkMethod method) {
      DataSets dataSetAnnotation = getAnnotation(method);
      String dataSetName = dataSetAnnotation.setUpDataSet();
      if ( ! dataSetName.equals("") ) {
        try {
          IDataSet dataSet = getReplacedDataSet(dataSetName, id);
          DatabaseOperation.CLEAN_INSERT.execute(dbunitConnection, dataSet);
        } catch (Exception e) {
          throw new RuntimeException( "exception inserting dataset " + dataSetName, e );
        }
      }
    } 
    
    private void assertDataSet(FrameworkMethod method) {
      DataSets dataSetAnnotation = getAnnotation(method);
      String dataSetName = dataSetAnnotation.assertDataSet();
      if ( ! dataSetName.equals("") ) {
        try {
          IDataSet expectedDataSet = getReplacedDataSet(dataSetName, id );
          IDataSet actualDataSet = dbunitConnection.createDataSet();
          Assertion.assertEquals( expectedDataSet, actualDataSet );
        } catch (Exception e) {
          throw new RuntimeException( "exception inserting dataset " + dataSetName, e );
        }
      }
    }
    
    private DataSets getAnnotation(FrameworkMethod method) {
      Method javaMethod = method.getMethod();
      return javaMethod.getAnnotation(DataSets.class);
    }
    
  }
}
