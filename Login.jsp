<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<jsp:include page="/layout/head.jsp" />
<title>Login</title>
</head>
<body id="inner-page">
<jsp:include page="/layout/menu.jsp" />
	<section id="main">
  		<div class="intro_wrap">
    		<div class="center">
      			<h2>Login</h2>
    		</div>
  		</div>
  		<!-- end .intro_wrap-->  
		<div class="center">  
	    	<section id="full_width">			
				<form action="/Community/Login" method="post">						
					${messages.mainError}
					${messages.success}
					<jsp:include page="/layout/CSRF.jsp" />
					<p>
						<label for="username">Username:</label>
						<input type="text" name="username" id="username" value="${param.username}" required="required" title="Please enter username" x-moz-errormessage="Please enter username" />
						${messages.usernameError}
					</p>
					<p>
						<label for="password">Password:</label>
						<input type="password" name="password" id="password" value="${param.password}" required="required" title="Please enter password" x-moz-errormessage="Please enter password" />
						${messages.passwordError}
					</p>
					<p>
						<a href="/Community/ForgotPassword">Forgot Password?</a>
					</p>
					<p><input type="submit" name="submit" id="button_submit" value="Login" /></p>
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