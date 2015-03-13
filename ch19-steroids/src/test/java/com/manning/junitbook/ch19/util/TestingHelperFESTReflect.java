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

package com.manning.junitbook.ch19.util;

import org.fest.reflect.core.Reflection;

public class TestingHelperFESTReflect {

  private TestingHelperFESTReflect() {
    throw new UnsupportedOperationException("this class only provides static methods");
  }
  
  /**
   * Sets an object's field, using reflection.
   * @param object object to set the field 
   * @param fieldName name of the field
   * @param newValue new value of the field
   */
  public static <T> void set( Object object, String fieldName, Class<T> fieldClass, T newValue ) {
    Reflection.field(fieldName).ofType(fieldClass).in(object).set(newValue);
  }

  /**
   * Gets the value of object's field, using reflection.
   * @param <T> type returned 
   * @param object object to get the field from
   * @param fieldName name of the field
   * @return value of the field
   */
  public static <T> T get(Object object, String fieldName, Class<T> fieldClass) {
    return Reflection.field(fieldName).ofType(fieldClass).in(object).get();
  }

}
