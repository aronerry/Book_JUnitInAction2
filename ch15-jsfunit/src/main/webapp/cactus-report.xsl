<?xml version="1.0" encoding="UTF-8"?>
<!--
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
-->
<!--
 
 Sample stylesheet to be used with the Cactus ServletTestRunner output.
 Based on the file junit-noframes.xsl from Apache Ant 1.5.
 
 @author Stephane Bailliez <a href="mailto:sbailliez@apache.org"/>
 @author Erik Hatcher <a href="mailto:ehatcher@apache.org"/>
 @author Christopher Lenz <a href="mailto:cmlenz@apache.org"/>
 
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="html" indent="yes" encoding="US-ASCII"/>
  <xsl:decimal-format decimal-separator="." grouping-separator=","/>
  <xsl:template match="testsuites">
    <html>
      <head>
        <style type="text/css">
          body {
            font:normal 68% verdana,arial,helvetica;
            color:#000000;
          }
          table tr td, table tr th {
              font-size: 68%;
          }
          table.details tr th{
            font-weight: bold;
            text-align:left;
            background:#a6caf0;
          }
          table.details tr td{
            background:#eeeee0;
          }
          
          p {
            line-height:1.5em;
            margin-top:0.5em; margin-bottom:1.0em;
          }
          h1 {
            margin: 0px 0px 5px; font: 165% verdana,arial,helvetica
          }
          h2 {
            margin-top: 1em; margin-bottom: 0.5em; font: bold 125% verdana,arial,helvetica
          }
          h3 {
            margin-bottom: 0.5em; font: bold 115% verdana,arial,helvetica
          }
          h4 {
            margin-bottom: 0.5em; font: bold 100% verdana,arial,helvetica
          }
          h5 {
            margin-bottom: 0.5em; font: bold 100% verdana,arial,helvetica
          }
          h6 {
            margin-bottom: 0.5em; font: bold 100% verdana,arial,helvetica
          }
          .Error {
            font-weight:bold; color:red;
          }
          .Failure {
            font-weight:bold; color:purple;
          }
        </style>
      </head>
      <body>
        <a name="top"/>
        <xsl:call-template name="header"/>  
        <xsl:call-template name="summary"/>
        <hr size="1" width="95%" align="left"/>
        <xsl:call-template name="classes"/>
      </body>
    </html>
  </xsl:template>
  
  <xsl:template name="header">
    <h1>Unit Test Results</h1>
    <table width="100%">
    <tr>
      <td align="left"/>
      <td align="right">
        Designed for use with 
        <a href="http://jakarta.apache.org/cactus/">Cactus</a>.
      </td>
    </tr>
    </table>
    <hr size="1"/>
  </xsl:template>
  
  <xsl:template name="summary">
    <h2>Summary</h2>
    <xsl:variable name="testCount" select="sum(testsuite/@tests)"/>
    <xsl:variable name="errorCount" select="sum(testsuite/@errors)"/>
    <xsl:variable name="failureCount" select="sum(testsuite/@failures)"/>
    <xsl:variable name="timeCount" select="sum(testsuite/@time)"/>
    <xsl:variable name="successRate" select="($testCount - $failureCount - $errorCount) div $testCount"/>
    <table class="details" border="0" cellpadding="5" cellspacing="2" width="95%">
    <tr valign="top">
      <th>Tests</th>
      <th>Failures</th>
      <th>Errors</th>
      <th>Success rate</th>
      <th>Time</th>
    </tr>
    <tr valign="top">
      <xsl:attribute name="class">
        <xsl:choose>
          <xsl:when test="$failureCount &gt; 0">Failure</xsl:when>
          <xsl:when test="$errorCount &gt; 0">Error</xsl:when>
        </xsl:choose>
      </xsl:attribute>
      <td><xsl:value-of select="$testCount"/></td>
      <td><xsl:value-of select="$failureCount"/></td>
      <td><xsl:value-of select="$errorCount"/></td>
      <td>
        <xsl:call-template name="display-percent">
          <xsl:with-param name="value" select="$successRate"/>
        </xsl:call-template>
      </td>
      <td>
        <xsl:call-template name="display-time">
          <xsl:with-param name="value" select="$timeCount"/>
        </xsl:call-template>
      </td>
    </tr>
    </table>
    <table border="0" width="95%">
    <tr>
    <td style="text-align: justify;">
      Note: <i>failures</i> are anticipated and checked for with assertions 
      while <i>errors</i> are unanticipated.
    </td>
    </tr>
    </table>
  </xsl:template>
  
  <xsl:template name="classes">
    <xsl:for-each select="testsuite">
      <xsl:sort select="@name"/>
      <!-- create an anchor to this class name -->
      <a name="{@name}"/>
      <h3>TestCase <xsl:value-of select="@name"/></h3>
      
      <table class="details" border="0" cellpadding="5" cellspacing="2" width="95%">
        <xsl:call-template name="testcase.test.header"/>
        <!--
        test can even not be started at all (failure to load the class)
        so report the error directly
        -->
        <xsl:if test="./error">
          <tr class="Error">
            <td colspan="4"><xsl:apply-templates select="./error"/></td>
          </tr>
        </xsl:if>
        <xsl:apply-templates select="./testcase" mode="print.test"/>
      </table>
      <p/>
      <a href="#top">Back to top</a>
    </xsl:for-each>
  </xsl:template>
  
  <xsl:template match="testsuite" mode="header">
    <tr valign="top">
      <th width="80%">Name</th>
      <th>Tests</th>
      <th>Errors</th>
      <th>Failures</th>
      <th nowrap="nowrap">Time(s)</th>
    </tr>
  </xsl:template>
    
  <!-- class header -->
  <xsl:template name="testsuite.test.header">
    <tr valign="top">
      <th width="80%">Name</th>
      <th>Tests</th>
      <th>Errors</th>
      <th>Failures</th>
      <th nowrap="nowrap">Time(s)</th>
    </tr>
  </xsl:template>

  <!-- method header -->
  <xsl:template name="testcase.test.header">
    <tr valign="top">
      <th>Name</th>
      <th>Status</th>
      <th width="80%">Type</th>
      <th nowrap="nowrap">Time(s)</th>
    </tr>
  </xsl:template>
  
  <!-- class information -->
  <xsl:template match="testsuite" mode="print.test">
    <tr valign="top">
      <!-- set a nice color depending if there is an error/failure -->
      <xsl:attribute name="class">
        <xsl:choose>
          <xsl:when test="@failures[.&gt; 0]">Failure</xsl:when>
          <xsl:when test="@errors[.&gt; 0]">Error</xsl:when>
        </xsl:choose>
      </xsl:attribute>
      <!-- print testsuite information -->
      <td><a href="#{@name}"><xsl:value-of select="@name"/></a></td>
      <td><xsl:value-of select="@tests"/></td>
      <td><xsl:value-of select="@errors"/></td>
      <td><xsl:value-of select="@failures"/></td>
      <td>
        <xsl:call-template name="display-time">
          <xsl:with-param name="value" select="@time"/>
        </xsl:call-template>
      </td>
    </tr>
  </xsl:template>
  
  <xsl:template match="testcase" mode="print.test">
    <tr valign="top">
      <xsl:attribute name="class">
        <xsl:choose>
          <xsl:when test="failure | error">Error</xsl:when>
        </xsl:choose>
      </xsl:attribute>
      <td><xsl:value-of select="@name"/></td>
      <xsl:choose>
        <xsl:when test="failure">
          <td>Failure</td>
          <td><xsl:apply-templates select="failure"/></td>
        </xsl:when>
        <xsl:when test="error">
          <td>Error</td>
          <td><xsl:apply-templates select="error"/></td>
        </xsl:when>
        <xsl:otherwise>
          <td>Success</td>
          <td/>
        </xsl:otherwise>
      </xsl:choose>
      <td>
        <xsl:call-template name="display-time">
          <xsl:with-param name="value" select="@time"/>
        </xsl:call-template>
      </td>
    </tr>
  </xsl:template>
  
  <xsl:template match="failure">
    <xsl:call-template name="display-failures"/>
  </xsl:template>
  
  <xsl:template match="error">
    <xsl:call-template name="display-failures"/>
  </xsl:template>
  
  <!-- Style for the error and failure in the tescase template -->
  <xsl:template name="display-failures">
    <xsl:choose>
      <xsl:when test="not(@message)">N/A</xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="@message"/>
      </xsl:otherwise>
    </xsl:choose>
    <!-- display the stacktrace -->
    <code>
      <p/>
      <xsl:call-template name="br-replace">
        <xsl:with-param name="word" select="."/>
      </xsl:call-template>
    </code>
  </xsl:template>
  
  <!--
    template that will convert a carriage return into a br tag
    @param word the text from which to convert CR to BR tag
  -->
  <xsl:template name="br-replace">
    <xsl:param name="word"/>
    <xsl:choose>
      <xsl:when test="contains($word,'
')">
        <xsl:value-of select="substring-before($word,'
')"/>
        <br/>
        <xsl:call-template name="br-replace">
          <xsl:with-param name="word" select="substring-after($word,'
')"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$word"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  
  <xsl:template name="display-time">
    <xsl:param name="value"/>
    <xsl:value-of select="format-number($value,'0.000')"/>
  </xsl:template>
  
  <xsl:template name="display-percent">
    <xsl:param name="value"/>
    <xsl:value-of select="format-number($value,'0.00%')"/>
  </xsl:template>
  
</xsl:stylesheet>