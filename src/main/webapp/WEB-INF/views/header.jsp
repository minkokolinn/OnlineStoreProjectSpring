<%@page import="utility.Utility"%>
<%@page import="models.CartRow"%>
<%@page import="java.util.List"%>
<%@page import="models.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Site Metas -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>
	<%
	out.print(request.getParameter("pageTitle"));
	%>
</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.png" type="">

<!-- bootstrap core css -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.css" />
<!-- font awesome style -->
<link href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css" rel="stylesheet" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css"
	rel="stylesheet" />
<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" />
<!-- responsive style -->
<link href="${pageContext.request.contextPath}/resources/css/responsive.css" rel="stylesheet" />

<!-- custom css -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/sample.css">

<!-- jquery -->
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.1.min.js"></script>

</head>
<body>

	<!-- <div class="hero_area"> -->
	<!-- header section strats -->
	<header class="header_section">
		<div class="container">
			<nav class="navbar navbar-expand-lg custom_nav-container ">
				<a class="navbar-brand brandText" href="index.html">Spring-Storebea</a>
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarSupportedContent"
					aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class=""> </span>
				</button>
				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav">
						<%
						if (!request.getParameter("pageName").equals("login") && !request.getParameter("pageName").equals("register")) {
						%>
						<li class="nav-item active"><a class="nav-link"
							href="index.jsp">Home <span class="sr-only">(current)</span></a>
						</li>
						<li class="nav-item"><a class="nav-link" href="/SpringOnlineStore/product">Products</a>
						</li>
						<li class="nav-item"><a class="nav-link"
							href="blog_list.html" data-toggle="modal"
							data-target=".catfilter">Category</a></li>
						<%
							List<CartRow> cartSession = (List<CartRow>)session.getAttribute("cart");
							if(session.getAttribute("cart")==null){
						%>
						<li class='nav-item mr-4'>							
							<div style='position: relative;'>
								<a class='nav-link' href='cart'> <i
									class="fa fa-shopping-cart"
									style="font-size: 26px; color: #223A66;"></i>
								</a>
							</div>
						</li>
						<% }else{ %>
						<li class='nav-item mr-4'>							
							<div style='position: relative;'>
								<a class='nav-link' href='/SpringOnlineStore/cart'> <i
									class="fa fa-shopping-cart"
									style="font-size: 26px; color: #223A66;"></i> <span
									class='badge badge-pill badge-danger'
									style='position: absolute; top: 1px; right: 3px;'> <%=cartSession.size() %> </span>
								</a>
							</div>
						</li>
						<% } %>
						<li class='nav-item mr-4'>
							<div style='position: relative;'>
								<a class='nav-link' href='/SpringOnlineStore/admin/login'> <i
									class="fa fa-lock"
									style="font-size: 26px; color: #223A66;"></i>
								</a>
							</div>
						</li>
						<%
						}
						%>
						<%
						if (request.getParameter("pageName").equals("login")) {
						%>
						<li class="nav-item active"><a class="nav-link"
							href="/SpringOnlineStore/">Home <span class="sr-only">(current)</span></a>
						</li>
						<li class="nav-item"><a class="nav-link" href="/SpringOnlineStore/register">Register</a>
						</li>
						<%
						}
						%>
						<%
						User loginUser = null;
						
						if(Utility.checkUserAuth(request)==0){
						%>
						<li class="nav-item"><a class="nav-link" href="login">Login</a>
						</li>
						<% }else{
							int userId = Utility.checkUserAuth(request);
							User user = Utility.getCurrentLoginedUser(userId);
						%>
						<li class="nav-item"><a class="nav-link" href="/SpringOnlineStore" style="text-decoration:underline;"><%=user==null?"":user.getName() %></a>
						</li>
						<li class="nav-item"><form action="logout" method="POST"><button type="submit" class="nav-link btn btn-link">Logout</button></form>
						</li>
						<% } %>
					</ul>
				</div>
			</nav>
		</div>
	</header>
	<!-- end header section -->