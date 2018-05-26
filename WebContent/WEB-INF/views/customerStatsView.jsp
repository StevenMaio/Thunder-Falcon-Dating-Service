<%@page import="org.tfds.beans.Profile"%>
<%@page import="org.tfds.beans.Like"%>
<%@ page import="org.tfds.AppConstants" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8"%>

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
    <title>Stats</title>
</head>

<body>
    <div class="container-fluid">
        <div class="row">
			<%@include file="header.jsp" %>
            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                <div class="container">
                <div class = "row">
                <h1>Statistics</h1>
                </div>
                    <div class = "row">
                    <div class = "col-md">
                    <h5>Most Active Profiles</h5>
                    	<div id="mostActive" class="tab-pane fade show active" role="tab-panel" aria-labelledby="mostActive-tab">
                            <table class="table">
                                <tr>
                                    <th scope = "col">#</th>
                                    <th scope="col">ID</th>
                                </tr>
                                <% 
                                ArrayList<String> activeAr = (ArrayList<String>) session.getAttribute(AppConstants.MOST_ACTIVE_PROFILES);
                               for(int i=0; i<activeAr.size();i++){
                            	   out.println("<tr>");
                            	   out.println("<td>" + (i+1) + "</td>");
                            	   out.println("<td>" + activeAr.get(i) + "</td>");
									out.println("</tr>");
                            	   }
                            	 %>	
                            </table>
                        </div>
                    </div>
                    <div class = "col-md">
                    <h5>Highest Rated Profiles</h5>
                         <div id="highestRated" class="tab-pane fade show active" role="tab-panel" aria-labelledby="highestRated-tab">
                            <table class="table">
                                <tr>
                                    <th scope = "col">#</th>
                                    <th scope="col">ID</th>
                                    <th scope="col">Rating</th>
                                </tr>
                                <% 
                                ArrayList<String[]> ratedAr = (ArrayList<String[]>) session.getAttribute(AppConstants.HIGHEST_RATED_PROFILES);
                               for(int i=0; i<ratedAr.size();i++){
                            	   out.println("<tr>");
                            	   out.println("<td>" + (i+1) + "</td>");
                            	   out.println("<td>" + ratedAr.get(i)[0] + "</td>");
                            	   out.println("<td>" + ratedAr.get(i)[1] + "</td>");
									out.println("</tr>");
                            	   }
                            	 %>	
                            </table>
                        </div>
                    </div>
                    <div class = "col-md">
                    <h5>Popular Date Locations</h5>
                                        	<div id="mostActive" class="tab-pane fade show active" role="tab-panel" aria-labelledby="mostActive-tab">
                            <table class="table">
                                <tr>
                                	<th scope = "col">#</th>
                                    <th scope="col">Location</th>
                                </tr>
                                <% 
                                ArrayList<String> locationAr = (ArrayList<String>) session.getAttribute(AppConstants.POPULAR_DATE_LOCATIONS);
                               for(int i=0; i<locationAr.size();i++){
                            	   out.println("<tr>");
                            	   out.println("<td>" + (i+1) + "</td>");
                            	   out.println("<td>" + locationAr.get(i) + "</td>");
									out.println("</tr>");
                            	   }
                            	 %>	
                            </table>
                        </div>
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