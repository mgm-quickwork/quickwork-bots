package co.quickwork.registrationbot.main;

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

import co.quickwork.bot.common.apihandler.CandidateProxy;
import co.quickwork.bot.common.apihandler.ICandidateProxy;
import co.quickwork.bot.common.model.BotAuthCredential;

@WebServlet("/RegistrationHandler")
public class CandidateHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
			
	private String apikey = "f13cba5597b044dbc4f43bd261b626c8";
	private String botname = "MSampleBot";
	 CandidateProxy candidateProxy= new CandidateProxy();
     BotAuthCredential botAuthCredential= new BotAuthCredential(botname, apikey);
	GupshupBotKit botkit = GupshupBotKit.getInstance(botname, apikey);
	
    public CandidateHandler() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GupshupContext gsContext = botkit.createContext(request);	   
        candidateProxy.getUserByContext(gsContext.getContextObj());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
