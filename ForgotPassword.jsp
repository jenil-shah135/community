<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<jsp:include page="/layout/head.jsp" />
<title>Forgot Password</title>
</head>
<body id="inner-page">
<jsp:include page="/layout/menu.jsp" />
	<section id="main">
  		<div class="intro_wrap">
    		<div class="center">
      			<h2>Forgot Password</h2>
    		</div>
  		</div>
  		<!-- end .intro_wrap-->  
		<div class="center">  
	    	<section id="full_width">			
				<form action="/Community/ForgotPassword" method="post">
					${messages.mainError}
					${messages.success}
					<jsp:include page="/layout/CSRF.jsp" />
					<p>
						<label for="username">Username:</label>
						<input type="text" id="username" name="username" value="${param.username}" placeholder="Enter username" required="required" title="Please enter username" x-moz-errormessage="Please enter username"  />
						${messages.usernameError}
					</p>
					<p><input type="submit" name="submit" id="button_submit" value="Forgot Password" /></p>
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