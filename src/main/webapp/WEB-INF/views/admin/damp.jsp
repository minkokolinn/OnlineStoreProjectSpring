	<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form:form method="POST" action="accountadd" modelAttribute="user">		
		<form:input type="text" class="form-control" id="nameInput"
							placeholder="Name" autocomplete="off" path="name" required="required"></form:input>
							<label for="nameInput">Name</label>
		<form:input type="email" path="email"></form:input>
		<form:input type="password" path="password"></form:input>
		<form:input type="text" path="nrc"></form:input>
		<form:input type="number" path="phone"></form:input>
		<button type="submit">click me</button>
	</form:form>
</body>
</html>