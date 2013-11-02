<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/layout/head.jsp" />
<title>Find Member</title>
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
      			<h2>Find Member</h2>
    		</div>
  		</div>
  		<!-- end .intro_wrap-->  
		<div class="center">  
	    	<section id="full_width">
	    		<div>
					<form action="/Community/User/Member/Find" method="get">
						${messages.mainError}
						<p>
							<label for="name">Name:</label>
							<input type="text" name="name" value="${param.name}" />
							${messages.nameError}
						</p>
						<p>
							<label for="name">Gender:</label>
							<input type="radio" name="gender" value="male" <c:if test='${param.gender == "male"}'>checked</c:if> />
							Male
							<input type="radio" name="gender" value="female" <c:if test='${param.gender == "female"}'>checked</c:if> />
							Female
							${messages.genderError}
						</p>
						<p>
							<label for="name">Marital Status:</label>
							<select name="maritalStatus">
								<option value="">Select</option>
								<option value="married" <c:if test='${param.maritalStatus == "married"}'> selected </c:if> >
									Married
								</option>
								<option value="unmarried" <c:if test='${param.maritalStatus == "unmarried"}'> selected </c:if> >
									Unmarried
								</option>
							</select>
							${messages.maritalStatusError}
						</p>
						<p>
							<label for="name">Contact No:</label>
							<input type="tel" name="contactNo" value="${param.contactNo}" />
							${messages.contactNoError}
						</p>
						<p>
							<label for="name">Email ID:</label>
							<input type="email" name="emailID" value="${param.emailID}" />
							${messages.emailIDError}
						</p>
						<p>
							<label for="name">Blood Group:</label>
							<select name="bloodgroup">
								<option value="" <c:if test="${param.bloodgroup == ''}">selected</c:if>>Select</option>
								<option value="A+" <c:if test="${param.bloodgroup == 'A+'}">selected</c:if>>A+</option>
								<option value="A-" <c:if test="${param.bloodgroup == 'A-'}">selected</c:if>>A-</option>
								<option value="B+" <c:if test="${param.bloodgroup == 'B+'}">selected</c:if>>B+</option>
								<option value="B-" <c:if test="${param.bloodgroup == 'B-'}">selected</c:if>>B-</option>
								<option value="AB+" <c:if test="${param.bloodgroup == 'AB+'}">selected</c:if>>AB+</option>
								<option value="AB-" <c:if test="${param.bloodgroup == 'AB-'}">selected</c:if>>AB-</option>
								<option value="O+" <c:if test="${param.bloodgroup == 'O+'}">selected</c:if>>O+</option>
								<option value="O-" <c:if test="${param.bloodgroup == 'O-'}">selected</c:if>>O-</option>				
							</select>	
							${messages.bloodgroupError}
						</p>
						<p>
							<label for="name">Age</label>
							<input type="number" name="age" value="${param.age}" />
							${messages.ageError}
						</p>
						<p>
							<input type="submit" name="submit" id="button_submit" value="Find Member" />
						</p>
					</form>
				</div>
				<div>
					<table border='0' class='display' rel='datatable' id='table_id'>
						<thead>
							<tr>
								<th>Member Name</th>
								<th>Gender</th>
								<th>Marital Status</th>
								<th>Contact No</th>
								<th>Email ID</th>
								<th>Blood Group</th>
								<th>Birth Date</th>
								<th class='no-sort'>View Family Detail</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="member" items="${requestScope.memberList}">
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
							   	   	<td><a href="/Community/User/Family?id=${member.getFamilyID()}">View Family Detail</a></td>
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
</body>
</html>