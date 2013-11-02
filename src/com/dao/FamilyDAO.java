package com.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.bean.AddressBean;
import com.bean.FamilyBean;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.utility.ExceptionLog;

public class FamilyDAO extends DBDAO
{
	public FamilyBean insert(FamilyBean objFamilyBean)
	{
		try
		{
			String query="{call INSERT_Family(?,?,?)}";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string",objFamilyBean.getNativeAddress());
			objParameters.put("int",objFamilyBean.getCurrentAddress().getAddressID());
			objFamilyBean.setFamilyID(dbConnection.insertCallableStatement(query, objParameters));						
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);			
		}
		return objFamilyBean;
	}
	public boolean update(FamilyBean objFamilyBean)
	{
		try
		{
			if(objFamilyBean.getFamilyID()==null)
				return false;
			String query="UPDATE TblFamily SET CurrentAddressId=?,NativeAddress=? WHERE FamilyID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int", objFamilyBean.getCurrentAddress().getAddressID());
			objParameters.put("string",objFamilyBean.getNativeAddress());
			objParameters.put("int",objFamilyBean.getFamilyID());
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
	public boolean delete(int familyID)
	{
		try
		{
			String query="DELETE FROM TblFamily WHERE FamilyID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int",familyID);
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
	public FamilyBean getByID(int familyID)
	{
		FamilyBean objFamilyBean=null;
		try
		{
			String query="SELECT * FROM TblFamily WHERE FamilyID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int",familyID);				
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);
			if(rs!=null)
			{
				if(rs.next())
				{
					objFamilyBean=getValue(rs);
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objFamilyBean;
	}
	public List<FamilyBean> getAll()
	{
		List<FamilyBean> objFamilyBeanList=new ArrayList<FamilyBean>();
		try
		{
			String query="SELECT * FROM TblFamily";
			ResultSet rs=dbConnection.runSelect(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					objFamilyBeanList.add(getValue(rs));
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objFamilyBeanList);
	}
	public List<FamilyBean> getAllNotActivated()
	{
		List<FamilyBean> objFamilyBeanList=new ArrayList<FamilyBean>();
		try
		{
			String query="SELECT * FROM TblFamily WHERE FamilyID NOT IN (SELECT FamilyId FROM TblLogin WHERE FamilyId IS NOT NULL)";
			ResultSet rs=dbConnection.runSelect(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					objFamilyBeanList.add(getValue(rs));
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objFamilyBeanList);
	}
	public List<FamilyBean> getAllActivated()
	{
		List<FamilyBean> objFamilyBeanList=new ArrayList<FamilyBean>();
		try
		{
			String query="SELECT * FROM TblFamily WHERE FamilyID IN (SELECT FamilyId FROM TblLogin)";
			ResultSet rs=dbConnection.runSelect(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					objFamilyBeanList.add(getValue(rs));
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objFamilyBeanList);
	}
	private FamilyBean getValue(ResultSet rs)
	{
		FamilyBean objFamilyBean=new FamilyBean();
		try
		{
			objFamilyBean.setFamilyID(rs.getInt("FamilyID"));					
			AddressDAO objAddressDAO=new AddressDAO();
			AddressBean objAddressBean=objAddressDAO.getByID(rs.getInt("CurrentAddressID"));
			if(objAddressBean!=null)
				objFamilyBean.setCurrentAddress(objAddressBean);
			else
				objFamilyBean.setCurrentAddress(new AddressBean());			
			objFamilyBean.setNativeAddress(rs.getString("NativeAddress"));
			objFamilyBean.setFamilyMembers(new MemberDAO().getAllByFamilyID(rs.getInt("FamilyID")));
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objFamilyBean);
	}
}