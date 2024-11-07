<%@page import="models.Category"%>
<%@page import="java.util.List"%>
<%@page import="models.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<jsp:include page="header.jsp">
	<jsp:param value="productadd" name="active" />
</jsp:include>


<div class="container-fluid mt-3 p-4">
	<!-- Content -->

	<div class="row flex-column flex-lg-row">
		<h5
			class="alert alert-primary d-flex justify-content-between align-item-center">
			<span class="d-flex align-items-center">Update Product</span>
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

	<%
	if (request.getAttribute("success") != null) {
	%>
	<div class="row flex-column flex-lg-row">
		<div class="alert alert-success mx-0"><%=request.getAttribute("success")%></div>
	</div>
	<%
	}
	%>
	
	<% 
	Product p = (Product) request.getAttribute("selectedproduct");
	
	if(p==null){
		response.sendRedirect("/SpringOnlineStore/admin/product");
	}
	%>
	

	<div class="row flex-column flex-lg-row">
		<div class="accountupdate">
			<div class="card-body">
				<form action="/SpringOnlineStore/admin/productupdate" method="POST">
					<input type="hidden" name="hiddenpid" value="<%=p==null?"":p.getId() %>">
					<div class="form-floating mb-3">
						<input type="text" class="form-control" id="nameInput"
							placeholder="Name" autocomplete="off" name="name" value="<%=p==null?"":p.getName() %>" required>
						<label for="nameInput">Name</label>
					</div>
					<div class="form-floating mb-3">
						<input type="text" class="form-control" id="nameInput"
							placeholder="Brand" autocomplete="off" name="brand" value="<%=p==null?"":p.getBrand() %>" required>
						<label for="nameInput">Brand</label>
					</div>
					<div class="form-floating mb-3">
						<input type="text" class="form-control" id="nameInput"
							placeholder="Model" autocomplete="off" name="model" value="<%=p==null?"":p.getModel() %>" required>
						<label for="nameInput">Model</label>
					</div>
					<div class="form-floating mb-3">
						<textarea class="form-control" placeholder="Description"
							id="despInput" style="height: 150px;" autocomplete="off"
							name="description" required><%=p==null?"":p.getDescription() %></textarea>
						<label for="despInput">Description</label>
					</div>
					<div class="form-floating mb-3">
						<div class="input-group mb-3">
							<input type="number" class="form-control" placeholder="Price"
								autocomplete="off" name="price" value="<%=p==null?"":p.getPrice() %>" required> <span
								class="input-group-text" id="despInput">MMK</span>
						</div>
					</div>
					<div class="form-floating mb-3">
						<input type="number" class="form-control" id="nameInput"
							placeholder="Quantity" autocomplete="off" name="quantity" value="<%=p==null?"":p.getQuantity() %>"
							required min="1"> <label for="nameInput">Quantity</label>
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