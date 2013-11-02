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
import com.utility.ExceptionLog;
import com.utility.General;
import com.utility.Mail;
import com.utility.Validations;

public class ChangePasswordServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public ChangePasswordServlet()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(true);
		String msg=General.GetMessage(session);
		if(msg!=null)
		{
			Map<String,String> messages=new HashMap<String,String>();
			messages.put("success", msg);
			request.setAttribute("messages", messages);
		}		
		Object isAdmin=session.getAttribute("isAdmin");		
		if(isAdmin!=null)
		{
			if((boolean)isAdmin)
			{
				RequestDispatcher rd= request.getRequestDispatcher("/Admin/ChangePassword.jsp");
				rd.forward(request, response);
			}
			else
			{
				RequestDispatcher rd= request.getRequestDispatcher("/User/ChangePassword.jsp");
				rd.forward(request, response);
			}
		}
		else
		{
			response.sendRedirect("/Community/");
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(true);		
		Map<String,String> messages=new HashMap<String,String>();
		try
		{
			if(session!=null)
			{
				LoginDAO objLoginDAO=new LoginDAO();
				String oldPassword=request.getParameter("oldPassword");
				String newPassword=request.getParameter("newPassword");
				String confirmPassword=request.getParameter("confirmPassword");
				if(oldPassword==null)
				{
					messages.put("oldPasswordError", "<div class='editor-error'>Please enter old password.</div>");
				}
				else
				{
					oldPassword=oldPassword.trim();
					if(oldPassword.length()==0)
					{
						messages.put("oldPasswordError", "<div class='editor-error'>Please enter old password.</div>");
					}				
				}
				if(newPassword==null)
				{
					messages.put("newPasswordError", "<div class='editor-error'>Please enter new password.</div>");
				}
				else
				{
					newPassword=newPassword.trim();
					if(newPassword.length()==0)
					{
						messages.put("newPasswordError", "<div class='editor-error'>Please enter new password.</div>");
					}
				}
				if(confirmPassword==null)
				{
					messages.put("confirmPasswordError", "<div class='editor-error'>Please enter confirm password.</div>");
				}
				else
				{
					confirmPassword=confirmPassword.trim();
					if(confirmPassword.length()==0)
					{
						messages.put("confirmPasswordError", "<div class='editor-error'>Please enter confirm password.</div>");
					}
					else
					{
						if(!confirmPassword.equals(newPassword))
						{
							messages.put("confirmPasswordError", "<div class='editor-error'>New password and confirm password not matched.</div>");
						}
					}
				}
				String loginID=session.getAttribute("loginID").toString();
				if(loginID==null)
				{
					response.sendRedirect("/Community/");
				}
				else
				{
					loginID=loginID.trim();
					if(loginID.length()==0)
					{
						response.sendRedirect("/Community/");
					}
					else
					{
						if(!Validations.isNumeric(loginID))
						{
							response.sendRedirect("/Community/");
						}
					}
				}
				LoginBean objLoginBean=objLoginDAO.getByID(Integer.parseInt(loginID));
				if(objLoginBean==null || objLoginBean.getLoginID()==null)
				{
					response.sendRedirect("/Community/");
				}
				else
				{
					if(!objLoginBean.getPassword().equals(Crypto.md5(oldPassword)))
					{
						messages.put("oldPasswordError", "<div class='editor-error'>Old password not matched.</div>");
					}
				}
				if(messages.size()==0)
				{
					objLoginBean.setPassword(Crypto.md5(newPassword));					
					if(!objLoginDAO.update(objLoginBean))
					{				
						messages.put("mainError", "<div class='editor-error'>Something went wrong please try again later.</div>");							
					}
					else
					{
						String message="Hi,\n";
						message+="Your password has been changed.\n";				
						message+="Thank you.";
						System.out.println(message);
						System.out.println("New Password=>"+newPassword);
						if(objLoginBean.getFamily()!=null)
						{
							MemberBean objHeadMemberBean = General.getHeadMember(objLoginBean.getFamily().getFamilyMembers().iterator());
							if(objHeadMemberBean!=null && objHeadMemberBean.getEmailID()!=null)
							{
								Mail.makeEmail(objHeadMemberBean.getEmailID(), "Community Change Password Notification", message);
							}
						}
					}
				}
			}
			else
			{
				response.sendRedirect("/Community/");
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
			messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
		}
		finally
		{
			if(messages.size()==0)
			{
				session.setAttribute("msg", "<div class='editor-success'>Password  successfully changed.</div>");
				Object isAdmin=session.getAttribute("isAdmin");	
				if(isAdmin!=null)
				{					
					if((boolean)isAdmin)
					{
						response.sendRedirect("/Community/Admin/ChangePassword");
					}
					else
					{
						response.sendRedirect("/Community/User/ChangePassword");
					}
				}
			}
			else
			{
				request.setAttribute("messages", messages);
				Object isAdmin=session.getAttribute("isAdmin");		
				if(isAdmin!=null)
				{
					if((boolean)isAdmin)
					{
						RequestDispatcher rd= request.getRequestDispatcher("/Admin/ChangePassword.jsp");
						rd.forward(request, response);
					}
					else
					{
						RequestDispatcher rd= request.getRequestDispatcher("/User/ChangePassword.jsp");
						rd.forward(request, response);
					}
				}
				else
				{
					response.sendRedirect("/Community/");
				}
			}
		}
	}
}