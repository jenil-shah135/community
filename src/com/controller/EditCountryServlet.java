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
import com.utility.Validations;

public class EditCountryServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public EditCountryServlet()
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
		String id=request.getParameter("id");
		if(id!=null && !id.trim().isEmpty() && Validations.isNumeric(id))
		{
			CountryDAO objCountryDAO=new CountryDAO();
			CountryBean objCountryBean=objCountryDAO.getByID(Integer.parseInt(id));
			if(objCountryBean!=null)
			{
				request.setAttribute("name", objCountryBean.getCountryName());
				request.setAttribute("countryID",objCountryBean.getCountryID());
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/EditCountry.jsp");
				rd.forward(request, response);
			}
			else
			{
				response.sendRedirect("/Community/Admin/");
			}
		}
		else
		{
			response.sendRedirect("/Community/Admin/");
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String name=request.getParameter("name");
		String countryID=request.getParameter("countryID");
		Map<String,String> messages=new HashMap<String,String>();
		try
		{
			CountryDAO objCountryDAO=new CountryDAO();
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
			if(countryID==null)
			{
				messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
			}
			else
			{
				countryID=countryID.trim();
				if(countryID.length()==0)
				{
					messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
				}
				else
				{
					if(objCountryDAO.getByID(Integer.parseInt(countryID))==null)
					{
						messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
					}
				}
			}
			if(messages.size()==0)
			{
				if(objCountryDAO.checkDuplicate(name,Integer.parseInt(countryID)))
				{
					messages.put("nameError", "<div class='editor-error'>Country already exist.</div>");
				}
				else
				{
					CountryBean objCountryBean=new CountryBean();
					objCountryBean.setCountryID(Integer.parseInt(countryID));
					objCountryBean.setCountryName(name);
					if(!objCountryDAO.update(objCountryBean))
					{
						messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
					}
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
				session.setAttribute("msg", "<div class='editor-success'>Country successfully updated.</div>");
				response.sendRedirect("/Community/Admin/Country/Edit?id="+countryID);					
			}
			else
			{
				request.setAttribute("messages", messages);
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/EditCountry.jsp");
				rd.forward(request, response);
			}
		}
	}
}