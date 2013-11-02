package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.FamilyBean;
import com.bean.LoginBean;
import com.dao.FamilyDAO;
import com.dao.LoginDAO;
import com.utility.Crypto;
import com.utility.ExceptionLog;
import com.utility.Mail;
import com.utility.Validations;

public class CreateLoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public CreateLoginServlet()
    {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();		
		try
		{
			String id=request.getParameter("id");			
			if(id!=null && !id.trim().isEmpty() && Validations.isNumeric(id))
			{
				FamilyDAO objFamilyDAO=new FamilyDAO();
				FamilyBean objFamilyBean=objFamilyDAO.getByID(Integer.parseInt(id));
				if(objFamilyBean==null)
				{
					out.print(false);
				}	
				else
				{
					LoginDAO objLoginDAO=new LoginDAO();
					LoginBean objLoginBean=objLoginDAO.getByFamilyID(objFamilyBean.getFamilyID());
					if(objLoginBean!=null)
					{
						out.print(false);
					}
					else
					{
						objLoginBean=new LoginBean();
						objLoginBean.setFamily(objFamilyBean);
						if(objFamilyBean.getFamilyMembers().get(0)==null)
						{
							out.print(false);
						}
						else
						{							
							String username=objFamilyBean.getFamilyMembers().get(0).getFirstName().substring(0, 1);														
							username+=objFamilyBean.getFamilyMembers().get(0).getMiddleName().substring(0, 1);							
							username+=objFamilyBean.getFamilyMembers().get(0).getLastName().substring(0, 1);						
							String temp=Crypto.randomString();
							username+=temp.substring(0,(temp.length()>3)?3:temp.length());							
							objLoginBean.setUsername(username);
							String password=Crypto.randomString();
							password=password.substring(0,(password.length()>6)?6:password.length());
							objLoginBean.setPassword(Crypto.md5(password));
							objLoginBean.setUserType(1);
							objLoginBean=objLoginDAO.insert(objLoginBean);
							if(objLoginBean==null || objLoginBean.getLoginID()==null)
							{								
								out.print(false);
							}
							else
							{
								String message="Hi,\n";
								message+="We have verified your entry\n";
								message+="Username:"+username+"\n";
								message+="Password:"+password+"\n";
								message+="Thank you.";
								System.out.println(message);
								if(objFamilyBean.getFamilyMembers().get(0)!=null && objFamilyBean.getFamilyMembers().get(0).getEmailID()!=null)
								{
									Mail.makeEmail(objFamilyBean.getFamilyMembers().get(0).getEmailID(), "Community Registration Notification", message);								
									out.print(true);
								}
								else
								{
									out.print(false);
								}
							}
						}
					}
				}
			}
			else
			{
				out.print(false);
			}
		}
		catch(Exception ex)
		{			
			ExceptionLog.LogException(ex);
		}
	}
}