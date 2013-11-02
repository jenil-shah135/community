package com.dao;

import com.utility.DBConnection;

public class DBDAO {
	protected DBConnection dbConnection;
	public DBDAO()
	{
		dbConnection=DBConnection.getInstant();
	}
}
