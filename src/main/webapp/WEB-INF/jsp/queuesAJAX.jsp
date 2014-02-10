<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" 
	import="ro.allevo.fintpui.core.*,java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="<c:url value="/resources/script/jquery/jquery-1.10.1.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/script/queuesAJAX.js" />"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>FinTP home page</title>
</head>
<body>
	<h2>QUEUES</h2>
	<table>
	<tbody id="queuesTable">
	<tr>
		<th>Name</th>
		<th>Description</th>
		<th>Holdstatus</th>
	</tr>
	
	</tbody>
	</table>
</body>
</html>