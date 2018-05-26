<%@page import="org.tfds.beans.Profile"%>
<%@page import="org.tfds.beans.Like"%>
<%@ page import="org.tfds.AppConstants"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.0.10/css/all.css"
	integrity="sha384-+d0P83n9kaQMCwj8F4RJB66tzIwOKmrdb46+porD/OvrJ+37WqIM7UoBtwHO6Nlg"
	crossorigin="anonymous">

<style>
<%@ include file ="/WEB-INF/css/dashboard.css" %>
</style>
<title>
<% if ((boolean) session.getAttribute(AppConstants.CREATING_NEW_EMPLOYEE)) %>New Employee
<% else %>Edit Employee
</title>
</head>

<body>
	<div class="container-fluid">
		<div class="row">
			<%@include file="header.jsp"%>
			<main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
			<div class="container">
			<% if ((boolean) session.getAttribute(AppConstants.CREATING_NEW_EMPLOYEE)) { %>
				<h5>Create Employee</h5>
				<form method = "POST" action = "">
				    <div class = "form-group">
						<label for = "ssn">SSN:</label>
						<input name="SSN" type = "text" class = "form-control" id = "ssn">
					</div>
					<div class = "form-group">
						<label for = "password">Password:</label>
						<input name="Password" type = "password" class = "form-control" id = "password">
					</div>
					<div class = "form-group">
						<label for = "firstName">First Name:</label>
						<input name="FirstName" "text" class = "form-control" id = "firstName">
					</div>
					<div class = "form-group">
						<label for = "lastName">Last Name:</label>
						<input name="LastName" type = "text" class = "form-control" id = "lastName">
					</div>
					<div class = "form-group">
						<label for = "street">Street:</label>
						<input name="Street" type = "text" class = "form-control" id = "street">
					</div>	
					<div class = "form-group">
						<label for = "city">City:</label>
						<input name="City" type = "text" class = "form-control" id = "city">
					</div>				
					<div class = "form-group">
						<label for = "state">State:</label>
						<input name="State" type = "text" class = "form-control" id = "state">
					</div>			
					<div class = "form-group">
						<label for = "zipCode">Zip Code:</label>
						<input name="Zipcode" type = "number" class = "form-control" id = "zipCode">
					</div>	
					<div class = "form-group">
						<label for = "email">Email:</label>
						<input name="Email" type = "email" class = "form-control" id = "email">
					</div>	
					<div class = "form-group">
						<label for = "telephone">Phone #:</label>
						<input name="Telephone" type = "tel" class = "form-control" id = "telephone">
					</div>
					<div class = "form-group">
						<label for = "salary">Salary:</label>
						<input name="HourlyRate" type = "text" class = "form-control" id = "salary">
					</div>
					<div class = "form-group">
						<label for = "role">Role:</label>
						<input name="Role" type = "text" class = "form-control" id = "role" value="CustRep">
					</div>
					<button class = "btn btn-lg btn-primary" type = "submit">Submit</button>							
				</form>
				<%} else { %>
				<h5>Edit Employee</h5>
        		<form method="POST" action="">
        			<div class = "form-group">
						<label for = "ssn">SSN:</label>
						<input type = "text" name="SSN" class = "form-control" id = "ssn" disabled value="${pagePerson.SSN}">
					</div>
					<div class = "form-group">
						<label for = "password">Password:</label>
						<input type = "password" name="Password" class = "form-control" id = "password" value="${pagePerson.password}">
					</div>
					<div class = "form-group">
						<label for = "firstName">First Name:</label>
						<input type = "text" name="FirstName" class = "form-control" id = "firstName" value="${pagePerson.firstName}">
					</div>
					<div class = "form-group">
						<label for = "lastName">Last Name:</label>
						<input type = "text" name="LastName" class = "form-control" id = "lastName" value="${pagePerson.lastName}">
					</div>
					<div class = "form-group">
						<label for = "street">Street:</label>
						<input type = "text" name="Street" class = "form-control" id = "street" value="${pagePerson.street}">
					</div>	
					<div class = "form-group">
						<label for = "city">City:</label>
						<input type = "text" name="City" class = "form-control" id = "city" value="${pagePerson.city}">
					</div>				
					<div class = "form-group">
						<label for = "state">State:</label>
						<input type = "text" name="State" class = "form-control" id = "state" value="${pagePerson.state}">
					</div>			
					<div class = "form-group">
						<label for = "zipCode">Zip Code:</label>
						<input type = "number" name="Zipcode" class = "form-control" id = "zipCode" value="${pagePerson.zipcode}">
					</div>	
					<div class = "form-group">
						<label for = "email">Email:</label>
						<input type = "email" name="Email" class = "form-control" id = "email" value="${pagePerson.email}">
					</div>	
					<div class = "form-group">
						<label for = "telephone">Phone #:</label>
						<input type = "tel" name="Telephone" class = "form-control" id = "telephone" value="${pagePerson.telephone}">
					</div>
					<div class = "form-group">
						<label for = "salary">Salary:</label>
						<input name="HourlyRate" type = "text" class = "form-control" id = "salary" value="${pageEmployee.hourlyRate}">
					</div>
					<div class = "form-group">
						<label for = "role">Role:</label>
						<input name="Role" type = "text" class = "form-control" id = "role" value="${pageEmployee.role }">
					</div>
					<button class = "btn btn-lg btn-primary" type = "submit">Submit</button>							
				</form>
				
				<% } %>
			</div>
			</main>
		</div>
	</div>
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
		integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
		crossorigin="anonymous"></script>
</body>

</html>