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

public class EditStateServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public EditStateServlet()
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
		request.setAttribute("countryList", new CountryDAO().getAll());
		String id=request.getParameter("id");
		if(id!=null && !id.trim().isEmpty() && Validations.isNumeric(id))
		{
			StateBean objStateBean=new StateDAO().getByID(Integer.parseInt(id));
			if(objStateBean!=null)
			{
				request.setAttribute("stateID",objStateBean.getStateID());
				request.setAttribute("name", objStateBean.getStateName());
				request.setAttribute("country",objStateBean.getCountry().getCountryID());
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/EditState.jsp");
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
		String stateID=request.getParameter("stateID");
		String name=request.getParameter("name");
		String country=request.getParameter("country");		
		CountryDAO objCountryDAO=new CountryDAO();
		StateDAO objStateDAO=new StateDAO();
		request.setAttribute("countryList", objCountryDAO.getAll());
		Map<String,String> messages=new HashMap<String,String>();
		try
		{
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
			if(stateID==null)
			{
				messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
			}
			else
			{
				stateID=stateID.trim();
				if(stateID.length()==0)
				{
					messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
				}
				else
				{
					if(objStateDAO.getByID(Integer.parseInt(stateID))==null)
					{
						messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
					}
				}
			}
			if(messages.size()==0)
			{
				CountryBean objCountry=objCountryDAO.getByID(Integer.parseInt(country));			
				if(objStateDAO.checkDuplicate(name,Integer.parseInt(stateID),objCountry.getCountryID()))
				{
					messages.put("nameError", "<div class='editor-error'>State already exist.</div>");
				}
				else
				{
					StateBean objStateBean=new StateBean();
					objStateBean.setStateID(Integer.parseInt(stateID));
					objStateBean.setStateName(name);
					objStateBean.setCountry(objCountry);
					if(!objStateDAO.update(objStateBean))
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
				session.setAttribute("msg", "<div class='editor-success'>State successfully updated.</div>");
				response.sendRedirect("/Community/Admin/State/Edit?id="+stateID);		
			}
			else
			{
				request.setAttribute("messages", messages);
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/EditState.jsp");
				rd.forward(request, response);
			}
		}
	}
}