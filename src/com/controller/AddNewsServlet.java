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

public class AddNewsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public AddNewsServlet()
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
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/AddNews.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Map<String,String> messages=new HashMap<String,String>();
		try
		{			
			String newsTitle=request.getParameter("newsTitle");
			String newsDesc=request.getParameter("newsDesc");			
			if(newsTitle==null)
			{
				messages.put("newsTitleError", "<div class='editor-error'>Please enter news title.</div>");
			}
			else
			{
				newsTitle=newsTitle.trim();
				if(newsTitle.length()==0)
				{
					messages.put("newsTitleError", "<div class='editor-error'>Please enter news title.</div>");
				}
			}
			if(newsDesc==null)
			{
				messages.put("newsDescError", "<div class='editor-error'>Please enter news desc.</div>");
			}
			else
			{
				newsDesc=newsDesc.trim();
				if(newsDesc.length()==0)
				{
					messages.put("newsDescError", "<div class='editor-error'>Please enter news desc.</div>");
				}
			}
			if(messages.size()==0)
			{
				NewsBean objNewsBean=new NewsBean();
				objNewsBean.setNewsTitle(newsTitle);
				objNewsBean.setNewsDesc(newsDesc);
				objNewsBean=new NewsDAO().insert(objNewsBean);
				if(objNewsBean==null || objNewsBean.getNewsID()==null)
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
				session.setAttribute("msg", "<div class='editor-success'>News successfully added.</div>");
				response.sendRedirect("/Community/Admin/News/Add");
			}
			else
			{
				request.setAttribute("messages", messages);
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/AddNews.jsp");
				rd.forward(request, response);
			}
		}
	}
}