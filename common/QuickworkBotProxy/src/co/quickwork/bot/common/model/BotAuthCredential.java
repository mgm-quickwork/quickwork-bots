package co.quickwork.bot.common.model;

public class BotAuthCredential {
	private String botName;
	private String botAuthKey;
	private String contextObj;
	
	public BotAuthCredential(String botName,String botAuthKey) {
		this.botName = botName;
		this.botAuthKey = botAuthKey;
	}
}
