package com.manning.junitbook.ch12.htmlunit;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;

/**
 * Tests navigating the Sun Javadoc 6 site.
 * 
 * @author <a href="mailto:ggregory@apache.org">Gary Gregory</a>
 * @version $Id: JavadocPageAllBrowserTest.java 392 2009-04-28 23:38:33Z
 *          garydgregory $
 */
@RunWith(value = Parameterized.class)
public class JavadocPageAllBrowserTest {

    @Parameters
    public static Collection<BrowserVersion[]> getBrowserVersions() {
        return Arrays.asList(new BrowserVersion[][] { { BrowserVersion.FIREFOX_2 }, { BrowserVersion.FIREFOX_3 },
                { BrowserVersion.INTERNET_EXPLORER_6 }, { BrowserVersion.INTERNET_EXPLORER_7 } });
    }

    private BrowserVersion browserVersion;

    public JavadocPageAllBrowserTest(BrowserVersion browserVersion) {
        this.browserVersion = browserVersion;
    }

    @Test
    public void testClassNav() throws IOException {
        WebClient webClient = new WebClient(this.browserVersion);
        webClient.setThrowExceptionOnScriptError(false);
        HtmlPage mainPage = (HtmlPage) webClient.getPage("http://htmlunit.sourceforge.net/apidocs/index.html");
        HtmlPage packagePage = (HtmlPage) mainPage.getFrameByName("packageFrame").getEnclosedPage();
        HtmlPage bVerPage = packagePage.getAnchorByHref("com/gargoylesoftware/htmlunit/BrowserVersion.html").click();
        HtmlParagraph p = (HtmlParagraph) bVerPage.getElementsByTagName("p").item(0);
        Assert.assertTrue("Unexpected text", p.asText().startsWith(
                "Objects of this class represent one specific version of a given browser."));
    }
}
