<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/layout/head.jsp" />
<title>Edit City</title>
</head>
<c:if test='${not empty param.cityID}'><% request.setAttribute("cityID",request.getParameter("cityID")); %></c:if>
<c:if test='${not empty param.name}'><% request.setAttribute("name",request.getParameter("name")); %></c:if>
<c:if test='${not empty param.state}'><% request.setAttribute("state",request.getParameter("state")); %></c:if>
<c:if test='${not empty param.country}'><% request.setAttribute("country",request.getParameter("country")); %></c:if>
<body id="inner-page">
<jsp:include page="/layout/AdminMenu.jsp" />
	<section id="main">
  		<div class="intro_wrap">
    		<div class="center">
      			<h2>Edit City</h2>
    		</div>
  		</div>
  		<!-- end .intro_wrap-->  
		<div class="center">  
	    	<section id="full_width">
					<form action="/Community/Admin/City/Edit" method="post">
					${messages.mainError}
					${messages.success}
					<jsp:include page="/layout/CSRF.jsp" />
					<input type="hidden" name="cityID" value="${cityID}" />
					<p>
						<label for="country">Country:</label>
						<select name="country" required="required" id="country" title="Please select country" x-moz-errormessage="Please select country" >
							<option value="">Select</option>
							<c:forEach var="objCountry" items="${requestScope.countryList}" >
						    	<option value="${objCountry.getCountryID()}" <c:if test='${country == objCountry.getCountryID()}'>selected</c:if> >${objCountry.getCountryName()}</option>		
						   	</c:forEach>
						</select>
						${messages.countryError}
					</p>
					<p>
						<label for="state">State:</label>
						<select name="state" required="required" id="state" title="Please select state" x-moz-errormessage="Please select state" >
							<option value="">Select</option>
							<c:forEach var="objState" items="${requestScope.stateList}" >
						    	<option value="${objState.getStateID()}" <c:if test='${state == objState.getStateID()}'>selected</c:if> >${objState.getStateName()}</option>		
						   	</c:forEach>
						</select>
						${messages.stateError}
					</p>
					<p>
						<label for="name">City Name:</label>
						<input type="text" id="name" name="name" value="${name}" required="required" title="Please enter city name" x-moz-errormessage="Please enter city name" />
						${messages.nameError}
					</p>
					<p><input type="submit" name="submit" id="button_submit" value="Update City" /></p>
				</form>
				<br /><br /><br />
	    	</section>
    		<!-- end #content-->
		</div>
  		<!-- end .center-->  
	</section>
	<!-- end #main-->
<jsp:include page="/layout/footer.jsp" />
<script type="text/javascript">
$(document).ready(function() {
	$('#country').change(function() {
		if($(this).val()) {
			var url="/Community/GetData?item=state&id="+$(this).val();
			$.get(url,
				function(data) {			
					if(data=="") {
						$('#state').html("<option value=''>Select</option>");
					} else {
						$('#state').html("<option value=''>Select</option>"+data);
					}
				},"text"
			);
		}
		else {
			$('#state').html("<option value=''>Select</option>");
		}
	});
});
</script>
</body>
</html>