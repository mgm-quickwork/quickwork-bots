package io.gs.botkit.nlp;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import io.gs.botkit.konstants.NLPConstants;
import io.gs.botkit.util.GupshupHelper;

public class WitAI implements GupshupNLPEngine
{

	private String accessToken;

	public WitAI(String accessToken)
	{
		this.accessToken = accessToken;
	}

	public JSONObject parseRaw(String message) throws Exception
	{
		JSONObject jo = null;
		jo = parseWitAI(message);
		if (jo == null) return null;
		NLPResponse res = new NLPResponse();
		res.setPlatform(NLPConstants.PLATFORM_WIT_AI);
		JSONObject entities = jo.getJSONObject("entities");
		return entities;
	}

	private JSONObject parseWitAI(String message) throws Exception
	{
		OkHttpClient client = new OkHttpClient();
		client.setReadTimeout(2, TimeUnit.MINUTES);
		
		Request request = new Request.Builder().url("https://api.wit.ai/message?q=" + URLEncoder.encode(message, "UTF-8")).get().addHeader("authorization", "Bearer " + this.accessToken).build();
		Response response = client.newCall(request).execute();
		String resStr = response.body().string();
		if (resStr != null)
		{
			JSONObject jo = new JSONObject(resStr);
			return jo;
		}
		return null;
	}

	public NLPResponse parse(String message) throws Exception
	{
		JSONObject data = this.parseWitAI(message);
		NLPResponse nlp = new NLPResponse();
		nlp.setPlatform(NLPConstants.PLATFORM_WIT_AI);
		System.out.println("NLP Message :" + message);
		if (data != null)
		{
			System.out.println("NLP Response: " + data.toString());
			if (data.has("entities"))
			{
				JSONObject entities = data.getJSONObject("entities");
				if (entities.length() == 0){
					System.out.println("NLP Response: No Response");
				}
				
				nlp.setEntityJSON(entities);
				String[] arr = JSONObject.getNames(entities);	

				if (arr == null)
					arr = new String[0];
				
				for (int i = 0; i < arr.length; i++)
				{
					String type = arr[i];
					nlp = process(type, entities.getJSONArray(type), nlp);
				}

				System.out.println("\nEntity JSON: " + nlp.getEntityJSON().toString() + "\n\n");

				return nlp;
			}
		}
		else
		{
			System.out.println("NLP Response: No Response");
		}

		return null;
	}

	private NLPResponse process(String key, JSONArray data, NLPResponse nlp)
	{
		switch (key)
		{
		case NLPConstants.ENTITY_TYPE_AMOUNT_OF_MONEY:

			ArrayList<MoneyAmount> money = processAmountOfMoney(data);
			nlp.setAmountOfMoneys(money);

			break;
		case NLPConstants.ENTITY_TYPE_CONTACT:

			ArrayList<Contact> contacts = processContact(data);
			nlp.setContacts(contacts);
			break;
		case NLPConstants.ENTITY_TYPE_DISTANCE:
			ArrayList<Distance> distances = processDistance(data);
			nlp.setDistances(distances);
			break;
		case NLPConstants.ENTITY_TYPE_NUMBER:
			ArrayList<Number> numbers = processNumber(data);
			nlp.setNumbers(numbers);

			break;
		case NLPConstants.ENTITY_TYPE_EMAIL:
			ArrayList<Email> emails = processEmails(data);
			nlp.setEmails(emails);

			break;
		case NLPConstants.ENTITY_TYPE_URL:
			ArrayList<URL> urls = processURL(data);
			nlp.setUrls(urls);
			break;
		case NLPConstants.ENTITY_TYPE_REMINDER:
			ArrayList<Reminder> reminders = processReminder(data);
			nlp.setReminders(reminders);

			break;
		case NLPConstants.ENTITY_TYPE_DURATION:
			ArrayList<Duration> durations = processDuration(data);
			nlp.setDurations(durations);
			break;
		case NLPConstants.ENTITY_TYPE_DATETIME:
			ArrayList<DateTime> datetimes = processDateTime(data);
			nlp.setDatetimes(datetimes);
			break;
		case NLPConstants.ENTITY_TYPE_LOCATION:
			ArrayList<Location> locations = processLocation(data);
			nlp.setLocations(locations);
			break;

		default:

			ArrayList<CustomEntity> custom = processCustom(data);
			nlp.setCustoms(key, custom);
			break;
		}

		return nlp;

	}

