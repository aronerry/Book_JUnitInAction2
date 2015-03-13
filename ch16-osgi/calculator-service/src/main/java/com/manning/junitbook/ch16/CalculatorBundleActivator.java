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
package com.manning.junitbook.ch16;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.manning.junitbook.ch16.service.CalculatorImpl;
import com.manning.junitbook.ch16.service.CalculatorService;

/**
 * Bundle activator for the Calculator service.
 * 
 * @version $Id: CalculatorBundleActivator.java 538 2009-08-17 09:08:14Z paranoid12 $
 */
public class CalculatorBundleActivator
    implements BundleActivator
{

    public void start( BundleContext bundleContext )
        throws Exception
    {
        System.out.println( "Starting calculator service ..." );
        bundleContext.registerService( CalculatorService.class.getName(), new CalculatorImpl(), null );
    }

    public void stop( BundleContext bundleContext )
        throws Exception
    {
        System.out.println( "Stopping calculator service ..." );
        // BLANK
    }
}
