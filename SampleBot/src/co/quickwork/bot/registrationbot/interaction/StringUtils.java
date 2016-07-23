package co.quickwork.bot.registrationbot.interaction;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {

	public static HashMap<String,String> botMessages= new HashMap<String,String>();
	
	public void insertBotMessage()
	{
		botMessages.put("mobile", "Tell me your mobile number");
		botMessages.put("fname", "What is your firstname");
		botMessages.put("lname", "What is your lastname");
		botMessages.put("email", "Tell me your email");
	}
	
	public static String getStr(String key,String local)
	{
		String value=(String)botMessages.get(key);
		return value;
	}
}