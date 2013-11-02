package com.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.bean.AddressBean;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.utility.ExceptionLog;

public class AddressDAO extends DBDAO
{
	public AddressBean insert(AddressBean objAddressBean)
	{
		try
		{
			String query="{call INSERT_Address(?,?,?,?,?)}";			
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string", objAddressBean.getStreet());
			objParameters.put("string",objAddressBean.getArea());
			objParameters.put("int", objAddressBean.getCity().getCityID());
			objParameters.put("string", objAddressBean.getPincode());
			objAddressBean.setAddressID(dbConnection.insertCallableStatement(query, objParameters));
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objAddressBean;
	}
	public boolean update(AddressBean objAddressBean)
	{
		try
		{
			if(objAddressBean.getAddressID()==null)
				return false;			
			String query="UPDATE TblAddress SET Street='"+objAddressBean.getStreet()+"',Area='"+objAddressBean.getArea()+"',Pincode='"+objAddressBean.getPincode()+"',CityId="+objAddressBean.getCity().getCityID()+" WHERE AddressID="+objAddressBean.getAddressID();
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
	public boolean delete(int addressID)
	{
		try
		{
			String query="DELETE FROM TblAddress WHERE AddressID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int", addressID);
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
	public AddressBean getByID(int addressID)
	{
		AddressBean objAddressBean=null;
		try
		{
			String query="SELECT * FROM TblAddress WHERE AddressID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int", addressID);
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);			
			if(rs!=null)
			{
				if(rs.next())
				{
					objAddressBean=getValue(rs);
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objAddressBean;
	}
	public List<AddressBean> getAll()
	{
		List<AddressBean> objAddressBeanList=new ArrayList<AddressBean>();
		try
		{			
			String query="SELECT * FROM TblAddress";
			ResultSet rs=dbConnection.runSelect(query);
			if(rs!=null)
			{
				while(rs.next())
				{						
					objAddressBeanList.add(getValue(rs));
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objAddressBeanList);
	}
	public List<AddressBean> getAllByCityID(int cityId)
	{
		List<AddressBean> objAddressBeanList=new ArrayList<AddressBean>();
		try
		{			
			String query="SELECT * FROM TblAddress WHERE CityId="+cityId;
			ResultSet rs=dbConnection.runSelect(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					objAddressBeanList.add(getValue(rs));
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objAddressBeanList);
	}
	private AddressBean getValue(ResultSet rs)
	{
		AddressBean objAddressBean=new AddressBean();
		try
		{
			objAddressBean.setAddressID(rs.getInt("AddressID"));
			objAddressBean.setStreet(rs.getString("Street"));
			objAddressBean.setArea(rs.getString("Area"));
			objAddressBean.setCity(new CityDAO().getByID(rs.getInt("CityId")));
			objAddressBean.setPincode(rs.getString("Pincode"));
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objAddressBean);
	}
}