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
 * A sample test-case that demonstrates the parameterized feature of JUnit.
 * 
 * @version $Id$
 */
@RunWith( value = Parameterized.class )
public class TestCalculator
{

    private int expected;

    private int actual;

    @Parameters
    public static Collection<Integer[]> data()
    {
        return Arrays.asList( new Integer[][] { { 1, 1 }, { 2, 4 }, { 3, 9 }, { 4, 16 }, { 5, 25 }, } );
    }

    public TestCalculator( int expected, int actual )
    {
        this.expected = expected;
        this.actual = actual;
    }

    @Test
    public void squareRoot()
    {
        Calculator calculator = new Calculator();
        assertEquals( expected, calculator.squareRoot( actual ) );
    }
}
