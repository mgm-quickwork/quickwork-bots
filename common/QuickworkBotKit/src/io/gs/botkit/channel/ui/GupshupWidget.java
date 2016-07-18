package io.gs.botkit.channel.ui;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class GupshupWidget
{
	public String getFacebookCatalouge(List<CatalougeItem> items, String[] replies)
	{

		if (items.size() < 1)
		{
			return null;
		}
		JSONObject catalouge = new JSONObject();
		catalouge.put("type", "catalogue");
		catalouge.put("replies", replies);
		JSONArray itemArray = new JSONArray();
		for (CatalougeItem item : items)
		{
			JSONObject i = new JSONObject();
			i.put("title", item.getTitle());
			i.put("imgurl", item.getImgurl());
			itemArray.put(i);
		}
		catalouge.put("items", itemArray);
		return catalouge.toString();
	}
}
