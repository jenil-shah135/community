package com.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ExceptionLog {

	public static void LogException(Exception ex)
	{
		try
		{
			File file = new File("E:/errors.txt");
			FileWriter fstream = new FileWriter(file,true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(ex.getMessage());
			out.newLine();			
			out.close();
			ex.printStackTrace();
		}
		catch(Exception e)
		{
			
		}
	}
}
