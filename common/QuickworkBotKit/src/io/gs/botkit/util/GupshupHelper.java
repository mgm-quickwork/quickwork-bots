/**
 * @author Madhusudhan Mahale
 */
package io.gs.botkit.util;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class GupshupHelper
{
	public static int random(int max)
	{
		return ThreadLocalRandom.current().nextInt(0, max + 1);
	}

	public static String getString(String key, JSONObject data)
	{
		try
		{
			return data.getString(key);
		}
		catch (Exception ex)
		{
			return null;
		}
	}
	
	public static double getDouble(String key, JSONObject data)
	{
		try
		{
			return data.getDouble(key);
		}
		catch (Exception ex)
		{
			return -1;
		}
	}

	public static JSONObject getJSONObject(String key, JSONObject data)
	{
		try
		{
			return data.getJSONObject(key);
		}
		catch (Exception ex)
		{
			return null;
		}
	}

	public static int getInt(String key, JSONObject data)
	{
		try
		{
			return data.getInt(key);
		}
		catch (Exception ex)
		{
			return -1;
		}
	}
	
	public static long getLong(String key, JSONObject data)
	{
		try
		{
			return data.getLong(key);
		}
		catch (Exception ex)
		{
			return -1;
		}
	}

	public static long getCurrentTime()
	{
		return new Date().getTime();
	}

	public static JSONObject getComponentData(String filename) throws IOException
	{
		String text = IOUtils.toString(GupshupHelper.class.getClassLoader().getResourceAsStream(filename));
		return new JSONObject(text);
	}

	public static boolean getBoolean(String key, JSONObject data)
	{
		try
		{
			return data.getBoolean(key);
		}
		catch (Exception ex)
		{
			return false;
		}
	}
	public static int parseNumber(String message)
	{
		try
		{
			int num = Integer.parseInt(message);
			return num;
		}
		catch(Exception ex)
		{
			
		}
		
		return -1;
	}
	public static boolean isNumber(String message)
	{
		try
		{
			//int num = Integer.parseInt(message);
			return true;
		}
		catch(Exception ex)
		{
			
		}
		
		return false;
	}
}