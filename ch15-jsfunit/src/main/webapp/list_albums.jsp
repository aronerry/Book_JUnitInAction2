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
    xmlns:c="http://java.sun.com/jstl/core">
    
    <head>
    	<title>List albums</title>
    </head>
    
    <body>
   		<h:form id="list_albums">    
			<b><h:outputText value="List of albums available in the store:" id="list_albums_label"/></b><br/>
	
	
			<table border='1'>
    			<th>Name</th>
    			<th>Author</th>
    			<th>Year</th>
    			<th>Price</th>
    
      			<c:forEach var="album" varStatus="status" items="#{listAlbumsBean.albums}">
        			<tr>
          				<td>
          					<h:commandLink id="j_id_${status.index}" action="#{albumDetailsBean.showAlbumDetails}">
          						<h:outputText value="${album.name}" />   					
          						<f:param name="albumName" value="#{album.name}" />
          					</h:commandLink>
          				</td>
          				<td>${album.author}</td>
          				<td>${album.year}</td>
          				<td>$ ${album.price}</td>
        			</tr>
      			</c:forEach>
    		</table>
   		</h:form>
   </body>
</html>