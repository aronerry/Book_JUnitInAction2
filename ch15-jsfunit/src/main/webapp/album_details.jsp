<!--
	Licensed to the Apache Software Foundation (ASF) under one or more
	contributor license agreements. See the NOTICE file distributed with
	this work for additional information regarding copyright ownership.
	The ASF licenses this file to you under the Apache License, Version
	2.0 (the "License"); you may not use this file except in compliance
	with the License. You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0 Unless required by
	applicable law or agreed to in writing, software distributed under the
	License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
	CONDITIONS OF ANY KIND, either express or implied. See the License for
	the specific language governing permissions and limitations under the
	License.
-->
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:rich="http://richfaces.org/rich"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:c="http://java.sun.com/jstl/core">

<head>
<title>Album details</title>
</head>
<body>
<h:form id="view_details">
	<b>Details for album '#{albumDetailsBean.album.name}':</b>
	<br />
	<table border='1'>
		<tr>
			<td>
			<table>
				<tr>
					<td>Title: #{albumDetailsBean.album.name}</td>
				</tr>
				<tr>
					<td>Year: #{albumDetailsBean.album.year}</td>
				</tr>
				<tr>
					<td>Style: #{albumDetailsBean.album.style}</td>
				</tr>
				<tr>
					<td>Author: #{albumDetailsBean.album.author}</td>
				</tr>
				<tr>
					<td>Price: $ #{albumDetailsBean.album.price}</td>
				</tr>
				<tr>
					<td>
						<rich:panel bodyClass="rich-laguna-panel-no-header">
								<a4j:commandButton id="PurchaseButton" value="Purchase" reRender="rep" action="#{albumDetailsBean.purchase}">
									<a4j:actionparam id="status" name="status" value="Successfully purchased: #{albumDetailsBean.album.name}" assignTo="#{albumDetailsBean.status}"/>
								</a4j:commandButton>
								<rich:spacer width="20" />
								<a4j:commandButton value="Cancel" reRender="rep" action="#{albumDetailsBean.cancel}"/>
						</rich:panel>
						<rich:spacer height="1" />
						<rich:panel bodyClass="rich-laguna-panel-no-header">
								<h:outputText id="rep" name="rep" value="#{albumDetailsBean.status}" />
						</rich:panel>
					</td>
				</tr>
			</table>
			</td>
			<td><img src="#{albumDetailsBean.album.imageURL}" width="100" height='100' /></td>
		</tr>
	</table>
</h:form>
</body>
</html>