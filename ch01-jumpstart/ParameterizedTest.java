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
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * A sample parameterized test-case.
 * 
 * @version $Id: ParameterizedTest.java 551 2010-03-06 11:37:34Z paranoid12 $
 */
@RunWith(value=Parameterized.class)
public class ParameterizedTest {

    private double expected; 
    private double valueOne; 
    private double valueTwo; 

    @Parameters 
    public static Collection<Integer[]> getTestParameters() {
       return Arrays.asList(new Integer[][] {
          {2, 1, 1},  //expected, valueOne, valueTwo   
          {3, 2, 1},  //expected, valueOne, valueTwo   
          {4, 3, 1},  //expected, valueOne, valueTwo   
       });
    }

    public ParameterizedTest(double expected, 
       double valueOne, double valueTwo) {
       this.expected = expected;
       this.valueOne = valueOne;
       this.valueTwo = valueTwo;
    }

    @Test
    public void sum() {
       Calculator calc = new Calculator();
       assertEquals(expected, calc.add(valueOne, valueTwo), 0);
    } 
}

