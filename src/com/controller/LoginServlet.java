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

import com.bean.LoginBean;
import com.dao.LoginDAO;

public class LoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public LoginServlet()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");		
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		Map<String,String> messages=new HashMap<String,String>();
		try
		{
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			if(username==null)
			{
				messages.put("usernameError", "<div class='editor-error'>Please enter username.</div>");
			}
			else
			{
				username=username.trim();
				if(username.length()==0)
				{
					messages.put("usernameError", "<div class='editor-error'>Please enter username.</div>");
				}
			}
			if(password==null)
			{
				messages.put("passwordError", "<div class='editor-error'>Please enter password.</div>");
			}
			else
			{
				password=password.trim();
				if(password.length()==0)
				{
					messages.put("passwordError", "<div class='editor-error'>Please enter password.</div>");
				}
			}
			if(messages.size()==0)
			{
				LoginDAO objLoginDAO=new LoginDAO();
				LoginBean objLoginBean=objLoginDAO.checkLogin(username, com.utility.Crypto.md5(password));				
				if(objLoginBean==null)
				{					
					messages.put("mainError", "<div class='editor-error'>Username and password not match.</div>");
				}
				else
				{
					HttpSession session = request.getSession(true);					
					session.setAttribute("isAdmin", (objLoginBean.getUserType().equals(0)));					
					if(objLoginBean.getFamily()!=null && objLoginBean.getFamily().getFamilyID()!=null)
						session.setAttribute("familyID", objLoginBean.getFamily().getFamilyID());
					else
						session.setAttribute("familyID",0);
					session.setAttribute("loginID", objLoginBean.getLoginID());
				}
			}
		}
		catch(Exception ex)
		{			
			messages.put("mainError", "<div class='editor-error'>Username and password not match.</div>");
		}
		finally
		{
			if(messages.size()==0)
			{
				HttpSession session = request.getSession(false);
				if(session==null)
				{
					messages.put("mainError", "<div class='editor-error'>Username and password not match.</div>");
					RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
					rd.forward(request, response);				
				}
				else
				{
					String isAdmin=session.getAttribute("isAdmin").toString();
					if(isAdmin==null)
					{						
						RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
						rd.forward(request, response);
					}
					else if(isAdmin.equalsIgnoreCase("true"))
					{
						response.sendRedirect("/Community/Admin/");
					}
					else if(isAdmin.equalsIgnoreCase("false"))
					{
						response.sendRedirect("/Community/User/");
					}
					else
					{
						RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
						rd.forward(request, response);
					}
				}
			}
			else
			{
				request.setAttribute("messages", messages);
				RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");
				rd.forward(request, response);
			}
		}
	}
}