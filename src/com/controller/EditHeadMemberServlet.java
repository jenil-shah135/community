package com.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.FamilyBean;
import com.bean.MemberBean;
import com.dao.FamilyDAO;
import com.dao.MemberDAO;
import com.dao.OccupationDAO;
import com.utility.General;
import com.utility.Validations;

public class EditHeadMemberServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;       
    public EditHeadMemberServlet()
    {
        super();
    }	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		FamilyDAO objFamilyDAO=new FamilyDAO();
		MemberDAO objMemberDAO=new MemberDAO();
		OccupationDAO objOccupationDAO=new OccupationDAO();
		HttpSession session=request.getSession(true);
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
			if(session.getAttribute("familyID")==null || session.getAttribute("isAdmin")==null || !session.getAttribute("isAdmin").toString().equalsIgnoreCase("false"))
			{
				messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
			}
			if(messages.size()==0)
			{
				MemberBean objMemberBean=General.getHeadMember(objFamilyDAO.getByID((int)session.getAttribute("familyID")).getFamilyMembers().iterator());
				if(objMemberBean!=null)
				{
					objMemberBean.setBirthDate(new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH).parse(birthdate));
					objMemberBean.setBirthPlace(birthplace);
					objMemberBean.setBloodGroup(bloodgroup);
					objMemberBean.setContactNo(contactNo);
					objMemberBean.setEducationQualification(educationQulification);
					objMemberBean.setEmailID(emailID);
					objMemberBean.setGender(gender.equalsIgnoreCase("male")?true:false);
					objMemberBean.setMaritalStatus(maritalStatus.equalsIgnoreCase("married")?true:false);
					objMemberBean.setFirstName(firstName);
					objMemberBean.setMiddleName(middleName);
					objMemberBean.setLastName(lastName);
					objMemberBean.setOccupation(objOccupationDAO.getByID(Integer.parseInt(occupation)));
					objMemberBean.setWorkingAddress(workingAddress);
					if(!objMemberDAO.update(objMemberBean))
					{
						messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
					}
				}
				else
				{
					messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
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
				session.setAttribute("msg", "<div class='editor-success'>Head Member successfully updated.</div>");
				response.sendRedirect("/Community/User/");
			}
			else
			{
				if(session!=null && session.getAttribute("familyID")!=null)
				{
					FamilyBean objFamilyBean=objFamilyDAO.getByID((int)session.getAttribute("familyID"));
					if(objFamilyBean!=null)
					{
						request.setAttribute("familyMember", General.removeHeadMember(objFamilyBean.getFamilyMembers().iterator()));			
					}
				}
				request.setAttribute("occupationList", new OccupationDAO().getAll());					
				request.setAttribute("messages", messages);
				RequestDispatcher rd = request.getRequestDispatcher("/User/ListCurrentFamily.jsp");
				rd.forward(request, response);
			}
		}
	}
}