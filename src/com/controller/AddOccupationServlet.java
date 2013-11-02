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

import com.bean.OccupationBean;
import com.dao.OccupationDAO;
import com.utility.General;

public class AddOccupationServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public AddOccupationServlet()
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
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/AddOccupation.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Map<String,String> messages=new HashMap<String,String>();
		try
		{
			OccupationDAO objOccupationDAO=new OccupationDAO();			
			String name=request.getParameter("name");			
			if(name==null)
			{
				messages.put("nameError", "<div class='editor-error'>Please enter occupation.</div>");
			}
			else
			{
				name=name.trim();
				if(name.length()==0)
				{
					messages.put("nameError", "<div class='editor-error'>Please enter occupation.</div>");
				}
				else
				{
					if(objOccupationDAO.getByOccupasionName(name)!=null)
					{
						messages.put("nameError", "<div class='editor-error'>Occupation already exist.</div>");
					}
				}
			}
			if(messages.size()==0)
			{
				OccupationBean objOccupationBean=new OccupationBean();
				objOccupationBean.setOccupationName(name);
				objOccupationBean=objOccupationDAO.insert(objOccupationBean);
				if(objOccupationBean==null || objOccupationBean.getOccupationID()==null)
				{
					messages.put("mainError", "<div class='editor-error'>Something went wrong please try again later.</div>");	
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
				session.setAttribute("msg", "<div class='editor-success'>Occupation successfully added.</div>");
				response.sendRedirect("/Community/Admin/Occupation/Add");				
			}
			else
			{
				request.setAttribute("messages", messages);
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/AddOccupation.jsp");
				rd.forward(request, response);
			}
		}
	}
}