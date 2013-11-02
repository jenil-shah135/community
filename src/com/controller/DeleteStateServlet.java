package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.StateDAO;
import com.utility.Validations;

public class DeleteStateServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public DeleteStateServlet()
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
			StateDAO objStateDAO=new StateDAO();
			if(objStateDAO.getByID(Integer.parseInt(id))!=null)
			{
				out.print(objStateDAO.delete(Integer.parseInt(id)));
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