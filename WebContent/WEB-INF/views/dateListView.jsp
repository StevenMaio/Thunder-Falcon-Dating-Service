   <%@page import="org.tfds.beans.Account.AccountType"%>
<%@page import="java.io.IOException"%>
<%@page import="org.tfds.beans.CustomerDate"%>
<%@page import="org.tfds.AppConstants"%>
<%@page import="org.tfds.beans.Report"%>
<%@page import="java.util.ArrayList"%>
<!doctype html>
<html lang="en">

<%! // Helper function for making popups
	public String getProfileLink(String profileName) {
		return String.format("<a href='../profile/%s'>%s</a>", profileName, profileName);
	}

	public void modalFactory(String type, int i, JspWriter stream, Object dataObject) throws IOException{

		stream.print("<div class = 'modal fade' id = '"+type+"-modal-" + i + "' role = 'dialog' aria-labelledby = '"+type+"-modal-title' aria-hidden='true'>");
		stream.print("<div class = 'modal-dialog' role = 'document'>");
		stream.print("<div class = 'modal-content'>");
		stream.print("<div class = 'modal-header'>");
			CustomerDate date = (CustomerDate) dataObject;
			stream.print("<h5 class = 'modal-title' id = "+type+"'-modal-title'>");
			stream.print("Date between " + getProfileLink(date.getProfile1()) + " and " + getProfileLink(date.getProfile2()) + "</h5>");
		
		stream.print("</div>");
		stream.print("<div class = 'modal-body'>");
			stream.print("<div>");
			stream.print("<table class = table-sm>");
			stream.print("<tr>");
			stream.print("<th>Date #</th>");
			stream.print("<td>" + date.getDateId() + "</td>");
			stream.print("</tr>");
			stream.print("<tr>");
			stream.print("<th>Location</th>");
			stream.print("<td>" + date.getLocation() + "</td>");
			stream.print("</tr>");
			stream.print("<tr>");
			stream.print("<th>Booking Fee</th>");
			stream.print("<td>" + date.getBookingFee() + "</td>");
			stream.print("</tr>");
			stream.print("<tr>");
			stream.print("<th>Date</th>");
			stream.print("<td>" + date.getDateTime() + "</td>");
			stream.print("</tr>");
			if (date.getComments() != null) {
				stream.print("<tr>");
//				stream.print("<th>Comment</th>");
				stream.print("<td>" + date.getComments() + "</td>");
				stream.print("</tr>");
			}
			stream.print("</table>");
			stream.print("</div>");
		stream.print("</div>");
		stream.print("<div class = 'modal-footer'>");
		stream.print("</div>");
		stream.print("</div></div></div> ");
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
<title>
View Dates
</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<%@include file="header.jsp"%>
			<main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
      <div class="container">
      	<table class = "table">
      	<tr><th>#</th><th>Profile1</th><th>Profile2</th></tr>
		<% 
		ArrayList<CustomerDate> dates = (ArrayList<CustomerDate>) session.getAttribute(AppConstants.CUSTOMER_DATE_LIST);
		if(dates!=null)
		for(int i=0;i<dates.size();i++){
			CustomerDate d = dates.get(i);
     	   out.println("<tr onclick = 'input' data-toggle='modal' href='#all-modal-"+i+"'>");
			out.print("<td>" + d.getDateId() + "</td>");
			out.print("<td>" + d.getProfile1() + "</td>");
			out.print("<td>" + d.getProfile2() + "</td>");
			out.print("</tr>");
		}
		out.print("</table>");
        for(int i=0; i<dates.size();i++)
			modalFactory("all",i,out,dates.get(i));
     	 %>
		
      </div>
</main></div></div>

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