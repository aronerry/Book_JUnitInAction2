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
import java.io.IOException;

/**
 * A sample WebClient that opens a connection to a web-server and reads the data from it.
 * 
 * @version $Id: WebClient2.java 503 2009-08-16 17:47:12Z paranoid12 $
 */
public class WebClient2
{
    /**
     * Open a connection to the given URL and read the content 
     * out of it. In case of an exception we return null.
     * 
     * @param connectionFactory
     * @return
     */
    public String getContent( ConnectionFactory connectionFactory )
        throws IOException
    {
        String result;

        StringBuffer content = new StringBuffer();
        InputStream is = null;
        try
        {
            is = connectionFactory.getData();

            int count;
            while ( -1 != ( count = is.read() ) )
            {
                content.append( new String( Character.toChars( count ) ) );
            }

            result = content.toString();
        }
        catch ( Exception e )
        {
            result = null;
        }

        // Close the stream
        if ( is != null )
        {
            try
            {
                is.close();
            }
            catch ( IOException e )
            {
                result = null;
            }
        }

        return result;
    }
}
