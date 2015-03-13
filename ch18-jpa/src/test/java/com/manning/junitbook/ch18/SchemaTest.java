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
package com.manning.junitbook.ch18;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class SchemaTest extends AbstractJpaTestCase {
  
  @Test
  public void testForeignKeysNames() {
    SqlHandler handler = new SqlHandler() {
      public void handle(String sql) {
        assertForeignKeysDoesNotHaveFunnyNames(sql);
      }
    };
    analyzeSchema(handler);
  }
  
  private static final String FK_LINE_REGEXP = "alter table (.*) add constraint (.*) foreign key .*";
  private static final Pattern FK_LINE_PATTERN = Pattern.compile(FK_LINE_REGEXP);
  private static final Matcher FK_LINE_MATCHER = FK_LINE_PATTERN.matcher("");
  private static final String FK_REGEXP = "fk_[a-z]+_[a-z]+$";
  private static final Pattern FK_PATTERN = Pattern.compile(FK_REGEXP);
  private static final Matcher FK_MATCHER = FK_PATTERN.matcher("");
  
  private void assertForeignKeysDoesNotHaveFunnyNames(String sql) {
    String[] lines = sql.split("\n");
    StringBuilder buffer = new StringBuilder();
    for ( String line : lines ) {
//      System.err.println("SQL line: " + line );
      FK_LINE_MATCHER.reset(line);
      if( FK_LINE_MATCHER.find() ) {
        final String table = FK_LINE_MATCHER.group(1);
        final String fk = FK_LINE_MATCHER.group(2);
//        System.err.println("SQL constraint line: " + line );
        if ( ! isValidFk(fk) ) {
          buffer.append(table).append("(").append(fk).append(") ");
        }
      }
    }
    String violations = buffer.toString();
    if ( violations.length() > 0 ) {
      fail( "One or more tables have weird FK names: " + violations );
    }
  }
  
  private boolean isValidFk(String fk) {
    FK_MATCHER.reset(fk);
    return FK_MATCHER.find();
  }  
  
}
