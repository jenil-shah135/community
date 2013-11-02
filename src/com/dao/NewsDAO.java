package com.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.bean.NewsBean;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.utility.ExceptionLog;

public class NewsDAO extends DBDAO
{
	public NewsBean insert(NewsBean objNewsBean)
	{
		try
		{
			String query="{call INSERT_News(?,?,?)}";			
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string", objNewsBean.getNewsTitle());
			objParameters.put("string",objNewsBean.getNewsDesc());
			objNewsBean=this.getByID(dbConnection.insertCallableStatement(query, objParameters));
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objNewsBean;
	}
	public boolean update(NewsBean objNewsBean)
	{
		try
		{
			if(objNewsBean.getNewsID()==null)
				return false;			
			String query="UPDATE TblNews SET NewsTitle='"+objNewsBean.getNewsTitle()+"',NewsDesc='"+objNewsBean.getNewsDesc()+"' WHERE NewsID="+objNewsBean.getNewsID();
			if(dbConnection.runQuery(query)>=0)
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
	public boolean delete(int newsID)
	{
		try
		{
			String query="DELETE FROM TblNews WHERE NewsID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int", newsID);
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
	public NewsBean getByID(int newsID)
	{
		NewsBean objNewsBean=null;
		try
		{
			String query="SELECT * FROM TblNews WHERE NewsID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int", newsID);
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);			
			if(rs!=null)
			{
				if(rs.next())
				{
					objNewsBean=getValue(rs);
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objNewsBean;
	}
	public List<NewsBean> getAll()
	{
		List<NewsBean> objNewsBean=new ArrayList<NewsBean>();
		try
		{			
			String query="SELECT * FROM TblNews ORDER BY NewsAddedDate DESC";
			ResultSet rs=dbConnection.runSelect(query);
			if(rs!=null)
			{
				while(rs.next())
				{						
					objNewsBean.add(getValue(rs));
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objNewsBean);
	}
	private NewsBean getValue(ResultSet rs)
	{
		NewsBean objNewsBean=new NewsBean();
		try
		{
			objNewsBean.setNewsID(rs.getInt("NewsID"));
			objNewsBean.setNewsTitle(rs.getString("newsTitle"));
			objNewsBean.setNewsDesc(rs.getString("newsDesc"));
			objNewsBean.setNewsAddedDate(rs.getDate("newsAddedDate"));			
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objNewsBean);
	}
}