<%@page import="org.tfds.beans.Person"%>
<%@page import="org.tfds.utils.DBUtils"%>
<%@page import="org.tfds.conn.MySQLConnUtils"%>
<%@page import="com.mysql.jdbc.Connection"%>
<%@page import="org.tfds.beans.Profile"%>
<%@page import="org.tfds.beans.Like"%>
<%@ page import="org.tfds.AppConstants" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8"%>

<!doctype html>
<html lang="en">

<%!
	// Helper function for getting profile pages
	public String getProfileLink(String profileName) {
		return String.format("<a href='../profile/%s'>%s</a>", profileName, profileName);
	}

	public Person getPersonFromId(Connection conn, String profileName){
		return DBUtils.fetchPersonFromPID(conn, profileName).getEntity();
	}
%>

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
    <title>Likes</title>
</head>

<body>
    <div class="container-fluid">
        <div class="row">
			<%@include file="header.jsp" %>
            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                <div class="container">
                <h1>Likes</h1>
                    <div class="row">
                        <ul class="nav nav-tabs" id="like-tabs" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" id="liked-tab" role="tab" data-toggle="tab" href="#liked" aria-controls="liked" aria-selected="true">Liked</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="likes-tab" role="tab" data-toggle="tab" href="#likes" aria-controls="likes" aria-selected="false">Likes</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="favorites-tab" role="tab" data-toggle="tab" href="#favorites" aria-controls="favorites" aria-selected="false">Favorites</a>
                            </li>
                        </ul>
                    </div>
                    <div id="like-tab-content" class="tab-content">
                        <div id="liked" class="tab-pane fade show active" role="tab-panel" aria-labelledby="liked-tab">
                            <table class="table">
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Name</th>
                                    <th scope="col">Date Liked</th>
                                </tr>
                                <% 
                                Connection conn = (Connection) session.getAttribute("connAttribute");
                                ArrayList<Like> likerAr = (ArrayList<Like>) session.getAttribute(AppConstants.SESSION_USER_LIKES);
                               for(int i=0; i<likerAr.size();i++){
                            	   Person p = getPersonFromId(conn, likerAr.get(i).getLikee());
                            	   out.println("<tr>");
                            	   out.println("<td>" + getProfileLink(likerAr.get(i).getLikee()) + "</td>");
                            	   out.println("<td>" + p.getFirstName() + " " + p.getLastName() + "</td>");
                            	   out.println("<td>" + likerAr.get(i).getDateTime() + "</td>");
									out.println("</tr>");
                            	   }
                            	 %>	
                            </table>
                        </div>
                        <div id="likes" class="tab-pane fade" role="tab-panel" aria-labelledby="likes-tab">
                            <table class="table">
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Name</th>
                                    <th scope="col">Date Liked</th>
                                </tr>
                                <% 
                                ArrayList<Like> likedAr = (ArrayList<Like>) session.getAttribute(AppConstants.SESSION_USER_IS_LIKED);
                               for(int i=0; i<likedAr.size();i++){
                            	   Person p = getPersonFromId(conn, likedAr.get(i).getLiker());
                            	   out.println("<tr>");
                            	   out.println("<td>" + getProfileLink(likedAr.get(i).getLiker()) + "</td>");
                            	   out.println("<td>" + p.getFirstName() + " " + p.getLastName() + "</td>");
                            	   out.println("<td>" + likedAr.get(i).getDateTime() + "</td>");
									out.println("</tr>");
                            	   }
                            	 %>
                               <%-- <c:forEach items="${userIsLiked}" var = "likes">
                                    <tr>
                                    <td>${likes.liker}</td>
                                    <td>${likes.liker}</td>
                                    <td>${likes.date}</td>
                                    </tr>
								</c:forEach>--%>
                            </table>
                        </div>
                        <div id="favorites" class="tab-pane fade" role="tab-panel" aria-labelledby="favorites-tab">
                            <table class="table">
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Name</th>
                                </tr>
                                <% 
                                ArrayList<String> favAr = (ArrayList<String>) session.getAttribute(AppConstants.SESSION_USER_FAVOURITES);
                               for(int i=0; i<favAr.size();i++){
                            	   Person p = getPersonFromId(conn, favAr.get(i).toString());
                            	   out.println("<tr>");
                            	   out.println("<td>" + getProfileLink(favAr.get(i).toString()) + "</td>");
                            	   out.println("<td>" + p.getFirstName() + " " + p.getLastName() + "</td>");
									out.println("</tr>");
                            	   }
                            	 %>
                            </table>
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