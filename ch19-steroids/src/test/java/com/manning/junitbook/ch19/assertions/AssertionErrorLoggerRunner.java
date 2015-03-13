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

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * Customer TestRunner that logs expected {@link AssertionError} exceptions.
 * 
 * Useful to demonstrate the different messages produced by different assertion utilities.
 * 
 * @author felipeal
 *
 */
public class AssertionErrorLoggerRunner extends BlockJUnit4ClassRunner {

  public AssertionErrorLoggerRunner(Class<?> klass) throws InitializationError {
    super(klass);
  }
  @Override
  protected Statement methodInvoker(FrameworkMethod method, Object test) {
    return new ExceptionCatcherInvoker(super.methodInvoker(method, test),
        method.getName());
  }
  
  static class ExceptionCatcherInvoker extends Statement {
    private final Statement invoker;
    private final String methodName;
    public ExceptionCatcherInvoker(Statement invoker, String methodName) {
      this.invoker = invoker;
      this.methodName = methodName;
    }
    @Override
    public void evaluate() throws Throwable {
      try {
        invoker.evaluate();
        throw new RuntimeException(methodName + " should have failed");
      } catch (AssertionError e) {
        System.out.println(methodName + ": " + e);
      }
    }
  }

}
