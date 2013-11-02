package com.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.bean.CityBean;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.utility.ExceptionLog;

public class CityDAO extends DBDAO
{
	public CityBean insert(CityBean objCityBean)
	{
		try
		{			
			String query="{call INSERT_City(?,?,?)}";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string", objCityBean.getCityName());
			objParameters.put("int", objCityBean.getState().getStateID());
			objCityBean.setCityID(dbConnection.insertCallableStatement(query, objParameters));
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);			
		}
		return objCityBean;
	}
	public boolean update(CityBean objCityBean)
	{
		try
		{
			if(objCityBean.getCityID()==null)
				return false;
			String query="UPDATE TblCity SET CityName=?,StateId=? WHERE CityID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string",objCityBean.getCityName());
			objParameters.put("int",objCityBean.getState().getStateID());
			objParameters.put("int",objCityBean.getCityID());
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
	public boolean delete(int cityID)
	{
		try
		{
			String query="DELETE FROM TblCity WHERE CityID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int",cityID);						
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
	public CityBean getByID(int cityID)
	{
		CityBean objCityBean=null;
		try
		{
			String query="SELECT * FROM TblCity WHERE CityID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int",cityID);			
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);			
			if(rs!=null)
			{
				if(rs.next())
				{
					objCityBean=getValue(rs);
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objCityBean;
	}
	public CityBean getByCityName(String cityName)
	{
		CityBean objCityBean=null;
		try
		{
			String query="SELECT * FROM TblCity WHERE CityName=?";			
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string",cityName);			
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);
			if(rs!=null)
			{
				if(rs.next())
				{
					objCityBean=getValue(rs);
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objCityBean;
	}
	public CityBean find(String cityName,int stateID)
	{
		CityBean objCityBean=null;
		try
		{
			String query="SELECT * FROM TblCity WHERE CityName=? AND stateID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string",cityName);
			objParameters.put("int",stateID);
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);			
			if(rs!=null)
			{
				if(rs.next())
				{
					objCityBean=getValue(rs);
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objCityBean;
	}
	public boolean checkDuplicate(String cityName,int cityID,int stateID)
	{
		boolean rtnValue=false;
		try
		{
			String query="SELECT CityID FROM TblCity WHERE CityName=? AND stateID=? AND CityID!=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string",cityName);
			objParameters.put("int",stateID);
			objParameters.put("int",cityID);			
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
		return rtnValue;
	}
	
	public List<CityBean> getAll()
	{
		List<CityBean> objCityBeanList=new ArrayList<CityBean>();
		try
		{
			String query="SELECT * FROM TblCity";
			ResultSet rs=dbConnection.runSelect(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					objCityBeanList.add(getValue(rs));
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objCityBeanList);
	}
	public List<CityBean> getAllByStateID(int stateID)
	{
		List<CityBean> objCityBeanList=new ArrayList<CityBean>();
		try
		{
			String query="SELECT * FROM TblCity WHERE StateID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int",stateID);			
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);	
			if(rs!=null)
			{
				while(rs.next())
				{
					objCityBeanList.add(getValue(rs));
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objCityBeanList);
	}
	private CityBean getValue(ResultSet rs)
	{
		CityBean objCityBean=new CityBean();
		try
		{
			objCityBean.setCityID(rs.getInt("CityID"));
			objCityBean.setCityName(rs.getString("CityName"));
			objCityBean.setState(new StateDAO().getByID(rs.getInt("StateId")));
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objCityBean);
	}
}