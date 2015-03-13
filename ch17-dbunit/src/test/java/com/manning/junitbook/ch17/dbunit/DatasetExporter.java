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
package com.manning.junitbook.ch17.dbunit;

import java.io.FileOutputStream;

import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.filter.ITableFilter;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class DatasetExporter extends AbstractDbUnitTestCase {

  private void export( String file) throws Exception {  
    IDataSet fullDataSet = dbunitConnection.createDataSet();
    FlatXmlDataSet.write(fullDataSet, new FileOutputStream(file+".xml"));    
    FlatDtdDataSet.write(fullDataSet, new FileOutputStream(file+".dtd"));
  }
  
  private void exportFK( String file) throws Exception {  
    IDataSet fullDataSet = dbunitConnection.createDataSet();
    ITableFilter filter = new DatabaseSequenceFilter(dbunitConnection);
    FilteredDataSet filteredDatSet = new FilteredDataSet( filter, fullDataSet );
    FlatXmlDataSet.write(filteredDatSet, new FileOutputStream(file+".xml"));    
    FlatDtdDataSet.write(filteredDatSet, new FileOutputStream(file+".dtd"));
  }
  
  public static void main(String[] args) throws Exception {
    DatasetExporter exporter = new DatasetExporter();
    DatasetExporter.setupDatabase();
    exporter.export("exported-full");
    exporter.exportFK("exported-fk");
    DatasetExporter.closeDatabase();
  }
  
}
