package io.gs.botkit.model;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class GupshupContext
{
	public static final String EVENT_BOT_MAPPED = "botmappedevent";

	private String botName;
	private String channelId;
	private String channelType;
	private String contextId;
	private String userDisplayName;
	private JSONObject contextObj;
	private double latitude;
	private double longitude;
	private String contextType;
	private String messageType;
	private String message;
	private JSONObject senderObj;
	private JSONObject messageObj;

	public JSONObject getMessageObj()
	{
		return messageObj;
	}

	public String getBotName()
	{
		return botName;
	}

	public void setBotName(String botName)
	{
		this.botName = botName;
	}

	public String getChannelId()
	{
		return channelId;
	}

	public void setChannelId(String channelId)
	{
		this.channelId = channelId;
	}

	public String getChannelType()
	{
		return channelType;
	}

	public void setChannelType(String channelType)
	{
		this.channelType = channelType;
	}

	public String getContextId()
	{
		return contextId;
	}

	public void setContextId(String contextId)
	{
		this.contextId = contextId;
	}

	public String getUserDisplayName()
	{
		return userDisplayName;
	}

	public void setUserDisplayName(String userDisplayName)
	{
		this.userDisplayName = userDisplayName;
	}

	public JSONObject getContextObj()
	{
		return contextObj;
	}

	public void setContextObj(JSONObject contextObj)
	{
		this.contextObj = contextObj;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public String getContextType()
	{
		return contextType;
	}

	public void setContextType(String contextType)
	{
		this.contextType = contextType;
	}

	public String getMessageType()
	{
		return messageType;
	}

	public void setMessageType(String messageType)
	{
		this.messageType = messageType;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public JSONObject getSenderObj()
	{
		return senderObj;
	}

	public void setSenderObj(JSONObject senderObj)
	{
		this.senderObj = senderObj;
	}

	public GupshupContext(String botname, JSONObject msgObj, JSONObject senderObj, JSONObject ctxObj) throws JSONException
	{

		createContext(botname, msgObj, senderObj, ctxObj);

	}

	private void createContext(String botname, JSONObject msgObj, JSONObject senderObj, JSONObject ctxObj)
	{

		this.contextObj = ctxObj;
		this.senderObj = senderObj;
		this.message = msgObj.getString("text");
		this.messageObj = msgObj;

		this.latitude = msgObj.has("latitude") ? Double.parseDouble(msgObj.getString("latitude")) : -1;
		this.longitude = msgObj.has("longitude") ? Double.parseDouble(msgObj.getString("longitude")) : -1;

		this.messageType = msgObj.getString("type");
		this.botName = botname;

		this.channelId = senderObj.getString("channelid");
		this.userDisplayName = senderObj.getString("display");
		this.contextId = ctxObj.getString("contextid");

		this.channelType = ctxObj.getString("channeltype");
		this.contextType = ctxObj.getString("contexttype");

	}

	public GupshupContext(HttpServletRequest request)
	{
		String msg = request.getParameter("messageobj");
		String ctx = request.getParameter("contextobj");
		String sender = request.getParameter("senderobj");
		String botname = request.getParameter("botname");
		createContext(botname, new JSONObject(msg), new JSONObject(sender), new JSONObject(ctx));
	}

	public boolean isMessage()
	{
		if (this.getMessageType().equals("msg"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean isEvent()
	{
		if (this.getMessageType().equals("event"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean isBotMappedEvent()
	{
		if (this.getMessageType().equals("event") && this.getMessage().equalsIgnoreCase(GupshupContext.EVENT_BOT_MAPPED))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean isFacebook()
	{
		if (channelType.equalsIgnoreCase("fb"))
			return true;
		return false;
	}

	public boolean isImage()
	{
		if (this.getMessageType().equals("image"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean isFile()
	{
		if (this.getMessageType().equals("file"))
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	@Override
	public String toString()
	{
		return "GupshupContext [botName=" + botName + ", channelId=" + channelId + ", channelType=" + channelType + ", contextId=" + contextId + ", userDisplayName=" + userDisplayName
				+ ", contextObj=" + contextObj + ", latitude=" + latitude + ", longitude=" + longitude + ", contextType=" + contextType + ", messageType=" + messageType + ", message=" + message
				+ ", senderObj=" + senderObj + "]";
	}

	public boolean isLocation()
	{
		if (this.getMessageType().equals("location"))
		{
			return true;
		} else
		{
			return false;
		}
	}

}
