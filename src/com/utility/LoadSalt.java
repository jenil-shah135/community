package com.utility;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
 
public class LoadSalt extends TagSupport
{
	private static final long serialVersionUID = 1L;
	private String addSalt(HttpServletRequest httpReq)
	{
        // Check the user session for the salt cache, if none is present we create one        
        @SuppressWarnings("unchecked")
        Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>)httpReq.getSession().getAttribute("csrfPreventionSaltCache");
        if (csrfPreventionSaltCache == null)
        {
            csrfPreventionSaltCache = CacheBuilder.newBuilder().maximumSize(5000).expireAfterWrite(20, TimeUnit.MINUTES).build();
            httpReq.getSession().setAttribute("csrfPreventionSaltCache", csrfPreventionSaltCache);
        } 
        // Generate the salt and store it in the users cache
        String salt = Crypto.randomString(20);
        csrfPreventionSaltCache.put(salt, Boolean.TRUE);
        // Add the salt to the current request so it can be used
        // by the page rendered in this request
        return salt;        
    }
	public int doStartTag() throws JspException
	{
		try
		{
			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			JspWriter out = pageContext.getOut();
			String salt=this.addSalt(request);
	        out.println("<input type='hidden' name='csrfPreventionSalt' value='"+salt+"' />");
		}
		catch (Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return SKIP_BODY;
	}
}