package com.manning.junitbook.ch12.htmlunit;

import org.junit.After;
import org.junit.Before;

import com.gargoylesoftware.htmlunit.WebClient;

/**
 * Manages an HtmlUnit WebClient on behalf of subclasses. The point of the class
 * is to make sure closeAllWindows() is called when a test is done with a
 * WebClient instance.
 * 
 * @author ggregory
 * @version $Id: JavadocPageTest.java 392 2009-04-28 23:38:33Z garydgregory $
 */
public abstract class ManagedWebClient {
    protected WebClient webClient;

    protected WebClient getWebClient() {
        return this.webClient;
    }

    @Before
    public void setUp() {
        this.setWebClient(new WebClient());
    }

    protected void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @After
    public void tearDown() {
        this.getWebClient().closeAllWindows();
    }
}
