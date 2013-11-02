package com.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.cache.Cache;
import com.utility.ExceptionLog;

public class LogoutServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public LogoutServlet()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			HttpSession session = request.getSession(false);
			if(session!=null)
			{
				@SuppressWarnings("unchecked")
				Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>)session.getAttribute("csrfPreventionSaltCache");		 
		        if (csrfPreventionSaltCache != null)
		        {
		        	csrfPreventionSaltCache.invalidateAll();
		        }
				session.invalidate();
			}
			session = request.getSession(true);			
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		response.sendRedirect("/Community/");
	}
}