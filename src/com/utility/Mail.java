package com.utility;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class Mail
{
	public static boolean makeEmail(String to,String subject,String message)
	{
		try
		{
			Email email = new SimpleEmail();
		    email.setCharset("utf-8");
		    email.addTo(to);
		    email.setSubject(subject);
		    email.setMsg(message);
		    email.setHostName(Constants.getEmailHost());
		    email.setSmtpPort(Constants.getEmailPort());
		    email.setAuthentication(Constants.getEmailUsername(),Constants.getEmailPassword());		    
		    email.setSSL(true);
		    email.setFrom(Constants.getEmailFrom());		    
		    email.send();
		    return true;
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return false;
	}
}