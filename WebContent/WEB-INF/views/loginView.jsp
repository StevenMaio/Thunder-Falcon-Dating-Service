<!doctype html>
<%@page import="org.tfds.AppConstants"%>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>

  <style><%@include file="/WEB-INF/css/signin.css"%></style>
	<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/WebContent/css/styles.css" />  --%>
    <title>Sign in</title>
	<%

	// Show the error string if there is one
	String errorString = (String) session.getAttribute(AppConstants.ERROR_STRING);
                                                
	if (errorString != null) {
		%>
		<script>
			alert("${errorString}")
		</script>
		<% 
		session.removeAttribute(AppConstants.ERROR_STRING);
	} 
	%>
  </head>
<body>
	
    <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
        <a class="navbar-brand" href="#">ThunderFalcon</a>
        <ul class = "nav navbar-nav navbar-right">
        <li>
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#loginModal">Sign In</button>
        <a role = "button" type = "button" class = "btn btn-outline-primary" href = "${pageContext.request.contextPath }/customer/create">Register</a>
        </li>
        </ul>
    </nav>
    <div class="container-fluid">
        <div id="mainCarousel" class="carousel-slide" data-ride="carousel">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img class="d-block w-100" src="${pageContext.request.contextPath }/img/carouselimg1.jpg" alt="First Slide">
                </div>
                <div class="carousel-item">
                    <img class="d-block w-100" src="${pageContext.request.contextPath }/img/carouselimg2.jpg" alt="Second Slide">
                </div>
                <div class="carousel-item">
                    <img class="d-block w-100" src="${pageContext.request.contextPath }/img/carouselimg3.jpg" alt="Third Slide">
                </div>
            </div>
            <a class="carousel-control-prev" href="#mainCarousel" role="button" data-slide="prev">
                <i class="fas fa-chevron-left"></i>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#mainCarousel" role="button" data-slide="next">
                <i class="fas fa-chevron-right"></i>
                <span class="sr-only">Next</span>
            </a>
        </div>
    </div>
    <div class="container">
        <div class="modal fade" id="loginModal" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="close">
                            <span aria-hidden="true">&times</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <body class = "text-center">
                                <form method="POST" class = "form-signin" action="${pageContext.request.contextPath}/login">
                                    <h1 class = "h3 mb-3 font-weight-normal">Please Sign In</h1>
                                    <label for "errorMessage" class="sr-only">${errorString}</label>
                                    <label for "inputEmail" class="sr-only">Username</label>
                                    <input type = "text" name="Username" id="inputEmail" class ="form-control" placeholder="Username" required autofocus>
                            
                                    <label for "inputPassword" class="sr-only">Password</label>
                                    <input type = "password" id="inputPassword" name="Password" class ="form-control" placeholder="Password" required>
                                    
                                    <button value="Submit" class="btn btn-large btn-primary btn-block" type="submit">Sign In</button>
                                </form>
                        </body>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->

    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
</body> 
</html>