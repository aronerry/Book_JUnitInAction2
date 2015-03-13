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
package com.manning.junitbook.ch06.stubs;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;

/**
 * A stub class to stub the HttpUrl connection. We override the getInputStream method to return the "It works" string.
 * 
 * @version $Id$
 */
public class StubHttpURLConnection
    extends HttpURLConnection
{
    private boolean isInput = true;

    protected StubHttpURLConnection( URL url )
    {
        super( url );
    }

    public InputStream getInputStream()
        throws IOException
    {
        if ( !isInput )
        {
            throw new ProtocolException( "Cannot read from URLConnection" + " if doInput=false (call setDoInput(true))" );
        }
        ByteArrayInputStream bais = new ByteArrayInputStream( new String( "It works" ).getBytes() );
        return bais;
    }

    public void disconnect()
    {
    }

    public void connect()
        throws IOException
    {
    }

    public boolean usingProxy()
    {
        return false;
    }
}