	private ArrayList<CustomEntity> processCustom(JSONArray data)
	{
		ArrayList<CustomEntity> customs = new ArrayList<CustomEntity>();
		for (int i = 0; i < data.length(); i++)
		{
			JSONObject d = data.getJSONObject(i);
			CustomEntity custom = new CustomEntity();
			custom = (CustomEntity) setEntityMeta(custom, d);
			custom.setValue(GupshupHelper.getString("value", d));

			customs.add(custom);
		}

		return customs;
	}

	private ArrayList<Location> processLocation(JSONArray data)
	{
		ArrayList<Location> locs = new ArrayList<Location>();

		for (int i = 0; i < data.length(); i++)
		{
			JSONObject d = data.getJSONObject(i);
			Location loc = new Location();
			loc = (Location) setEntityMeta(loc, d);
			loc.setLocation(GupshupHelper.getString("value", d));

			locs.add(loc);
		}

		return locs;
	}

	private ArrayList<DateTime> processDateTime(JSONArray data)
	{
		ArrayList<DateTime> result = new ArrayList<>();

		for (int i = 0; i < data.length(); i++)
		{
			JSONObject d = data.getJSONObject(i);
			DateTime dateTime = new DateTime();
			dateTime = (DateTime) setEntityMeta(dateTime, d);

			dateTime.setValue(GupshupHelper.getString("value", d));
			dateTime.setGrain(GupshupHelper.getString("grain", d));

			if (d.has("to") && d.has("from"))
			{
				DateTime to = new DateTime();
				JSONObject toData = d.getJSONObject("to");
				to = (DateTime) setEntityMeta(to, toData);

				to.setValue(GupshupHelper.getString("value", toData));
				to.setGrain(GupshupHelper.getString("grain", toData));

				dateTime.setTo(to);

				DateTime from = new DateTime();
				JSONObject fromData = d.getJSONObject("from");
				from = (DateTime) setEntityMeta(from, fromData);

				from.setValue(GupshupHelper.getString("value", fromData));
				from.setGrain(GupshupHelper.getString("grain", fromData));

				dateTime.setFrom(from);
			}

			result.add(dateTime);
		}

		return result;
	}

	private ArrayList<Duration> processDuration(JSONArray data)
	{
		ArrayList<Duration> result = new ArrayList<>();

		for (int i = 0; i < data.length(); i++)
		{
			JSONObject d = data.getJSONObject(i);
			Duration duration = new Duration();
			duration = (Duration) setEntityMeta(duration, d);
			duration.setValue(GupshupHelper.getLong("value", d));
			duration.setUnit(GupshupHelper.getString("unit", d));
			duration.setDurationInSeconds(GupshupHelper.getLong("value", GupshupHelper.getJSONObject("normalized", d)));

			result.add(duration);
		}

		return result;
	}

	private ArrayList<Reminder> processReminder(JSONArray data)
	{
		ArrayList<Reminder> result = new ArrayList<>();

		for (int i = 0; i < data.length(); i++)
		{
			JSONObject d = data.getJSONObject(i);
			Reminder m = new Reminder();
			m = (Reminder) setEntityMeta(m, d);
			m.setValue(GupshupHelper.getString("value", d));

			result.add(m);
		}

		return result;
	}

	private ArrayList<URL> processURL(JSONArray data)
	{
		ArrayList<URL> result = new ArrayList<>();

		for (int i = 0; i < data.length(); i++)
		{
			JSONObject d = data.getJSONObject(i);
			URL u = new URL();
			u = (URL) setEntityMeta(u, d);
			u.setValue(GupshupHelper.getString("value", d));

			result.add(u);
		}

		return result;
	}

