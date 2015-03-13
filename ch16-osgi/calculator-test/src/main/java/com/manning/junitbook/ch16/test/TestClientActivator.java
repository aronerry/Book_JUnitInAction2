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
package com.manning.junitbook.ch16.test;

import org.apache.felix.ipojo.junit4osgi.OSGiTestCase;
import org.osgi.framework.ServiceReference;

import com.manning.junitbook.ch16.service.CalculatorService;

/**
 * A test-case for the client application.
 * 
 * @version $Id: TestClientActivator.java 603 2010-04-27 20:21:31Z paranoid12 $
 */
public class TestClientActivator
    extends OSGiTestCase
{

    public void testServiceAvailability()
    {
        ServiceReference ref = context.getServiceReference( CalculatorService.class.getName() );
        assertNotNull( "Assert Service Availability", ref );
    }

    public void testParseUserCorrectInput()
    {
        ServiceReference ref = context.getServiceReference( CalculatorService.class.getName() );
        assertNotNull( "Assert Availability", ref );
        CalculatorService cs = (CalculatorService) context.getService( ref );
        double[] result = cs.parseUserInput( "11.5 12.2 13.7" );
        assertNotNull( "Result must not be null", result );
        assertEquals( "Result must be 11.5", 11.5, result[0], 0.1 );
        assertEquals( "Result must be 12.2", 12.2, result[1], 0.1 );
        assertEquals( "Result must be 13.7", 13.7, result[2], 0.1 );
    }

    public void testParseUserInCorrectInput()
    {
        ServiceReference ref = context.getServiceReference( CalculatorService.class.getName() );
        assertNotNull( "Assert Availability", ref );
        CalculatorService cs = (CalculatorService) context.getService( ref );
        try
        {
            double[] result = cs.parseUserInput( "THIS IS A RANDOM STRING TO TEST EXCEPTION HANDLING" );

            fail( "A NumberFormatException was supposed to be thorown but was not" );
        }
        catch ( NumberFormatException nex )
        {
            assertTrue( true ); // this is the normal execution flow
        }
    }

    public void testAddMethod()
    {
        assertTrue( "Check availability of the service", isServiceAvailable( CalculatorService.class.getName() ) );
        CalculatorService cs = (CalculatorService) getServiceObject( CalculatorService.class.getName(), null );
        double[] numbers = cs.parseUserInput( "1.2 2.4" );
        assertNotNull( "Result from parseUserInput must not be null", numbers );
        double result = cs.add( numbers );
        assertNotNull( "Result from add must not be null", result );
        assertEquals( "Result must be 3.6", 3.6, result, 0.1 );
    }
    
    public void testMultiplyMethod()
    {
        assertTrue( "Check availability of the service", isServiceAvailable( CalculatorService.class.getName() ) );
        CalculatorService cs = (CalculatorService) getServiceObject( CalculatorService.class.getName(), null );
        double[] numbers = cs.parseUserInput( "1.2 2.4" );
        assertNotNull( "Result from parseUserInput must not be null", numbers );
        double result = cs.multiply( numbers );
        assertNotNull( "Result from multiply must not be null", result );
        assertEquals( "Result must be 2.88", 2.88, result, 0.1 );
    }
}
