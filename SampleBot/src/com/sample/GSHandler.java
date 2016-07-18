package com.sample;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import io.gs.botkit.channel.ui.CatalougeItem;
import io.gs.botkit.channel.ui.GupshupWidget;
import io.gs.botkit.main.GupshupBotKit;
import io.gs.botkit.model.GupshupContext;
import io.gs.botkit.proxy.GupshupBotAPI;

/**
 * Servlet implementation class GSHandler
 */
@WebServlet("/GSHandler")
public class GSHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String apikey ="29a25d75b09d4bebcbaa4ea47418577d";
	private String botname="qwsamplebot";
	GupshupBotKit botkit = GupshupBotKit.getInstance(botname, apikey);
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GSHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		GupshupContext gsContext = botkit.createContext(request);
		GupshupBotAPI gsApi =  botkit.gupshupAPI();
		GupshupWidget widget = new GupshupWidget();
		System.out.println(gsContext.getContextObj());
		if(gsContext.isBotMappedEvent())
		{
			gsApi.sendMessage("Hi there I'M Ayyub's Bot",gsContext.getContextObj());
		}
		if(gsContext.isMessage() && gsContext.isFacebook())
		{
			System.out.println(gsContext.getMessage());
			
			CatalougeItem i1 = new CatalougeItem();
			i1.setImgurl("http://techstory.in/wp-content/uploads/2015/05/sachin-tendulkar.jpg");
			i1.setTitle("Sachin");
			CatalougeItem i2 = new CatalougeItem();
			i2.setImgurl("http://techstory.in/wp-content/uploads/2015/05/sachin-tendulkar.jpg");
			i2.setTitle("Tendulkar");
			ArrayList<CatalougeItem> list = new ArrayList<CatalougeItem>();
			list.add(i1);
			list.add(i2);
			String cat = widget.getFacebookCatalouge(list, new String []{"like","more info"});
			gsApi.sendMessage(cat,gsContext.getContextObj());
			
			
			
			
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
