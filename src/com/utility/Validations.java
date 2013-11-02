package com.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations
{
	public static boolean validateEmailID(final String hex)
	{
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern=Pattern.compile(EMAIL_PATTERN);;
		Matcher matcher= pattern.matcher(hex);
		return matcher.matches();
	}
	public static boolean isNumeric(String input)
	{
		try
		{
			Long.parseLong(input);
		    return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}
}