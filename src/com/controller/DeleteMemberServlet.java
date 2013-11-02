package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.MemberBean;
import com.dao.MemberDAO;
import com.utility.Validations;

public class DeleteMemberServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;       
    public DeleteMemberServlet()
    {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();		
		String id=request.getParameter("id");
		if(id!=null && !id.trim().isEmpty() && Validations.isNumeric(id))
		{
			MemberDAO objMemberDAO=new MemberDAO();
			MemberBean objMemberBean=objMemberDAO.getByID(Integer.parseInt(id));
			if(objMemberBean!=null && !objMemberBean.getRelation().getRelationName().equalsIgnoreCase("Head"))
			{
				out.print(objMemberDAO.delete(Integer.parseInt(id)));
			}
			else
			{
				out.print(false);
			}
		}
		else
		{
			out.print(false);
		}
	}	
}