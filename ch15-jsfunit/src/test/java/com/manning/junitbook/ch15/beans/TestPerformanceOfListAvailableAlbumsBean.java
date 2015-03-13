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
package com.manning.junitbook.ch15.beans;

import java.io.IOException;

import javax.faces.event.PhaseId;

import org.apache.cactus.ServletTestCase;
import org.jboss.jsfunit.framework.JSFTimer;
import org.jboss.jsfunit.jsfsession.JSFServerSession;
import org.jboss.jsfunit.jsfsession.JSFSession;
import org.xml.sax.SAXException;

/**
 * Test the performance of the JSF application by using JSFUnit.
 * 
 * @version $Id: TestPerformanceOfListAvailableAlbumsBean.java 530 2009-08-16 19:01:19Z paranoid12 $
 */
public class TestPerformanceOfListAvailableAlbumsBean
    extends ServletTestCase
{
    public void testJSFPerformance()
        throws SAXException, IOException
    {
        JSFSession jsfSession = new JSFSession( "/" );
        JSFServerSession server = jsfSession.getJSFServerSession();

        assertEquals( "/list_albums.jsp", server.getCurrentViewID() );
        JSFTimer timer = JSFTimer.getTimer();
        assertTrue( "Total time of getting a response should not take more than 100 ms. ", timer.getTotalTime() < 100 );

        PhaseId appPhase = PhaseId.INVOKE_APPLICATION;
        assertTrue( "Invoking of the application should not take more than 100 ms.", timer.getPhaseTime( appPhase ) < 100 );
    }
}
