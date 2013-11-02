package com.utility;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import javax.sql.rowset.CachedRowSet;

import com.google.common.collect.Multimap;
import com.sun.rowset.CachedRowSetImpl;

public class DBConnection
{
	private Connection conn;
	private static DBConnection instant=null;
	private DBConnection()
	{
		super();
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();						
			//conn = DriverManager.getConnection(Constants.getSqlConnectionString(),Constants.getDBUserName(),Constants.getDBPassword());
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}		
	}
	public static DBConnection getInstant()
	{
		if(instant==null)
			instant=new DBConnection();
		return instant;
	}
	private synchronized void connect() throws SQLException
	{
		try
		{
			conn = DriverManager.getConnection(Constants.getSqlConnectionString(),Constants.getDBUserName(),Constants.getDBPassword());	
		}
		catch(SQLException ex)
		{
			throw ex;
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
	}
	public synchronized CachedRowSet runSelect(String query)
	{
		CachedRowSet crs=null;    
		if(query!="")
		{
			try
			{
				connect();
				crs=new CachedRowSetImpl();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				crs.populate(rs);
				rs.close();	
				stmt.close();
			}
			catch(Exception ex)
			{
				ExceptionLog.LogException(ex);		
			}
			finally
			{
				disconnect();
			}
		}
		return crs;
	}
	public synchronized int runQuery(String query)
	{
		int rows=-1;
		if(query!="")
		{
			try
			{
				connect();
				Statement stmt = conn.createStatement();				
				rows = stmt.executeUpdate(query);
				stmt.close();
			}
			catch(Exception ex)
			{
				System.out.println("Error Query=>"+query);
				ExceptionLog.LogException(ex);
			}
			finally
			{
				disconnect();
			}
		}
		return(rows);
	}
	public synchronized ResultSet selectPreparedStatement(String query,Multimap<String, Object> paramaters)
	{
		CachedRowSetImpl crs=null;
		if(query!="")
		{
			try
			{
				connect();
				PreparedStatement pstmt=conn.prepareStatement(query);
				pstmt=fillPreparedStatement(pstmt, paramaters);				
				ResultSet rs = pstmt.executeQuery();				
				crs=new CachedRowSetImpl();
				crs.populate(rs);
				rs.close();
				pstmt.close();
			}
			catch(Exception ex)
			{
				System.out.println("Error Query=>"+query);
				ExceptionLog.LogException(ex);	
			}
			finally
			{
				disconnect();
			}
		}
		return crs;
	}
	public synchronized int runPreparedStatement(String query,Multimap<String, Object> parameters)
	{
		int rows=-1;
		if(query!="")
		{
			try
			{
				connect();
				PreparedStatement pstmt=conn.prepareStatement(query);
				pstmt=fillPreparedStatement(pstmt, parameters);
				rows = pstmt.executeUpdate();
				pstmt.close();
			}
			catch(Exception ex)
			{
				ExceptionLog.LogException(ex);	
			}
			finally
			{
				disconnect();
			}
		}
		return rows;
	}
	private PreparedStatement fillPreparedStatement(PreparedStatement pstmt, Multimap<String, Object> parameters) throws SQLException 
	{
		if (parameters != null)
		{
			ParameterMetaData pmd = null;
			pmd = pstmt.getParameterMetaData();
			if (pmd.getParameterCount() < parameters.size()) 
			{
				throw new SQLException("Too many parameters: expected "+ pmd.getParameterCount() + ", was given " + parameters.size());	         
			}
			int index=1;
			for (Map.Entry<String, Object> e : parameters.entries()) 
			{	
				if(e.getValue()==null)
				{
					switch(e.getKey().toLowerCase())
					{
						case "int":
							pstmt.setNull(index,java.sql.Types.INTEGER);
							break;
						case "string":
							pstmt.setNull(index,java.sql.Types.VARCHAR);
							break;
						case "date":
							pstmt.setNull(index,java.sql.Types.DATE);
							break;
						case "boolean":
							pstmt.setNull(index,java.sql.Types.BOOLEAN);
							break;
						case "bit":
							pstmt.setNull(index,java.sql.Types.BIT);
							break;
						case "double":
							pstmt.setNull(index,java.sql.Types.DOUBLE);
							break;
						case "float":
							pstmt.setNull(index,java.sql.Types.FLOAT);
							break;
						case "long":
							pstmt.setNull(index,java.sql.Types.BIGINT);
							break;
						case "null":
							pstmt.setNull(index, java.sql.Types.NULL);
							break;
						case "short":
							pstmt.setNull(index,java.sql.Types.TINYINT);
							break;
						case "time":
							pstmt.setNull(index, java.sql.Types.TIME);
							break;
						case "timestamp":
							pstmt.setNull(index, java.sql.Types.TIMESTAMP);
							break;
						case "blob":
							pstmt.setNull(index,java.sql.Types.BLOB);
							break;
						case "object":
							pstmt.setNull(index,java.sql.Types.JAVA_OBJECT);
							break;
						default:
							pstmt.setNull(index,java.sql.Types.NVARCHAR);
							break;
					}
				}
				else
				{
					switch(e.getKey().toLowerCase())
					{
						case "int":
							pstmt.setInt(index,(int) e.getValue());
							break;
						case "string":
							pstmt.setString(index,e.getValue().toString());
							break;
						case "date":
							java.util.Date utilDate=(java.util.Date)e.getValue();
							pstmt.setDate(index,new java.sql.Date(utilDate.getTime()));
							break;
						case "boolean":
							pstmt.setBoolean(index,(boolean)e.getValue());
							break;
						case "byte":
							pstmt.setByte(index,(byte)e.getValue());
							break;
						case "bytes":
							pstmt.setBytes(index,(byte[])e.getValue());
							break;
						case "double":
							pstmt.setDouble(index,(double)e.getValue());
							break;
						case "float":
							pstmt.setFloat(index,(float)e.getValue());
							break;
						case "long":
							pstmt.setLong(index,(long)e.getValue());
							break;
						case "null":
							pstmt.setNull(index, java.sql.Types.NULL);
							break;
						case "short":
							pstmt.setShort(index, (short)e.getValue());
							break;
						case "blob":
							pstmt.setBlob(index,(Blob)e.getValue());
							break;
						case "object":
							pstmt.setObject(index,e.getValue());
							break;
						default:
							pstmt.setObject(index,e.getValue());
							break;
					}
				}
				index++;			
			}
		}
		return pstmt;
	}
	public synchronized CallableStatement getCallableStatement(String query)
	{
		if(query!="")
		{
			try
			{
				connect();
				return conn.prepareCall(query);
			}
			catch(Exception ex)
			{
				ExceptionLog.LogException(ex);
			}
		}
		return null;
	}	
	public synchronized Integer insertCallableStatement(String query,Multimap<String, Object> parameters)
	{
		Integer generatedKey=null;
		int outIndex=parameters.size()+1;
		if(query!="")
		{
			try
			{
				connect();
				CallableStatement cstmt = conn.prepareCall(query);
				cstmt.registerOutParameter(outIndex, java.sql.Types.INTEGER);
				cstmt=fillCallableStatement(cstmt, parameters);
				cstmt.executeUpdate();
				generatedKey=cstmt.getInt(outIndex);
				cstmt.close();
				disconnect();
			}
			catch(Exception ex)
			{
				ExceptionLog.LogException(ex);
			}
		}
		return generatedKey;
	}	
	private CallableStatement fillCallableStatement(CallableStatement cstmt,Multimap<String, Object> parameters)
	{
		int index=1;
		try
		{
			for (Map.Entry<String, Object> e : parameters.entries()) 
			{	
				if(e.getValue()==null)
				{
					switch(e.getKey().toLowerCase())
					{
						case "int":
							cstmt.setNull(index,java.sql.Types.INTEGER);
							break;
						case "string":
							cstmt.setNull(index,java.sql.Types.VARCHAR);
							break;
						case "date":
							cstmt.setNull(index,java.sql.Types.DATE);
							break;
						case "boolean":
							cstmt.setNull(index,java.sql.Types.BOOLEAN);
							break;
						case "bit":
							cstmt.setNull(index,java.sql.Types.BIT);
							break;
						case "double":
							cstmt.setNull(index,java.sql.Types.DOUBLE);
							break;
						case "float":
							cstmt.setNull(index,java.sql.Types.FLOAT);
							break;
						case "long":
							cstmt.setNull(index,java.sql.Types.BIGINT);
							break;
						case "null":
							cstmt.setNull(index, java.sql.Types.NULL);
							break;
						case "short":
							cstmt.setNull(index,java.sql.Types.TINYINT);
							break;
						case "time":
							cstmt.setNull(index, java.sql.Types.TIME);
							break;
						case "timestamp":
							cstmt.setNull(index, java.sql.Types.TIMESTAMP);
							break;
						case "blob":
							cstmt.setNull(index,java.sql.Types.BLOB);
							break;
						case "object":
							cstmt.setNull(index,java.sql.Types.JAVA_OBJECT);
							break;
						default:
							cstmt.setNull(index,java.sql.Types.NVARCHAR);
							break;
					}
				}
				else
				{
					switch(e.getKey().toLowerCase())
					{
						case "int":
							cstmt.setInt(index,(int) e.getValue());
							break;
						case "string":
							cstmt.setString(index,e.getValue().toString());
							break;
						case "date":
							java.util.Date utilDate=(java.util.Date)e.getValue();
							cstmt.setDate(index,new java.sql.Date(utilDate.getTime()));
							break;
						case "boolean":
							cstmt.setBoolean(index,(boolean)e.getValue());
							break;						
						case "byte":
							cstmt.setByte(index,(byte)e.getValue());
							break;
						case "bytes":
							cstmt.setBytes(index,(byte[])e.getValue());
							break;
						case "double":
							cstmt.setDouble(index,(double)e.getValue());
							break;
						case "float":
							cstmt.setFloat(index,(float)e.getValue());
							break;
						case "long":
							cstmt.setLong(index,(long)e.getValue());
							break;
						case "null":
							cstmt.setNull(index, java.sql.Types.NULL);
							break;
						case "short":
							cstmt.setShort(index, (short)e.getValue());
							break;						
						case "blob":
							cstmt.setBlob(index,(Blob)e.getValue());
							break;
						case "object":
							cstmt.setObject(index,e.getValue());
							break;
						default:
							cstmt.setObject(index,e.getValue());
							break;
					}
				}
				index++;
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return cstmt;
	}
	public synchronized CallableStatement executeCallableStatement(CallableStatement cstmt)
	{
		try
		{
			if(cstmt!=null)
				cstmt.executeUpdate();
			return cstmt;
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
			return null;
		}
	}
	public synchronized void closeCallableStatement(CallableStatement cstmt)
	{
		try
		{
			cstmt.close();
			disconnect();
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
	}
	public synchronized void disconnect()
	{
		try
		{
			if(conn!=null)
				conn.close();		
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		finally
		{
			conn=null;	
		}
	}
//	private PreparedStatement fillPreparedStatement(PreparedStatement pstmt, Object[] params) throws SQLException 
//	{
//		if (params != null)
//		{
//			ParameterMetaData pmd = null;
//			pmd = pstmt.getParameterMetaData();
//			if (pmd.getParameterCount() < params.length) 
//			{
//				throw new SQLException("Too many parameters: expected "+ pmd.getParameterCount() + ", was given " + params.length);	         
//			}
//			for (int i = 0; i < params.length; i++) 
//			{
//				if (params[i] != null)
//				{
//					pstmt.setObject(i + 1, params[i]);
//				}
//				else
//				{
//					int sqlType = pmd.getParameterType(i + 1);	                 
//					pstmt.setNull(i + 1, sqlType);
//				}
//			}
//		}
//		return pstmt;
//	}
}