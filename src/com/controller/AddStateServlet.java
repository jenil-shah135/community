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
import com.bean.StateBean;
import com.dao.CountryDAO;
import com.dao.StateDAO;
import com.utility.General;
import com.utility.Validations;

public class AddStateServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public AddStateServlet()
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
		CountryDAO objCountryDAO=new CountryDAO();
		request.setAttribute("countryList", objCountryDAO.getAll());
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/AddState.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		CountryDAO objCountryDAO=new CountryDAO();
		StateDAO objStateDAO=new StateDAO();		
		Map<String,String> messages=new HashMap<String,String>();
		try
		{
			String name=request.getParameter("name");
			String country=request.getParameter("country");
			if(name==null)
			{
				messages.put("nameError", "<div class='editor-error'>Please enter state name.</div>");
			}
			else
			{
				name=name.trim();
				if(name.length()==0)
				{
					messages.put("nameError", "<div class='editor-error'>Please enter state name.</div>");
				}
			}
			if(country==null)
			{
				messages.put("countryError", "<div class='editor-error'>Please select country.</div>");
			}
			else
			{
				country=country.trim();
				if(country.length()==0)
				{
					messages.put("countryError", "<div class='editor-error'>Please select country.</div>");
				}
				else
				{
					if(!Validations.isNumeric(country))
					{
						messages.put("countryError", "<div class='editor-error'>Please select country.</div>");
					}
					else
					{
						CountryBean objCountryBean=objCountryDAO.getByID(Integer.parseInt(country));
						if(objCountryBean==null || objCountryBean.getCountryID()==null)
						{
							messages.put("countryError", "<div class='editor-error'>Please select country.</div>");
						}
					}
				}
			}
			if(messages.size()==0)
			{
				CountryBean objCountry=objCountryDAO.getByID(Integer.parseInt(country));				
				if(objStateDAO.find(name,objCountry.getCountryID())!=null)
				{
					messages.put("nameError", "<div class='editor-error'>State already exist.</div>");
				}
				else
				{
					StateBean objStateBean=new StateBean();
					objStateBean.setStateName(name);
					objStateBean.setCountry(objCountry);
					objStateBean=objStateDAO.insert(objStateBean);
					if(objStateBean==null || objStateBean.getStateID()==null)
					{
						messages.put("mainError", "<div class='editor-error'>Something went wrong please try again later.</div>");	
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
				session.setAttribute("msg", "<div class='editor-success'>State successfully added.</div>");
				response.sendRedirect("/Community/Admin/State/Add");				
			}
			else
			{
				request.setAttribute("countryList", objCountryDAO.getAll());
				request.setAttribute("messages", messages);
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/AddState.jsp");
				rd.forward(request, response);
			}
		}
	}
}