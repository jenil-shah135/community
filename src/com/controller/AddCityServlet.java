package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.CityBean;
import com.bean.CountryBean;
import com.bean.StateBean;
import com.dao.CityDAO;
import com.dao.CountryDAO;
import com.dao.StateDAO;
import com.utility.General;
import com.utility.Validations;

public class AddCityServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public AddCityServlet()
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
		request.setAttribute("stateList", new ArrayList<StateBean>());
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/AddCity.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Map<String,String> messages=new HashMap<String,String>();
		try
		{
			CountryDAO objCountryDAO=new CountryDAO();
			StateDAO objStateDAO=new StateDAO();
			CityDAO objCityDAO=new CityDAO();		
			request.setAttribute("countryList", objCountryDAO.getAll());
			request.setAttribute("stateList", new ArrayList<StateBean>());
			String name=request.getParameter("name");
			String country=request.getParameter("country");
			String state=request.getParameter("state");
			if(name==null)
			{
				messages.put("nameError", "<div class='editor-error'>Please enter city name.</div>");
			}
			else
			{
				name=name.trim();
				if(name.length()==0)
				{
					messages.put("nameError", "<div class='editor-error'>Please enter city name.</div>");
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
						else
						{							
							request.setAttribute("stateList", objStateDAO.getAllByCountryID(Integer.parseInt(country)));
						}
					}
				}
			}
			if(state==null)
			{
				messages.put("stateError", "<div class='editor-error'>Please select state.</div>");
			}
			else
			{
				state=state.trim();
				if(state.length()==0)
				{
					messages.put("stateError", "<div class='editor-error'>Please select state.</div>");
				}
				else
				{
					if(!Validations.isNumeric(state))
					{
						messages.put("stateError", "<div class='editor-error'>Please select state.</div>");
					}
					else
					{
						StateBean objStateBean=objStateDAO.getByID(Integer.parseInt(state));						
						if(objStateBean==null || objStateBean.getStateID()==null)
						{							
							messages.put("stateError", "<div class='editor-error'>Please select state.</div>");
						}
						else
						{
							if(!objStateBean.getCountry().getCountryID().toString().equals(country))
							{
								messages.put("stateError", "<div class='editor-error'>Please select valid state.</div>");
							}
						}
					}
				}
			}
			if(messages.size()==0)
			{
				StateBean objStateBean=objStateDAO.getByID(Integer.parseInt(state));
				if(objCityDAO.find(name,objStateBean.getStateID())!=null)
				{
					messages.put("nameError", "<div class='editor-error'>City already exist.</div>");
				}
				else
				{
					CityBean objCityBean=new CityBean();
					objCityBean.setCityName(name);
					objCityBean.setState(objStateBean);
					objCityBean=objCityDAO.insert(objCityBean);
					if(objCityBean==null || objCityBean.getCityID()==null)
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
				session.setAttribute("msg", "<div class='editor-success'>City successfully added.</div>");
				response.sendRedirect("/Community/Admin/City/Add");		
			}
			else
			{
				request.setAttribute("messages", messages);
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/AddCity.jsp");
				rd.forward(request, response);
			}
		}
	}
}