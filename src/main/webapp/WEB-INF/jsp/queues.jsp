<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" 
	import="ro.allevo.fintpui.core.*,java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>FinTP home page</title>
</head>
<body>
	<h2>QUEUES</h2>
	<table>
	<tbody>
	<tr>
		<th>Name</th>
		<th>Description</th>
		<th>Holdstatus</th>
	</tr>	
	
	<c:forEach items="${queues}" var="article">
		<tr>
			<td><c:out value="${article.name}" /></td>
			<td><c:out value="${article.description}" /><td>
			<td><c:out value="${article.holdstatus}" /></td>
		<tr>		
		
	</c:forEach>
	</tbody>
	</table>
</body>
</html>