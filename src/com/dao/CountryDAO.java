package com.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.bean.CountryBean;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.utility.ExceptionLog;

public class CountryDAO extends DBDAO
{
	public CountryBean insert(CountryBean objCountryBean)
	{
		try
		{
			String query="{call INSERT_Country(?,?)}";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string", objCountryBean.getCountryName());			
			objCountryBean.setCountryID(dbConnection.insertCallableStatement(query, objParameters));			
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objCountryBean;
	}
	public boolean update(CountryBean objCountryBean)
	{
		try
		{
			if(objCountryBean.getCountryID()==null)
				return false;			
			String query="UPDATE TblCountry SET CountryName=? WHERE CountryID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string", objCountryBean.getCountryName());
			objParameters.put("int",objCountryBean.getCountryID());
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
	public boolean delete(int countryID)
	{
		try
		{
			String query="DELETE FROM TblCountry WHERE CountryID=?";			
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int", countryID);
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
	public CountryBean getByID(int countryID)
	{
		CountryBean objCountryBean=null;
		try
		{
			String query="SELECT * FROM TblCountry WHERE CountryID=?";			
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int", countryID);
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);			
			if(rs!=null)
			{
				if(rs.next())
				{
					objCountryBean=getValue(rs);
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objCountryBean;
	}
	public CountryBean getByCountryName(String countryName)
	{
		CountryBean objCountryBean=null;
		try
		{
			String query="SELECT * FROM TblCountry WHERE CountryName=?";			
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string", countryName);
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);
			if(rs!=null)
			{
				if(rs.next())
				{
					objCountryBean=getValue(rs);
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}	
		return(objCountryBean);
	}
	public boolean checkDuplicate(String countryName,int countryID)
	{
		boolean rtnValue=false;
		try
		{
			String query="SELECT CountryID FROM TblCountry WHERE CountryName=? and CountryID!=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string", countryName);
			objParameters.put("int", countryID);
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);			
			if(rs!=null)
			{
				if(rs.next())
				{
					rtnValue=true;
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}	
		return(rtnValue);
	}
	public List<CountryBean> getAll()
	{
		List<CountryBean> objCountryBeanList=new ArrayList<CountryBean>();
		try
		{
			String query="SELECT * FROM TblCountry";
			ResultSet rs=dbConnection.runSelect(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					objCountryBeanList.add(getValue(rs));
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}	
		return(objCountryBeanList);
	}
	private CountryBean getValue(ResultSet rs)
	{
		CountryBean objCountryBean=new CountryBean();
		try
		{
			objCountryBean.setCountryID(rs.getInt("CountryID"));
			objCountryBean.setCountryName(rs.getString("CountryName"));
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}	
		return(objCountryBean);
	}
}