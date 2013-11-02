package com.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.bean.MemberBean;

public class General {

	public static String GetMessage(HttpSession session)
	{
		String msg=null;
		try
		{
			if(session!=null)
			{
				if(session.getAttribute("msg")!=null)
				{
					msg=session.getAttribute("msg").toString();
					session.setAttribute("msg", null);
				}
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.LogException(ex);
		}
		return msg;
	}
	public static MemberBean getHeadMember(Iterator<MemberBean> objMemberBeanIterator)
	{
		while(objMemberBeanIterator.hasNext())
	    {
	    	MemberBean objMemberbean=objMemberBeanIterator.next();
	    	if(objMemberbean!=null && objMemberbean.getRelation()!=null && objMemberbean.getRelation().getRelationName()!=null)
	    	{	    		
		    	if(objMemberbean.getRelation().getRelationName().equalsIgnoreCase("head"))
		    	{		    		
		    		return objMemberbean;
		    	}
	    	}
	    }
		return null;
	}
	public static List<MemberBean> removeHeadMember(Iterator<MemberBean> objMemberBeanIterator)
	{		
		List<MemberBean> objMemberBeanList=new ArrayList<MemberBean>();
		while(objMemberBeanIterator.hasNext())
	    {
	    	MemberBean objMemberbean=objMemberBeanIterator.next();
	    	if(objMemberbean!=null && objMemberbean.getRelation()!=null && objMemberbean.getRelation().getRelationName()!=null)
	    	{
		    	if(!objMemberbean.getRelation().getRelationName().equalsIgnoreCase("Head"))		    			    	
		    		objMemberBeanList.add(objMemberbean);		    	
	    	}
	    }
		return objMemberBeanList;
	}
}