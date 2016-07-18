package io.gs.botkit.nlp;

import org.json.JSONObject;

public interface GupshupNLPEngine
{

	JSONObject parseRaw(String message) throws Exception;

	NLPResponse parse(String message) throws Exception;
}
