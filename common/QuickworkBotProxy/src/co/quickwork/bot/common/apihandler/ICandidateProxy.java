package co.quickwork.bot.common.apihandler;

import org.json.JSONObject;

import co.quickwork.bot.common.model.BotAuthCredential;

import com.mongodb.util.JSON;

public interface ICandidateProxy {

	public JSONObject getUserByContext(JSONObject contextobj);
	
   }
