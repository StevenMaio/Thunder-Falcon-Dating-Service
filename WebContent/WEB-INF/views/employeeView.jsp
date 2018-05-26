<%@page import="org.tfds.beans.Report"%>
<%@page import="java.util.Date"%>
<%@page import="com.mysql.jdbc.Connection"%>
<%@page import="org.tfds.utils.DBUtils"%>
<%@page import="org.tfds.beans.Profile"%>
<%@page import="org.tfds.beans.Person"%>
<%@page import="org.tfds.beans.Like"%>
<%@ page import="org.tfds.AppConstants"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">

<%!
	// Helper function for getting profile pages
	public String getProfileLink(String profileName) {
		return String.format("<a href='./profile/%s'>%s</a>", profileName, profileName);
	}

	public Person getPersonFromId(Connection conn, String profileName){
		return DBUtils.fetchPersonFromPID(conn, profileName).getEntity();
	}
	
	public void generateModal(){
		System.out.println("ARG");
	}
%>

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
<title>Stats</title>
</head>

<body>
	<div class="container-fluid">
		<div class="row">
			<%@include file="header.jsp"%>
			<main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
			<div class="container">
				<h5>Employee Dashboard</h5>
				<div class="row">
					<div class="col-sm">
						<div class="list-group">
							<button type="button"
								class="list-group-item list-group-item-action active"
								data-toggle = "modal"
								data-target = "#customerlistModal">
								Customer List</button>
							<button type="button"
								class="list-group-item list-group-item-action"
								data-toggle = "modal"
								data-target = "#mostactiveModal">
								Most Active Customers</button>
							<button type="button"
								class="list-group-item list-group-item-action"
								data-toggle = "modal"
								data-target = "#highestratedprofilesModal">
								Highest Rated Profiles</button>
							<button type="button"
								class="list-group-item list-group-item-action"
								data-toggle = "modal"
								data-target = "#populardatelocationsModal">
								Popular Date Locations</button>
							<button type="button"
								class="list-group-item list-group-item-action"
								data-toggle = "modal"
								data-target = "#highestgrossingcustomersModal">
								Highest Grossing Customers</button>
							<button type="button"
								class="list-group-item list-group-item-action"
								data-toggle = "modal"
								data-target = "#mailinglistModal">
								Customer Mailing List</button>
							<button type="button"
								class="list-group-item list-group-item-action"
								data-toggle = "modal"
								data-target = "#highestgrossingemployeesModal">
								Highest Grossing Employees</button>
							<button type="button"
								class="list-group-item list-group-item-action"
								data-toggle = "modal"
								data-target = "#populardaysModal">
								Popular Calendar Days</button>
						</div>

					</div>
					<div class="col-md">
					<div class = "row">
					<a class = "btn btn-sm btn-primary" href = "${pageContext.request.contextPath }/customer/create">Create User</a>
					</div>
					<% 
					Account account = (Account) session.getAttribute(AppConstants.SESSION_ACCOUNT);
					if(account.getAccountType()==Account.AccountType.MANAGER){
					%>
					<div class = "row">
					<a class = "btn btn-sm btn-primary" href = "${pageContext.request.contextPath }/employee/create">Create Employee</a>
					</div>
					<div class = "row">
					<button type="button" class="btn btn-sm btn-primary" data-toggle = "modal" data-target = "#salesReportModal">View Sales Reports</button>
					</div>
					<div class = "row">	
					<button type="button" class="btn btn-sm btn-primary" data-toggle = "modal" data-target = "#searchDateModal">View Calendar Dates</button>
					</div>
					<div class = "row">	
					<button type="button" class="btn btn-sm btn-primary" data-toggle = "modal" data-target = "#searchProfileModal">View Profile</button>
					</div>
					<% } %>
					</div>
				</div>
				<script type="text/javascript">
					$('.list-group-item').on(
							'click',
							function() {
								var $this = $(this);
								var $alias = $this.data('alias');

								$('.active').filter('.list-group-item')
										.removeClass('active');
								$this.toggleClass('active')
							})
				</script>
			</div>
			</main>
		</div>
	</div>
	
<%	//Define conn for using JDBC functions	
	Connection conn = (Connection) session.getAttribute("connAttribute");
 %>
      <div class="modal fade" id="searchProfileModal" role="dialog" aria-labelledby="searchProfileModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="searchProfileModalLabel">Search for Profile</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
		<form action = "${pageContext.request.contextPath}/profile/" method = "GET">
  			<div class = "form-group">
			<label for = "profile">Profile: </label>
			<input name="Profile" type = "text" class = "form-control" id = "profile">
			</div>
			<button id="searchProfileButton" class = "btn btn-lg btn-primary" type = "submit">View Profile</button>							
		</form>
      </div>
    </div>
  </div>
