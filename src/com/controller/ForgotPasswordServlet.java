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
import com.bean.MemberBean;
import com.dao.LoginDAO;
import com.utility.Crypto;
import com.utility.General;
import com.utility.Mail;

public class ForgotPasswordServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public ForgotPasswordServlet()
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
		RequestDispatcher rd= request.getRequestDispatcher("/ForgotPassword.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Map<String,String> messages=new HashMap<String,String>();
		try
		{
			LoginDAO objLoginDAO=new LoginDAO();
			String username=request.getParameter("username");
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
			if(messages.size()==0)
			{
				LoginBean objLoginBean=objLoginDAO.getByUsername(username);
				if(objLoginBean==null || objLoginBean.getLoginID()==null)
				{
					response.sendRedirect("/Community/");
				}
				else
				{
					String password=Crypto.randomString();
					password=password.substring(0,(password.length()>6)?6:password.length());
					objLoginBean.setPassword(Crypto.md5(password));
					if(objLoginDAO.update(objLoginBean))
					{
						String message="Hi,\n";
						message+="We have reset your password as per your request\n";				
						message+="New Password:"+password+"\n";
						message+="Thank you.";
						if(objLoginBean.getFamily()!=null)
						{						
							MemberBean objHeadMemberBean = General.getHeadMember(objLoginBean.getFamily().getFamilyMembers().iterator());
							if(objHeadMemberBean!=null && objHeadMemberBean.getEmailID()!=null)
							{
								Mail.makeEmail(objHeadMemberBean.getEmailID(), "Community Password Reset Notification", message);
							}
						}
					}
					/*
							if(!Mail.makeEmail(objHeadMemberBean.getEmailID(), "Community Password Reset Notification", message))
							{
								messages.put("mainError", "<div class='editor-error'>Something went wrong please try again later.</div>");
							}
						}
						else
						{
							messages.put("mainError", "<div class='editor-error'>Something went wrong please try again later.</div>");
						}
						*/
					else
					{
						messages.put("mainError", "<div class='editor-error'>Something went wrong please try again later.</div>");
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
				session.setAttribute("msg", "<div class='editor-success'>Password reset instrunction is set to family head's Email-ID.</div>");
				response.sendRedirect("/Community/ForgotPassword");							
			}
			else
			{
				request.setAttribute("messages", messages);
				RequestDispatcher rd= request.getRequestDispatcher("/ForgotPassword.jsp");
				rd.forward(request, response);
			}
		}
	}
}