<%@page import="org.tfds.beans.Account.AccountType"%>
<%@ page import = "org.tfds.beans.CustomerDate" %>
<%@ page import = "org.tfds.beans.Profile" %>
<%@ page import = "org.tfds.beans.BlindDate" %>
<%@ page import = "org.tfds.beans.SuggestedBy" %>
<%@ page import="org.tfds.AppConstants" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.IOException" %>
<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8"%>

<%! // Helper function for making popups
	public String getProfileLink(String profileName) {
		return String.format("<a href='../profile/%s'>%s</a>", profileName, profileName);
	}

	public void modalFactory(String type, int i, JspWriter stream, Object dataObject, String ProfileName, Account userAccount) throws IOException{

		stream.print("<div class = 'modal fade' id = '"+type+"-modal-" + i + "' role = 'dialog' aria-labelledby = '"+type+"-modal-title' aria-hidden='true'>");
		stream.print("<div class = 'modal-dialog' role = 'document'>");
		stream.print("<div class = 'modal-content'>");
		stream.print("<div class = 'modal-header'>");
		if(type=="upcoming" || type=="past"){
			CustomerDate date = (CustomerDate) dataObject;
			stream.print("<h5 class = 'modal-title' id = "+type+"'-modal-title'>");
			if(userAccount.getAccountType().equals(Account.AccountType.CUSTOMER))
				stream.print("Your Date With " + getProfileLink(ProfileName) + "</h5>");
			else
				stream.print("Date With " + getProfileLink(ProfileName) + "</h5>");
			
		}
		if(type=="ref"){
			BlindDate date = (BlindDate) dataObject;
			stream.print("<h5 class = 'modal-title' id = "+type+"'-modal-title'>");
			stream.print("You were referred by " + getProfileLink(date.getProfileA()) + "</h5>");
			
		}
		if(type=="suggest"){
			SuggestedBy date = (SuggestedBy) dataObject;
			stream.print("<h5 class = 'modal-title' id = "+type+"'-modal-title'>");
			stream.print("You were referred by an employee</h5>");
			
		}
		stream.print("</div>");
		stream.print("<div class = 'modal-body'>");
		if(type=="upcoming" || type=="past"){
			CustomerDate date = (CustomerDate) dataObject;
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

			stream.print("</table>");
			if (date.getComments() != null) {
				stream.print("<tr>");
//				stream.print("<th>Comment</th>");
				stream.print("<td>" + date.getComments() + "</td>");
				stream.print("</tr>");
			}
			stream.print("</div>");
		}

		if(type=="ref"){
			BlindDate date = (BlindDate) dataObject;
			stream.print("<div>");
			stream.print("<table class = table-sm>");
			stream.print("<tr>");
			stream.print("<th>ID</th>");
			stream.print("<td>" + getProfileLink(ProfileName) + "</td>");
			stream.print("</tr>");
			stream.print("<tr>");
			stream.print("<th>Date</th>");
			stream.print("<td>" + date.getDateTime() + "</td>");
			stream.print("</tr>");
			stream.print("</table>");
			stream.print("</div>");
		}
		if(type=="suggest"){
			SuggestedBy date = (SuggestedBy) dataObject;
			stream.print("<div>");
			stream.print("<table class = table-sm>");
			stream.print("<tr>");
			stream.print("<th>ID</th>");
			stream.print("<td>" + getProfileLink(ProfileName) +"</td>");
			stream.print("</tr>");
			stream.print("<tr>");
			stream.print("<th>Date</th>");
			stream.print("<td>" + date.getDateTime() + "</td>");
			stream.print("</tr>");
			stream.print("</table>");
			stream.print("</div>");
		}
		if(type=="past"){
			CustomerDate date = (CustomerDate) dataObject;
			if(date.getUser1Rating()!=0 || date.getUser2Rating()!=0){
				stream.print("<table class = table>");
				stream.print("<tr>");
				if(date.getUser1Rating()!=0){
					stream.print("<th>"+ date.getProfile1() +"</th>");
					stream.print("<td>"+ date.getUser1Rating() +"</td>");
				}
				if(date.getUser2Rating()!=0){
					stream.print("<th>"+ date.getProfile2() +"</th>");
					stream.print("<td>"+ date.getUser2Rating() +"</td>");
				}
				stream.print("</tr>");
				stream.print("</table>");
			}
		}
		stream.print("</div>");
		stream.print("<div class = 'modal-footer'>");
		if (type =="upcoming"){
			stream.print("<form action='../delete/date' method='POST'><input type='text' value='" +  ((CustomerDate) dataObject).getDateId() + "' style='visibility: hidden;' name='DateId'><button id='like-button' type='submit' class='btn btn-sm btn-danger'><i class='fa fa-times-circle'></i> Cancel</button></form>");
		} else if (type=="past" && userAccount.getAccountType() == AccountType.CUSTOMER) {
			CustomerDate date = (CustomerDate) dataObject;
			stream.print("<form action='../comment/" + date.getDateId() + "' method='POST'>");
			stream.print("<div class = 'row'>");
			stream.print("<div class = 'col-md'>");
			stream.print("<input class = 'form-control form-inline' type='text' value='' name='Comment'>");
			//stream.print("<form action='../rate/" + date.getDateId() + "' method='POST'>");
			stream.print("</div>");
			stream.print("<div class = 'col-md'>");
			stream.print("<div class='form-group form-inline'>");
			stream.print("<label for='ratingForm'>Rate</label>");
			stream.print("<select name='Rating' class='form-control form-inline' id='ratingForm'>");
			stream.print("<option>1</option><option>2</option><option>3</option><option>4</option><option>5</option>");
			stream.print("</select>");
			stream.print("</div>");
			stream.print("<button class = 'btn btn-sm btn-primary' type='submit' value='Post'>Submit</button>");
			stream.print("</div>");
			stream.print("</div>");
			stream.print("</form>");
		}
		stream.print("</div>");
		stream.print("</div></div></div> ");
	}
