<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/layout/head.jsp" />
<title>Edit State</title>
</head>
<c:if test='${not empty param.name}'><% request.setAttribute("name",request.getParameter("name")); %></c:if>
<c:if test='${not empty param.country}'><% request.setAttribute("country",request.getParameter("country")); %></c:if>
<c:if test='${not empty param.stateID}'><% request.setAttribute("stateID",request.getParameter("stateID")); %></c:if>
<body id="inner-page">
<jsp:include page="/layout/AdminMenu.jsp" />
	<section id="main">
  		<div class="intro_wrap">
    		<div class="center">
      			<h2>Edit State</h2>
    		</div>
  		</div>
  		<!-- end .intro_wrap-->  
		<div class="center">  
	    	<section id="full_width">
				<form action="/Community/Admin/State/Edit" method="post">
					${messages.mainError}
					${messages.success}
					<jsp:include page="/layout/CSRF.jsp" />
					<input type="hidden" name="stateID" value="${stateID}" />
					<p><label for="country">Country:</label>
						<select name="country" required="required">
							<option value="">Select</option>
							<c:forEach var="objCountry" items="${requestScope.countryList}" >
						    	<option value="${objCountry.getCountryID()}" <c:if test='${country == objCountry.getCountryID()}'>selected</c:if> >${objCountry.getCountryName()}</option>		
						   	</c:forEach>
						</select>
						${messages.countryError}
					</p>
					<p>
						<label for="name">State Name:</label>
						<input type="text" id="name" name="name" value="${name}" required="required" title="Please enter state name" x-moz-errormessage="Please enter state name" />
						${messages.nameError}
					</p>
					<p>
						<input type="submit" name="submit" id="button_submit" value="Update State" />
					</p>
				</form>
				<br /><br /><br />
	    	</section>
    		<!-- end #content-->
		</div>
  		<!-- end .center-->  
	</section>
	<!-- end #main-->
<jsp:include page="/layout/footer.jsp" />
</body>
</html>