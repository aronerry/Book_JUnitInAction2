package com.manning.junitbook.ch12.servlets;

import java.io.IOException;

import javax.servlet.ServletException;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.cactus.ServletTestCase;

import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HTMLParser;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id: HtmlUnitServletTestCase.java 397 2009-04-30 19:00:19Z
 *          garydgregory $
 */
public class HtmlUnitServletTestCase extends ServletTestCase {
    public static Test suite() {
        return new TestSuite(HtmlUnitServletTestCase.class);
    }

    public HtmlUnitServletTestCase(String name) {
        super(name);
    }

    public void end(WebResponse webResponse) throws IOException {
        WebClient webClient = new WebClient();
        try {
            HtmlPage page = HTMLParser.parse(webResponse, webClient.getCurrentWindow());
            WebAssert.assertTitleEquals(page, "Hello World2");
        } finally {
            webClient.closeAllWindows();
        }
    }

    public void test() throws ServletException {
        SampleServlet servlet = new SampleServlet();
        servlet.init(this.config);
        // call servlet methods
        // asserts
    }
}
