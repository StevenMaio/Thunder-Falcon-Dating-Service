<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

  <style><%@include file="/WEB-INF/css/signin.css"%></style>
    <title>Sign in</title>
  </head>

<body class = "text-center">
	<form method="POST" class = "form-signin" action="#">
    	<h1 class = "h3 mb-3 font-weight-normal">Please Sign In</h1>
        	<label for "inputSSN" class="sr-only">SSN</label>
            <input type = "text" name="Username" id="inputSSN" class ="form-control" placeholder="SSN" required autofocus>
                            
            <label for "inputPassword" class="sr-only">Password</label>
            <input type = "password" id="inputPassword" name="Password" class ="form-control" placeholder="Password" required>                        
            <button value="Submit" class="btn btn-large btn-primary btn-block" type="submit">Sign In</button>
     </form>
</body>