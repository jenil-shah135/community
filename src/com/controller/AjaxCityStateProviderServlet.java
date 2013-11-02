package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.CityBean;
import com.bean.StateBean;
import com.dao.CityDAO;
import com.dao.StateDAO;
import com.utility.Validations;

public class AjaxCityStateProviderServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public AjaxCityStateProviderServlet()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String item=request.getParameter("item");
		String id=request.getParameter("id");
		if(item!=null && id!=null && Validations.isNumeric(id))
		{
			if(item.trim().toLowerCase().equals("state"))
			{				
				List<StateBean> objStateBeanList=new StateDAO().getAllByCountryID(Integer.parseInt(id));
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				for(StateBean objStateBean : objStateBeanList)
				{
					out.write("<option value='"+objStateBean.getStateID()+"'>"+objStateBean.getStateName()+"</option>");
				}
			}
			else if(item.trim().toLowerCase().equals("city"))
			{
				List<CityBean> objCityBeanList=new CityDAO().getAllByStateID(Integer.parseInt(id));
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				for(CityBean objCityBean : objCityBeanList)
				{
					out.write("<option value='"+objCityBean.getCityID()+"'>"+objCityBean.getCityName()+"</option>");
				}
			}
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	}
}