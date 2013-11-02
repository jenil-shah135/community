<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/layout/head.jsp" />
<title>Edit Relation</title>
</head>
<c:if test='${not empty param.name}'><% request.setAttribute("name",request.getParameter("name")); %></c:if>
<c:if test='${not empty param.relationID}'><% request.setAttribute("relationID",request.getParameter("relationID")); %></c:if>
<body id="inner-page">
<jsp:include page="/layout/AdminMenu.jsp" />
	<section id="main">
  		<div class="intro_wrap">
    		<div class="center">
      			<h2>Edit Relation</h2>
    		</div>
  		</div>
  		<!-- end .intro_wrap-->  
		<div class="center">  
	    	<section id="full_width">
	    		<form action="/Community/Admin/Relation/Edit" method="post">
					${messages.mainError}
					${messages.success}
					<jsp:include page="/layout/CSRF.jsp" />
					<input type="hidden" name="relationID" value="${relationID}" />
					<p>
						<label for="name">Relation Name:</label>
						<input type="text" id="name" name="name" value="${name}" required="required" title="Please enter relation name" x-moz-errormessage="Please enter relation name" />
						${messages.nameError}
					</p>
					<p>
						<input type="submit" name="submit" id="button_submit" value="Update Relation" />
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