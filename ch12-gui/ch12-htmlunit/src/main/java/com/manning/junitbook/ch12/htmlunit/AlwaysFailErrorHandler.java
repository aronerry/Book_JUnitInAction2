/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.manning.junitbook.ch12.htmlunit;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

/**
 * This error handler fails on any error, fatal error, or warning.
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id: AlwaysFailErrorHandler.java 390 2009-04-28 23:34:07Z garydgregory $
 */
public class AlwaysFailErrorHandler implements ErrorHandler {

    /**
     * Throws the given {@link CSSParseException}.
     */
    public void error(CSSParseException e) throws CSSException {
        throw e;
    }

    /**
     * Throws the given {@link CSSParseException}.
     */
    public void fatalError(CSSParseException e) throws CSSException {
        throw e;
    }

    /**
     * Throws the given {@link CSSParseException}.
     */
    public void warning(CSSParseException e) throws CSSException {
        throw e;
    }
}
