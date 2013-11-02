<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<jsp:include page="/layout/head.jsp" />
<title>Add Occupation</title>
</head>
<body id="inner-page">
<jsp:include page="/layout/AdminMenu.jsp" />
	<section id="main">
  		<div class="intro_wrap">
    		<div class="center">
      			<h2>Add Occupation</h2>
    		</div>
  		</div>
  		<!-- end .intro_wrap-->  
		<div class="center">  
	    	<section id="full_width">
				<form action="/Community/Admin/Occupation/Add" method="post">
					${messages.mainError}
					${messages.success}
					<jsp:include page="/layout/CSRF.jsp" />
					<p>
						<label for="name">Occupation:</label>
						<input type="text" id="name" name="name" value="${param.name}" required="required" title="Please enter occupation" x-moz-errormessage="Please enter occupation" />
						${messages.nameError}
					</p>
					<p><input type="submit" id="button_submit" name="submit" value="Add Occupation" /></p>
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