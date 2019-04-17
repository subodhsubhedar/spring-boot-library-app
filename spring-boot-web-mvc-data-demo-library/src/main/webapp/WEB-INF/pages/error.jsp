<!doctype html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Library App - Spring data</title>


<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
	integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script>
<title>Library App</title>
</head>
<body>
	<br>
	<br>
	<div class="container">
		<div class="jumbotron">
			<div align="right">
				<a class="btn btn-info" href="/logout">Logout</a> <br>Logged in
				User : ${loggedInUser}
			</div>
			<h1 class="display-4">Library App Error..</h1>
			<br> <b>Error Cause</b> : ${Cause} 
			<br> <b>Error Message </b>: ${message}
			<br> <b> Url </b>: ${url}
			<br> <b>Timestamp </b>: ${timestamp}
			<br> <b>Status </b>: ${status}
 			<br> <b>Details </b>: ${error}
 			<br> <b>Trace :</b> ${trace}

		</div>
	</div>
</body>
</html>