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
 * Calculator service interface.
 * 
 * @version $Id: CalculatorService.java 538 2009-08-17 09:08:14Z paranoid12 $
 */
public interface CalculatorService
{

    public double[] parseUserInput( String str )
        throws NumberFormatException;

    public double add( double... numbers );

    public double multiply( double... numbers );

    public void printResult( double result );
}
