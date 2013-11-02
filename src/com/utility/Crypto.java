package com.utility;

import java.security.*;

public class Crypto {

	public static String hex(byte[] array)
	{
	  StringBuffer sb = new StringBuffer();
	  for (int i = 0; i < array.length; ++i)
	  {
	    sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1,3));
	  }
	  return sb.toString();
	} 
	public static String md5(String message)
	{ 
	  try
	  { 
	    MessageDigest md = MessageDigest.getInstance("MD5"); 
	    return hex (md.digest(message.getBytes("CP1252"))); 
	  }
	  catch (Exception ex)
	  {
		  ExceptionLog.LogException(ex);
	  }
	  return null;
	}
	public static String randomString()
	{
		return randomString(8);
	}
	public static String randomString(int length)
	{
		RandomString randomString = new RandomString(length);
	    return randomString.nextString();
	}
}