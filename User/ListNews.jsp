<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/layout/head.jsp" />
<title>Latest News</title>
</head>
<body id="inner-page">
<jsp:include page="/layout/UserMenu.jsp" />
	<section id="main">
  		<div class="intro_wrap">
    		<div class="center">
      			<h2>News</h2>
    		</div>
  		</div>
  		<!-- end .intro_wrap-->  
		<div class="center">  
	    	<section id="full_width">			
				<div>
					<c:forEach var="news" items="${requestScope.newsList}">
					   	<h2>${news.getNewsTitle()}</h2>
					   	<div style="text-align:right;">${news.getNewsAddedDate()}</div>
						<p>${news.getNewsDesc()}</p>
						<hr />
					</c:forEach>
				</div>
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