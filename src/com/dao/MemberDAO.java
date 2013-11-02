package com.dao;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.bean.MemberBean;
import com.dao.DBDAO;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.utility.ExceptionLog;

public class MemberDAO extends DBDAO
{
	public MemberBean insert(MemberBean objMemberBean)
	{		
		try
		{
			String query="{call INSERT_Member(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string",objMemberBean.getFirstName());
			objParameters.put("string",objMemberBean.getMiddleName());
			objParameters.put("string",objMemberBean.getLastName());
			objParameters.put("boolean",objMemberBean.getMaritalStatus());
			objParameters.put("string",objMemberBean.getContactNo());
			objParameters.put("string",objMemberBean.getEmailID());
			objParameters.put("string",objMemberBean.getBloodGroup());
			objParameters.put("string",objMemberBean.getEducationQualification());
			if(objMemberBean.getOccupation()!=null && objMemberBean.getOccupation().getOccupationID()!=null)
				objParameters.put("int", objMemberBean.getOccupation().getOccupationID());
			else
				objParameters.put("int", null);
			objParameters.put("string",objMemberBean.getWorkingAddress());
			objParameters.put("date",objMemberBean.getBirthDate());
			objParameters.put("string",objMemberBean.getBirthPlace());
			objParameters.put("boolean",objMemberBean.getGender());
			objParameters.put("int",objMemberBean.getFamilyID());
			objParameters.put("int",objMemberBean.getRelation().getRelationID());
			objMemberBean.setMemberID(dbConnection.insertCallableStatement(query, objParameters));			
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return objMemberBean;
	}
	public boolean update(MemberBean objMemberBean)
	{
		try
		{
			if(objMemberBean.getMemberID()==null)
				return false;
			String query="UPDATE TblMember SET FirstName=?,MiddleName=?,LastName=?,IsMarried=?,ContactNo=?,EmailID=?,BloodGroup=?,EducationQualification=?,OccupationId=?,WorkingAddress=?,BirthDate=?,BirthPlace=?,Gender=?,FamilyId=?,RelationId=? WHERE MemberID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("string", objMemberBean.getFirstName());
			objParameters.put("string", objMemberBean.getMiddleName());
			objParameters.put("string", objMemberBean.getLastName());
			objParameters.put("boolean", objMemberBean.getMaritalStatus());
			objParameters.put("string", objMemberBean.getContactNo());
			objParameters.put("string", objMemberBean.getEmailID());
			objParameters.put("string", objMemberBean.getBloodGroup());
			objParameters.put("string", objMemberBean.getEducationQualification());
			objParameters.put("int", objMemberBean.getOccupation().getOccupationID());
			objParameters.put("string", objMemberBean.getWorkingAddress());
			objParameters.put("date", objMemberBean.getBirthDate());
			objParameters.put("string", objMemberBean.getBirthPlace());
			objParameters.put("boolean", objMemberBean.getGender());
			objParameters.put("int", objMemberBean.getFamilyID());
			objParameters.put("int", objMemberBean.getRelation().getRelationID());
			objParameters.put("int",objMemberBean.getMemberID());			
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
	public boolean delete(int memberID)
	{
		try
		{
			String query="DELETE FROM TblMember WHERE MemberID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int",memberID);			
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

	public MemberBean getByID(int memberID)
	{
		MemberBean objMemberBean=null;
		try
		{
			String query="SELECT * FROM TblMember WHERE MemberID=?";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int",memberID);
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);
			if(rs!=null)
			{
				if(rs.next())
				{
					objMemberBean=new MemberBean();
					objMemberBean.setMemberID(rs.getInt("MemberID"));
					objMemberBean.setFirstName(rs.getString("FirstName"));
					objMemberBean.setMiddleName(rs.getString("MiddleName"));
					objMemberBean.setLastName(rs.getString("LastName"));
					objMemberBean.setBirthDate(new java.sql.Date(rs.getDate("BirthDate").getTime()));
					objMemberBean.setBirthPlace(rs.getString("BirthPlace"));
					objMemberBean.setBloodGroup(rs.getString("BloodGroup"));
					objMemberBean.setContactNo(rs.getString("ContactNo"));
					objMemberBean.setEducationQualification(rs.getString("EducationQualification"));
					objMemberBean.setEmailID(rs.getString("EmailID"));
					objMemberBean.setGender(rs.getBoolean("Gender"));
					objMemberBean.setMaritalStatus(rs.getBoolean("ISMarried"));
					objMemberBean.setOccupation(new OccupationDAO().getByID(rs.getInt("OccupationID")));
					objMemberBean.setWorkingAddress(rs.getString("WorkingAddress"));
					objMemberBean.setFamilyID(rs.getInt("FamilyId"));
					objMemberBean.setRelation(new RelationDAO().getByID(rs.getInt("RelationId")));					
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objMemberBean);
	}
	public List<MemberBean> getAll()
	{
		List<MemberBean> objMemberBeanList=new ArrayList<MemberBean>();
		try
		{
			String query="SELECT MemberID FROM TblMember";
			ResultSet rs=dbConnection.runSelect(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					objMemberBeanList.add(new MemberDAO().getByID(rs.getInt("MemberID")));
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objMemberBeanList);
	}
	public List<MemberBean> getAllByFamilyID(int familyID)
	{
		List<MemberBean> objMemberBeanList=new ArrayList<MemberBean>();
		try
		{
			String query="SELECT MemberID FROM TblMember WHERE familyID=?";			
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			objParameters.put("int",familyID);
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);
			MemberBean objMemberBean;
			if(rs!=null)
			{
				while(rs.next())
				{
					objMemberBean=this.getByID(rs.getInt("MemberID"));
					if(objMemberBean!=null)
						objMemberBeanList.add(objMemberBean);
				}
				rs.close();
			}			
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objMemberBeanList);
	}
	public List<MemberBean> Find(String name,String gender,String maritalStatus,String contactNo,String bloodGroup,String age)
	{
		List<MemberBean> objMemberBeanList=new ArrayList<MemberBean>();
		try
		{
			String query="SELECT MemberID FROM TblMember";
			Multimap<String,Object> objParameters=LinkedListMultimap.create();
			boolean isFirst=true;
			if(!name.isEmpty())
			{
				if(isFirst)
				{
					isFirst=false;
					query+=" WHERE ";
				}
				else
				{
					query+=" AND ";	
				}
				query+=" upper(FirstName) like ?";
				objParameters.put("string","%"+name.toUpperCase()+"%");
				query+=" or upper(MiddleName) like ?";
				objParameters.put("string","%"+name.toUpperCase()+"%");
				query+=" or upper(LastName) like ?";
				objParameters.put("string","%"+name.toUpperCase()+"%");
			}
			if(!gender.isEmpty())
			{
				if(isFirst)
				{
					isFirst=false;
					query+=" WHERE ";
				}
				else
				{
					query+=" AND ";
				}
				query+=" GENDER=?";
				objParameters.put("boolean",(gender.equalsIgnoreCase("male"))?true:false);
			}
			if(!maritalStatus.isEmpty())
			{
				if(isFirst)
				{
					isFirst=false;
					query+=" WHERE ";
				}
				else
				{
					query+=" AND ";	
				}
				query+=" ISMARRIED=?";
				objParameters.put("boolean",gender.equalsIgnoreCase("married"));
			}			
			if(!contactNo.isEmpty())
			{
				if(isFirst)
				{
					isFirst=false;
					query+=" WHERE ";
				}
				else
				{
					query+=" AND ";	
				}
				query+=" contactNo like ?";
				objParameters.put("string","%"+contactNo+"%");
			}
			if(!bloodGroup.isEmpty())
			{
				if(isFirst)
				{
					isFirst=false;
					query+=" WHERE ";			
				}
				else
				{
					query+=" AND ";				
				}
				query+="BLOODGROUP like ?";
				objParameters.put("string",bloodGroup);
			}
			if(!age.isEmpty())
			{
				int ageYear=-1;
				try
				{
					ageYear=Integer.parseInt(age);
				}
				catch(Exception ex)
				{
					ageYear=-1;			
				}
				if(ageYear!=-1)
				{
					if(isFirst)
					{
						isFirst=false;
						query+=" WHERE ";
					}
					else
					{
						query+=" AND ";		
					}
					query+=" BIRTHDATE between ? AND ?";
					java.util.Date objStartDate = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH).parse(Calendar.getInstance().get(Calendar.YEAR)-ageYear+"-1-1");
					java.util.Date objEndDate = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH).parse(Calendar.getInstance().get(Calendar.YEAR)-ageYear+"-12-31");
					objParameters.put("date",objStartDate);
					objParameters.put("date",objEndDate);
				}
			}
			System.out.println("Member Search query=>"+query + java.util.Arrays.asList(objParameters));
			ResultSet rs=dbConnection.selectPreparedStatement(query, objParameters);
			MemberBean objMemberBean;
			if(rs!=null)
			{
				while(rs.next())
				{
					objMemberBean=this.getByID(rs.getInt("MemberID"));
					if(objMemberBean!=null)
						objMemberBeanList.add(objMemberBean);
				}
				rs.close();
			}	
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return(objMemberBeanList);
	}
}