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
Settings
</title>
</head>

<body>
	<div class="container-fluid">
		<div class="row">
			<%@include file="header.jsp"%>
			<main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
				<h4>Settings</h4>
				<br>
				<h5>Account</h5>
				<br>
				<button id="employee-date-button" type="submit" class="btn btn-sm btn-primary" data-toggle = "modal" data-target = "#addCardModal">
                	<i class="fa fa-credit-card"></i>
                    Add Credit Card</button>
                <br>
                <h5>Credit Cards</h5>
                <br>
                
                <%
                	ArrayList<Account> accounts = (ArrayList<Account>) session.getAttribute(AppConstants.CUSTOMER_BANK_ACCOUNTS);
                	if(accounts!=null){
                		out.print("<table class = 'table'>");
                		out.print("<tr><th>Account #</th><th>Card #</th><th>Date Created</th></tr>");
                		for(Account acc : accounts){
                			out.print("<tr>");
                			out.print("<form action='../credit-account/delete' method='POST'>");
                			out.print("<input name='AccountNumber' style='display:none;' value='" + acc.getAcctNum() + "'></input>");
                			out.print("<input name='CardNumber' style='display:none;' value='" + acc.getCardNumber() + "'></input>");
                			out.print("<td>" + acc.getAcctNum() + "</td>");
                			out.print("<td>" + acc.getCardNumber() + "</td>");
                			out.print("<td>" + acc.getAcctCreationDate() + "</td>");
                			out.print("<td>" + "<button class='btn btn-sm btn-danger' type='submit'><i class ='fa fa-times-circle'></i> Delete</button>" + "</td>");
                			out.print("</form>");
                			out.print("</tr>");
               
                		}
                		out.print("</table>");
                	}
                %>
			</main>
		</div>
	</div>

   <div class="modal fade" id="addCardModal" role="dialog" aria-labelledby="addCardModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="addCardModalLabel">Add Payment Method</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
			<form method = "POST" action = "">
			    <div class = "form-group">
					<label for = "cardNumber">Card #: </label>
					<input name="CardNumber" type = "number" class = "form-control" id = "cardNumber">
				</div>
				<div class = "form-group">
					<label for = "accountNumber">Account #: </label>
					<input name="AccountNumber" type = "number" class = "form-control" id = "accountNumber">
				</div>
				<button class = "btn btn-lg btn-primary" type = "submit">Submit</button>							
			</form>
      </div>
    </div>
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