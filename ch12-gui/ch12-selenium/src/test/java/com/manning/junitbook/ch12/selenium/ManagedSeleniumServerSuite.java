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

package com.manning.junitbook.ch12.selenium;

import java.util.List;

import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.junit.runners.model.Statement;
import org.openqa.selenium.server.SeleniumServer;

/**
 * A JUnit Suite that manages a Selenium server. Use from
 * &#64;RunWith(ManagedSeleniumServerSuite.class)
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id$
 */
public class ManagedSeleniumServerSuite extends Suite {
    private static SeleniumServer seleniumServer;

    public static void startSeleniumServer() throws Exception {
        ManagedSeleniumServerSuite.stopSeleniumServer();
        seleniumServer = new SeleniumServer();
        seleniumServer.start();
    }

    public static void stopSeleniumServer() {
        if (seleniumServer != null) {
            seleniumServer.stop();
            seleniumServer = null;
        }
    }

    public ManagedSeleniumServerSuite(Class<?> klass, Class<?>[] suiteClasses) throws InitializationError {
        super(klass, suiteClasses);
    }

    public ManagedSeleniumServerSuite(Class<?> klass, List<Runner> runners) throws InitializationError {
        super(klass, runners);
    }

    public ManagedSeleniumServerSuite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }

    public ManagedSeleniumServerSuite(RunnerBuilder builder, Class<?> klass, Class<?>[] suiteClasses) throws InitializationError {
        super(builder, klass, suiteClasses);
    }

    public ManagedSeleniumServerSuite(RunnerBuilder builder, Class<?>[] classes) throws InitializationError {
        super(builder, classes);
    }

    @Override
    public void run(final RunNotifier notifier) {
        EachTestNotifier testNotifier = new EachTestNotifier(notifier, this.getDescription());
        try {
            ManagedSeleniumServerSuite.startSeleniumServer();
            Statement statement = this.classBlock(notifier);
            statement.evaluate();
        } catch (AssumptionViolatedException e) {
            testNotifier.fireTestIgnored();
        } catch (StoppedByUserException e) {
            throw e;
        } catch (Throwable e) {
            testNotifier.addFailure(e);
        } finally {
            ManagedSeleniumServerSuite.stopSeleniumServer();
        }
    }

}
