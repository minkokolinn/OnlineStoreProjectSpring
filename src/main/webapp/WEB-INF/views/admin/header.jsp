<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="utility.Utility"%>
<%@page import="models.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Administration</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/admin/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.1/css/all.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/admin/css/custom.css">
<script
	src="${pageContext.request.contextPath}/resources/admin/js/jquery-3.6.1.min.js"></script>
</head>
<body class="bg-secondary">
	<%
	int userid = 0;
	User currentlogin = null;
	boolean isAdmin = false;
	if (Utility.checkAdminAuth(request) == 0) {
		userid=0;
	%>
	<script type="text/javascript">
		alert("Access Denied!");
		location = "login";
	</script>
	<%
	}else{
		userid = Utility.checkAdminAuth(request);
		currentlogin = Utility.getCurrentLoginedUser(userid);
		if(!currentlogin.isIsadmin()){
			%>
			<script type="text/javascript">
				alert("Access Denied! You are not admin");
				location = "/SpringOnlineStore";
			</script>
			<%
		}
	}
	%>
	<div class="container-fluid" id="mainContainer">
		<div class="row g-0 h-100">
			<!-- Wrapper -->

			<nav class="col-2 pe-3 border-right bg-secondary h-100">
				<!-- Left Side Nav -->

				<h1 class="h4 py-3 text-center text-primary">
					<i class="fas fa-ghost mr-2 text-white"></i> <span
						class="d-none d-lg-inline text-white">STOREBEA ADMIN</span>
				</h1>

				<div class="d-flex justify-content-center">
					<div class="list-group text-center text-lg-start navDiv">
						<span class="list-group-item disabled d-none d-lg-block"> <small>ADMINISTRATION</small>
						</span>

						<%
						if (request.getParameter("active").equals("dashboard")) {
						%>
						<a href="/SpringOnlineStore/admin/dashboard"
							class="list-group-item list-group-item-action active"> <i
							class="fas fa-home"></i> <span class="d-none d-lg-inline">Dashboard</span>
						</a>
						<%
						} else {
						%>
						<a href="/SpringOnlineStore/admin/dashboard"
							class="list-group-item list-group-item-action"> <i
							class="fas fa-home"></i> <span class="d-none d-lg-inline">Dashboard</span>
						</a>
						<%
						}
						%>

						<%
						if (request.getParameter("active").equals("account")) {
						%>
						<a href="/SpringOnlineStore/admin/account"
							class="list-group-item list-group-item-action active"> <i
							class="fas fa-user"></i> <span class="d-none d-lg-inline">Account</span>
						</a>
						<%
						} else {
						%>
						<a href="/SpringOnlineStore/admin/account"
							class="list-group-item list-group-item-action"> <i
							class="fas fa-user"></i> <span class="d-none d-lg-inline">Account</span>
						</a>
						<%
						}
						%>
					</div>
				</div>

				<div class="d-flex justify-content-center">
					<div class="list-group mt-4 text-center w-80 text-lg-start navDiv">
						<span class="list-group-item disabled d-none d-lg-block"> <small>Product</small>
						</span>
						<%
						if (request.getParameter("active").equals("productadd")) {
						%>
						<a href="/SpringOnlineStore/admin/productadd"
							class="list-group-item list-group-item-action active"> <i
							class="fas fa-plus"></i> <span class="d-none d-lg-inline">Add
								New Product</span>
						</a>
						<%
						} else {
						%>
						<a href="/SpringOnlineStore/admin/productadd"
							class="list-group-item list-group-item-action"> <i
							class="fas fa-plus"></i> <span class="d-none d-lg-inline">Add
								New Product</span>
						</a>
						<%
						}
						%>

						<%
						if (request.getParameter("active").equals("product")) {
						%>
						<a href="/SpringOnlineStore/admin/product"
							class="list-group-item list-group-item-action active"> <i
							class="fas fa-eye"></i> <span class="d-none d-lg-inline">View
								Product</span>
						</a>
						<%
						} else {
						%>
						<a href="/SpringOnlineStore/admin/product" class="list-group-item list-group-item-action">
							<i class="fas fa-eye"></i> <span class="d-none d-lg-inline">View
								Product</span>
						</a>
						<%
						}
						%>

						<%
						if (request.getParameter("active").equals("category")) {
						%>
						<a href="/SpringOnlineStore/admin/category"
							class="list-group-item list-group-item-action active"> <i
							class="fas fa-calendar-alt"></i> <span class="d-none d-lg-inline">Category</span>
						</a>
						<%
						} else {
						%>
						<a href="/SpringOnlineStore/admin/category" class="list-group-item list-group-item-action">
							<i class="fas fa-calendar-alt"></i> <span
							class="d-none d-lg-inline">Category</span>
						</a>
						<%
						}
						%>
					</div>
				</div>

				<div class="d-flex justify-content-center">
					<div class="list-group mt-4 text-center w-80 text-lg-start navDiv">
						<span class="list-group-item disabled d-none d-lg-block"> <small>Order
								Control</small>
						</span>
						<%
						if (request.getParameter("active").equals("order")) {
						%>
						<a href="/SpringOnlineStore/admin/order"
							class="list-group-item list-group-item-action active"> <i
							class="fas fa-eye"></i> <span class="d-none d-lg-inline">View
								Order</span>
						</a>
						<%
						} else {
						%>
						<a href="/SpringOnlineStore/admin/order" class="list-group-item list-group-item-action">
							<i class="fas fa-eye"></i> <span class="d-none d-lg-inline">View
								Order</span>
						</a>
						<%
						}
						%>
					</div>
				</div>

			</nav>
			<!-- Left Side Nav -->

			<main class="col-10 bg-light">
				<!-- Main (Top Nav & Content) -->

				<nav class="navbar navbar-expand-lg navbar-light bg-secondary">
					<div class="flex-fill"></div>
					<div class="navbar nav">
						<li class="nav-item mx-5 text-white">Account : <%=currentlogin==null?"":currentlogin.getEmail() %></li>
						<li class="nav-item mx-3">
							<form action="/SpringOnlineStore/admin/logout" method="POST">
								<button type="submit" class="btn btn-link text-white"
									style="text-decoration: none;">
									<i class="fas fa-sign-out-alt"></i> Logout
								</button>
							</form>
						</li>
					</div>
				</nav>