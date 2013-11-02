package com.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dao.NewsDAO;

public class ListNewsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public ListNewsServlet()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setAttribute("newsList", new NewsDAO().getAll());
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/ListNews.jsp");
		rd.forward(request, response);
	}	
}