package com.nsds.prefminer.alib.rules;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Rule {


	public static final String VALUE_ALL = "all";

	private final String location;
	private final String time;
	private final String activity;
	private final String c_id;
	private final String app_name;
	private final JSONArray keywords;
	private JSONArray sample_notifications;

	public Rule(String location, String time, String activity, String c_id, String app_name, JSONArray keywords, JSONArray sample_notifications) 
	{
		this.location = location;
		this.time = time;
		this.activity = activity;
		this.c_id = c_id;
		this.app_name = app_name;
		this.keywords = keywords;
		this.sample_notifications = sample_notifications;
	}

	public Rule(String json_string) throws JSONException
	{
		JSONObject jo = new JSONObject(json_string);
		this.location = jo.getString("location");
		this.time = jo.getString("time");
		this.activity = jo.getString("activity");
		this.c_id = jo.getString("c_id");
		this.app_name = jo.getString("app_name");
		this.keywords = jo.getJSONArray("keywords");
		this.sample_notifications = jo.getJSONArray("sample_notifications");
	}

	public String convertToString() throws JSONException
	{
		JSONObject jo = new JSONObject();
		jo.put("location", location);
		jo.put("time", time);
		jo.put("activity", activity);
		jo.put("c_id", c_id);
		jo.put("app_name", app_name);
		jo.put("keywords", keywords);
		jo.put("sample_notifications", sample_notifications);
		return jo.toString();
	}


	public String convertToCompressedString() throws JSONException
	{
		JSONObject jo = new JSONObject();
		jo.put("location", location);
		jo.put("time", time);
		jo.put("activity", activity);
		jo.put("c_id", c_id);
		jo.put("app_name", app_name);
		jo.put("keywords", keywords);
		jo.put("sample_notifications", new JSONArray());
		return jo.toString();
	}

	public String getLocation() {
		return location;
	}

	public String getTime() {
		return time;
	}

	public String getActivity() {
		return activity;
	}

	public String getCId() {
		return c_id;
	}

	public String getAppName() {
		return app_name;
	}

	public Set<String> getKeywords() throws JSONException {
		Set<String> keyword_set = new HashSet<String>();
		for(int i = 0; i < keywords.length(); i++)
			keyword_set.add(keywords.getString(i).trim().toLowerCase(Locale.getDefault()));
		return keyword_set;
	}

	public Set<String> getSampleNotifications() throws JSONException {
		Set<String> sample_notifications_set = new HashSet<String>();
		for(int i = 0; i < sample_notifications.length(); i++)
			sample_notifications_set.add(sample_notifications.getString(i));
		return sample_notifications_set;
	}

	public boolean isLocationBasedRuleEqual(Rule r)
	{
		try 
		{
			if(!this.getAppName().equalsIgnoreCase(r.getAppName()))
				return false;

			if(this.getKeywords().size() >= r.getKeywords().size())
			{
				for(String k : r.getKeywords())
					if(!this.getKeywords().contains(k))
						return false;
			}

			if(!this.location.equals("all") && !this.location.equals(r.getLocation()) )
				return false;

			return true;
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
			return false;
		}	
	}

}