	private ArrayList<Email> processEmails(JSONArray data)
	{
		ArrayList<Email> result = new ArrayList<>();

		for (int i = 0; i < data.length(); i++)
		{
			JSONObject d = data.getJSONObject(i);
			Email e = new Email();
			e = (Email) setEntityMeta(e, d);
			e.setValue(GupshupHelper.getString("value", d));

			result.add(e);
		}

		return result;
	}

	private ArrayList<Number> processNumber(JSONArray data)
	{
		ArrayList<Number> result = new ArrayList<>();

		for (int i = 0; i < data.length(); i++)
		{
			JSONObject d = data.getJSONObject(i);
			Number n = new Number();
			n = (Number) setEntityMeta(n, d);
			n.setSuggested(GupshupHelper.getBoolean("suggested", d));
			n.setUnit(GupshupHelper.getString("unit", d));
			n.setValue(d.getDouble("value"));

			result.add(n);
		}

		return result;
	}

	private ArrayList<Distance> processDistance(JSONArray data)
	{
		ArrayList<Distance> result = new ArrayList<>();

		for (int i = 0; i < data.length(); i++)
		{
			JSONObject d = data.getJSONObject(i);
			Distance dist = new Distance();
			dist = (Distance) setEntityMeta(dist, d);
			dist.setUnit(GupshupHelper.getString("unit", d));
			dist.setValue(GupshupHelper.getDouble("value", d));

			result.add(dist);
		}

		return result;
	}

	private ArrayList<MoneyAmount> processAmountOfMoney(JSONArray data)
	{
		ArrayList<MoneyAmount> result = new ArrayList<>();

		for (int i = 0; i < data.length(); i++)
		{
			JSONObject d = data.getJSONObject(i);
			MoneyAmount m = new MoneyAmount();
			m = (MoneyAmount) setEntityMeta(m, d);
			m.setUnit(GupshupHelper.getString("unit", d));
			m.setValue(GupshupHelper.getDouble("value", d));

			result.add(m);
		}

		return result;
	}

	private ArrayList<Contact> processContact(JSONArray data)
	{
		ArrayList<Contact> result = new ArrayList<>();

		for (int i = 0; i < data.length(); i++)
		{
			JSONObject d = data.getJSONObject(i);
			Contact c = new Contact();
			c = (Contact) setEntityMeta(c, d);
			c.setValue(GupshupHelper.getString("value", d));

			result.add(c);
		}

		return result;
	}

	private Entity setEntityMeta(Entity e, JSONObject data)
	{
		e.setConfidence(GupshupHelper.getDouble("confidence", data));
		e.setSuggested(GupshupHelper.getBoolean("suggested", data));
		e.setRaw(data);

		return e;
	}

	@SuppressWarnings("unused")
	private boolean notStandardEnity(String key)
	{
		if (key.equalsIgnoreCase(NLPConstants.ENTITY_TYPE_AMOUNT_OF_MONEY) || key.equalsIgnoreCase(NLPConstants.ENTITY_TYPE_CONTACT) || key.equalsIgnoreCase(NLPConstants.ENTITY_TYPE_DISTANCE)
				|| key.equalsIgnoreCase(NLPConstants.ENTITY_TYPE_NUMBER) || key.equalsIgnoreCase(NLPConstants.ENTITY_TYPE_EMAIL) || key.equalsIgnoreCase(NLPConstants.ENTITY_TYPE_URL)
				|| key.equalsIgnoreCase(NLPConstants.ENTITY_TYPE_REMINDER) || key.equalsIgnoreCase(NLPConstants.ENTITY_TYPE_DURATION) || key.equalsIgnoreCase(NLPConstants.ENTITY_TYPE_AMOUNT_OF_MONEY)
				|| key.equalsIgnoreCase(NLPConstants.ENTITY_TYPE_DATETIME))
			return true;
		return false;
	}
}