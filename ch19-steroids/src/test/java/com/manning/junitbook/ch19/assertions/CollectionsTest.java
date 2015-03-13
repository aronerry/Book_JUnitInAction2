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

package com.manning.junitbook.ch19.assertions;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import junitx.framework.ArrayAssert;
import junitx.framework.ComparableAssert;
import junitx.framework.ListAssert;

import org.fest.assertions.Assertions;
import org.fest.assertions.Condition;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.reflectionassert.ReflectionAssert;

@RunWith(AssertionErrorLoggerRunner.class)
public class CollectionsTest {

  private final List<Integer> LIST1 = Arrays.asList(4, 8, 15, 16, 23, 42);
  private final List<Integer> LIST2 = Arrays.asList(108);
  private final List<Integer> LIST3 = Arrays.asList(4, 8, 15, 16, 42, 23);
  private final List<Integer> LIST4 = Arrays.asList(4, 8, 15, 16, 108, 23);
  private final int[] ARRAY1 = new int[] { 4, 8, 15, 16, 23, 42 };
  private final int[] ARRAY2 = new int[] { 108 };
  private final int[] ARRAY3 = new int[] { 4, 8, 15, 16, 42, 23 };
  private final Integer[] ARRAY1_INTEGER = new Integer[] { 4, 8, 15, 16, 23, 42 };
//  private final Integer[] ARRAY2_INTEGER = new Integer[] { 108 };
  private final Integer[] ARRAY3_INTEGER = new Integer[] { 4, 8, 15, 16, 42, 23 };

  @Test
  public void testJUnitListsDifferentSizes() {
    Assert.assertEquals(LIST1, LIST2);
  }

  @Test
  public void testJUnitListsDifferent() {
    Assert.assertEquals(LIST1, LIST4);
  }

  @Test
  public void testJUnitArraysDifferentSizes() {
    Assert.assertEquals(ARRAY1, ARRAY2);
  }

  @Test
  public void testJUnitArraysDifferent() {
    Assert.assertEquals(ARRAY1, ARRAY3);
  }

  @Test
  public void testJUnitAssertArraysDifferentSizes() {
    Assert.assertArrayEquals(ARRAY1, ARRAY2);
  }

  @Test
  public void testJUnitAssertArraysDifferent() {
    Assert.assertArrayEquals(ARRAY1, ARRAY3);
  }

  @Test
  public void testJUnitAddonsListsDifferentSizes() {
    ListAssert.assertEquals(LIST1, LIST2);
  }

  @Test
  public void testJUnitAddonsListsDifferentOrder() {
    ListAssert.assertEquals(LIST1, LIST3);
    Assert.fail("Assertion was ok, because it 'Asserts that two lists are equal (the order is not relevant)'");
  }
  
  @Test
  public void testJUnitAddonsListsDifferent() {
    ListAssert.assertEquals(LIST1, LIST4);
  }

  @Test
  public void testJUnitAddonsListContains() {
    ListAssert.assertContains(LIST1, 666);
  }
  
  @Test
  public void testJUnitAddonsArraysDifferent() {
    ArrayAssert.assertEquals(ARRAY1, ARRAY3);
  }
  
  @Test
  public void testJUnitAddonsArraysDifferentOrder() {
    ArrayAssert.assertEquivalenceArrays(ARRAY1_INTEGER, ARRAY3_INTEGER);
    Assert.fail("Did not fail because are equivalent");
  }
  
  @Test
  public void testJUnitAddonsComparable() {
    int x = 23;
//    ComparableAssert.assertGreater("bad X", 42, x);
    ComparableAssert.assertGreater(42, x);
  }

  
  @Test
  public void testUnitilsListsDifferentSizes() {
    ReflectionAssert.assertLenientEquals(LIST1, LIST2);
  }

  @Test
  public void testUnitilsListsDifferentSizesNonLenient() {
    ReflectionAssert.assertReflectionEquals(LIST1, LIST2);
  }
  
  @Test
  public void testUnitilsListsDifferentOrder() {
    ReflectionAssert.assertLenientEquals(LIST1, LIST3);
    Assert.fail("Should have failed, but passed because elements were switched");
  }

  @Test
  public void testUnitilsListsDifferentOrderNonLenient() {
    ReflectionAssert.assertReflectionEquals(LIST1, LIST3);
  }
  
  @Test
  public void testUnitilsListsDifferent() {
    ReflectionAssert.assertLenientEquals(LIST1, LIST4);
  }
    
  @Test
  public void testUnitilsListsDifferentNonLenient() {
    ReflectionAssert.assertReflectionEquals(LIST1, LIST4);
  }
  
  @Test
  public void testFESTListsDifferent() {
//    Assertions.assertThat(LIST1).isEqualTo(LIST4);
    Assertions.assertThat(Arrays.asList(1,1)).doesNotHaveDuplicates();
//    Assertions.assertThat(LIST1).contains(108, 666).
  }
  
  @Test
  public void testFESTListsDifferentSize() {
    Assertions.assertThat(LIST1).isEqualTo(LIST2);
  }
  
  @Test
  public void testFESTFunnyStuff() {
    Condition<Collection<?>> allNegative = new Condition<Collection<?>>("all negative") {
      @Override
      public boolean matches(Collection<?> value) {
        for ( Object object : value ) {
          if ( ! (object instanceof Integer) ) {
            return false;
          }
          Integer number = (Integer) object;
          if ( number >= 0 ) {
            return false;
          }
        }
        return true;
      }
    };
    Assertions.assertThat(LIST1).satisfies(allNegative);
//    int x = 23;
//    Assertions.assertThat(x).as("X").isGreaterThan(42);
//    Assertions.assertThat(x).isGreaterThan(42).isLessThan(108);
//    Assertions.assertThat(LIST1)
//        .hasSize(6)
//        .contains(42)
//        .doesNotHaveDuplicates();
  }
  
}