</div>
     <div class="modal fade" id="searchDateModal" role="dialog" aria-labelledby="searchDateModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="searchDateModalLabel">Search for Date</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
		<form action = "${pageContext.request.contextPath}/calendar-date/" method = "GET">
			<div class="form-group">
			<label for="day">Day: </label>
			<input name="Day" type = "number" class = "form-control" id = "day">
			</div>
			<div class="form-group form-inline">
    		<label for="month">Month: </label>
    		<select name = "Month" class="form-control" id="month">
    		<% for(int i=1;i<=12;i++){
    				out.print("<option>"+i+"</option>");
    			} %>
		    </select>
  			</div>
  			<div class = "form-group">
			<label for = "year">Year: </label>
			<input name="Year" type = "number" class = "form-control" id = "year">
			</div>
			<button id="viewReportButton" class = "btn btn-lg btn-primary" type = "submit">View Report</button>							
		</form>
      </div>
    </div>
  </div>
</div>
 
    <div class="modal fade" id="salesReportModal" role="dialog" aria-labelledby="salesReportModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="salesReportModalLabel">Obtain a Sales Report</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
		<form action = "${pageContext.request.contextPath}/employee/sales" method = "GET">
			<div class="form-group form-inline">
    		<label for="month">Month: </label>
    		<select name = "Month" class="form-control" id="month">
    		<% for(int i=1;i<=12;i++){
    				out.print("<option>"+i+"</option>");
    			} %>
		    </select>
  			</div>
  			<div class = "form-group">
			<label for = "year">Year: </label>
			<input name="Year" type = "number" class = "form-control" id = "year">
			</div>
			<button id="viewReportButton" class = "btn btn-lg btn-primary" type = "submit">View Report</button>							
		</form>
		
      </div>
    </div>
  </div>
</div>



   <div class="modal fade" id="customerlistModal" role="dialog" aria-labelledby="customerlistModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="customerlistModalLabel">Customer List</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
		<% 
      	ArrayList<String[]> customerListAr = (ArrayList<String[]>) session.getAttribute(AppConstants.CUSTOMER_LIST);
		out.println("<table class = 'table-sm'>");
		out.println("<tr><th>ID</th><th>Name</th></tr>");
        for(int i=0; i<customerListAr.size();i++){
        	Person p = getPersonFromId(conn, customerListAr.get(i)[0]);
        	out.println("<tr>");
        	out.println("<td>" + getProfileLink(customerListAr.get(i)[0]) + "</td>");
        	out.println("<td>" + p.getFirstName() + " " + p.getLastName() + "</td>");
			out.println("</tr>");
        }
		out.println("</table>");
        %>
      </div>
    </div>
  </div>
</div>

   <div class="modal fade" id="mostactiveModal" role="dialog" aria-labelledby="mostactiveModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="mostactiveModalLabel">Most Active Profiles</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
		<% 
      	ArrayList<String> mostActiveAr = (ArrayList<String>) session.getAttribute(AppConstants.MOST_ACTIVE_PROFILES);
		out.println("<table class = 'table-sm'>");
		out.println("<tr><th>ID</th><th>Name</th></tr>");
        for(int i=0; i<mostActiveAr.size();i++){
        	Person p = getPersonFromId(conn, mostActiveAr.get(i));
        	out.println("<tr>");
        	out.println("<td>" + getProfileLink(mostActiveAr.get(i)) + "</td>");
        	out.println("<td>" + p.getFirstName() + " " + p.getLastName() + "</td>");
			out.println("</tr>");
        }
		out.println("</table>");
        %>
      </div>
    </div>
  </div>
</div>

   <div class="modal fade" id="highestratedprofilesModal" role="dialog" aria-labelledby="highestratedprofilesModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="highestratedprofilesModalLabel">Highest Rated Profiles</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
		<% 
      	ArrayList<String[]> highestRatedAr = (ArrayList<String[]>) session.getAttribute(AppConstants.HIGHEST_RATED_PROFILES);
		out.println("<table class = 'table-sm'>");
		out.println("<tr><th>ID</th><th>Name</th><th>Rating</th></tr>");
        for(int i=0; i<highestRatedAr.size();i++){
        	Person p = getPersonFromId(conn, highestRatedAr.get(i)[0]);
        	out.println("<tr>");
        	out.println("<td>" + getProfileLink(highestRatedAr.get(i)[0]) + "</td>");
        	out.println("<td>" + p.getFirstName() + " " + p.getLastName() + "</td>");
        	out.println("<td>" + highestRatedAr.get(i)[1] + "</td>");
			out.println("</tr>");
        }
		out.println("</table>");
        %>
      </div>
    </div>
  </div>
