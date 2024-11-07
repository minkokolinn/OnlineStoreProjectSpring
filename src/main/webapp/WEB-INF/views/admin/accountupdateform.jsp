<%@page import="java.text.SimpleDateFormat"%>
<%@page import="models.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="header.jsp">
	<jsp:param value="account" name="active" />
</jsp:include>

<%
User user = null;
if (request.getAttribute("usertoupdate") == null) {
	response.sendRedirect("/SpringOnlineStore/admin/account");
} else {
	user = (User)request.getAttribute("usertoupdate");
}
%>

<div class="container-fluid mt-3 p-4">
	<!-- Content -->

	<div class="row flex-column flex-lg-row">
		<h5
			class="alert alert-primary d-flex justify-content-between align-item-center">
			<span class="d-flex align-items-center">Account Update</span>
		</h5>
	</div>

	<%
	if (request.getAttribute("error") != null) {
	%>
	<div class="row flex-column flex-lg-row">
		<div class="alert alert-danger mx-0"><%=request.getAttribute("error")%></div>
	</div>
	<%
	}
	%>

	<div class="row flex-column flex-lg-row">
		<div class="accountupdate">
			<div class="card-body">
				<form action="/SpringOnlineStore/admin/accountupdate" method="POST">
					<input type="hidden" name="hiddenid" value="<%=user==null?"":user.getId() %>">
					<div class="form-floating mb-3">
						<input type="text" class="form-control" id="nameInput"
							placeholder="Name" autocomplete="off" name="name"
							value="<%=user == null ? "" : user.getName()%>" required>
						<label for="nameInput">Name</label>
					</div>
					<div class="form-floating mb-3">
						<input type="email" class="form-control" id="emailInput"
							placeholder="Email" autocomplete="off" name="email"
							value="<%=user == null ? "" : user.getEmail()%>" required>
						<label for="emailInput">Email</label>
					</div>
					<div class="form-floating mb-3">
						<input type="text" class="form-control" id="nrcInput"
							placeholder="NRC" autocomplete="off" name="nrc"
							value="<%=user == null ? "" : user.getNrc()%>"> <label
							for="nrcInput">NRC</label>
					</div>
					<div class="form-floating mb-3">
						<input type="number" class="form-control" id="phoneInput"
							placeholder="Phone" autocomplete="off" name="phone"
							value="<%=user == null ? "" : user.getPhone()%>"> <label
							for="phoneInput">Phone</label>
					</div>
					<div class="form-floating mb-3">
						<input type="date" class="form-control" id="dobInput"
							placeholder="Date of birth" autocomplete="off"
							value="<%=user == null || user.getDob() == null ? "" : new SimpleDateFormat("yyyy-MM-dd").format(user.getDob())%>"
							name="dob"> <label for="dobInput">Date of Birth</label>
					</div>
					<div class="form-floating mb-3">
						<textarea class="form-control" placeholder="Address"
							id="despInput" style="height: 150px;" autocomplete="off"
							name="address"><%=user == null ? "" : user.getAddress()%></textarea>
						<label for="despInput">Address</label>
					</div>

					<div class="form-floating mb-3">

						<div class="form-check">
							<%
							if (user != null) {
								if (user.isIsadmin()) {
							%>
							<input class="form-check-input" type="checkbox"
								id="flexCheckDefault" name="isadmin" checked> <label
								class="form-check-label" for="flexCheckDefault">
								Admin account </label>
							<%
							} else {
							%>
							<input class="form-check-input" type="checkbox"
								id="flexCheckDefault" name="isadmin"> <label
								class="form-check-label" for="flexCheckDefault"> Admin
								account </label>
							<%
							}
							}
							%>
						</div>
					</div>

					<div class="mb-3 d-flex justify-content-end">
						<button type="submit" class="btn btn-primary">Update</button>
					</div>
				</form>
			</div>
		</div>
	</div>

</div>

<jsp:include page="footer.jsp"></jsp:include>