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

import com.bean.NewsBean;
import com.dao.NewsDAO;
import com.utility.General;
import com.utility.Validations;

public class EditNewsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public EditNewsServlet()
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
			NewsBean objNewsBean=new NewsDAO().getByID(Integer.parseInt(id));
			if(objNewsBean!=null)
			{
				request.setAttribute("newsID",objNewsBean.getNewsID());
				request.setAttribute("newsTitle", objNewsBean.getNewsTitle());
				request.setAttribute("newsDesc", objNewsBean.getNewsDesc());				
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/EditNews.jsp");
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
		String newsID=request.getParameter("newsID");
		String newsTitle=request.getParameter("newsTitle");
		String newsDesc=request.getParameter("newsDesc");
		NewsDAO objNewsDAO=new NewsDAO();
		Map<String,String> messages=new HashMap<String,String>();
		try
		{			
			if(newsDesc==null)
			{
				messages.put("newsDescError", "<div class='editor-error'>Please enter news description.</div>");
			}
			else
			{
				newsDesc=newsDesc.trim();
				if(newsDesc.length()==0)
				{
					messages.put("newsDescError", "<div class='editor-error'>Please enter news description.</div>");
				}
			}
			
			if(newsTitle==null)
			{
				messages.put("newsTitleError", "<div class='editor-error'>Please enter news title</div>");
			}
			else
			{
				newsTitle=newsTitle.trim();
				if(newsTitle.length()==0)
				{
					messages.put("newsTitleError", "<div class='editor-error'>Please enter news title</div>");
				}
			}
			if(newsID==null)
			{
				messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
			}
			else
			{
				newsID=newsID.trim();
				if(newsID.length()==0)
				{
					messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
				}
				else
				{
					if(objNewsDAO.getByID(Integer.parseInt(newsID))==null)
					{
						messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
					}
				}
			}
			if(messages.size()==0)
			{
				NewsBean objNewsBean=new NewsBean();
				objNewsBean.setNewsID(Integer.parseInt(newsID));
				objNewsBean.setNewsTitle(newsTitle);
				objNewsBean.setNewsDesc(newsDesc);					
				if(!objNewsDAO.update(objNewsBean))
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
				session.setAttribute("msg", "<div class='editor-success'>News successfully updated.</div>");
				response.sendRedirect("/Community/Admin/News/Edit?id="+newsID);				
			}
			else
			{
				request.setAttribute("messages", messages);
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/EditNews.jsp");
				rd.forward(request, response);
			}
		}
	}
}