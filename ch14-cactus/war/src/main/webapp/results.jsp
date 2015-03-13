<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
   <head>
      <title>Results Page</title>
   </head>
   <body bgcolor="white">
      <table border="1">
         <d:properties var="properties"
            item="${requestScope.results[0]}"/>

         <tr>
            <c:forEach var="property" items="${properties}">
               <th><c:out value="${property.name}"/></th>
            </c:forEach>
         </tr>

         <c:forEach var="result" items="${requestScope.results}">
            <tr>
               <c:forEach var="property" items="${properties}">
                  <td><d:getProperty name="${property.name}"
                                          item="${result}"/></td>
               </c:forEach>
           </tr>
         </c:forEach>
      </table>
   </body>
</html>