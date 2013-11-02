package com.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dao.CountryDAO;

public class ListCountryServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public ListCountryServlet()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setAttribute("countryList", new CountryDAO().getAll());
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/ListCountry.jsp");
		rd.forward(request, response);
	}	
}