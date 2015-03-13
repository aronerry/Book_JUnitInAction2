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
package com.manning.junitbook.appD.custom.runners;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import com.manning.junitbook.appD.custom.model.Interceptor;
import com.manning.junitbook.appD.custom.model.InterceptorStatement;

/**
 * A custom runner for JUnit4.5 in which we demonstrate the interceptor pattern.
 * 
 * @version $Id: InterceptorRunner.java 201 2009-02-15 19:18:09Z paranoid12 $
 */
public class InterceptorRunner
    extends BlockJUnit4ClassRunner
{
    /**
     * This is the InterceptorClasses annotation, which serves to hold our interceptor class implementations.
     */
    @Retention( RetentionPolicy.RUNTIME )
    @Target( ElementType.TYPE )
    public @interface InterceptorClasses
    {
        /**
         * @return the classes to be run
         */
        public Class<?>[] value();
    }

    /**
     * This constructor is a must.
     * 
     * @param clazz the test-case class
     */
    public InterceptorRunner( Class<?> clazz )
        throws InitializationError
    {
        super( clazz );
    }

    /**
     * Override the methodInvoker, so that when it is called we wrap the statement with our own.
     * 
     * @param method the test method
     * @param test the test-case
     */
    @Override
    public Statement methodInvoker( FrameworkMethod method, Object test )
    {
        InterceptorStatement statement = new InterceptorStatement( super.methodInvoker( method, test ) );
        InterceptorClasses annotation = test.getClass().getAnnotation( InterceptorClasses.class );
        Class<?>[] klasez = annotation.value();
        try
        {
            for ( Class<?> klaz : klasez )
            {

                statement.addInterceptor( (Interceptor) klaz.newInstance() );

            }
        }
        catch ( IllegalAccessException ilex )
        {
            ilex.printStackTrace();
        }
        catch ( InstantiationException e )
        {
            e.printStackTrace();
        }
        return statement;
    }
}
