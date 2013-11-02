package com.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.bean.OccupationBean;
import com.dao.DBDAO;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.utility.ExceptionLog;

public class OccupationDAO extends DBDAO
{
	public OccupationBean insert(OccupationBean objOccupationBean)
	{		
		try
		{
			String query="{call INSERT_Occupation(?,?)}";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string",objOccupationBean.getOccupationName());
			objOccupationBean.setOccupationID(dbConnection.insertCallableStatement(query, objParameters));			
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objOccupationBean;
	}
	public boolean update(OccupationBean objOccupationBean)
	{
		try
		{
			if(objOccupationBean.getOccupationID()==null)
				return false;			
			String query="UPDATE TblOccupation SET OccupationName=? WHERE OccupationID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string", objOccupationBean.getOccupationName());
			objParameters.put("int",objOccupationBean.getOccupationID());
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
	public boolean delete(int occupationID)
	{
		try
		{
			String query="DELETE FROM TblOccupation WHERE OccupationID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int",occupationID);
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
	public OccupationBean getByID(int occupationID)
	{
		OccupationBean objOccupationBean=null;
		try
		{
			String query="SELECT * FROM TblOccupation WHERE OccupationID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int",occupationID);
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);
			if(rs!=null)
			{
				if(rs.next())
				{
					objOccupationBean=getValue(rs);		
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objOccupationBean;
	}
	public OccupationBean getByOccupasionName(String occupationName)
	{
		OccupationBean objOccupationBean=null;
		try
		{
			String query="SELECT * FROM TblOccupation WHERE OccupationName=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string", occupationName);
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);
			if(rs!=null)
			{
				if(rs.next())
				{
					objOccupationBean=getValue(rs);							
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objOccupationBean;
	}
	public boolean checkDuplicate(String occupationName,int occupationID)
	{
		boolean rtnValue=false;
		try
		{
			String query="SELECT OccupationID FROM TblOccupation WHERE OccupationName=? AND OccupationID!=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string", occupationName);
			objParameters.put("int", occupationID);
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
	public List<OccupationBean> getAll()
	{
		List<OccupationBean> objOccupationBeanList=new ArrayList<OccupationBean>();
		try
		{
			String query="SELECT * FROM TblOccupation";
			ResultSet rs=dbConnection.runSelect(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					objOccupationBeanList.add(getValue(rs));
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objOccupationBeanList);
	}
	private OccupationBean getValue(ResultSet rs)
	{
		OccupationBean objOccupationBean=new OccupationBean();
		try
		{
			objOccupationBean.setOccupationID(rs.getInt("OccupationID"));
			objOccupationBean.setOccupationName(rs.getString("OccupationName"));
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objOccupationBean;
	}
}