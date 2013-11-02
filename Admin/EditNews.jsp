<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/layout/head.jsp" />
<title>Edit News</title>
</head>
<c:if test='${not empty param.newsID}'><% request.setAttribute("newsID",request.getParameter("newsID")); %></c:if>
<c:if test='${not empty param.newsTitle}'><% request.setAttribute("newsTitle",request.getParameter("newsTitle")); %></c:if>
<c:if test='${not empty param.newsDesc}'><% request.setAttribute("newsDesc",request.getParameter("newsDesc")); %></c:if>
<body id="inner-page">
<jsp:include page="/layout/AdminMenu.jsp" />
	<section id="main">
  		<div class="intro_wrap">
    		<div class="center">
      			<h2>Edit News</h2>
    		</div>
  		</div>
  		<!-- end .intro_wrap-->  
		<div class="center">  
	    	<section id="full_width">
	    		<form action="/Community/Admin/News/Edit" method="post">
					${messages.mainError}
					${messages.success}
					<jsp:include page="/layout/CSRF.jsp" />
					<input type="hidden" name="newsID" value="${newsID}" />
					<p>
						<label for="newsTitle">News Title:</label>
						<input type="text" id="newsTitle" name="newsTitle" value="${newsTitle}" required="required" title="Please enter news title" x-moz-errormessage="Please enter news title" />
						${messages.newsTitleError}
					</p>
					<p>
					<label for="newsDesc">News Description:</label>
						<textarea name="newsDesc" id="newsDesc" required="required" title="Please enter news description" x-moz-errormessage="Please enter news description">${newsDesc}</textarea>
						${messages.newsDescError}
					</p>
					<p>
						<input type="submit" name="submit" id="button_submit" value="Update News" />
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