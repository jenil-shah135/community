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

import com.bean.RelationBean;
import com.dao.RelationDAO;
import com.utility.General;

public class AddRelationServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public AddRelationServlet()
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
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/AddRelation.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Map<String,String> messages=new HashMap<String,String>();
		try
		{
			RelationDAO objRelationDAO=new RelationDAO();			
			String name=request.getParameter("name");			
			if(name==null)
			{
				messages.put("nameError", "<div class='editor-error'>Please enter relation.</div>");
			}
			else
			{
				name=name.trim();
				if(name.length()==0)
				{
					messages.put("nameError", "<div class='editor-error'>Please enter relation.</div>");
				}
				else
				{
					if(objRelationDAO.getByRelationName(name)!=null)
					{
						messages.put("nameError", "<div class='editor-error'>Relation already exist.</div>");
					}
				}
			}
			if(messages.size()==0)
			{
				RelationBean objRelationBean=new RelationBean();
				objRelationBean.setRelationName(name);
				objRelationBean=objRelationDAO.insert(objRelationBean);
				if(objRelationBean==null || objRelationBean.getRelationID()==null)
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
				session.setAttribute("msg", "<div class='editor-success'>Relation successfully added.</div>");
				response.sendRedirect("/Community/Admin/Relation/Add");				
			}
			else
			{
				request.setAttribute("messages", messages);
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/AddRelation.jsp");
				rd.forward(request, response);
			}
		}
	}
}