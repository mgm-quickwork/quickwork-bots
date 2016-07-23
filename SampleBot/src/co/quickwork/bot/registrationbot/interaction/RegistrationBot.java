package co.quickwork.bot.registrationbot.interaction;

import org.json.JSONObject;

import co.quickwork.bot.common.handler.CandidateProxy;

public class RegistrationBot {

	public String botMessage;
	public static String key1="mobile";
	
	CandidateProxy candidateProxy= new CandidateProxy();
	
	public String getBotMessage(JSONObject contentobj)
	{	
		candidateProxy.getUserByContext(contentobj);	
		return StringUtils.getStr(key1, "English");
		
	}
}