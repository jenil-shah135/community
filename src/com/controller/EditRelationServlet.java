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
import com.utility.Validations;

public class EditRelationServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public EditRelationServlet()
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
			RelationBean objRelationBean=new RelationDAO().getByID(Integer.parseInt(id));
			if(objRelationBean!=null)
			{
				request.setAttribute("name", objRelationBean.getRelationName());
				request.setAttribute("relationID",objRelationBean.getRelationID());
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/EditRelation.jsp");
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
		String relationID=request.getParameter("relationID");
		Map<String,String> messages=new HashMap<String,String>();
		try
		{
			RelationDAO objRelationDAO=new RelationDAO();
			if(name==null)
			{
				messages.put("nameError", "<div class='editor-error'>Please enter relation name.</div>");
			}
			else
			{
				name=name.trim();
				if(name.length()==0)
				{
					messages.put("nameError", "<div class='editor-error'>Please enter relation name.</div>");
				}
			}
			if(relationID==null)
			{
				messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
			}
			else
			{
				relationID=relationID.trim();
				if(relationID.length()==0)
				{
					messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
				}
				else
				{
					if(objRelationDAO.getByID(Integer.parseInt(relationID))==null)
					{
						messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
					}
				}
			}
			if(messages.size()==0)
			{
				if(objRelationDAO.checkDuplicate(name,Integer.parseInt(relationID)))
				{
					messages.put("nameError", "<div class='editor-error'>Relation already exist.</div>");
				}
				else
				{
					RelationBean objRelationBean=new RelationBean();
					objRelationBean.setRelationID(Integer.parseInt(relationID));
					objRelationBean.setRelationName(name);
					if(!objRelationDAO.update(objRelationBean))
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
				session.setAttribute("msg", "<div class='editor-success'>Relation successfully updated.</div>");
				response.sendRedirect("/Community/Admin/Relation/Edit?id="+relationID);			
			}
			else
			{
				request.setAttribute("messages", messages);
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/EditRelation.jsp");
				rd.forward(request, response);
			}
		}
	}
}