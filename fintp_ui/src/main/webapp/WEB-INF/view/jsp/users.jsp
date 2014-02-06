<div xmlns:c="http://java.sun.com/jsp/jstl/core"  
xmlns:spring="http://www.springframework.org/tags" 
xmlns:tiles="http://tiles.apache.org/tags-tiles" 
xmlns:jsp="http://java.sun.com/JSP/Page" class="content">

	<c:choose>
	    <c:when test="${empty users}">
	        Table is empty.
	    </c:when>
	   <c:otherwise>
	 		<table>
				<thead>
			        <tr>
						<th> First Name </th>
						<th> Last name </th>
			        </tr>
				</thead>
			    <tbody>
					<c:forEach var="user" items="${users}">
						<tr>
							<td> <c:out value="${user.firstName}"/> </td>
							<td> <c:out value="${user.lastName}"/> </td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	    </c:otherwise>
	</c:choose>

</div>