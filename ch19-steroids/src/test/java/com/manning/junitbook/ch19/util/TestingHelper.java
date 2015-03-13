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

import java.lang.reflect.Field;

public class TestingHelper {

  private TestingHelper() {
    throw new UnsupportedOperationException("this class only provides static methods");
  }
  
  private static Field getField(final Class<?> clazz, String fieldName) {
    Class<?> tmpClass = clazz;
    do { 
      for ( Field field : tmpClass.getDeclaredFields() ) {
        String candidateName = field.getName();
        if ( ! candidateName.equals(fieldName) ) {
          continue;
        }
        field.setAccessible(true);
        return field;
      }
      tmpClass = tmpClass.getSuperclass();
    } while ( tmpClass != null );
    throw new RuntimeException("Field '" + fieldName + "' not found on class "  + clazz);
  }

  /**
   * Sets an object's field, using reflection.
   * @param object object to set the field 
   * @param fieldName name of the field
   * @param newValue new value of the field
   */
  public static void set( Object object, String fieldName, Object newValue ) {
    Field field = getField(object.getClass(), fieldName);
    try {
      field.set(object, newValue);
    } catch (IllegalAccessException e) {
      throw new RuntimeException( "Could not set value of field '" + fieldName + "' on object "  + object + " to " + newValue, e );
    }
  }
  
  /**
   * Gets the value of object's field, using reflection.
   * @param <T> type returned 
   * @param object object to get the field from
   * @param fieldName name of the field
   * @return value of the field
   */
  public static <T> T get(Object object, String fieldName) {
    Field field = getField(object.getClass(), fieldName);
    Object value;
    try {
      value = field.get(object);
    } catch (IllegalAccessException e) {
      throw new RuntimeException( "Could not get value of field '" + fieldName + "' from object "  + object, e );
    }
    @SuppressWarnings("unchecked")
    T castValue = (T) value;
    return castValue;
  }

}
