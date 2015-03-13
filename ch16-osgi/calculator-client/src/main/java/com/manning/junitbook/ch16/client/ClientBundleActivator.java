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
package com.manning.junitbook.ch16.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.manning.junitbook.ch16.service.CalculatorService;

/**
 * A sample implementation of the BundleActivator for the client application.
 * 
 * @version $Id: ClientBundleActivator.java 541 2009-08-17 09:12:34Z paranoid12 $
 */
public class ClientBundleActivator
    implements BundleActivator
{

    private String operation = null;

    private String userNumberInput = null;
    
    private double result = 0;

    public void start( BundleContext context )
        throws Exception
    {
        if (operation==null || userNumberInput==null) {
            initUserInput();
        }
        
        ServiceReference reference = context.getServiceReference( CalculatorService.class.getName() );

        if ( reference != null )
        {

            CalculatorService calculator = (CalculatorService) context.getService( reference );

            double[] numbers = calculator.parseUserInput( getUserNumberInput() );

            if ( getOperation().equals( "add" ) )
            {
                result = calculator.add( numbers );
            }
            else if ( getOperation().equals( "multiply" ) )
            {
                result = calculator.multiply( numbers );
            }
            else
            {
                throw new UnsupportedOperationException( "Unknown command: " + getOperation() );
            }
            
            calculator.printResult( result );
            context.ungetService( reference );
        }
        else
        {
            System.out.println( "Calculator service is not installed or not started ..." );
        }
    }

    public void stop( BundleContext context )
    {
    }

    public void initUserInput()
    {
        BufferedReader in = null;

        try
        {
            in = new BufferedReader( new InputStreamReader( System.in ) );
            System.out.println( "Enter operation (add or multiply):" );
            operation = in.readLine();

            System.out.println( "Enter several numbers separated with a space:" );
            userNumberInput = in.readLine();
        }
        catch ( IOException ex )
        {
            System.err.println( "Error reading from the reader." );
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                in.close();
            }
            catch ( IOException e )
            {
                System.err.println( "Error closing the reader." );
                e.printStackTrace();
            }
        }
    }

    public String getOperation()
    {
        return operation;
    }

    public double getResult()
    {
        return result;
    }

    public void setResult( double result )
    {
        this.result = result;
    }

    public void setOperation( String operation )
    {
        this.operation = operation;
    }

    public String getUserNumberInput()
    {
        return userNumberInput;
    }

    public void setUserNumberInput( String userNumberInput )
    {
        this.userNumberInput = userNumberInput;
    }
}