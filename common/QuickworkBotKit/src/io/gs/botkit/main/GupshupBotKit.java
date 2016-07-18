package io.gs.botkit.main;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import io.gs.botkit.channel.ui.GupshupWidget;
import io.gs.botkit.model.GupshupContext;
import io.gs.botkit.nlp.NLP;
import io.gs.botkit.processor.GupshupTransactionStateProcessor;
import io.gs.botkit.proxy.GupshupBotAPI;

public class GupshupBotKit
{

	private GupshupBotKit()
	{};

	private static GupshupBotKit instance;

	public static GupshupBotKit getInstance(String botname, String apikey)
	{
		if (instance == null)
		{
			instance = new GupshupBotKit();
			instance.gsAPIKey = apikey;
			instance.botname = botname;
		}
		return instance;

	}

	private GupshupTransactionStateProcessor bsm;
	private GupshupBotAPI gsApi;
	private GupshupWidget gsWidget;
	private String gsAPIKey;
	private String botname;

	public NLP createGupshupNLP()
	{
		
			return new NLP();
	}

	public GupshupTransactionStateProcessor guspshupTransactionStateProcessor()
	{
		if (bsm == null)
		{
			instance.bsm = GupshupTransactionStateProcessor.getInstance();
		}

		return bsm;
	}

	public GupshupContext createContext(HttpServletRequest request)
	{

		return new GupshupContext(request);

	}
	public GupshupContext createContext(String botname, JSONObject msgObj, JSONObject senderObj, JSONObject ctxObj)
	{

		return new GupshupContext(botname, msgObj, senderObj, ctxObj);

	}
	
	public GupshupBotAPI gupshupAPI()
	{
		if (gsApi == null)
			instance.gsApi = new GupshupBotAPI(botname, instance.gsAPIKey);
		return gsApi;
	}

	public String getBotname()
	{
		return botname;
	}

	public void setBotname(String botname)
	{
		this.botname = botname;
	}

	public GupshupWidget gupshupWidget()
	{
		if (gsWidget == null)
			instance.gsWidget = new GupshupWidget();
		return gsWidget;
	}

}
