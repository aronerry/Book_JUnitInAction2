package com.manning.junitbook.ch13;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.googlecode.jslint4java.Issue;
import com.googlecode.jslint4java.JSLint;
import com.googlecode.jslint4java.JSLintBuilder;

public class TestJsonService {

    private static final String URL_FIXTURE = "http://localhost/ch13personal/glossary.json";

    @Test
    public void testGetJsonBasicCheck() throws IOException {
        HttpClient httpClient = new HttpClient();
        GetMethod get = new GetMethod(URL_FIXTURE);
        String responseString;
        try {
            httpClient.executeMethod(get);
            InputStream input = get.getResponseBodyAsStream();
            responseString = IOUtils.toString(input, "UTF-8");
        } finally {
            get.releaseConnection();
        }
        String responseNoWs = StringUtils.deleteWhitespace(responseString);
        String response1Line = "{ \"glossary\": { \"title\": \"example glossary\", \"GlossDiv\": { \"title\": \"S\", \"GlossList\": { \"GlossEntry\": { \"ID\": \"SGML\", \"SortAs\": \"SGML\", \"GlossTerm\": \"Standard Generalized Markup Language\", \"Acronym\": \"SGML\", \"Abbrev\": \"ISO 8879:1986\", \"GlossDef\": { \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\", \"GlossSeeAlso\": [\"GML\", \"XML\"] }, \"GlossSee\": \"markup\" } } } } }";
        String response1LineNoWs = StringUtils.deleteWhitespace(response1Line);
        Assert.assertTrue(responseString, responseNoWs.equals(response1LineNoWs));
    }

    @Test
    public void testGetJsonAndValidate() throws IOException, ParserConfigurationException, SAXException {
        HttpClient httpClient = new HttpClient();
        GetMethod get = new GetMethod(URL_FIXTURE);
        String responseString;
        try {
            httpClient.executeMethod(get);
            InputStream input = get.getResponseBodyAsStream();
            responseString = IOUtils.toString(input, "UTF-8");
        } finally {
            get.releaseConnection();
        }
        JSLint jslint = new JSLintBuilder().fromDefault();
        List<Issue> issues = jslint.lint(URL_FIXTURE, responseString);
        StringBuilder builder = new StringBuilder();
        String eol = System.getProperty("line.separator");
        for (Issue issue : issues) {
            builder.append(issue.toString());
            builder.append(eol);
        }
        Assert.assertEquals(builder.toString(), 0, issues.size());
    }

}
