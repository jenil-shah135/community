package com.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bean.AddressBean;
import com.bean.CityBean;
import com.bean.CountryBean;
import com.bean.FamilyBean;
import com.bean.MemberBean;
import com.bean.RelationBean;
import com.bean.StateBean;
import com.dao.AddressDAO;
import com.dao.CityDAO;
import com.dao.CountryDAO;
import com.dao.FamilyDAO;
import com.dao.MemberDAO;
import com.dao.OccupationDAO;
import com.dao.RelationDAO;
import com.dao.StateDAO;
import com.utility.Validations;

public class RegisterServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;  
    public RegisterServlet()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		request.setAttribute("countryList", new CountryDAO().getAll());
		request.setAttribute("currentStateList", new ArrayList<StateBean>());
		request.setAttribute("currentCityList", new ArrayList<CityBean>());
		request.setAttribute("occupationList", new OccupationDAO().getAll());
		RequestDispatcher rd = request.getRequestDispatcher("/Register.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		CountryDAO objCountryDAO=new CountryDAO();
		StateDAO objStateDAO=new StateDAO();
		CityDAO objCityDAO=new CityDAO();
		AddressDAO objAddressDAO=new AddressDAO();
		FamilyDAO objFamilyDAO=new FamilyDAO();
		RelationDAO objRelationDAO=new RelationDAO();
		MemberDAO objMemberDAO=new MemberDAO();
		OccupationDAO objOccupationDAO=new OccupationDAO();
		
		request.setAttribute("currentStateList", new ArrayList<StateBean>());
		request.setAttribute("currentCityList", new ArrayList<CityBean>());
		
		Map<String,String> messages=new HashMap<String,String>();
		try
		{
			String firstName=request.getParameter("firstName");
			String middleName=request.getParameter("middleName");
			String lastName=request.getParameter("lastName");
			String gender=request.getParameter("gender");
			String maritalStatus=request.getParameter("maritalStatus");
			String contactNo=request.getParameter("contactNo");
			String emailID=request.getParameter("emailID");
			String educationQulification=request.getParameter("eduQualification");
			String occupation=request.getParameter("occupation");
			String bloodgroup=request.getParameter("bloodgroup");
			String birthdate=request.getParameter("birthdate");
			String birthplace=request.getParameter("birthplace");
			String workingAddress=request.getParameter("workingAddress");
			String currentAddressStreet=request.getParameter("currentAddressStreet");
			String currentAddressArea=request.getParameter("currentAddressArea");
			String currentAddressCity=request.getParameter("currentAddressCity");
			String currentAddressState=request.getParameter("currentAddressState");
			String currentAddressCountry=request.getParameter("currentAddressCountry");
			String currentAddressPincode=request.getParameter("currentAddressPincode");
			String nativeAddress=request.getParameter("nativeAddress");

			if(lastName==null)
			{
				messages.put("nameError", "<div class='editor-error'>Please enter last name.</div>");
			}
			else
			{
				lastName=lastName.trim();
				if(lastName.length()==0)
				{
					messages.put("nameError", "<div class='editor-error'>Please enter last name.</div>");
				}
			}
			if(middleName==null)
			{
				messages.put("nameError", "<div class='editor-error'>Please enter middle name.</div>");
			}
			else
			{
				middleName=middleName.trim();
				if(middleName.length()==0)
				{
					messages.put("nameError", "<div class='editor-error'>Please enter middle name.</div>");
				}
			}
			if(firstName==null)
			{
				messages.put("nameError", "<div class='editor-error'>Please enter name.</div>");
			}
			else
			{
				firstName=firstName.trim();
				if(firstName.length()==0)
				{
					messages.put("nameError", "<div class='editor-error'>Please enter name.</div>");
				}
			}
			if(gender==null)
			{
				messages.put("genderError", "<div class='editor-error'>Please select gender.</div>");
			}
			else
			{
				gender=gender.trim().toLowerCase();
				if(!(gender.equals("male") || gender.equals("female")))
				{
					messages.put("genderError", "<div class='editor-error'>Please select gender.</div>");
				}
			}
			if(maritalStatus==null)
			{
				messages.put("maritalStatusError", "<div class='editor-error'>Please select marital status.</div>");
			}
			else
			{
				maritalStatus=maritalStatus.trim().toLowerCase();
				if(!(maritalStatus.equals("married") || maritalStatus.equals("unmarried")))
				{
					messages.put("maritalStatusError", "<div class='editor-error'>Please select marital status.</div>");
				}
			}
			if(emailID==null)
			{
				messages.put("emailIDError", "<div class='editor-error'>Please enter Email ID.</div>");
			}
			else
			{
				emailID=emailID.trim().toLowerCase();
				if(emailID.length()==0)
				{
					messages.put("emailIDError", "<div class='editor-error'>Please enter Email ID.</div>");
				}
				else
				{
					if(!Validations.validateEmailID(emailID))
					{
						messages.put("emailIDError", "<div class='editor-error'>Please enter valid Email ID.</div>");
					}
				}
			}
			if(contactNo==null)
			{
				messages.put("contactNoError", "<div class='editor-error'>Please enter contact no.</div>");
			}
			else
			{
				contactNo=contactNo.trim();
				if(contactNo.length()==0)
				{
					messages.put("contactNoError", "<div class='editor-error'>Please enter contact no.</div>");
				}
				else
				{
					if(!Validations.isNumeric(contactNo))
					{
						messages.put("contactNoError", "<div class='editor-error'>Please enter valid contact no.</div>");
					}
				}
			}
			if(educationQulification==null)
			{
				educationQulification="";
			}
			if(occupation==null)
			{
				messages.put("occupationError", "<div class='editor-error'>Please select occupation.</div>");
			}
			else
			{
				occupation=occupation.trim();
				if(occupation.length()==0)
				{
					messages.put("occupationError", "<div class='editor-error'>Please select occupation.</div>");
				}
				else
				{
					if(!Validations.isNumeric(occupation))
					{
						messages.put("occupationError", "<div class='editor-error'>Please select occupation.</div>");
					}
					else
					{						
						if(objOccupationDAO.getByID(Integer.parseInt(occupation))==null)
						{
							messages.put("occupationError", "<div class='editor-error'>Please select occupation.</div>");
						}					
					}
				}
			}
			if(workingAddress==null)
			{
				workingAddress="";
			}
			if(bloodgroup==null)
			{
				messages.put("bloodgroupError", "<div class='editor-error'>Please select bloodgroup.</div>");
			}
			else
			{
				bloodgroup=bloodgroup.trim();
				switch(bloodgroup)
				{
					case "A+":
					case "A-":
					case "B+":
					case "B-":
					case "O+":
					case "O-":
					case "AB+":
					case "AB-":
						break;
					default:
						messages.put("bloodgroupError", "<div class='editor-error'>Please select bloodgroup.</div>");
				}			
			}
			if(birthdate==null)
			{
				messages.put("birthdateError", "<div class='editor-error'>Please select birthdate.</div>");
			}
			else
			{
				birthdate=birthdate.trim();
				if(birthdate.length()==0)
				{
					messages.put("birthdateError", "<div class='editor-error'>Please select birthdate.</div>");
				}
				else
				{
					try
					{					
						java.util.Date objDate = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH).parse(birthdate);						
						if(objDate.after(new java.util.Date()))
						{
							messages.put("birthdateError", "<div class='editor-error'>Please enter valid birthdate.</div>");
						}
					}
					catch(Exception ex)
					{
						messages.put("birthdateError", "<div class='editor-error'>Please enter valid birthdate.</div>");
					}
				}
			}
			if(birthplace==null)
			{
				birthplace="";
			}
			if(currentAddressStreet==null)
			{
				currentAddressStreet="";
			}
			if(currentAddressArea==null)
			{
				currentAddressArea="";
			}
			if(currentAddressCountry==null)
			{
				messages.put("currentAddressCountryError", "<div class='editor-error'>Please select current country.</div>");
			}
			else
			{
				currentAddressCountry=currentAddressCountry.trim();
				if(currentAddressCountry.length()==0)
				{
					messages.put("currentAddressCountryError", "<div class='editor-error'>Please select current country.</div>");
				}
				else
				{
					if(!Validations.isNumeric(currentAddressCountry))
					{
						messages.put("currentAddressCountryError", "<div class='editor-error'>Please select current country.</div>");
					}
					else
					{
						CountryBean objCurrentCountry=objCountryDAO.getByID(Integer.parseInt(currentAddressCountry));
						if(objCurrentCountry==null)
						{
							messages.put("currentAddressCountryError", "<div class='editor-error'>Please select current country.</div>");
						}
						else
						{
							request.setAttribute("currentStateList", objStateDAO.getAllByCountryID(Integer.parseInt(currentAddressCountry)));	
						}
					}
				}
			}
			if(currentAddressState==null)
			{
				messages.put("currentAddressStateError", "<div class='editor-error'>Please select current state.</div>");
			}
			else
			{
				currentAddressState=currentAddressState.trim();
				if(currentAddressState.length()==0)
				{
					messages.put("currentAddressStateError", "<div class='editor-error'>Please select current state.</div>");
				}
				else
				{
					if(!Validations.isNumeric(currentAddressState))
					{
						messages.put("currentAddressStateError", "<div class='editor-error'>Please select your current state.</div>");
					}
					else
					{
						StateBean objCurrentState=objStateDAO.getByID(Integer.parseInt(currentAddressState));
						if(objCurrentState==null)
						{
							messages.put("currentAddressStateError", "<div class='editor-error'>Please select your current state.</div>");
						}
						else
						{
							request.setAttribute("currentCityList", objCityDAO.getAllByStateID(Integer.parseInt(currentAddressState)));	
						}
					}
				}
			}
			if(currentAddressCity==null)
			{
				messages.put("currentAddressCityError", "<div class='editor-error'>Please enter current city.</div>");
			}
			else
			{
				currentAddressCity=currentAddressCity.trim();
				if(currentAddressCity.length()==0)
				{
					messages.put("currentAddressCityError", "<div class='editor-error'>Please select current city.</div>");
				}
				else
				{
					if(!Validations.isNumeric(currentAddressCity))
					{
						messages.put("currentAddressCityError", "<div class='editor-error'>Please select current city.</div>");
					}
					else
					{
						CityBean objCurrentCity=objCityDAO.getByID(Integer.parseInt(currentAddressCity));
						if(objCurrentCity==null)
						{
							messages.put("currentAddressCityError", "<div class='editor-error'>Please select current city.</div>");
						}			
					}
				}
			}			
			if(currentAddressPincode==null)
			{
				currentAddressPincode="";
			}
			else
			{
				currentAddressPincode=currentAddressPincode.trim();
				if(currentAddressPincode.length()!=0)
				{
					if(!Validations.isNumeric(currentAddressPincode))
					{
						messages.put("currentAddressPincodeError", "<div class='editor-error'>Please enter valid current address pincode.</div>");
					}
				}
			}
			if(nativeAddress==null)
			{
				nativeAddress="";
			}			
			
			if(messages.size()==0)
			{
				CityBean objCurrentCity=objCityDAO.getByID(Integer.parseInt(currentAddressCity));
				if(!objCurrentCity.getState().getStateID().toString().equals(currentAddressState))
				{
					messages.put("currentAddressStateError", "<div class='editor-error'>Please select current state.</div>");
				}
				else
				{
					if(!objCurrentCity.getState().getCountry().getCountryID().toString().equals(currentAddressCountry))
					{
						messages.put("currentAddressCountryError", "<div class='editor-error'>Please select current country.</div>");
					}
				}
				RelationBean objRelationBean=objRelationDAO.getByRelationName("Head");
				if(objRelationBean==null)
				{
					messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
				}
				if(messages.size()==0)
				{					
					AddressBean objCurrentAddress=new AddressBean();
					objCurrentAddress.setStreet(currentAddressStreet);
					objCurrentAddress.setArea(currentAddressArea);
					objCurrentAddress.setCity(objCurrentCity);
					objCurrentAddress.setPincode(currentAddressPincode);
					objCurrentAddress=objAddressDAO.insert(objCurrentAddress);					
					FamilyBean objFamilyBean=new FamilyBean();
					objFamilyBean.setCurrentAddress(objCurrentAddress);
					objFamilyBean.setNativeAddress(nativeAddress);
					objFamilyBean=objFamilyDAO.insert(objFamilyBean);					
					MemberBean objMemberBean=new MemberBean();
					objMemberBean.setBirthDate(new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH).parse(birthdate));
					objMemberBean.setBirthPlace(birthplace);
					objMemberBean.setBloodGroup(bloodgroup);
					objMemberBean.setContactNo(contactNo);
					objMemberBean.setEducationQualification(educationQulification);
					objMemberBean.setEmailID(emailID);
					objMemberBean.setFamilyID(objFamilyBean.getFamilyID());
					objMemberBean.setGender(gender.equalsIgnoreCase("male")?true:false);
					objMemberBean.setMaritalStatus(maritalStatus.equalsIgnoreCase("married")?true:false);
					objMemberBean.setFirstName(firstName);
					objMemberBean.setMiddleName(middleName);
					objMemberBean.setLastName(lastName);					
					objMemberBean.setOccupation(objOccupationDAO.getByID(Integer.parseInt(occupation)));
					objMemberBean.setRelation(objRelationBean);
					objMemberBean.setWorkingAddress(workingAddress);
					objMemberBean=objMemberDAO.insert(objMemberBean);
					if(objMemberBean.getMemberID()==null)
					{
						messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
					}
				}
			}
		}
		catch(Exception ex)
		{
			messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
		}
		finally
		{
			if(messages.size()==0)
			{
				response.sendRedirect("/Community/Thank-You.jsp");
			}
			else
			{
				request.setAttribute("countryList", objCountryDAO.getAll());
				request.setAttribute("occupationList", objOccupationDAO.getAll());
				request.setAttribute("messages", messages);
				RequestDispatcher rd = request.getRequestDispatcher("/Register.jsp");
				rd.forward(request, response);
			}
		}
	}
}