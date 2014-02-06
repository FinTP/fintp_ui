<div xmlns:c="http://java.sun.com/jsp/jstl/core"  
xmlns:spring="http://www.springframework.org/tags" 
xmlns:tiles="http://tiles.apache.org/tags-tiles" 
xmlns:jsp="http://java.sun.com/JSP/Page" class="content">

	<c:choose>
	    <c:when test="${empty messagetypes}">
	        There are no payments to show.
	    </c:when>
	   <c:otherwise>
	 		<c:forEach var="messagetype" items="${messagetypes}">
				<div>
					<c:out value="${messagetype.name}"/>
				</div>
			</c:forEach>
	    </c:otherwise>
	</c:choose>

</div>