<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/layout/head.jsp" />
<title>Family Detail</title>
<style type="text/css">
table,tr{
	border:1px solid black;
	text-align:center;
	width:100%;
}
</style>
</head>
<body id="inner-page">
<jsp:include page="/layout/UserMenu.jsp" />
	<section id="main">
  		<div class="intro_wrap">
    		<div class="center">
      			<h2>Family Detail</h2>
    		</div>
  		</div>
  		<!-- end .intro_wrap-->  
		<div class="center">  
	    	<section id="full_width">			
				<div>
					<table border='1' style="text-align:center;">
						<thead>
							<tr>
								<th>Member Name</th>
								<th>Gender</th>
								<th>Marital Status</th>
								<th>Contact No</th>
								<th>Email ID</th>
								<th>Blood Group</th>
								<th>Birth Date</th>
								<th>Birth Place</th>
								<th>Education Qualification</th>
								<th>Occupation</th>
								<th>Working Address</th>
								<th>Relation</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="member" items="${requestScope.familyMember}">
							   	<tr>
							   		<td>${member.getFirstName()} ${member.getMiddleName()} ${member.getLastName()}</td>
							   		<td>
							   			<c:if test='${member.getGender()}'>Male</c:if>
								   		<c:if test='${not member.getGender()}'>Female</c:if>
								   	</td>
							   		<td>
							   			<c:if test='${member.getMaritalStatus()}'>Married</c:if>
								   		<c:if test='${not member.getMaritalStatus()}'>UnMarried</c:if>
									</td>			   		
							   		<td>${member.getContactNo()}</td>
							   		<td>${member.getEmailID()}</td>
							   		<td>${member.getBloodGroup()}</td>
							   		<td>${member.getBirthDate()}</td>
							   		<td>${member.getBirthPlace()}</td>
							   	   	<td>${member.getEducationQualification()}</td>
							   	   	<td>${member.getOccupation().getOccupationName()}</td>
							   	   	<td>${member.getWorkingAddress()}</td>
							   	   	<td>${member.getRelation().getRelationName()}</td>			   	   	
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<p>
					Current Address:
					${requestScope.currentAddress.getStreet()}
					<c:if test='${not empty requestScope.currentAddress.getStreet()}'>,</c:if>
					${requestScope.currentAddress.getArea()}
					<c:if test='${not empty requestScope.currentAddress.getArea()}'>,</c:if>
					${requestScope.currentAddress.getCity().getCityName()}
					<c:if test='${not empty requestScope.currentAddress.getCity().getCityName()}'>,</c:if>
					${requestScope.currentAddress.getCity().getState().getStateName()}
					<c:if test='${not empty requestScope.currentAddress.getCity().getState().getStateName()}'>,</c:if>
					${requestScope.currentAddress.getCity().getState().getCountry().getCountryName()}
				</p>
				<p>
					Native Address:${requestScope.nativeAddress}
				</p>
				<p>
					<a href='javascript:history.go(-1)'>Go Back</a>
				</p>
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