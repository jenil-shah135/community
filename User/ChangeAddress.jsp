<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/layout/head.jsp" />
<title>Change Address</title>
</head>
<c:if test='${not empty param.currentAddressStreet}'><% request.setAttribute("currentAddressStreet",request.getParameter("currentAddressStreet")); %></c:if>
<c:if test='${not empty param.currentAddressArea}'><% request.setAttribute("currentAddressArea",request.getParameter("currentAddressArea")); %></c:if>
<c:if test='${not empty param.currentAddressCity}'><% request.setAttribute("currentAddressCity",request.getParameter("currentAddressCity")); %></c:if>
<c:if test='${not empty param.currentAddressState}'><% request.setAttribute("currentAddressState",request.getParameter("currentAddressState")); %></c:if>
<c:if test='${not empty param.currentAddressCountry}'><% request.setAttribute("currentAddressCountry",request.getParameter("currentAddressCountry")); %></c:if>
<c:if test='${not empty param.currentAddressPincode}'><% request.setAttribute("currentAddressPincode",request.getParameter("currentAddressPincode")); %></c:if>
<c:if test='${not empty param.nativeAddress}'><% request.setAttribute("nativeAddress",request.getParameter("nativeAddress")); %></c:if>
<body id="inner-page">
<jsp:include page="/layout/UserMenu.jsp" />
	<section id="main">
  		<div class="intro_wrap">
    		<div class="center">
      			<h2>Change Address</h2>
    		</div>
  		</div>
  		<!-- end .intro_wrap-->  
		<div class="center">  
	    	<section id="full_width">
				<form action="/Community/User/Address/Update" method="post">
					${messages.mainError}
					${messages.success}
					<jsp:include page="/layout/CSRF.jsp" />
					<p>
						<label for="currentAddressStreet">Current Address Street:</label>
						<input type="text" name="currentAddressStreet" value="${currentAddressStreet}" />
						${messages.currentAddressStreetError}
					</p>
					<p>
						<label for="currentAddressArea">Current Address Area:</label>
						<input type="text" name="currentAddressArea" value="${currentAddressArea}" />
						${messages.currentAddressAreaError}
					</p>
					<p>
						<label for="ccountry">Current Address Country:</label>
						<select name="currentAddressCountry" required="required" id="ccountry" title="Please select country" x-moz-errormessage="Please select country" >
							<option value="">Select</option>
							<c:forEach var="country" items="${requestScope.countryList}" >
						    	<option value="${country.getCountryID()}" <c:if test='${currentAddressCountry == country.getCountryID()}'>selected</c:if> >${country.getCountryName()}</option>		
						   	</c:forEach>
						</select>	
						${messages.currentAddressCountryError}
					</p>
					<p>
						<label for="cstate">Current Address State:</label>
						<select name="currentAddressState" required="required" id="cstate" title="Please select state" x-moz-errormessage="Please select state" >
							<option value="">Select</option>
							<c:forEach var="state" items="${requestScope.currentStateList}" >
						    	<option value="${state.getStateID()}" <c:if test='${currentAddressState == state.getStateID()}'>selected</c:if> >${state.getStateName()}</option>		
						   	</c:forEach>
						</select>
						${messages.currentAddressStateError}
					</p>
					<p>
						<label for="ccity">Current Address City:</label>
						<select name="currentAddressCity" required="required" id="ccity" title="Please select city" x-moz-errormessage="Please select city" >
							<option value="">Select</option>
							<c:forEach var="city" items="${requestScope.currentCityList}" >
						    	<option value="${city.getCityID()}" <c:if test='${currentAddressCity == city.getCityID()}'>selected</c:if> >${city.getCityName()}</option>		
						   	</c:forEach>
						</select>
						${messages.currentAddressCityError}
					</p>
					<p>
						<label for="currentAddressPincode">Current Address Pincode:</label>
						<input type="text" name="currentAddressPincode" value="${currentAddressPincode}" />
						${messages.currentAddressPincodeError}
					</p>
					<p>
						<label for="nativeAddress">Native Address:</label>
						<textarea cols="25" rows="5" name="nativeAddress">${nativeAddress}</textarea>
						${messages.nativeAddressError}
					</p>
					<p>
						<input type="submit" name="submit" id="button_submit" value="Update Address" />
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