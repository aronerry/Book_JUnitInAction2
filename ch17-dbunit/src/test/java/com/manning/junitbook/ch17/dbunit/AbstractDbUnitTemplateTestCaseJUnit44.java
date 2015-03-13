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

import static org.dbunit.Assertion.*;

import java.lang.reflect.Method;

import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunNotifier;

@SuppressWarnings("deprecation")
@RunWith(AbstractDbUnitTemplateTestCaseJUnit44.DataSetsTemplateRunner.class)
public abstract class AbstractDbUnitTemplateTestCaseJUnit44 extends AbstractDbUnitTestCase {
    
  protected static long id;
  
  public static class DataSetsTemplateRunner extends JUnit4ClassRunner {

    public DataSetsTemplateRunner(Class<?> klass) throws InitializationError {
      super(klass);
    }

    @Override
    protected void invokeTestMethod(Method method, RunNotifier notifier) {
      setupDataSet(method);
      super.invokeTestMethod(method, notifier);
      assertDataSet(method);
    }
    
    private void setupDataSet(Method method) {
      DataSets dataSetAnnotation = method.getAnnotation(DataSets.class);
      if ( dataSetAnnotation == null ) {
        return;
      }
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
 
    private void assertDataSet(Method method) {
      DataSets dataSetAnnotation = method.getAnnotation(DataSets.class);
      if ( dataSetAnnotation == null ) {
        return;
      }
      String dataSetName = dataSetAnnotation.assertDataSet();
      if ( ! dataSetName.equals("") ) {
        try {
          IDataSet expectedDataSet = getReplacedDataSet(dataSetName, id );
          IDataSet actualDataSet = dbunitConnection.createDataSet();
          assertEquals( expectedDataSet, actualDataSet );
        } catch (Exception e) {
          throw new RuntimeException( "exception asserting dataset " + dataSetName, e );
        }
      }
    }
  }
    
}
