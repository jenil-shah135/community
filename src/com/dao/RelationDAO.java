package com.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.bean.RelationBean;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.utility.ExceptionLog;

public class RelationDAO extends DBDAO
{
	public RelationBean insert(RelationBean objRelationBean)
	{
		try
		{
			String query="{call INSERT_Relation(?,?)}";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string",objRelationBean.getRelationName());
			objRelationBean.setRelationID(dbConnection.insertCallableStatement(query, objParameters));			
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objRelationBean;
	}
	public boolean update(RelationBean objRelationBean)
	{
		try
		{
			if(objRelationBean.getRelationID()==null)
				return false;			
			String query="UPDATE TblRelation SET RelationName=? WHERE RelationID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string", objRelationBean.getRelationName());
			objParameters.put("int", objRelationBean.getRelationID());
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
	public boolean delete(int relationID)
	{
		try
		{
			String query="DELETE FROM TblRelation WHERE RelationID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int", relationID);
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
	public RelationBean getByID(int relationID)
	{
		RelationBean objRelationBean=null;
		try
		{
			String query="SELECT * FROM TblRelation WHERE RelationID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int", relationID);
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);
			if(rs!=null)
			{
				if(rs.next())
				{
					objRelationBean=getValue(rs);				
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objRelationBean;
	}
	public RelationBean getByRelationName(String relationName)
	{
		RelationBean objRelationBean=null;
		try
		{
			String query="SELECT * FROM TblRelation WHERE RelationName=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string", relationName);			
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);
			if(rs!=null)
			{				
				if(rs.next())
				{					
					objRelationBean=getValue(rs);										
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{			
			ExceptionLog.LogException(ex);
		}
		return objRelationBean;
	}
	public boolean checkDuplicate(String relationName,int relationID)
	{
		boolean rtnValue=false;
		try
		{
			String query="SELECT RelationID FROM TblRelation WHERE RelationName=? AND RelationID!=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string", relationName);
			objParameters.put("int", relationID);			
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
	public List<RelationBean> getAll()
	{
		List<RelationBean> objRelationBeanList=new ArrayList<RelationBean>();
		try
		{
			String query="SELECT * FROM TblRelation";
			ResultSet rs=dbConnection.runSelect(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					objRelationBeanList.add(getValue(rs));
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objRelationBeanList);
	}
	public List<RelationBean> getAllWithoutHead()
	{
		List<RelationBean> objRelationBeanList=new ArrayList<RelationBean>();
		try
		{
			String query="SELECT * FROM TblRelation WHERE RelationName!='Head'";
			ResultSet rs=dbConnection.runSelect(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					objRelationBeanList.add(getValue(rs));
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objRelationBeanList);
	}
	private RelationBean getValue(ResultSet rs)
	{
		RelationBean objRelationBean=new RelationBean();		
		try
		{
			objRelationBean.setRelationID(rs.getInt("RelationID"));
			objRelationBean.setRelationName(rs.getString("RelationName"));
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objRelationBean;
	}
}