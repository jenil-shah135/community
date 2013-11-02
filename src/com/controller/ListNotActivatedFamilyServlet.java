package com.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dao.FamilyDAO;

public class ListNotActivatedFamilyServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public ListNotActivatedFamilyServlet()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setAttribute("familyList", new FamilyDAO().getAllNotActivated());
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/ListNotValidFamily.jsp");
		rd.forward(request, response);
	}	
}