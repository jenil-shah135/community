package com.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dao.StateDAO;

public class ListStateServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public ListStateServlet()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setAttribute("stateList", new StateDAO().getAll());
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/ListState.jsp");
		rd.forward(request, response);
	}	
}