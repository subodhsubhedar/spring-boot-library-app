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
					<c:choose>
						<c:when test="${subject !=null && deletedSubjectTitle==null}">
							<c:if test="${noBooks == false}">
								Cannot delete subject, as there are one or more books associated with it.
							</c:if>
							<c:if test="${noBooks == true}">
								Subject Found, are you sure you want to delete?
							</c:if>
						</c:when>
						<c:when test="${deletedSubjectTitle !=null}">
							Subject - <b>${deletedSubjectTitle} </b> deleted Successfully.
							
						</c:when>
						<c:otherwise>
								Subject Not Found.
						</c:otherwise>
					</c:choose>
				</h1>
			</div>

			<c:if test="${subject !=null && deletedSubjectTitle==null}">

				<form:form name="deleteSubjectForm" action="delete-subject"
					method="post" modelAttribute="subject">

					<c:if test="${noBooks == true}">
						<button type="submit" class="btn btn-danger">Submit</button>
						<br>
						<br>
					</c:if>

					<div class="card">
						<div class="card-header">Subject ID : ${subject.subjectId}</div>

						<div class="card-body">
							<h5 class="card-title">${subject.subtitle}</h5>
							<form:input type="hidden" path="subtitle" />
							<p class="card-text">
							<table class="table table-bordered">
								<thead class="thead-dark">
									<tr>
										<th scope="col">#</th>
										<th scope="col">Book Title</th>
										<th scope="col">ID</th>
										<th scope="col">Volume</th>
										<th scope="col">Price</th>
										<th scope="col">Published date</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="book" items="${subject.references}"
										varStatus="counter">
										<tr>
											<td>${counter.count}</td>
											<td>${book.title}</td>
											<td>${book.bookId}</td>
											<td>${book.volume}</td>
											<td>${book.price}</td>
											<td>${book.publishDate}</td>
										</tr>
									</c:forEach>
							</table>
							</p>

						</div>
						<div class="card-footer">Subject Duration (in hrs) :
							${subject.durationInHrs}</div>
					</div>

				</form:form>
			</c:if>
			<br> <br> <br> <a class="btn btn-primary"
				href="goBackToMainMenu" role="button">Back to Main Menu</a>
		</div>

	</div>
</body>

</html>