package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.OccupationDAO;
import com.utility.Validations;

public class DeleteOccupationServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public DeleteOccupationServlet()
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
			OccupationDAO objOccupationDAO=new OccupationDAO();
			if(objOccupationDAO.getByID(Integer.parseInt(id))!=null)
			{
				out.print(objOccupationDAO.delete(Integer.parseInt(id)));
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