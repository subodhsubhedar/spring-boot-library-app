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
<title>Insert title here</title>


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

			<div>
				<h1 class="display-4">
					<c:if test="${addedSubjectTitle!=null}">
					Subject <b> ${subject.subtitle} </b> added successfully.
					
					</c:if>
				</h1>
			</div>

			<c:if test="${addedSubjectTitle==null}">

				<form:form method="post" action="add-new-subject"
					name="addSubjectForm" modelAttribute="subject">

					<div class="card border-primary">
						<div class="badge badge-primary text-left">

							<h5>Add New Subject</h5>

						</div>
						<div class="card-body">
							<p class="card-text">

								<spring:bind path="subtitle">
									<div class="form-group">
										<label class="col-sm-2 control-label">Subject Title :
										</label>
										<div class="col-sm-10">
											<form:input path="subtitle" type="text"
												class="form-control ${status.error ? 'is-invalid' : ''}"
												id="subjectTitle" placeholder="Subject title" size="100"
												maxlength="150" value="${subject.subtitle}" />
											<form:errors path="subtitle" class="control-label" />
										</div>
									</div>
								</spring:bind>

								<spring:bind path="durationInHrs">
									<div class="form-group">
										<label class="col-sm-2 control-label">Duration in hrs
											: </label>
										<div class="col-sm-10">
											<form:input path="durationInHrs" type="number"
												class="form-control ${status.error ? 'is-invalid' : ''}"
												id="duration" placeholder="Duration in hrs" size="4"
												maxlength="150" value="${subject.durationInHrs}" />
											<form:errors path="durationInHrs" class="control-label" />
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
			</c:if>

			<br> <br> <br> <a class="btn btn-primary"
				href="goBackToMainMenu" role="button">Back to Main Menu</a>

		</div>

	</div>
</body>

</html>