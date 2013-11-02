package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.FamilyBean;
import com.bean.MemberBean;
import com.dao.FamilyDAO;
import com.utility.ExceptionLog;
import com.utility.General;


public class FamilyDetailServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public FamilyDetailServlet()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{			
		try
		{
			if(request.getParameter("id")!=null)
			{				
				FamilyBean objFamilyBean=new FamilyDAO().getByID(Integer.parseInt(request.getParameter("id")));
				if(objFamilyBean!=null)
				{					
					List<MemberBean> memberList=new ArrayList<MemberBean>();
					memberList.add(General.getHeadMember(objFamilyBean.getFamilyMembers().iterator()));
					memberList.addAll(General.removeHeadMember(objFamilyBean.getFamilyMembers().iterator()));					
					request.setAttribute("familyMember", memberList);						
					request.setAttribute("currentAddress", objFamilyBean.getCurrentAddress());
					request.setAttribute("nativeAddress", objFamilyBean.getNativeAddress());					
					RequestDispatcher rd=request.getRequestDispatcher("/User/FamilyDetail.jsp");
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
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
			response.sendRedirect("/Community/");			
		}
	}
}