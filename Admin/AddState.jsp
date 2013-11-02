<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/layout/head.jsp" />
<title>Add State</title>
</head>
<body id="inner-page">
<jsp:include page="/layout/AdminMenu.jsp" />
	<section id="main">
  		<div class="intro_wrap">
    		<div class="center">
      			<h2>Add State</h2>
    		</div>
  		</div>
  		<!-- end .intro_wrap-->  
		<div class="center">  
	    	<section id="full_width">
	    		<form action="/Community/Admin/State/Add" method="post">					
					${messages.mainError}
					${messages.success}
					<jsp:include page="/layout/CSRF.jsp" />
					<p><label for="country">Country:</label>
						<select id="country" name="country" required="required" title="Please select country" x-moz-errormessage="Please select country" >
						<option value="">Select</option>
						<c:forEach var="country" items="${requestScope.countryList}" >
					    	<option value="${country.getCountryID()}" <c:if test='${param.country == country.getCountryID()}'>selected</c:if> >${country.getCountryName()}</option>		
					   </c:forEach>
						</select>
						${messages.countryError}
					</p>
					<p><label for="name">State Name:</label>
						<input type="text" id="name" name="name" value="${param.name}" required="required" title="Please enter state name" x-moz-errormessage="Please enter state name" />
						${messages.nameError}
					</p>
					<p><input type="submit" name="submit" id="button_submit" value="Add State" /></p>
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