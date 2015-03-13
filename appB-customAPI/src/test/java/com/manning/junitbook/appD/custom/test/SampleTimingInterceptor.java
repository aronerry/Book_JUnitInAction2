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
package com.manning.junitbook.appD.custom.test;

import com.manning.junitbook.appD.custom.model.Interceptor;

/**
 * A sample timing interceptor to test our custom runner.
 * 
 * @version $Id: SampleTimingInterceptor.java 201 2009-02-15 19:18:09Z paranoid12 $
 */
public class SampleTimingInterceptor
    implements Interceptor
{
    Timer timer = new Timer();

    /**
     * The method to execute before the test.
     */
    public void interceptBefore()
    {
        System.out.println("Interceptor started.");
        timer.start();
    }

    /**
     * The method to execute after the test.
     */
    public void interceptAfter()
    {
        timer.stop();
        System.out.println("Interceptor ended. The test executed for " + timer.time() + " sec.");
    }

    /**
     * Private class to use for timing purposes.
     */
    class Timer  {
        private long nanoStart = 0;
        private long nanoEnd = 0;
        
        /**
         * Start the timer.
         */
        void start() {
            nanoStart = System.currentTimeMillis();
        }
        
        /**
         * Stop the timer.
         */
        void stop() {
            nanoEnd = System.currentTimeMillis();
        }
        
        /**
         * Get the time that passed.
         * 
         * @return the time that passed
         */
        long time() {
            return (nanoEnd - nanoStart) / 1000;
        }
    }
}
