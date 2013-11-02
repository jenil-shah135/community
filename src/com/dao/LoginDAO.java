package com.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.bean.LoginBean;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.utility.ExceptionLog;

public class LoginDAO extends DBDAO {
	public LoginBean insert(LoginBean objLoginBean)
	{
		try
		{
			String query="{call INSERT_Login(?,?,?,?,?)}";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string",objLoginBean.getUsername());
			objParameters.put("string",objLoginBean.getPassword());			
			objParameters.put("int",objLoginBean.getUserType());
			objParameters.put("int",objLoginBean.getFamily().getFamilyID());
			objLoginBean.setLoginID(dbConnection.insertCallableStatement(query, objParameters));
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objLoginBean;
	}
	public boolean update(LoginBean objLoginBean)
	{
		try
		{
			if(objLoginBean.getLoginID()==null)
				return false;
			String query="UPDATE TblLogin SET Password=? WHERE LoginID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string",objLoginBean.getPassword());
			objParameters.put("int",objLoginBean.getLoginID());
			if(dbConnection.runPreparedStatement(query, objParameters)>=0)
				return true;
			else
				return false;
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
			return false;
		}
	}
	public boolean delete(int loginID)
	{
		try
		{
			String query="DELETE FROM TblLogin WHERE LoginID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int",loginID);
			if(dbConnection.runPreparedStatement(query, objParameters)>=0)
				return true;
			else
				return false;
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
			return false;
		}
	}
	public LoginBean getByID(int loginID)
	{
		LoginBean objLoginBean=null;
		try
		{
			String query="SELECT * FROM TblLogin WHERE LoginID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int",loginID);
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);
			if(rs!=null)
			{
				if(rs.next())
				{
					objLoginBean=getValue(rs);
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objLoginBean;
	}
	public LoginBean getByUsername(String username)
	{
		LoginBean objLoginBean=null;
		try
		{
			String query="SELECT * FROM TblLogin WHERE Username=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string",username);
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);
			if(rs!=null)
			{
				if(rs.next())
				{
					objLoginBean=getValue(rs);
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objLoginBean;
	}	
	public List<LoginBean> getAll()
	{
		List<LoginBean> objLoginBeanList=new ArrayList<LoginBean>();
		try
		{
			String query="SELECT * FROM TblLogin";
			ResultSet rs=dbConnection.runSelect(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					objLoginBeanList.add(getValue(rs));
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objLoginBeanList);
	}
	public LoginBean getByFamilyID(int familyID)
	{
		LoginBean objLoginBean=null;
		try
		{
			String query="SELECT * FROM TblLogin WHERE FamilyID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int",familyID);
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);
			if(rs!=null)
			{
				if(rs.next())
				{
					objLoginBean=getValue(rs);
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objLoginBean);
	}
	public LoginBean checkLogin(String username,String password)
	{
		try
		{
			String query="SELECT * FROM TblLogin WHERE username=? and password=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string",username);
			objParameters.put("string",password);
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);
			if(rs!=null)
			{
				if(rs.next())
				{
					LoginBean objLoginBean=getValue(rs);
					rs.close();
					return(objLoginBean);
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(null);
	}
	private LoginBean getValue(ResultSet rs)
	{
		LoginBean objLoginBean=new LoginBean();
		try
		{
			objLoginBean.setLoginID(rs.getInt("LoginID"));
			objLoginBean.setUsername(rs.getString("Username"));
			objLoginBean.setPassword(rs.getString("Password"));			
			if(rs.getInt("FamilyId")!=0)
				objLoginBean.setFamily(new FamilyDAO().getByID(rs.getInt("FamilyId")));
			objLoginBean.setUserType(rs.getInt("UserType"));
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objLoginBean);
	}
}