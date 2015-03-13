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
package com.manning.junitbook.appD.custom.model;

/**
 * This interface is used to declare the methods for every interceptor.
 * 
 * @version $Id: Interceptor.java 201 2009-02-15 19:18:09Z paranoid12 $
 */
public interface Interceptor
{
    /**
     * This method will be called before every test - we can implement our own logic in every implementation.
     */
    public void interceptBefore();

    /**
     * This method will be called after every test - we can implement our own logic in every implementation.
     */
    public void interceptAfter();
}
