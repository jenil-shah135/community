<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/layout/head.jsp" />
<title>Register</title>
<link href="/Community/css/jquery.ui.theme.css" type="text/css" rel="stylesheet" />
<link href="/Community/css/jquery.ui.datepicker.css" type="text/css" rel="stylesheet" />
<style type="text/css">
.ui-datepicker-next,.ui-datepicker-prev {
	display:none;
}
</style>
</head>
<body id="inner-page">
<jsp:include page="/layout/menu.jsp" />
	<section id="main">
  		<div class="intro_wrap">
    		<div class="center">
      			<h2>Register</h2>
    		</div>
  		</div>
  		<!-- end .intro_wrap-->  
		<div class="center">  
	    	<section id="full_width">			
				<form action="/Community/Register" method="post">
					${messages.mainError}
					${messages.success}
					<jsp:include page="/layout/CSRF.jsp" />
					<p>
						<label for="name">Name:</label>
						<input type="text" id="name" placeholder="First Name" name="firstName" value="${param.firstName}" required="required" title="Please enter first name" />
						<input type="text" id="name" placeholder="Middle Name" name="middleName" value="${param.middleName}" required="required" title="Please enter middle name" />	
						<input type="text" id="name" placeholder="Lase Name" name="lastName" value="${param.lastName}" required="required" title="Please enter last name" />
						${messages.nameError}
					</p>
					<p>
						<label for="gender">Gender:</label>
						<input type="radio" name="gender" id="gender" value="male" <c:if test='${param.gender == "male"}'>checked</c:if> required="required" />
						Male
						<input type="radio" name="gender" id="gender" value="female" <c:if test='${param.gender == "female"}'>checked</c:if> required="required" />
						Female
						${messages.genderError}
					</p>
					<p>
						<label for="maritalStatus">Marital Status:</label>
						<select id="maritalStatus" name="maritalStatus" required="required" title="Please select marital status">
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
						<label for="contactNo">Contact No:</label>
						<input type="tel" id="contactNo" name="contactNo" value="${param.contactNo}" required="required" title="Please enter valid contact no" />
						${messages.contactNoError}
					</p>
					<p>
						<label for="email">Email ID:</label>
						<input id="email" type="email" name="emailID" value="${param.emailID}" required="required" title="Please enter valid Email ID" />
						${messages.emailIDError}
					</p>
					<p>
						<label for="eduQualification">Education Qualification:</label>
						<input type="text" id="eduQualification" name="eduQualification" value="${param.eduQualification}" required="required" title="Please enter education qualification" />
						${messages.eduQualificationError}
					</p>
					<p>
						<label for="occupation">Occupation:</label>
						<select id="occupation" name="occupation" required="required" title="Please select occupation">
							<option value="">Select</option>
							<c:forEach var="objOccupation" items="${requestScope.occupationList}" >
						    	<option value="${objOccupation.getOccupationID()}" <c:if test='${param.occupation == objOccupation.getOccupationID()}'>selected</c:if> >${objOccupation.getOccupationName()}</option>		
						   	</c:forEach>
						</select>	
						${messages.occupationError}
					</p>
					<p>
						<label for="workingAddress">Working Address:</label>
						<textarea cols="25" rows="5" name="workingAddress" id="workingAddress">${param.workingAddress}</textarea>
						${messages.workingAddressError}
					</p>
					<p>
						<label for="bloodgroup">Blood Group:</label>
						<select name="bloodgroup" id="bloodgroup">
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
						<label for="datepicker">Birth Date:</label>
						<input type="date" id="datepicker" name="birthdate" value="${param.birthdate}" required="required" title="Please enter birthdate" />
						${messages.birthdateError}
					</p>
					<p>
						<label for="birthplace">Birth Place:</label>
						<input type="text" id="birthplace" name="birthplace" value="${param.birthplace}" />
						${messages.birthplaceError}
					</p>
					<label for="currentAddress">Current Address:</label>
					<hr />
					<p>
						<label for="currentAddressStreet">Street:</label>
						<input type="text" id="currentAddressStreet" name="currentAddressStreet" value="${param.currentAddressStreet}" />
						${messages.currentAddressStreetError}
					</p>
					<p>
						<label for="currentAddressArea">Area:</label>
						<input id="currentAddressArea" type="text" name="currentAddressArea" value="${param.currentAddressArea}" />
						${messages.currentAddressAreaError}
					</p>
					<p>
						<label for="ccountry">Country:</label>
						<select name="currentAddressCountry" required="required" id="ccountry" title="Please select country" x-moz-errormessage="Please select country" >
							<option value="">Select</option>
							<c:forEach var="country" items="${requestScope.countryList}" >
						    	<option value="${country.getCountryID()}" <c:if test='${param.currentAddressCountry == country.getCountryID()}'>selected</c:if> >${country.getCountryName()}</option>		
						   	</c:forEach>
						</select>	
						${messages.currentAddressCountryError}
					</p>
					<p>
						<label for="cstate">State:</label>
						<select name="currentAddressState" required="required" id="cstate" title="Please select state" x-moz-errormessage="Please select state" >
							<option value="">Select</option>
							<c:forEach var="state" items="${requestScope.currentStateList}" >
						    	<option value="${state.getStateID()}" <c:if test='${param.currentAddressState == state.getStateID()}'>selected</c:if> >${state.getStateName()}</option>		
						   	</c:forEach>
						</select>
						${messages.currentAddressStateError}
					</p>
					<p>
						<label for="ccity">City:</label>
						<select name="currentAddressCity" required="required" id="ccity" title="Please select city" x-moz-errormessage="Please select city" >
							<option value="">Select</option>
							<c:forEach var="city" items="${requestScope.currentCityList}" >
						    	<option value="${city.getCityID()}" <c:if test='${param.currentAddressCity == city.getCityID()}'>selected</c:if> >${city.getCityName()}</option>		
						   	</c:forEach>
						</select>
						${messages.currentAddressCityError}
					</p>
					<p>
						<label for="currentAddressPincode">Pincode:</label>
						<input type="text" id="currentAddressPincode" name="currentAddressPincode" value="${param.currentAddressPincode}" />
						${messages.currentAddressPincodeError}
					</p>
					<p>
						<label for="nativeAddres">Native Address:</label>
						<textarea id="nativeAddres" cols="25" rows="5" name="nativeAddress">${param.nativeAddress}</textarea>
						${messages.nativeAddress}
					</p>
					<p><input type="submit" name="submit" id="button_submit" value="Register" /></p>
				</form>
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
	$('#ccountry').change(function() {
		if($(this).val()) {
			var url="/Community/GetData?item=state&id="+$(this).val();
			$.get(url,
				function(data) {			
					if(data=="") {
						$('#cstate').html("<option value=''>Select</option>");
						$('#ccity').html("<option value=''>Select</option>");
					} else {
						$('#cstate').html("<option value=''>Select</option>"+data);
						$('#ccity').html("<option value=''>Select</option>");
					}
				},"text"
			);
		}
		else {
			$('#cstate').html("<option value=''>Select</option>");
			$('#ccity').html("<option value=''>Select</option>");
		}
	});
	$('#cstate').change(function() {
		if($(this).val()) {
			var url="/Community/GetData?item=city&id="+$(this).val();
			$.get(url,
				function(data) {			
					if(data=="") {
						$('#ccity').html("<option value=''>Select</option>");
					} else {
						$('#ccity').html("<option value=''>Select</option>"+data);
					}
				},"text"	
			);
		}
		else {
			$('#ccity').html("<option value=''>Select</option>");
		}
	});
});
</script>
</body>
</html>