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
package com.manning.junitbook.ch16.client.mock;

import static org.junit.Assert.assertEquals;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.manning.junitbook.ch16.client.ClientBundleActivator;
import com.manning.junitbook.ch16.service.CalculatorImpl;
import com.manning.junitbook.ch16.service.CalculatorService;

/**
 * A test-case to test the Calculator service using the JMock framework.
 * 
 * @version $Id: TestClientCalculatorServiceMock.java 537 2009-08-17 09:06:49Z paranoid12 $
 */
public class TestClientCalculatorServiceMock
{

    /**
     * The mockery context that we use to create our mocks.
     */
    private Mockery context = new JUnit4Mockery();

    /**
     * The mock instance of the AccountManager to use.
     */
    private BundleContext mockBundleContext;

    /**
     * The mock service reference.
     */
    private ServiceReference mockServiceReference;

    @Before
    public void setUp()
    {
        mockBundleContext = context.mock( BundleContext.class );
        mockServiceReference = context.mock( ServiceReference.class );
        final CalculatorImpl service = new CalculatorImpl();

        context.checking( new Expectations()
        {
            {
                oneOf( mockBundleContext ).getServiceReference( CalculatorService.class.getName() );
                will( returnValue( mockServiceReference ) );

                oneOf( mockBundleContext ).getService( mockServiceReference );
                will( returnValue( service ) );

                oneOf( mockBundleContext ).ungetService( mockServiceReference );
            }
        } );
    }

    @Test
    public void testAddMethod()
        throws Exception
    {
        ClientBundleActivator activator = new ClientBundleActivator();
        activator.setOperation( "add" );
        activator.setUserNumberInput( "1 2 3 4 5 6 7 8 9" );
        activator.start( mockBundleContext );
        assertEquals( "The result is not the same as expected", activator.getResult(), 45, 0 );
    }

    @Test
    public void testMultiplyMethod()
        throws Exception
    {
        ClientBundleActivator activator = new ClientBundleActivator();

        activator.setOperation( "multiply" );
        activator.setUserNumberInput( "1 2 3 4 5 6 7 8 9" );
        activator.start( mockBundleContext );
        assertEquals( "The result is not the same as expected", activator.getResult(), 362880, 0 );
    }
}
