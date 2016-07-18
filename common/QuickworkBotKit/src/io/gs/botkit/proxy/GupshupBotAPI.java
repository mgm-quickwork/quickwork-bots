package io.gs.botkit.proxy;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import io.gs.botkit.konstants.CommonConstants;

public class GupshupBotAPI
{

	private String botname;
	private String apikey;
	

	public GupshupBotAPI(String botname, String apikey)
	{
		this.botname = botname;
		this.apikey = apikey;
	}

	public void sendMessage(String message, String contextObj)
	{
		try
		{
			sendPost(message, contextObj);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	public void sendMessage(String message, JSONObject contextObj)
	{
		try
		{
			sendPost(message, contextObj.toString());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private String sendPost(String message, String contextObj) throws Exception
	{
		System.out.println(contextObj);
		String url = CommonConstants.GS_API_BASE_URL + botname + "/msg";
		String encodedContext = URLEncoder.encode(contextObj, "UTF-8");
		String encodedMessage = URLEncoder.encode(message, "UTF-8");
		String bodyData = "context=" + encodedContext + "&message=" + encodedMessage;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("content-type", "application/x-www-form-urlencoded");
		con.setRequestProperty("apikey", this.apikey);
		String urlParameters = bodyData;
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null)
		{
			response.append(inputLine);
		}
		in.close();
		System.out.println(response.toString());
		return response.toString();
	}

}
