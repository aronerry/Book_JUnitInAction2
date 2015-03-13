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

import java.util.ArrayList;
import java.util.List;

import org.junit.runners.model.Statement;

/**
 * A statement for our custom runner. 
 * @version $Id: InterceptorStatement.java 201 2009-02-15 19:18:09Z paranoid12 $
 */
public class InterceptorStatement
    extends Statement
{
    /**
     * A wrapping invoker that will procede the execution, once we execute our interceptors.
     */
    private final Statement invoker;

    /**
     * A list of interceptors that will be executed before the other statements.
     */
    private List<Interceptor> interceptors = new ArrayList<Interceptor>();

    /**
     * A constructor that takes another invoker to wrap our statement.
     * 
     * @param invoker
     */
    public InterceptorStatement( Statement invoker )
    {
        this.invoker = invoker;
    }

    /**
     * We override this method to call our interceptors, and then evaluate the wrapping invoker.
     */
    @Override
    public void evaluate()
        throws Throwable
    {
        for ( Interceptor interceptor : interceptors )
        {
            interceptor.interceptBefore();
        }

        invoker.evaluate();

        for ( Interceptor interceptor : interceptors )
        {
            interceptor.interceptAfter();
        }
    }

    /**
     * Add another interceptor to the list of interceptors we have.
     * 
     * @param interceptor we want to add
     */
    public void addInterceptor( Interceptor interceptor )
    {
        interceptors.add( interceptor );
    }
}
