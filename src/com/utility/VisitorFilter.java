package com.utility;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class VisitorFilter implements Filter
{
	public void init(FilterConfig fc) throws ServletException {}
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException
    {
    	try
    	{
    		boolean authorized = true;
    		if (request instanceof HttpServletRequest)
    		{
    			HttpSession session = ((HttpServletRequest)request).getSession(false);
    			if (session != null && session.getAttribute("isAdmin")!=null)
    			{
    				authorized=false;
    				if((boolean)session.getAttribute("isAdmin"))
    				{
    					((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath() + "/Admin/");
    				}
    				else
    				{
    					((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath() + "/User/");
    				}
    			}
	    	}
	    	if (authorized)
	    	{		    		
	    		chain.doFilter(request, response);
	    	}		    	
    	}
    	catch (Exception ex)
    	{
    		ExceptionLog.LogException(ex);
    	}	        
    }
	public void destroy(){}	
}