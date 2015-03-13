package com.manning.junitbook.ch12.htmlunit;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;

/**
 * Tests navigating the Sun Javadoc 6 site.
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id: JavadocPageTest.java 410 2009-05-20 21:37:55Z garydgregory $
 */
public class JavadocPageTest extends ManagedWebClient {

    @Test
    public void testClassNav() throws IOException {
        HtmlPage mainPage = (HtmlPage) this.webClient.getPage("http://htmlunit.sourceforge.net/apidocs/index.html");
        HtmlPage packagePage = (HtmlPage) mainPage.getFrameByName("packageFrame").getEnclosedPage();
        HtmlPage bVerPage = packagePage.getAnchorByHref("com/gargoylesoftware/htmlunit/BrowserVersion.html").click();
        HtmlParagraph p = (HtmlParagraph) bVerPage.getElementsByTagName("p").item(0);
        Assert.assertTrue("Unexpected text", p.asText().startsWith(
                "Objects of this class represent one specific version of a given browser."));
    }
}
