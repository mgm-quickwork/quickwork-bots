package co.quickwork.bot.agentbot.main;

/**
 * @author Madhusudhan Mahale
 */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.gs.botkit.channel.ui.GupshupWidget;
import io.gs.botkit.main.GupshupBotKit;
import io.gs.botkit.model.GupshupContext;
import io.gs.botkit.proxy.GupshupBotAPI;

@WebServlet("/handler")
public class Handler extends HttpServlet {

	private String apikey = "29a25d75b09d4bebcbaa4ea47418577d";
	private String botname = "qwsamplebot";
	GupshupBotKit botkit = GupshupBotKit.getInstance(botname, apikey);
	String receitStr = "{ \"attachment\": { \"type\": \"template\", \"payload\": { \"template_type\": \"receipt\", \"recipient_name\": \"Stephane Crozatier\", \"order_number\": \"12345678902\", \"currency\": \"USD\", \"payment_method\": \"Visa 2345\", \"order_url\": \"http://petersapparel.parseapp.com/order?order_id=123456\", \"timestamp\": \"1428444852\", \"elements\": [{ \"title\": \"Classic White T-Shirt\", \"subtitle\": \"100% Soft and Luxurious Cotton\", \"quantity\": 2, \"price\": 50, \"currency\": \"USD\", \"image_url\": \"http://petersapparel.parseapp.com/img/whiteshirt.png\" }, { \"title\": \"Classic Gray T-Shirt\", \"subtitle\": \"100% Soft and Luxurious Cotton\", \"quantity\": 1, \"price\": 25, \"currency\": \"USD\", \"image_url\": \"http://petersapparel.parseapp.com/img/grayshirt.png\" }], \"address\": { \"street_1\": \"1 Hacker Way\", \"street_2\": \"\", \"city\": \"Menlo Park\", \"postal_code\": \"94025\", \"state\": \"CA\", \"country\": \"US\" }, \"summary\": { \"subtotal\": 75.00, \"shipping_cost\": 4.95, \"total_tax\": 6.19, \"total_cost\": 56.14 }, \"adjustments\": [{ \"name\": \"New Customer Discount\", \"amount\": 20 }, { \"name\": \"$10 Off Coupon\", \"amount\": 10 }] } } }";
	String quickreply = "{ \"text\": \"Pick a color:\", \"quick_replies\": [{ \"content_type\": \"text\", \"title\": \"Red\", \"payload\": \"DEVELOPER_DEFINED_PAYLOAD_FOR_PICKING_RED\" }, { \"content_type\": \"text\", \"title\": \"Green\", \"payload\": \"DEVELOPER_DEFINED_PAYLOAD_FOR_PICKING_GREEN\" }] }";

	String button = "{ \"attachment\": { \"type\": \"template\", \"payload\": { \"template_type\": \"button\", \"text\": \"What do you want to do next?\", \"buttons\": [{ \"type\": \"web_url\", \"url\": \"http://google.com\", \"title\": \"Visit my website\" }, { \"type\": \"postback\", \"title\": \"See Gallery\", \"payload\": \"USER_DEFINED_PAYLOAD\" }, { \"type\": \"postback\", \"title\": \"Book a Show\", \"payload\": \"USER_DEFINED_PAYLOAD\" }] } } }";

	public Handler() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Inside process of QuickworkAgentBot...");
		GupshupContext gsContext = botkit.createContext(request);
		System.out.println(gsContext.toString());
		GupshupBotAPI gsApi = botkit.gupshupAPI();
		GupshupWidget widget = new GupshupWidget();
		gsApi.sendMessage("Received Message", gsContext.getContextObj(), true);
		if (gsContext.isImage()) {
			System.out.println("Link:" + gsContext.getMessage());
		}

	}

}
