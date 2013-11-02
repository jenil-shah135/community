package com.utility;

public class Constants {

	private static final String DBSERVERNAME="127.0.0.1";
	private static final int DBPORT=1521;
	private static final String DATABASENAME="communitydb";
	private static final String DBUserName="system";
	private static final String DBPassword="community";
	private static final String EmailHOST="smtp.gmail.com";
	private static final int EmailPORT=465;	
	private static final String EmailUSERNAME="";
	private static final String EmailPassword="";
	private static final String EmailFROM="admin@community.com";
	public static String getDBServerName()
	{
		return DBSERVERNAME;
	}
	public static int getDBPort()
	{
		return DBPORT;
	}
	public static String getDatabaseName()
	{
		return DATABASENAME;
	}
	public static String getMysqlConnectionString()
	{
		return("jdbc:mysql://"+DBSERVERNAME+":"+DBPORT+"/"+DATABASENAME);
	}
	public static String getSqlConnectionString()
	{
		return("jdbc:oracle:thin:@"+DBSERVERNAME+":"+DBPORT+"/"+DATABASENAME);
	}
	public static String getDBUserName()
	{
		return(DBUserName);
	}
	public static String getDBPassword()
	{
		return(DBPassword);
	}
	public static String getEmailHost()
	{
		return(EmailHOST);
	}
	public static int getEmailPort()
	{
		return(EmailPORT);
	}
	public static String getEmailUsername()
	{
		return(EmailUSERNAME);
	}
	public static String getEmailPassword()
	{
		return(EmailPassword);
	}
	public static String getEmailFrom()
	{
		return(EmailFROM);
	}
}
