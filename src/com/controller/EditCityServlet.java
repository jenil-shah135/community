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

public class EditCityServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public EditCityServlet()
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
			CityBean objCityBean=new CityDAO().getByID(Integer.parseInt(id));
			if(objCityBean!=null)
			{
				request.setAttribute("stateList", new StateDAO().getAllByCountryID(objCityBean.getState().getCountry().getCountryID()));
				request.setAttribute("cityID",objCityBean.getCityID());
				request.setAttribute("name", objCityBean.getCityName());
				request.setAttribute("state",objCityBean.getState().getStateID());
				request.setAttribute("country",objCityBean.getState().getCountry().getCountryID());
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/EditCity.jsp");
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
		String cityID=request.getParameter("cityID");
		String state=request.getParameter("state");
		String name=request.getParameter("name");
		String country=request.getParameter("country");
		CountryDAO objCountryDAO=new CountryDAO();
		StateDAO objStateDAO=new StateDAO();
		CityDAO objCityDAO=new CityDAO();
		request.setAttribute("countryList", objCountryDAO.getAll());
		request.setAttribute("stateList", new ArrayList<StateBean>());
		Map<String,String> messages=new HashMap<String,String>();
		try
		{			
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
							request.setAttribute("stateList", objStateDAO.getAllByCountryID(objCountryBean.getCountryID()));				
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
						if(objStateBean==null || objStateBean.getStateID()==null || objStateBean.getCountry().getCountryID()!=Integer.parseInt(country))
						{
							messages.put("stateError", "<div class='editor-error'>Please select state.</div>");
						}
					}
				}
			}
			if(cityID==null)
			{
				messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
			}
			else
			{
				cityID=cityID.trim();
				if(cityID.length()==0)
				{
					messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
				}
				else
				{
					if(objCityDAO.getByID(Integer.parseInt(cityID))==null)
					{
						messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
					}
				}
			}
			if(messages.size()==0)
			{
				StateBean objStateBean=objStateDAO.getByID(Integer.parseInt(state));	
				if(objCityDAO.checkDuplicate(name,Integer.parseInt(cityID),objStateBean.getStateID()))
				{
					messages.put("nameError", "<div class='editor-error'>City already exist.</div>");
				}
				else
				{
					CityBean objCityBean=new CityBean();
					objCityBean.setCityID(Integer.parseInt(cityID));
					objCityBean.setCityName(name);
					objCityBean.setState(objStateBean);
					if(!objCityDAO.update(objCityBean))
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
				session.setAttribute("msg", "<div class='editor-success'>City successfully updated.</div>");
				response.sendRedirect("/Community/Admin/City/Edit?id="+cityID);				
			}
			else
			{
				request.setAttribute("messages", messages);
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/EditCity.jsp");
				rd.forward(request, response);
			}
		}
	}
}