package com.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.MemberDAO;
import com.utility.ExceptionLog;
import com.utility.Validations;

public class FindMemberServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public FindMemberServlet()
    {
        super();
    }    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		Map<String,String> messages=new HashMap<String,String>();
		try
		{
			String name=request.getParameter("name");
			String gender=request.getParameter("gender");
			String maritalStatus=request.getParameter("maritalStatus");
			String contactNo=request.getParameter("contactNo");
			String emailID=request.getParameter("emailID");
			String bloodgroup=request.getParameter("bloodgroup");
			String age=request.getParameter("age");
			if(age==null)
			{
				age="";
			}
			else
			{
				age=age.trim();
				if(age.length()==0)
				{
					age="";
				}
				else
				{
					if(!Validations.isNumeric(age))
					{
						messages.put("ageError", "<div class='editor-error'>Age should be numeric.</div>");
					}					
				}
			}
			if(name==null)
			{
				name="";
			}
			if(gender==null)
			{
				gender="";
			}
			if(maritalStatus==null)
			{
				maritalStatus="";
			}
			if(emailID==null)
			{
				emailID="";
			}
			else
			{
				emailID=emailID.trim().toLowerCase();
				if(emailID.length()==0)
				{
					emailID="";
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
				contactNo="";
			}
			else
			{
				contactNo=contactNo.trim();
				if(contactNo.length()==0)
				{
					contactNo="";
				}
				else
				{
					if(!Validations.isNumeric(contactNo))
					{
						messages.put("contactNoError", "<div class='editor-error'>Please enter valid contact no.</div>");
					}
				}
			}
			if(bloodgroup==null)
			{
				bloodgroup="";
			}
			else
			{
				bloodgroup=bloodgroup.trim();						
			}
			request.setAttribute("memberList",new MemberDAO().Find(name, gender, maritalStatus, contactNo, bloodgroup, age));
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		request.setAttribute("messages", messages);
		RequestDispatcher rd = request.getRequestDispatcher("/User/FindMember.jsp");
		rd.forward(request, response);
	}
}