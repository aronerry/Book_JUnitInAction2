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
package com.manning.junitbook.ch15.analysis;

import java.util.HashSet;
import java.util.Set;

import org.jboss.jsfunit.analysis.AbstractFacesConfigTestCase;
/**
 * A static-analusys test-case for your faces-config.xml
 * 
 * @version $Id: FacesConfigTestCase.java 530 2009-08-16 19:01:19Z paranoid12 $
 */
public class FacesConfigTestCase
    extends AbstractFacesConfigTestCase
{

    private static Set<String> paths = new HashSet<String>()
    {
        {
            add( "src/main/webapp/WEB-INF/faces-config.xml" );
        }
    };

    public FacesConfigTestCase()
    {
        super( paths );
    }
}
