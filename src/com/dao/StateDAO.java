package com.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.bean.StateBean;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.utility.ExceptionLog;

public class StateDAO extends DBDAO
{
	public StateBean insert(StateBean objStateBean)
	{
		try
		{
			String query="{call INSERT_State(?,?,?)}";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string",objStateBean.getStateName());
			objParameters.put("int", objStateBean.getCountry().getCountryID());
			objStateBean.setStateID(dbConnection.insertCallableStatement(query, objParameters));			
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);			
		}
		return objStateBean;
	}
	public boolean update(StateBean objStateBean)
	{
		try
		{
			if(objStateBean.getStateID()==null)
				return false;			
			String query="UPDATE TblState SET StateName=?,CountryId=? WHERE StateID=?";			
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string", objStateBean.getStateName());
			objParameters.put("int", objStateBean.getCountry().getCountryID());
			objParameters.put("int", objStateBean.getStateID());
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
	public boolean delete(int stateID)
	{
		try
		{
			String query="DELETE FROM TblState WHERE StateID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int", stateID);			
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
	public StateBean getByID(int stateID)
	{
		StateBean objStateBean=null;
		try
		{
			String query="SELECT * FROM TblState WHERE StateID=?";			
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int", stateID);
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);			
			if(rs!=null)
			{				
				if(rs.next())
				{
					objStateBean=getValue(rs);					
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objStateBean;
	}
	public StateBean getByStateName(String stateName)
	{
		StateBean objStateBean=null;
		try
		{
			String query="SELECT * FROM TblState WHERE StateName=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string", stateName);			
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);			
			if(rs!=null)
			{
				if(rs.next())
				{
					objStateBean=getValue(rs);
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objStateBean;
	}
	public StateBean find(String stateName,int countryID)
	{
		StateBean objStateBean=null;
		try
		{			 
			if(stateName!=null)
			{			
				String query="SELECT * FROM TblState WHERE StateName=? AND CountryID=?";
				Multimap<String,Object> objParameters=LinkedListMultimap.create();
				objParameters.put("string", stateName);
				objParameters.put("int", countryID);				
				ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);				
				if(rs!=null)
				{
					if(rs.next())
					{
						objStateBean=getValue(rs);
					}
					rs.close();
				}
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objStateBean;
	}
	public boolean checkDuplicate(String stateName,int stateID,int countryID)
	{
		boolean rtnValue=false;
		try
		{			 
			if(stateName!=null)
			{			
				String query="SELECT StateID FROM TblState WHERE StateName=? AND CountryId=? and StateID!=?";				
				Multimap<String,Object> objParameters=LinkedListMultimap.create();
				objParameters.put("string", stateName);
				objParameters.put("int", countryID);
				objParameters.put("int", stateID);
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
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return rtnValue;
	}
	public List<StateBean> getAll()
	{
		List<StateBean> objStateBeanList=new ArrayList<StateBean>();
		try
		{
			String query="SELECT * FROM TblState";
			ResultSet rs=dbConnection.runSelect(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					objStateBeanList.add(getValue(rs));
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objStateBeanList);
	}
	public List<StateBean> getAllByCountryID(int countryID)
	{
		List<StateBean> objStateBeanList=new ArrayList<StateBean>();
		try
		{
			String query="SELECT * FROM TblState WHERE CountryId=?";			
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int", countryID);
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);		
			if(rs!=null)
			{
				while(rs.next())
				{
					objStateBeanList.add(getValue(rs));
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objStateBeanList);
	}
	private StateBean getValue(ResultSet rs)
	{
		StateBean objStateBean=new StateBean();
		try
		{
			objStateBean.setStateID(rs.getInt("StateID"));
			objStateBean.setStateName(rs.getString("StateName"));					
			objStateBean.setCountry(new CountryDAO().getByID(rs.getInt("CountryId")));
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objStateBean);
	}
}