<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!doctype html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search Subject by duration</title>


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
</head>
<body>
	<div class="container">

		<br> <br>
		<div class="jumbotron">
			<div align="right">
				<a class="btn btn-info" href="/logout">Logout</a> <br>Logged in
				User : ${loggedInUser}
			</div>

			<form:form method="post" action="search-subject-by-duration"
				name="searchSubjectForm" modelAttribute="searchSubjectCriteria">

				<div class="card border-primary">
					<div class="badge badge-primary text-left">

						<h5>Search Subject by duration</h5>

					</div>
					<div class="card-body">
						<p class="card-text">

							<spring:bind path="durationStart">
								<div class="form-group">
									<div class="col-sm-8">
										<label class="col-sm-8 control-label">Duration start
											(in hrs) : </label>

										<form:input path="durationStart" type="number"
											class="form-control ${status.error ? 'is-invalid' : ''}"
											id="durationStartId" placeholder="Duration start in hrs"
											size="4" maxlength="150"
											value="${searchSubjectCriteria.durationStart}" />
										<form:errors path="durationStart" class="control-label" />
									</div>
								</div>
							</spring:bind>
							<spring:bind path="durationEnd">
								<div class="form-group">
									<div class="col-sm-8">
										<label class="col-sm-8 control-label">Duration end (in
											hrs) : </label>

										<form:input path="durationEnd" type="number"
											class="form-control ${status.error ? 'is-invalid' : ''}"
											id="durationEndId" placeholder="Duration end in hrs" size="4"
											maxlength="150" value="${searchSubjectCriteria.durationEnd}" />
										<form:errors path="durationEnd" class="control-label" />
									</div>
								</div>
							</spring:bind>
							<br> <br>


						</p>
					</div>
				</div>
				<br>
				<br>
				<p class="lead">
					<button type="submit" class="btn btn-primary">Submit</button>
				</p>
			</form:form>


			<br> <br> <br> <a class="btn btn-primary"
				href="goBackToMainMenu" role="button">Back to Main Menu</a>

		</div>

	</div>
</body>

</html>