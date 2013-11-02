<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/layout/head.jsp" />
<title>Update Member</title>
</head>
<c:if test='${not empty param.id}'><% request.setAttribute("id",request.getParameter("id")); %></c:if>
<c:if test='${not empty param.firstName}'><% request.setAttribute("firstName",request.getParameter("firstName")); %></c:if>
<c:if test='${not empty param.middleName}'><% request.setAttribute("middleName",request.getParameter("middleName")); %></c:if>
<c:if test='${not empty param.lastName}'><% request.setAttribute("lastName",request.getParameter("lastName")); %></c:if>
<c:if test='${not empty param.gender}'><% request.setAttribute("gender",request.getParameter("gender")); %></c:if>
<c:if test='${not empty param.maritalStatus}'><% request.setAttribute("maritalStatus",request.getParameter("maritalStatus")); %></c:if>
<c:if test='${not empty param.contactNo}'><% request.setAttribute("contactNo",request.getParameter("contactNo")); %></c:if>
<c:if test='${not empty param.emailID}'><% request.setAttribute("emailID",request.getParameter("emailID")); %></c:if>
<c:if test='${not empty param.eduQualification}'><% request.setAttribute("eduQualification",request.getParameter("eduQualification")); %></c:if>
<c:if test='${not empty param.occupation}'><% request.setAttribute("occupation",request.getParameter("occupation")); %></c:if>
<c:if test='${not empty param.bloodgroup}'><% request.setAttribute("bloodgroup",request.getParameter("bloodgroup")); %></c:if>
<c:if test='${not empty param.birthdate}'><% request.setAttribute("birthdate",request.getParameter("birthdate")); %></c:if>
<c:if test='${not empty param.birthplace}'><% request.setAttribute("birthplace",request.getParameter("birthplace")); %></c:if>
<c:if test='${not empty param.workingAddress}'><% request.setAttribute("workingAddress",request.getParameter("workingAddress")); %></c:if>
<c:if test='${not empty param.relation}'><% request.setAttribute("relation",request.getParameter("relation")); %></c:if>
<body id="inner-page">
<jsp:include page="/layout/UserMenu.jsp" />
	<section id="main">
  		<div class="intro_wrap">
    		<div class="center">
      			<h2>Edit Member Detail</h2>
    		</div>
  		</div>
  		<!-- end .intro_wrap-->  
		<div class="center">  
	    	<section id="full_width">
				<form action="/Community/User/Member/Edit" method="post">
					${messages.mainError}
					${messages.success}
					<jsp:include page="/layout/CSRF.jsp" />
					<input type="hidden" name="id" value="${param.id}" />
					<p>
						<label for="name">Name:</label>
						<input type="text" name="firstName" value="${firstName}" required="required" placeholder="First Name" x-moz-errormessage="Please enter first name" title="Please enter first name" />
						<input type="text" name="middleName" value="${middleName}" required="required" placeholder="Middle Name" x-moz-errormessage="Please enter middle name" title="Please enter middle name" />	
						<input type="text" name="lastName" value="${lastName}" required="required" placeholder="Last Name" x-moz-errormessage="Please enter last name" title="Please enter last name" />
						${messages.nameError}
					</p>
					<p>
						<label for="gender">Gender:</label>
						<input type="radio" name="gender" value="male" <c:if test='${gender == "male"}'>checked</c:if> required="required" />
						Male
						<input type="radio" name="gender" value="female" <c:if test='${gender == "female"}'>checked</c:if> required="required" />
						Female
						${messages.genderError}
					</p>
					<p>
						<label for="maritalStatus">Marital Status:</label>
						<select name="maritalStatus" required="required" x-moz-errormessage="Please select marital status" title="Please select marital status">
							<option value="">Select</option>
							<option value="married" <c:if test='${maritalStatus == "married"}'> selected </c:if> >
								Married
							</option>
							<option value="unmarried" <c:if test='${maritalStatus == "unmarried"}'> selected </c:if> >
								Unmarried
							</option>
						</select>
						${messages.maritalStatusError}
					</p>
					<p>
						<label for="contactNo">Contact No:</label>
						<input type="tel" name="contactNo" value="${contactNo}" required="required" x-moz-errormessage="Please enter valid contact no" title="Please enter valid contact no" />
						${messages.contactNoError}
					</p>
					<p>
						<label for="emailID">Email ID:</label>
						<input type="email" name="emailID" value="${emailID}" required="required" x-moz-errormessage="Please enter valid Email ID" title="Please enter valid Email ID" />
						${messages.emailIDError}
					</p>
					<p>
						<label for="eduQualification">Education Qualification:</label>
						<input type="text" name="eduQualification" value="${eduQualification}" required="required" x-moz-errormessage="Please enter education qualification" title="Please enter education qualification" />
						${messages.eduQualificationError}
					</p>
					<p>
						<label for="occupation">Occupation:</label>
						<select name="occupation" required="required" x-moz-errormessage="Please select occupation" title="Please select occupation">
							<option value="">Select</option>
							<c:forEach var="objOccupation" items="${requestScope.occupationList}" >
						    	<option value="${objOccupation.getOccupationID()}" <c:if test='${occupation == objOccupation.getOccupationID()}'>selected</c:if> >${objOccupation.getOccupationName()}</option>		
						   	</c:forEach>
						</select>	
						${messages.occupationError}
					</p>
					<p>
						<label for="workingAddress">Working Address:</label>
						<textarea cols="25" rows="5" name="workingAddress">${workingAddress}</textarea>						
						${messages.workingAddressError}
					</p>
					<p>
						<label for="bloodgroup">Blood Group:</label>
						<select name="bloodgroup">
							<option value="" <c:if test="${bloodgroup == ''}">selected</c:if>>Select</option>
							<option value="A+" <c:if test="${bloodgroup == 'A+'}">selected</c:if>>A+</option>
							<option value="A-" <c:if test="${bloodgroup == 'A-'}">selected</c:if>>A-</option>
							<option value="B+" <c:if test="${bloodgroup == 'B+'}">selected</c:if>>B+</option>
							<option value="B-" <c:if test="${bloodgroup == 'B-'}">selected</c:if>>B-</option>
							<option value="AB+" <c:if test="${bloodgroup == 'AB+'}">selected</c:if>>AB+</option>
							<option value="AB-" <c:if test="${bloodgroup == 'AB-'}">selected</c:if>>AB-</option>
							<option value="O+" <c:if test="${bloodgroup == 'O+'}">selected</c:if>>O+</option>
							<option value="O-" <c:if test="${bloodgroup == 'O-'}">selected</c:if>>O-</option>				
						</select>	
						${messages.bloodgroupError}
					</p>
					<p>
						<label for="birthdate">Birth Date:</label>
						<input type="date" id="datepicker" name="birthdate" value="${birthdate}" required="required" x-moz-errormessage="Please enter birthdate" title="Please enter birthdate" />
						${messages.birthdateError}
					</p>
					<p>
						<label for="birthplace">Birth Place:</label>
						<input type="text" name="birthplace" value="${birthplace}" />
						${messages.birthplaceError}
					</p>
					<p>
						<label for="relation">Relation:</label>
						<select name="relation" required="required" x-moz-errormessage="Please select relation" title="Please select relation">
							<option value="">Select</option>
							<c:forEach var="objRelation" items="${requestScope.relationList}" >
						    	<option value="${objRelation.getRelationID()}" <c:if test='${relation == objRelation.getRelationID()}'>selected</c:if> >${objRelation.getRelationName()}</option>		
						   	</c:forEach>
						</select>	
						${messages.relationError}
					</p>
					<p>
						<input type="submit" name="submit" id="button_submit" value="Add Member" />
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