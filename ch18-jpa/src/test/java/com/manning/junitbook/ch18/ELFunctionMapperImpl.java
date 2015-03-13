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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.el.FunctionMapper;

public class ELFunctionMapperImpl extends FunctionMapper {
  
  private static final Map<String, Method> METHODS = new HashMap<String, Method>();
  
  private static final Map<String,Long> IDS = new HashMap<String, Long>();
  
  private static final int INITIAL_ID = 1;

  static { 
    for ( Method method : ELFunctionMapperImpl.class.getDeclaredMethods() ) {
      int modifiers = method.getModifiers();
      String name = method.getName();
      if ( Modifier.isStatic(modifiers) && name.startsWith("db_")) {
        METHODS.put(name.replace('_', ':'), method);
      }
    }
  }  

  @Override
  public Method resolveFunction(String prefix, String localName) {
    return METHODS.get(prefix+":"+localName);
  }
  
  public static long db_id(String className) {
    long id;
    if ( IDS.containsKey(className) ) {
      id = IDS.get(className);
    } else {
      id = INITIAL_ID;
    }
    return id;
  }
  
  public static void setId(String className, long newId) {
    // each class' id must be set only once per method
    if ( ! IDS.containsKey(className) ) {
      IDS.put(className, newId);
    }
  }

  public static void resetIds() {
    IDS.clear();
  }

  public static long getId(Class<?> clazz) {
    return db_id(clazz.getSimpleName());
  }

}
