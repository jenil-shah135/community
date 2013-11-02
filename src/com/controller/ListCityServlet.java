package com.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dao.CityDAO;

public class ListCityServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public ListCityServlet()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setAttribute("cityList", new CityDAO().getAll());
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/ListCity.jsp");
		rd.forward(request, response);
	}	
}