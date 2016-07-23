package co.quickwork.bot.common.handler;

import org.json.JSONObject;

public interface IVerifyProxy {
	
   public boolean sendVerification(JSONObject contextobj,JSONObject messageobj);
}