%>

<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
        crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.10/css/all.css" integrity="sha384-+d0P83n9kaQMCwj8F4RJB66tzIwOKmrdb46+porD/OvrJ+37WqIM7UoBtwHO6Nlg"
        crossorigin="anonymous">


    <style>
        <%@include file="/WEB-INF/css/dashboard.css"%>
    </style>
    <title>Dates</title>
</head>

<body>
    <div class="container-fluid">
        <div class="row">
            
			<%@include file="header.jsp"%>
            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                <div class="container">
                    <h1>Dates</h1>
                    <div class="row">
                        <ul class="nav nav-tabs" id="date-tabs" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" id="upcoming-tab" role="tab" data-toggle="tab" href="#upcoming" aria-controls="upcoming" aria-selected="true">Upcoming</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="past-tab" role="tab" data-toggle="tab" href="#past" aria-controls="past" aria-selected="false">Past</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="referral-tab" role="tab" data-toggle="tab" href="#referral" aria-controls="referral" aria-selected="false">Referrals</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="suggestion-tab" role="tab" data-toggle="tab" href="#suggestions" aria-controls="suggestions" aria-selected="false">Suggestions</a>
                            </li>
                        </ul>
                    </div>
                    <div id="date-tab-content" class="tab-content">
                            <div id="upcoming" class="tab-pane fade show active" role="tab-panel" aria-labelledby="upcoming-tab">
                                <table class="table table-hover">
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">Location</th>
                                        <th scope="col">Date</th>
                                    </tr>
                                <% 
                           	Account userAccount = (Account) session.getAttribute(AppConstants.SESSION_ACCOUNT);
                           	String currentID = "";
       						if(userAccount.getAccountType().equals(Account.AccountType.CUSTOMER)){
                         	   currentID = ((Profile) session.getAttribute(AppConstants.SESSION_PROFILE)).getProfileID();
       						}

                                ArrayList<CustomerDate> upcomingAr = (ArrayList<CustomerDate>) session.getAttribute(AppConstants.SESSION_UPCOMING_DATES);
                               for(int i=0; i<upcomingAr.size();i++){
                            	   out.println("<tr onclick = 'input' data-toggle='modal' href='#upcoming-modal-"+i+"'>");
                            	   if(upcomingAr.get(i).getProfile1().equals(currentID)){
                            	   		out.println("<td>" + upcomingAr.get(i).getProfile2() + "</td>");
                            	   }
                            	   else{
                            		   out.println("<td>" + upcomingAr.get(i).getProfile1() + "</td>");
                            	   }
                            	   out.println("<td>" + upcomingAr.get(i).getLocation() + "</td>");
                            	   out.println("<td>" + upcomingAr.get(i).getDateTime() + "</td>");
									out.println("</tr>");
								
                            	   }
                               out.println("</table>");
                               for(int i=0;i<upcomingAr.size();i++)
									modalFactory("upcoming",i,out,upcomingAr.get(i),(upcomingAr.get(i).getProfile1().equals(currentID)?
											upcomingAr.get(i).getProfile2():upcomingAr.get(i).getProfile1()),userAccount);
                            	
                            	 %>
                            </div>
                            <div id="past" class="tab-pane fade" role="tab-panel" aria-labelledby="past-tab">
                                <table class="table table-hover">
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">Location</th>
                                        <th scope="col">Date</th>
                                    </tr>
                                <% 
                                ArrayList<CustomerDate> pastAr = (ArrayList<CustomerDate>) session.getAttribute(AppConstants.SESSION_PAST_DATES);
                               for(int i=0; i<pastAr.size();i++){
                            	   out.println("<tr onclick = 'input' data-toggle='modal' href='#past-modal-"+i+"'>");
                            	   if(pastAr.get(i).getProfile1().equals(currentID)){
                            	   out.println("<td>" + pastAr.get(i).getProfile2() + "</td>");
                            	   }
                            	   else{
                            		   out.println("<td>" + pastAr.get(i).getProfile1() + "</td>");
                            	   }
                            	   out.println("<td>" + pastAr.get(i).getLocation() + "</td>");
                            	   out.println("<td>" + pastAr.get(i).getDateTime() + "</td>");
									out.println("</tr>");
									
									/*modalFactory("past",i,out,pastAr.get(i),(pastAr.get(i).getProfile1().equals(currentID)?
											pastAr.get(i).getProfile2():pastAr.get(i).getProfile1()));)*/

                            	   }
                               out.println("</table>");
                               for(int i=0;i<pastAr.size();i++)
                               modalFactory("past",i,out,pastAr.get(i),(pastAr.get(i).getProfile1().equals(currentID)?
										pastAr.get(i).getProfile2():pastAr.get(i).getProfile1()),userAccount);
                            	   
                            	 %>
                            </div>
                            <div id="referral" class="tab-pane fade" role="tab-panel" aria-labelledby="referral-tab">
                                <table class="table table-hover">
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">Date</th>
                                    </tr>
                                <% 
                                ArrayList<BlindDate> refAr = (ArrayList<BlindDate>) session.getAttribute(AppConstants.SESSION_BLIND_DATES);
                               for(int i=0; i<refAr.size();i++){
                            	   out.println("<tr onclick = 'input' data-toggle='modal' href='#ref-modal-"+i+"'>");
                            	   if(refAr.get(i).getProfileC().equals(currentID)){
                            	   out.println("<td>" + refAr.get(i).getProfileB() + "</td>");
                            	   }
                            	   else{
                            		   out.println("<td>" + refAr.get(i).getProfileC() + "</td>");
                            	   }
                            	   out.println("<td>" + refAr.get(i).getDateTime() + "</td>");
									out.println("</tr>");
									
                            	   }
                               out.println("</table>");
                               for(int i=0; i<refAr.size();i++)
								modalFactory("ref",i,out,refAr.get(i),(refAr.get(i).getProfileC().equals(currentID)?
										refAr.get(i).getProfileB():refAr.get(i).getProfileC()),userAccount);
                            	 %>
                            </div>
                            <div id="suggestions" class="tab-pane fade" role="tab-panel" aria-labelledby="suggestion-tab">
                                <table class="table table-hover">
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">Date</th>
                                    </tr>
                                <% 
                                ArrayList<SuggestedBy> suggestAr = (ArrayList<SuggestedBy>) session.getAttribute(AppConstants.SESSION_SUGGESTIONS);
                               for(int i=0; i<suggestAr.size();i++){
                            	   out.println("<tr onclick = 'input' data-toggle='modal' href='#suggest-modal-"+i+"'>");
                            	   if(suggestAr.get(i).getProfile1().equals(currentID)){
                            	   out.println("<td>" + suggestAr.get(i).getProfile2() + "</td>");
                            	   }
                            	   else{
                            		   out.println("<td>" + suggestAr.get(i).getProfile1() + "</td>");
                            	   }
                            	   out.println("<td>" + suggestAr.get(i).getDateTime() + "</td>");
									out.println("</tr>");
									
                            	   }
                               out.println("</table>");
                               for(int i=0; i<suggestAr.size();i++)
								modalFactory("suggest",i,out,suggestAr.get(i),(suggestAr.get(i).getProfile1().equals(currentID)?
										suggestAr.get(i).getProfile2():suggestAr.get(i).getProfile1()),userAccount);
                            	 %>
                            </div>
                        </div>
                </div>
            </main>
        </div>
    </div>


    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
</body>

</html>