</div>

   <div class="modal fade" id="populardatelocationsModal" role="dialog" aria-labelledby="populardatelocationsModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="populardatelocationsModalLabel">Popular Date Locations</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
		<% 
      	ArrayList<String> popularLocationsAr = (ArrayList<String>) session.getAttribute(AppConstants.POPULAR_DATE_LOCATIONS);
		out.println("<table class = 'table-sm'>");
		out.println("<tr><th>#</th><th>Location</th></tr>");
        for(int i=0; i<popularLocationsAr.size();i++){
        	out.println("<tr>");
        	out.println("<td>" + (i+1) + "</td>");
        	out.println("<td>" + popularLocationsAr.get(i) + "</td>");
			out.println("</tr>");
        }
		out.println("</table>");
        %>
      </div>
    </div>
  </div>
</div>

   <div class="modal fade" id="highestgrossingcustomersModal" role="dialog" aria-labelledby="highestgrossingcustomersModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="highestgrossingcustomersModalLabel">Highest Grossing Customers</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
		<% 
      	ArrayList<String[]> grossingCustomersAr = (ArrayList<String[]>) session.getAttribute(AppConstants.HIGHEST_GROSSING_CUSTOMERS);
		out.println("<table class = 'table-sm'>");
		out.println("<tr><th>ID</th><th>Name</th><th>Profit</th></tr>");
        for(int i=0; i<grossingCustomersAr.size();i++){
        	Person p = getPersonFromId(conn, grossingCustomersAr.get(i)[0]);
        	out.println("<tr>");
        	out.println("<td>" + getProfileLink(grossingCustomersAr.get(i)[0]) + "</td>");
        	out.println("<td>" + p.getFirstName() + " " + p.getLastName() + "</td>");
        	out.println("<td>" + grossingCustomersAr.get(i)[1] + "</td>");
			out.println("</tr>");
        }
		out.println("</table>");
        %>
      </div>
    </div>
  </div>
</div>

   <div class="modal fade" id="mailinglistModal" role="dialog" aria-labelledby="mailinglistModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="mailinglistModalLabel">Customer Mailing List</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
		<% 
      	ArrayList<String[]> mailingListAr = (ArrayList<String[]>) session.getAttribute(AppConstants.CUSTOMER_MAILING_LIST);
		out.println("<table class = 'table-sm'>");
		out.println("<tr><th>ID</th><th>Name</th><th>Email</th></tr>");
        for(int i=0; i<mailingListAr.size();i++){
        	Person p = getPersonFromId(conn, mailingListAr.get(i)[0]);
        	out.println("<tr>");
        	out.println("<td>" + getProfileLink(mailingListAr.get(i)[0]) + "</td>");
        	out.println("<td>" + p.getFirstName() + " " + p.getLastName() + "</td>");
        	out.println("<td>" + (mailingListAr.get(i)[1]) + "</td>");
			out.println("</tr>");
        }
		out.println("</table>");
        %>
      </div>
    </div>
  </div>
</div>

   <div class="modal fade" id="highestgrossingemployeesModal" role="dialog" aria-labelledby="highestgrossingemployeesModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="highestgrossingemployeesModalLabel">Highest Grossing Employee</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
		<% 
      	ArrayList<String[]> grossingEmployeesAr = (ArrayList<String[]>) session.getAttribute(AppConstants.HIGHEST_GROSSING_EMPLOYEES);
		out.println("<table class = 'table-sm'>");
		out.println("<tr><th>SSN</th><th>Profit</th></tr>");
        for(int i=0; i<grossingEmployeesAr.size();i++){
        	out.println("<tr>");
        	out.println("<td>" + grossingEmployeesAr.get(i)[0] + "</td>");
        	out.println("<td>" + grossingEmployeesAr.get(i)[1] + "</td>");
			out.println("</tr>");
        }
		out.println("</table>");
        %>
      </div>
    </div>
  </div>
</div>

   <div class="modal fade" id="populardaysModal" role="dialog" aria-labelledby="populardaysModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="populardaysModalLabel">Popular Calendar Days</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
		<% 
      	ArrayList<Date> popularDaysAr = (ArrayList<Date>) session.getAttribute(AppConstants.POPULAR_CALENDAR_DAYS);
		out.println("<table class = 'table-sm'>");
		out.println("<tr><th>#</th><th>Date</th></tr>");
        for(int i=0; i<popularDaysAr.size();i++){
        	out.println("<tr>");
        	out.println("<td>" + (i+1) + "</td>");
        	out.println("<td>" + popularDaysAr.get(i).toString() + "</td>");
			out.println("</tr>");
        }
		out.println("</table>");
        %>
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