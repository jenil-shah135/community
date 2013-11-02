<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/layout/head.jsp" />
<title>City</title>
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
      			<h2>City</h2>
    		</div>
  		</div>
  		<!-- end .intro_wrap-->  
		<div class="center">  
	    	<section id="full_width">			
				<div>
					<table border='0' class='display' rel='datatable' id='table_id'>
						<thead>
							<tr>
								<th>City Name</th>
								<th>State Name</th>
								<th>Country Name</th>
								<th class='no-sort'>Edit</th>
								<th class='no-sort'>Delete</th>
							</tr>
						</thead>
						<tbody>		
							<c:forEach var="city" items="${requestScope.cityList}">
							   	<tr>
								   	<td>${city.getCityName()}</td>
								   	<td>${city.getState().getStateName()}</td>
								   	<td>${city.getState().getCountry().getCountryName()}</td>
								   	<td><a href="/Community/Admin/City/Edit?id=${city.getCityID()}"><img src="/Community/images/edit.png" alt="Edit" /></a></td>
								   	<td>
								   		<form method="post" action="/Community/Admin/City/Delete" class="deleteform">
									   		<input type="hidden" name="id" value="${city.getCityID()}" />
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
				<!--
				<div id='jqxWidget' style="font-size: 13px; font-family: Verdana; float: left;">
        			<div id="jqxgrid"></div>
    			</div>
    			-->
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