package co.quickwork.bot.registrationbot.main;

import io.gs.botkit.channel.ui.GupshupWidget;
import io.gs.botkit.main.GupshupBotKit;
import io.gs.botkit.model.GupshupContext;
import io.gs.botkit.proxy.GupshupBotAPI;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.quickwork.bot.common.handler.CandidateProxy;
import co.quickwork.bot.common.model.BotAuthCredential;
import co.quickwork.bot.registrationbot.interaction.RegistrationBot;

@WebServlet("/RegistrationHandler")
public class CandidateHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
			
	private String apikey = "f13cba5597b044dbc4f43bd261b626c8";
	private String botname = "MSampleBot";
	RegistrationBot registrationBot= new RegistrationBot();
    BotAuthCredential botAuthCredential= new BotAuthCredential(botname, apikey);
	GupshupBotKit botkit = GupshupBotKit.getInstance(botname, apikey);
	
    public CandidateHandler() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GupshupContext gsContext = botkit.createContext(request);	
		GupshupBotAPI gsApi =  botkit.gupshupAPI();
		if(gsContext.isBotMappedEvent())
		{
			gsApi.sendMessage("Hi there I'm Registration's Bot",gsContext.getContextObj());
		}
		if(gsContext.isMessage() )
		{  
			gsContext.getMessageObj();
			String botMessage=registrationBot.getBotMessage(gsContext.getContextObj());
			gsApi.sendMessage(botMessage,gsContext.getContextObj());	
		}
        
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}