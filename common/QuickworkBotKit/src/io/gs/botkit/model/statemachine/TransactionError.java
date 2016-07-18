package io.gs.botkit.model.statemachine;

public class TransactionError
{

	public static final String DATA_NOT_FOUND = "datanotfound";
	public static final String DATA_CONFIDENCE_LOW = "dataconfidencelow";

	String errorType;
	String entityType;

	public String getErrorType()
	{
		return errorType;
	}

	public void setErrorType(String errorType)
	{
		this.errorType = errorType;
	}

	public String getEntityType()
	{
		return entityType;
	}

	public void setEntityType(String entityType)
	{
		this.entityType = entityType;
	}

}
