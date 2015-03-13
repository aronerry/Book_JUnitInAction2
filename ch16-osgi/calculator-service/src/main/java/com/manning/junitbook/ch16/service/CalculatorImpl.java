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
package com.manning.junitbook.ch16.service;

/**
 * Implementation of the calculator service.
 * 
 * @version $Id: CalculatorImpl.java 538 2009-08-17 09:08:14Z paranoid12 $
 */
public class CalculatorImpl
    implements CalculatorService
{

    public double add( double... numbers )
    {
        double result = 0;
        for ( double number : numbers )
            result += number;
        return result;
    }

    public double multiply( double... numbers )
    {
        double result = 1;
        for ( double number : numbers )
            result *= number;
        return result;
    }

    public double[] parseUserInput( String str )
        throws NumberFormatException
    {
        String[] numbers = str.split( " " );

        double[] result = new double[numbers.length];

        for ( int i = 0; i < numbers.length; i++ )
        {
            result[i] = Double.parseDouble( numbers[i] );
        }

        return result;
    }

    public void printResult( double result )
    {
        System.out.println( "The result is: " + result );
    }
}
