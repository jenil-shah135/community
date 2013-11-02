package com.utility;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminFilter implements Filter
{
	private FilterConfig filterConfig;	    
    public void init(FilterConfig fc) throws ServletException
    {
    	filterConfig=fc;
    }
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException
    {
    	try
    	{
    		boolean authorized = false;
    		if (request instanceof HttpServletRequest)
    		{
    			HttpSession session = ((HttpServletRequest)request).getSession(false);
    			if (session != null && session.getAttribute("isAdmin")!=null)
    			{
    				authorized=(boolean) session.getAttribute("isAdmin");	    				
    			}
	    	}	    		
	    	if (authorized)
	    	{		    		
	    		chain.doFilter(request, response);
	    	}
	    	else if (filterConfig != null)
	    	{		    		
	    		ServletContext context = filterConfig.getServletContext();
	    		String login_page = context.getInitParameter("loginPage");		    		
	    		if (login_page != null)
	    		{
	    			((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath() + login_page);
	    		}
	    	}
	    	else
	    	{
	    		throw new ServletException ("Unauthorized access, unable to forward to login page");
	    	}
    	}
    	catch (Exception ex)
    	{
    		ExceptionLog.LogException(ex);
    	}	        
    }
    public void destroy(){}
}