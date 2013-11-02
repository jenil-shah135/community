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
import com.utility.Validations;

public class EditOccupationServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public EditOccupationServlet()
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
			OccupationBean objOccupationBean=new OccupationDAO().getByID(Integer.parseInt(id));
			if(objOccupationBean!=null)
			{
				request.setAttribute("name", objOccupationBean.getOccupationName());
				request.setAttribute("occupationID",objOccupationBean.getOccupationID());
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/EditOccupation.jsp");
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
		String occupationID=request.getParameter("occupationID");	
		Map<String,String> messages=new HashMap<String,String>();
		try
		{
			OccupationDAO objOccupationDAO=new OccupationDAO();
			if(name==null)
			{
				messages.put("nameError", "<div class='editor-error'>Please enter occupation name.</div>");
			}
			else
			{
				name=name.trim();
				if(name.length()==0)
				{
					messages.put("nameError", "<div class='editor-error'>Please enter occupation name.</div>");
				}
			}
			if(occupationID==null)
			{
				messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
			}
			else
			{
				occupationID=occupationID.trim();
				if(occupationID.length()==0)
				{
					messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
				}
				else
				{
					if(objOccupationDAO.getByID(Integer.parseInt(occupationID))==null)
					{
						messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
					}
				}
			}
			if(messages.size()==0)
			{
				if(objOccupationDAO.checkDuplicate(name,Integer.parseInt(occupationID)))
				{
					messages.put("nameError", "<div class='editor-error'>Occupation already exist.</div>");
				}
				else
				{
					OccupationBean objOccupationBean=new OccupationBean();
					objOccupationBean.setOccupationID(Integer.parseInt(occupationID));
					objOccupationBean.setOccupationName(name);
					if(!objOccupationDAO.update(objOccupationBean))
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
				session.setAttribute("msg", "<div class='editor-success'>Occupation successfully updated.</div>");
				response.sendRedirect("/Community/Admin/Occupation/Edit?id="+occupationID);				
			}
			else
			{
				request.setAttribute("messages", messages);
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/EditOccupation.jsp");
				rd.forward(request, response);
			}
		}
	}
}