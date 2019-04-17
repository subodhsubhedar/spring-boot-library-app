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
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>

<script type="text/javascript">
	$(document)
			.ready(
					function() {

						$("input[name='menuIndex']").on("click", setValue);

						function setValue() {
							var val = ($('input:radio:checked').val());

							if (val == 6 || val == 5 || val == 3 || val == 4) {
								document.getElementById("searchCriteriaDiv").style.display = "block";
							} else {
								document.getElementById("searchCriteriaDiv").style.display = "none";
								document.getElementById("searchCriteriaId").value = "";
							}
						}

						$(window).load(setValue);
					});
</script>

</head>
<body>

	<div class="container">
		<br> <br>
		<form:form method="post" action="process-main" name="menuSelForm"
			modelAttribute="mainMenuModel">

			<div class="jumbotron">

				<div align="right">
					<a class="btn btn-info" href="/logout">Logout</a> <br>Logged in
					User : ${loggedInUser}
				</div>
				<h1 class="display-4">${welcomeMsg}
					<br> <br>

				</h1>
				<p class="lead">
					<b>${selectionMsg}</b>
				</p>
				<hr class="my-4">


				<p>
					<c:forEach var="menu" items="${menuModelList}">

						<form:radiobutton path="menuIndex" value="${menu.menuIndex}"
							name="menuIndex" id="menuIndex${menu.menuIndex}"
							label="${menu.menuDesc}" />
						<br>
					</c:forEach>
				</p>
				<div id="searchCriteriaDiv" style="display: none;">

					<spring:bind path="menuCriteria">
						<div class="form-group">
							<label class="col-sm-2 control-label">Criteria for search
								: </label>
							<div class="col-sm-10">
								<form:input path="menuCriteria" type="text"
									class="form-control ${status.error ? 'is-invalid' : ''}"
									placeholder="Enter search criteria" size="100"
									id="searchCriteriaId" name="searchCriteriaName"
									value="${mainMenuModel.menuCriteria}" />
								<form:errors path="menuCriteria" class="control-label" />
							</div>
						</div>
					</spring:bind>

				</div>

				<br>


				<p class="lead">

					<button type="submit" class="btn btn-primary">Submit</button>
				</p>
			</div>
		</form:form>


	</div>
</body>

</html>