package io.gs.botkit.nlp;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import io.gs.botkit.konstants.NLPConstants;

public class NLPResponse
{
	@Override
	public String toString()
	{
		return "NLPResponse [platform=" + platform + ", entityMap=" + entityMap + "]";
	}

	public NLPResponse()
	{
		entityMap = new HashMap<String, ArrayList<?>>();
	}

	private String platform;
	private HashMap<String, ArrayList<?>> entityMap;
	private JSONObject entityJSON;
	
	public JSONObject getEntityJSON()
	{
		return entityJSON;
	}

	public void setEntityJSON(JSONObject entityJSON)
	{
		this.entityJSON = entityJSON;
	}

	public String getPlatform()
	{
		return platform;
	}

	public NLPResponse setPlatform(String platform)
	{
		this.platform = platform;
		return this;
	}

	public void setAmountOfMoneys(ArrayList<MoneyAmount> data)
	{
		entityMap.put(NLPConstants.ENTITY_TYPE_AMOUNT_OF_MONEY, data);
	}

	public void setLocations(ArrayList<Location> data)
	{
		entityMap.put(NLPConstants.ENTITY_TYPE_LOCATION, data);
	}

	public void setContacts(ArrayList<Contact> data)
	{
		entityMap.put(NLPConstants.ENTITY_TYPE_CONTACT, data);
	}

	public void setDistances(ArrayList<Distance> data)
	{
		entityMap.put(NLPConstants.ENTITY_TYPE_DISTANCE, data);
	}

	public void setNumbers(ArrayList<Number> data)
	{
		entityMap.put(NLPConstants.ENTITY_TYPE_NUMBER, data);
	}

	public void setEmails(ArrayList<Email> data)
	{
		entityMap.put(NLPConstants.ENTITY_TYPE_EMAIL, data);
	}

	public void setUrls(ArrayList<URL> data)
	{
		entityMap.put(NLPConstants.ENTITY_TYPE_URL, data);
	}

	public void setReminders(ArrayList<Reminder> data)
	{
		entityMap.put(NLPConstants.ENTITY_TYPE_REMINDER, data);
	}

	public void setDurations(ArrayList<Duration> data)
	{
		entityMap.put(NLPConstants.ENTITY_TYPE_DURATION, data);
	}

	public void setDatetimes(ArrayList<DateTime> data)
	{
		entityMap.put(NLPConstants.ENTITY_TYPE_DATETIME, data);
	}

	public void setCustoms(String key, ArrayList<CustomEntity> data)
	{
		entityMap.put(key, data);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Location> getLocations()
	{
		return (ArrayList<Location>) this.entityMap.get(NLPConstants.ENTITY_TYPE_LOCATION);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<MoneyAmount> getAmounts()
	{
		return (ArrayList<MoneyAmount>) this.entityMap.get(NLPConstants.ENTITY_TYPE_AMOUNT_OF_MONEY);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Contact> getContacts()
	{
		return (ArrayList<Contact>) this.entityMap.get(NLPConstants.ENTITY_TYPE_CONTACT);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Distance> getDistances()
	{
		return (ArrayList<Distance>) this.entityMap.get(NLPConstants.ENTITY_TYPE_DISTANCE);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Number> getNumbers()
	{
		return (ArrayList<Number>) this.entityMap.get(NLPConstants.ENTITY_TYPE_NUMBER);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Email> getEmails()
	{
		return (ArrayList<Email>) this.entityMap.get(NLPConstants.ENTITY_TYPE_EMAIL);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<URL> getUrls()
	{
		return (ArrayList<URL>) this.entityMap.get(NLPConstants.ENTITY_TYPE_URL);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Reminder> getReminders()
	{
		return (ArrayList<Reminder>) this.entityMap.get(NLPConstants.ENTITY_TYPE_REMINDER);
	}
	

	
	
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<Duration> getDurations()
	{
		return (ArrayList<Duration>) this.entityMap.get(NLPConstants.ENTITY_TYPE_DURATION);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<DateTime> getDateTimes()
	{
		return (ArrayList<DateTime>) this.entityMap.get(NLPConstants.ENTITY_TYPE_DATETIME);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<CustomEntity> getCustoms(String key)
	{
		return (ArrayList<CustomEntity>) this.entityMap.get(key);
	}

	public boolean hasAmounts()
	{
		boolean resp = entityMap.containsKey(NLPConstants.ENTITY_TYPE_AMOUNT_OF_MONEY);
		return resp;
	}

	public boolean hasLocations()
	{
		boolean resp = entityMap.containsKey(NLPConstants.ENTITY_TYPE_LOCATION);
		return resp;
	}

	public boolean hasContacts()
	{
		boolean resp = entityMap.containsKey(NLPConstants.ENTITY_TYPE_CONTACT);
		return resp;
	}

	public boolean hasDistances()
	{
		boolean resp = entityMap.containsKey(NLPConstants.ENTITY_TYPE_DISTANCE);
		return resp;
	}

	public boolean hasNumbers()
	{
		boolean resp = entityMap.containsKey(NLPConstants.ENTITY_TYPE_NUMBER);
		return resp;
	}

	public boolean hasEmails()
	{
		boolean resp = entityMap.containsKey(NLPConstants.ENTITY_TYPE_EMAIL);
		return resp;
	}

	public boolean hasURLs()
	{
		boolean resp = entityMap.containsKey(NLPConstants.ENTITY_TYPE_URL);
		return resp;
	}

	public boolean hasReminders()
	{
		boolean resp = entityMap.containsKey(NLPConstants.ENTITY_TYPE_REMINDER);
		return resp;
	}

	public boolean hasDurations()
	{
		boolean resp = entityMap.containsKey(NLPConstants.ENTITY_TYPE_DURATION);
		return resp;
	}

	public boolean hasDateTimes()
	{
		boolean resp = entityMap.containsKey(NLPConstants.ENTITY_TYPE_DATETIME);
		return resp;
	}

	public boolean hasCustoms(String key)
	{
		// This is abhishek
		// This is madhu
		boolean resp = entityMap.containsKey(key);
		return resp;
	}
	
	
}
