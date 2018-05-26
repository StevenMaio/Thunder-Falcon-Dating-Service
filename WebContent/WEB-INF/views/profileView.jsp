<%@page import="org.tfds.utils.DBUtils"%>
<%@page import="org.tfds.beans.Person"%>
<%@page import="com.mysql.jdbc.Connection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.tfds.beans.Profile"%>
<%@page import="org.tfds.beans.Account"%>


<%@ page import="org.tfds.AppConstants" %>
<!doctype html>
<html lang="en">

<%! // Helper functions
	public String getProfileLink(String profileName) {
		return String.format("<a href='../profile/%s'>%s</a>", profileName, profileName);
	}

	public String pickRand(String[] s){
		int r = (int) (Math.random()*s.length);
		return s[r];
	}
	
	public Person getPersonFromId(Connection conn, String profileName){
		return DBUtils.fetchPersonFromPID(conn, profileName).getEntity();
	}

	public String getDescription(Profile p, Person person){
		String result = "";
		result+=(pickRand(new String[]{"Hello! ","Hi. ","Salutations! "}));
		//result+="My name is " + person.getFirstName() + " " + person.getLastName() + ". ";
		result+="I am a " + p.getAge() + " year old " + p.getGender() + ". ";
		//result+=pickRand(new String[]{"I live in ","I reside in ","I am living in "});;
		//result+=person.getCity() + ", " + person.getState() + ". ";
		return result;
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

	    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
        
    <style>
        <%@include file="/WEB-INF/css/dashboard.css"%>
    </style>
    <title>${pageProfile.profileID}</title>
</head>



<body>
    <div class="container-fluid">
        <div class="row">
            <%@include file="header.jsp"%>
            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                <div>
                    <div class="container">
                        <div class="row">
                            <div class="col-lg">

                                <img class="rounded mx-auto d-block img-thumbnail" style = "width:50%" id="birdpic" src="/img/bird_silhouette1.png" class="circle float-left" alt="Profile Picture">
                                                            <script>
                            var b = Math.floor((Math.random() * 3) + 1);
                            var birdpiclink = "img/bird_silhouette"+b+".png";
                            $("#birdpic").attr("src", birdpiclink);
                            </script>
                            </div>
                            <div class="col">
                                <h2>${pageProfile.profileID}</h2>
                            </div>
                        </div>
                        
                        <!-- Bar for profile buttons -->
                        <div class="row">
                            <div class="col"></div>
                            <div class="col-sm">
                            <div class = "row">
                        	<%
                        		// Hide the like button if this is the user's profile
								Profile userProfile = (Profile) session.getAttribute(AppConstants.SESSION_PROFILE);
								Profile pageProfile = (Profile) session.getAttribute(AppConstants.PAGE_PROFILE);
								Account userAccount = (Account) session.getAttribute(AppConstants.SESSION_ACCOUNT);
                        	                        	
                        	if (userAccount.getAccountType() == Account.AccountType.CUSTOMER && ! userProfile.getProfileID().equals(pageProfile.getProfileID())) {
                        	%>
                                <form action="" method="POST">
                                    <button id="like-button" type="submit" class="btn btn-sm btn-primary">
                                        <i class="fa fa-thumbs-up"></i>
                                        Like</button>
                                </form>
                                    <button id="date-button" type="submit" class="btn btn-sm btn-danger" data-toggle = "modal" data-target = "#dateModal">
                                        <i class="fa fa-heart"></i>
                                        Date</button>
                                    <button id="referral-button" type="submit" class="btn btn-sm btn-success" data-toggle = "modal" data-target = "#refModal">
                                        <i class="fa fa-group"></i>
                                        Refer</button>
                        	<% } %>
                        	
                        	<% 
                        	if (userProfile != null &&
                        		userProfile.getProfileID().equals(pageProfile.getProfileID())){
                       		%>
                       		
                       			<a href = "${pageContext.request.contextPath }/customer/edit/${pageProfile.profileID}" class = "btn btn-sm btn-warning">
                       				<i class = "fa fa-wrench"></i>
                       				 Edit</a>
                        		
                        	<% } %>
                        	
							<%
							if(userAccount.getAccountType().equals(Account.AccountType.EMPLOYEE) || userAccount.getAccountType().equals(Account.AccountType.MANAGER)){
								%>
									<button id="employee-date-button" type="submit" class="btn btn-sm btn-secondary" data-toggle = "modal" data-target = "#employeeInfoModal">
                                        <i class="fa fa-info-circle"></i>
                                        Info</button>
								    <button id="employee-date-button" type="submit" class="btn btn-sm btn-danger" data-toggle = "modal" data-target = "#employeeDateModal">
                                        <i class="fa fa-heart"></i>
                                        Date</button>
                                    <button id="suggest-button" type="submit" class="btn btn-sm btn-success" data-toggle = "modal" data-target = "#suggestModal">
                                        <i class="fa fa-group"></i>
                                        Suggest</button>
                                    <a href = "../customer/edit/${pageProfile.profileID}" id="edit-button" type="submit" class="btn btn-sm btn-warning">
                                        <i class="fa fa-group"></i>
                                        Edit</a>	
                                    <button id="delete-button" type="submit" class="btn btn-sm btn-danger" data-toggle = "modal" data-target = "#deleteModal">
                                        <i class="fa fa-group"></i>
                                        Delete</button>							
                            <%
							}
							%>
                        		</div>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="container">
                        <div class="row">
                            <div class="col-md">
                                <h3>About Me</h3>
                                <p>
                                	<% 
                                	
                                    Connection conn = (Connection) session.getAttribute("connAttribute");
                             	   	Person person = getPersonFromId(conn, pageProfile.getProfileID());
                             	   	out.print(getDescription(pageProfile, person));
                             	   	%>
                                    <%--Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus accumsan varius dui eu faucibus. Nulla lobortis pharetra
                                    congue. Aenean sed metus vitae ex cursus placerat vitae et enim. Interdum et malesuada
                                    fames ac ante ipsum primis in faucibus. Nulla id quam et quam molestie elementum. Aenean
                                    quis eleifend velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra,
                                    per inceptos himenaeos. Ut viverra et tellus vitae auctor. Duis nec nunc sit amet dolor
                                    sodales placerat. In diam turpis, lobortis vitae euismod sed, dapibus ac ante. Quisque
                                    nunc nisi, tempus sit amet ornare ut, egestas vulputate risus. Sed et eros risus. Proin
                                    eleifend lorem sed risus pharetra auctor. Suspendisse diam tellus, ornare vitae ligula
                                    vitae, finibus convallis ante. Pellentesque consequat tincidunt dolor, in posuere dolor
                                    convallis ut. Cras quis augue dapibus lorem aliquam venenatis. Vestibulum dictum est
                                    a lorem bibendum, vel convallis odio auctor. Nam et tortor varius, feugiat est eget,
                                    congue lectus. Maecenas vitae commodo ante. Nulla id mauris et ligula ultricies egestas.
                                    Quisque at tellus sapien. Praesent tempus maximus sapien in porttitor. Praesent elementum,
                                    dui maximus placerat rhoncus, augue nunc faucibus enim, vitae scelerisque turpis elit
                                    id mauris. Suspendisse potenti. Pellentesque nec bibendum augue. Fusce sit amet justo
                                    eu erat accumsan molestie.--%>
                                </p>
                            </div>
                            <div class="col">
                                <h3>Info</h3>
                                <table class="table-sm">
                                    <tr>
                                        <th>Gender</th>
                                        <td>${pageProfile.gender}</td>
                                    </tr>
                                    <tr>
                                        <th>Age</th>
                                        <td>${pageProfile.age}</td>
                                    </tr>
                                    <tr>
                                        <th>Height (ft)</th>
                                        <td>${pageProfile.height}</td>
                                    </tr>
                                    <tr>
                                        <th>Weight (lbs)</th>
                                        <td>${pageProfile.weight}</td>
                                    </tr>
                                    <tr>
                                        <th>Hair Color</th>
                                        <td>${pageProfile.hairColor}</td>
                                    </tr>
                                    <tr>
                                        <th>Hobbies</th>
                                        <td>${pageProfile.hobbies}</td>
                                    </tr>                        
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

            </main>
        </div>
    </div>
    
    <!-- Pop-ups -->
<div class="modal fade" id="employeeInfoModal" role="dialog" aria-labelledby="employeeInfoModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="employeeInfoModalLabel">Information of ${pageProfile.profileID}</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
          <a href = "../likes/${pageProfile.profileID}" class = "btn btn-sm btn-primary">View Likes</a>
          <a href = "../dates/${pageProfile.profileID}" class = "btn btn-sm btn-danger">View Dates</a>
          <button data-dismiss="modal" data-toggle="modal" data-target="#dateListModal" class = "btn btn-sm btn-warning">View Datelist</button>
      </div>
    </div>
  </div>
</div>

    <div class="modal fade" id="dateListModal" role="dialog" aria-labelledby="dateListModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="dateListModalLabel">View ${pageProfile.profileID}'s Dates</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        		<% 
      	ArrayList<String> dateList = (ArrayList<String>) session.getAttribute(AppConstants.PEOPLE_DATED);
        if(dateList!=null){
		out.println("<table class = 'table-sm'>");
		out.println("<tr><th>ID</th></tr>");
        for(int i=0; i<dateList.size();i++){
        	out.println("<tr>");
        	out.println("<td>" + getProfileLink(dateList.get(i)) + "</td>");
			out.println("</tr>");
        }
		out.println("</table>");
        }
        %>
      </div>
    </div>
  </div>
</div>    
    
    <div class="modal fade" id="dateModal" role="dialog" aria-labelledby="dateModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="dateModalLabel">Make a date with ${pageProfile.profileID}</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form method="POST" action="${pageContext.request.contextPath}/dates/${pageProfile.profileID}">
          <div class="form-group">
            <label for="location" class="col-form-label">Location</label>
            <input type="text" class="form-control" id="location" name="location">
          </div>
		<div class="form-group">
            <label for="DateTime" class="col-form-label">Date and Time (yyyy/mm/dd hh:mm:ss)</label>
            <input type="text" class="form-control" id="DateTime" name="dateTime">
          </div>
          
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary">Make Date</button>
        </form>
      </div>
    </div>
  </div>
</div>

   <div class="modal fade" id="refModal" role="dialog" aria-labelledby="refModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="refModalLabel">Refer ${pageProfile.profileID}</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form method="POST" action="${pageContext.request.contextPath}/referals">
          <div class="form-group"> 
          	<input name = "ProfileB" style="display:none;" value = "${pageProfile.profileID }">
            <label for="location" class="col-form-label">ProfileID</label>
            <input type="text" class="form-control" id="location" name="ProfileC">
          </div>
          
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary">Refer</button>
        </form>
      </div>
    </div>
  </div>
</div>

   <div class="modal fade" id="suggestModal" role="dialog" aria-labelledby="suggestModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="suggestModalLabel">Suggest ${pageProfile.profileID}</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form method="POST" action="${pageContext.request.contextPath}/suggest/${pageProfile.profileID}">
          <div class="form-group">
            <label for="ProfileId" class="col-form-label">ProfileID</label>
            <input type="text" class="form-control" id="ProfileId" name="Profile2">
          </div>
          
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary">Suggest</button>
        </form>
      </div>
    </div>
  </div>
</div>

    <div class="modal fade" id="employeeDateModal" role="dialog" aria-labelledby="employeeDateModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="employeeDateModalLabel">Make a date</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form method="POST" action="${pageContext.request.contextPath}/dates/${pageProfile.profileID}">
         <div class="form-group">
            <label for="datee" class="col-form-label">Date</label>
            <input type="text" class="form-control" id="datee" name="profileA">
          </div>
          <div class="form-group">
            <label for="location" class="col-form-label">Location</label>
            <input type="text" class="form-control" id="location" name="location">
          </div>
		<div class="form-group">
            <label for="DateTime" class="col-form-label">Date and Time</label>
            <input type="text" class="form-control" id="DateTime" name="dateTime">
          </div>
          
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary">Make Date</button>
        </form>
      </div>
    </div>
  </div>
</div>

    <div class="modal fade" id="deleteModal" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="deleteDateModalLabel">Delete ${pageProfile.profileID}</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form method="POST" action="../delete/customer/${pageProfile.profileID}">
        <p>Are you sure you want to delete this profile?</p>
        <br>
        
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-danger"><i class='fa fa-times-circle'></i> Delete</button>
        </form>
      </div>
    </div>
  </div>
</div>

    <!-- jQuery first, then Popper.js, then Bootstrap JS -->

    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
    <!-- Optional JavaScript -->

</body>

</html>