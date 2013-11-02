package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.CityDAO;
import com.google.gson.Gson;

public class AjaxCityServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public AjaxCityServlet()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.write(new Gson().toJson(new CityDAO().getAll()));
	}
}