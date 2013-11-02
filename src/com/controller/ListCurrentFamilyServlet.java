package com.controller;

import java.io.IOException;
import java.util.HashMap;
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
import com.dao.OccupationDAO;
import com.utility.ExceptionLog;
import com.utility.General;


public class ListCurrentFamilyServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public ListCurrentFamilyServlet()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(true);		
		try
		{
			String msg=General.GetMessage(session);
			if(msg!=null)
			{
				Map<String,String> messages=new HashMap<String,String>();
				messages.put("success", msg);
				request.setAttribute("messages", messages);
			}
			request.setAttribute("occupationList", new OccupationDAO().getAll());			
			if(session.getAttribute("familyID")!=null)
			{			
				FamilyDAO objFamilyDAO=new FamilyDAO();
				FamilyBean objFamilyBean=objFamilyDAO.getByID((int)session.getAttribute("familyID"));
				if(objFamilyBean!=null)
				{					
					MemberBean objMemberBean = General.getHeadMember(objFamilyBean.getFamilyMembers().iterator());					
					if(objMemberBean!=null)
					{
						request.setAttribute("firstName",objMemberBean.getFirstName());
						request.setAttribute("middleName",objMemberBean.getMiddleName());
						request.setAttribute("lastName",objMemberBean.getLastName());
						request.setAttribute("gender",(objMemberBean.getGender()?"male":"female"));
						request.setAttribute("maritalStatus",(objMemberBean.getMaritalStatus()?"married":"unmarried"));						
						request.setAttribute("contactNo",objMemberBean.getContactNo());
						request.setAttribute("emailID",objMemberBean.getEmailID());
						request.setAttribute("eduQualification",objMemberBean.getEducationQualification());
						request.setAttribute("occupation",objMemberBean.getOccupation().getOccupationID());						
						request.setAttribute("bloodgroup",objMemberBean.getBloodGroup());
						request.setAttribute("birthdate",objMemberBean.getBirthDate());
						request.setAttribute("birthplace",objMemberBean.getBirthPlace());
						request.setAttribute("workingAddress",objMemberBean.getWorkingAddress());
						request.setAttribute("relation",objMemberBean.getRelation().getRelationID());
						request.setAttribute("occupationList", new OccupationDAO().getAll());				
						request.setAttribute("familyMember", General.removeHeadMember(objFamilyBean.getFamilyMembers().iterator()));						
						RequestDispatcher rd=request.getRequestDispatcher("/User/ListCurrentFamily.jsp");
						rd.forward(request, response);
					}
					else
					{
						response.sendRedirect("/Community/");
					}
				}
				else
				{					
					response.sendRedirect("/Community/");
				}
			}
			else
			{				
				response.sendRedirect("/Community/");
			}			
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
			response.sendRedirect("/Community/");
		}
	}
}