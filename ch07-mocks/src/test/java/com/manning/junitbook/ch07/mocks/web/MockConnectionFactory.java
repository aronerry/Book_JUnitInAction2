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
package com.manning.junitbook.ch07.mocks.web;

import java.io.InputStream;

/**
 * Mock implementation of the ConnectionFactory interface.
 * 
 * @version $Id: MockConnectionFactory.java 505 2009-08-16 17:58:38Z paranoid12 $
 */
public class MockConnectionFactory implements ConnectionFactory
{
    /**
     * The input stream for the connection.
     */
    private InputStream inputStream;

    /**
     * Set the input stream.
     * 
     * @param stream
     */
    public void setData(InputStream stream)
    {
        this.inputStream = stream;
    }

    /**
     * Get the input stream.
     * 
     * @throws Exception
     */
    public InputStream getData() throws Exception
    {
        return this.inputStream;
    }
}
