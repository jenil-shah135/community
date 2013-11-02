<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<jsp:include page="/layout/head.jsp" />
<title>Change Password</title>
</head>
<body id="inner-page">
<jsp:include page="/layout/AdminMenu.jsp" />
	<section id="main">
  		<div class="intro_wrap">
    		<div class="center">
      			<h2>Change Password</h2>
    		</div>
  		</div>
  		<!-- end .intro_wrap-->  
		<div class="center">  
	    	<section id="full_width">			
				<form action="/Community/Admin/ChangePassword" method="post">
					${messages.mainError}
					${messages.success}
					<jsp:include page="/layout/CSRF.jsp" />
					<p>
						<label for="oldPassword">Old Password:</label>
						<input type="password" id="oldPassword" name="oldPassword" value="" required="required" x-moz-errormessage="Please enter old password" title="Please enter old password" />
						${messages.oldPasswordError}
					</p>
					<p>
						<label for="newPassword">New Password:</label>
						<input type="password" id="newPassword" name="newPassword" value="" required="required" x-moz-errormessage="Please enter new password" title="Please enter new password" />
						${messages.newPasswordError}
					</p>
					<p>
						<label for="confirmPassword">Confirm Password:</label>
						<input type="password" id="confirmPassword" name="confirmPassword" value="" required="required" x-moz-errormessage="New and confirm password should not be different" title="New and confirm password should not be different" />
						${messages.confirmPasswordError}
					</p>
					<p><input type="submit" name="submit" id="button_submit" value="Change Password" /></p>
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