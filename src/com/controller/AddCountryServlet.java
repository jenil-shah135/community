package com.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.CountryBean;
import com.dao.CountryDAO;
import com.utility.General;

public class AddCountryServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;  
    public AddCountryServlet()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String msg=General.GetMessage(request.getSession(true));
		if(msg!=null)
		{
			Map<String,String> messages=new HashMap<String,String>();
			messages.put("success", msg);
			request.setAttribute("messages", messages);
		}
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/AddCountry.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Map<String,String> messages=new HashMap<String,String>();
		try
		{
			CountryDAO objCountryDAO=new CountryDAO();
			String name=request.getParameter("name");
			if(name==null)
			{
				messages.put("nameError", "<div class='editor-error'>Please enter country name.</div>");
			}
			else
			{
				name=name.trim();
				if(name.length()==0)
				{
					messages.put("nameError", "<div class='editor-error'>Please enter country name.</div>");
				}
			}
			if(messages.size()==0)
			{
				CountryBean objCountryBean=objCountryDAO.getByCountryName(name);			
				if(objCountryBean==null)
				{
					objCountryBean=new CountryBean();
					objCountryBean.setCountryName(name);
					objCountryBean=objCountryDAO.insert(objCountryBean);
				}
				else
				{
					messages.put("nameError", "<div class='editor-error'>Country already exist.</div>");
				}
			}
		}
		catch(Exception ex)
		{
			messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
		}
		finally
		{
			if(messages.size()==0)
			{
				HttpSession session=request.getSession(true);
				session.setAttribute("msg", "<div class='editor-success'>Country successfully added.</div>");
				response.sendRedirect("/Community/Admin/Country/Add");
			}
			else
			{
				request.setAttribute("messages", messages);
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/AddCountry.jsp");
				rd.forward(request, response);
			}
		}
	}
}