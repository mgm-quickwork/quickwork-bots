package io.gs.botkit.nlp;

public class NLP
{
	
	public GupshupNLPEngine witAIEngine(String accessToken)
	{
		return new WitAI(accessToken);
	}
	public GupshupNLPEngine apiAIEngine()
	{
		return null;
	}
}
