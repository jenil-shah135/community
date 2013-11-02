<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/layout/head.jsp" />
<title>Valid Family</title>
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
      			<h2>Family</h2>
    		</div>
  		</div>
  		<!-- end .intro_wrap-->  
		<div class="center">
	    	<section id="full_width">
				<div>
					<table border='0' class='display' rel='datatable' id='table_id'>
						<thead>
							<tr>
								<th>Member Name</th>
								<th>Current Address</th>
								<th>Native Address</th>
								<th class='no-sort'>Delete</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="family" items="${requestScope.familyList}">
							   	<tr>
							   		<td>${family.getFamilyMembers().get(0).getFirstName()}</td>
							   	   	<td>
								   		${family.getCurrentAddress().getStreet()}
								   		<c:if test='${not empty family.getCurrentAddress().getStreet()}'>,</c:if>
								   		${family.getCurrentAddress().getArea()}
								   		<c:if test='${not empty family.getCurrentAddress().getArea()}'>,</c:if>
								   		${family.getCurrentAddress().getCity().getCityName()}
								   		<c:if test='${not empty family.getCurrentAddress().getCity().getCityName()}'>,</c:if>
								   		${family.getCurrentAddress().getCity().getState().getStateName()}
								   		<c:if test='${not empty family.getCurrentAddress().getCity().getState().getStateName()}'>,</c:if>
								   		${family.getCurrentAddress().getCity().getState().getCountry().getCountryName()}
								   	</td>
								   	<td>
								   		${family.getNativeAddress()}				   		
								   	</td>
								   	<td>
								   		<form method="post" action="/Community/Admin/Family/Delete" class="deleteform">
									   		<input type="hidden" name="id" value="${family.getFamilyID()}" />
									   		<jsp:include page="/layout/CSRF.jsp" />
									   		<input type="submit" name="delete" value="Remove Family" class="deletelink" />									   		
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
	/* 
	$(".deletelink").click(function (event) {
         event.preventDefault();
         var link = this;
         if (confirm("Are you sure that you want to delete?")) {
        	 $.get(link.href,
     			function(data) {			
     				if(data=="true") {
     					$(link).parents("tr").remove();
     				} else {
     					alert("This cannot be deleted");
     				}
     			},"text"
     		);
         }
     });
	 */	
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