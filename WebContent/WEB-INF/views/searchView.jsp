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
Search
</title>
</head>

<body>
	<div class="container-fluid">
		<div class="row">
			<%@include file="header.jsp"%>
			<main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
				<h5>Search For Profile</h5>
				<form action="${pageContext.request.contextPath }/profile-search/" method="GET">
					<div class = "form-group">
					<label for="profileID">ProfileID: </label>
					<input name="ProfileId" type = "text" class = "form-control" id = "ProfileID">
					</div>
					<div class = "form-group">
					<label for="FirstName">First Name: </label>
					<input name="FirstName" type = "text" class = "form-control" id = "FirstName">
					</div>
					<div class = "form-group">
					<label for="LastName">Last Name: </label>
					<input name="LastName" type = "text" class = "form-control" id = "LastName">
					</div>
					<div class = "form-group">
					<label for="zipcode">Zipcode: </label>
					<input name="Zipcode" type = "number" class = "form-control" id = "zipcode">
					</div>
					<div class = "form-group">
					<label for="city">City: </label>
					<input name="City" type = "text" class = "form-control" id = "city">
					</div>
					<div class = "form-group">
					<label for="state">State: </label>
					<input name="State" type = "text" class = "form-control" id = "state">
					</div>
					<div class = "form-group">
					<label for="gender">Gender: </label>
					<input name="Gender" type = "text" class = "form-control" id = "gender">
					</div>
					<div class = "form-group">
					<label for="height">Height: </label>
					<input name="Height" type = "text" class = "form-control" id = "height">
					</div>
					<div class = "form-group">
					<label for="weight">Weight: </label>
					<input name="Weight" type = "text" class = "form-control" id = "weight">
					</div>
					<div class = "form-group">
					<label for="hairColor">Hair Color: </label>
					<input name="HairColor" type = "text" class = "form-control" id = "hairColor">
					</div>
					<div class = "form-group">
					<label for="hobbies">Hobby: </label>
					<input name="Hobbies" type = "text" class = "form-control" id = "hobbies">
					</div>
				<button class = "btn btn-lg btn-primary" type = "submit">Submit</button>							
				</form>
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