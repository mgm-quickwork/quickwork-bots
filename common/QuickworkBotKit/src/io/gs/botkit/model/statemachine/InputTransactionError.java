package io.gs.botkit.model.statemachine;

import org.json.JSONObject;

public class InputTransactionError extends TransactionError
{
	private JSONObject entity;
	

	public JSONObject getEntity()
	{
		return entity;
	}

	public void setEntity(JSONObject entity)
	{
		this.entity = entity;
	}
}	
