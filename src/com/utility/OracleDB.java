//package com.utility;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.dbutils.QueryRunner;
//import org.apache.commons.dbutils.ResultSetHandler;
//import org.apache.commons.dbutils.handlers.BeanListHandler;
//
//import oracle.jdbc.pool.OracleDataSource;
//
//public class OracleDB
//{
//	private OracleDataSource ds;
//	public OracleDB()
//	{
//		try
//		{
//			ds = new OracleDataSource();
//		    ds.setDriverType("thin");
//		    ds.setServerName(Constants.getDBServerName());
//		    ds.setPortNumber(Constants.getDBPort());
//		    ds.setDatabaseName(Constants.getDatabaseName());
//		    ds.setUser(Constants.getDBUserName());
//		    ds.setPassword(Constants.getDBPassword());
//		}
//		catch(Exception ex)
//		{
//			ExceptionLog.LogException(ex);
//		}
//	}
//	public <T> List<T> getList(String query,T s)
//	{
//		try
//		{
//			@SuppressWarnings("unchecked")
//			ResultSetHandler<List<T>> h = new BeanListHandler<T>((Class<T>) s.getClass());
//			QueryRunner run = new QueryRunner(ds);
//			return run.query(query, h);
//		}
//		catch(Exception ex)
//		{
//			ExceptionLog.LogException(ex);
//		}
//		return new ArrayList<T>();
//	}
//}
//
//
//
//
//
//
//
//package com.utility;
//
//import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import javax.sql.rowset.CachedRowSet;
//
//import oracle.jdbc.pool.OracleDataSource;
//
//import com.sun.rowset.CachedRowSetImpl;
//
//public class DBConnection
//{
//	private static DBConnection instance = null;
//	private Connection conn;
//	private ResultSet rs;
//	private Statement stmt;
//	public synchronized static DBConnection getInstance()
//	{
//		if(null == DBConnection.instance)
//			instance = new DBConnection();
//		return instance;
//	}
//	private DBConnection() 
//	{
//		super();
//		conn = null;
//		rs=null;
//		stmt=null;
//		try
//		{
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			connect();
//		}
//		catch(Exception ex)
//		{
//			ExceptionLog.LogException(ex);
//		}
//	}
//	private synchronized void connect()
//	{
//		try
//		{
//			conn = DriverManager.getConnection(Constants.getSqlConnectionString(),Constants.getDBUserName(),Constants.getDBPassword());
//			System.out.println("Connecting To Database..");
//		}
//		catch(Exception ex)
//		{
//			ExceptionLog.LogException(ex);
//		}
//	}
//	public synchronized CachedRowSet runSelect(String query)
//	{
//		CachedRowSet crs=null;    
//		if(query!="")
//		{			
//			try
//			{
//				crs=new CachedRowSetImpl();
//				stmt = conn.createStatement();
//				rs = stmt.executeQuery(query);
//				crs.populate(rs);
//				rs.close();
//				stmt.close();
//			}
//			catch(Exception ex)
//			{
//				ExceptionLog.LogException(ex);				
//			}
//		}
//		return crs;
//	}
//	public synchronized int runQuery(String query)
//	{
//		if(query!="")
//		{
//			try
//			{
//				stmt = conn.createStatement();				
//				int rows = stmt.executeUpdate(query);
//				stmt.close();
//				return(rows);
//			}	
//			catch(Exception ex)
//			{
//				ExceptionLog.LogException(ex);
//				return -1;
//			}
//		}
//		return -1;
//	}
//	public synchronized PreparedStatement getPreparedStatement(String query)
//	{
//		if(query!="")
//		{
//			try
//			{
//				return conn.prepareStatement(query);
//			}
//			catch(Exception ex)
//			{
//				ExceptionLog.LogException(ex);	
//			}
//		}
//		return null;
//	}
//	public synchronized CachedRowSet selectPreparedStatement(PreparedStatement pstmt)
//	{
//		CachedRowSet crs=null;
//		if(pstmt!=null)
//		{
//			try
//			{
//				crs=new CachedRowSetImpl();
//				rs = pstmt.executeQuery();
//				crs.populate(rs);
//				rs.close();
//				pstmt.close();
//			}		
//			catch(Exception ex)
//			{
//				ExceptionLog.LogException(ex);
//			}
//		}
//		return crs;
//	}
//	public synchronized CallableStatement getCallableStatement(String query)
//	{
//		if(query!="")
//		{
//			try
//			{
//				return conn.prepareCall(query);
//			}
//			catch(Exception ex)
//			{
//				ExceptionLog.LogException(ex);				
//			}
//		}
//		return null;
//	}
//	public synchronized CallableStatement executeCallableStatement(CallableStatement cstmt)
//	{
//		try
//		{
//			if(cstmt!=null)
//				cstmt.executeUpdate();
//			return cstmt;
//		}
//		catch(Exception ex)
//		{
//			ExceptionLog.LogException(ex);
//			return null;
//		}
//	}
//	public synchronized void closeCallableStatement(CallableStatement cstmt)
//	{
//		try
//		{
//			cstmt.close();
//		}
//		catch(Exception ex)
//		{
//			ExceptionLog.LogException(ex);
//		}
//	}
//}