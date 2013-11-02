<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/layout/head.jsp" />
<title>News</title>
<style type="text/css">
table,tr{
	border:1px solid black;
	text-align:center;
	width:100%;
}
</style>
</head>
<body id="inner-page">
<jsp:include page="/layout/AdminMenu.jsp" />
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
					<table border='0' class='display' rel='datatable' id='table_id'>
						<thead>
							<tr>
								<th>News Title</th>
								<th>News Desc</th>
								<th>News Added Date</th>
								<th class='no-sort'>Edit</th>
								<th class='no-sort'>Delete</th>
							</tr>
						</thead>
						<tbody>		
							<c:forEach var="news" items="${requestScope.newsList}">
							   	<tr>
								   	<td>${news.getNewsTitle()}</td>
								   	<td>${news.getNewsDesc()}</td>
								   	<td>${news.getNewsAddedDate()}</td>
								   	<td><a href="/Community/Admin/News/Edit?id=${news.getNewsID()}"><img src="/Community/images/edit.png" alt="Edit" /></a></td>
								   	<td>
								   		<form method="post" action="/Community/Admin/News/Delete" class="deleteform">
									   		<input type="hidden" name="id" value="${news.getNewsID()}" />
									   		<jsp:include page="/layout/CSRF.jsp" />
									   		<input type="submit" name="delete" value="Delete" class="deletelink" />									   		
									   	</form>
									</td>								   	
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
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
	 $(".deleteform").bind('submit', function (e) {
		 e.preventDefault();
		 if (confirm("Are you sure that you want to delete?")) {		        
			 $.ajax({
	            type: "POST",
	            url: $(this).attr('action'),
	            dataType: "html",
	            data: $(this).serialize(),
	            context: this,            
	            success:function(data) {			
	 				if(data=="true") {
	 					$(this).parents("tr").remove(); 					
	 				} else {
	 					alert("Something went wrong.Data is not be deleted");
	 				}
	 			},
	        	error:function(data) {			
	 				if(data=="true") {
	 					$(this).parents("tr").remove();
	 				} else {
	 					alert("Something went wrong.Data is not be deleted");
	 				}
	        	}
	        });
		}
	 	return false;   
	 });
});
</script>
</body>
</html>