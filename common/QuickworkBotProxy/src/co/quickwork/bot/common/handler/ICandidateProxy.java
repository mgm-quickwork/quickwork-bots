package co.quickwork.bot.common.handler;

import org.json.JSONObject;

public interface ICandidateProxy {

	public JSONObject getUserByContext(JSONObject contextobj);
	
	public JSONObject updateUser(JSONObject contextobj);
	
   }
