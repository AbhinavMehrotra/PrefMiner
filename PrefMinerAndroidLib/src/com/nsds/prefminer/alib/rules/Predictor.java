package com.nsds.prefminer.alib.rules;

import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONException;

public class Predictor {

	private final ArrayList<Rule> rules;
	
	public Predictor(ArrayList<Rule> rules) 
	{
		this.rules = rules;
	}
	
	public boolean predictWithText(String app_name, String title) throws JSONException
	{
		title = title.toLowerCase(Locale.getDefault());
		for(Rule r : rules)
		{
			if((r.getAppName().equalsIgnoreCase(app_name) || r.getAppName().equalsIgnoreCase(Rule.VALUE_ALL)) 
					&& r.getLocation().equalsIgnoreCase(Rule.VALUE_ALL) 
					&& r.getTime().equalsIgnoreCase(Rule.VALUE_ALL) 
					&& r.getActivity().equalsIgnoreCase(Rule.VALUE_ALL) )
			{
				boolean found = true; 
				for(String s : r.getKeywords())
				{
					if(title.contains(s) == false)
					{
						found = false;
						break;
					}
				}
				if(found)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param app_name
	 * @param title
	 * @param location
	 * @return (boolean) Returns true if a notifications is predicted to be ignored by the user, otherwise false. 
	 * @throws JSONException
	 */
	public boolean predictWithTextAndLocation(String app_name, String title, String location) throws JSONException
	{
		title = title.toLowerCase(Locale.getDefault());
		for(Rule r : rules)
		{
			System.out.println("Rule -- " + r.convertToCompressedString());
			if((r.getAppName().equalsIgnoreCase(app_name) || r.getAppName().equalsIgnoreCase(Rule.VALUE_ALL)) 
					&& (r.getLocation().equalsIgnoreCase(location) || r.getLocation().equalsIgnoreCase(Rule.VALUE_ALL)) 
					&& r.getTime().equalsIgnoreCase(Rule.VALUE_ALL) 
					&& r.getActivity().equalsIgnoreCase(Rule.VALUE_ALL) )
			{
				System.out.println("Context matched. Now checking for keywords.");
				boolean found = true; 
				for(String s : r.getKeywords())
				{
					System.out.println("Check --  if \'" + title + "\' contains \'" + s + "\'");
					if(title.contains(s) == false)
					{
						found = false;
						break;
					}
				}
				System.out.println("Result -- " + found);
				if(found)
					return true;
			}
		}
		return false;
	}
	
	public boolean predictWithTextAndTime(String app_name, String title, String time) throws JSONException
	{
		title = title.toLowerCase(Locale.getDefault());
		for(Rule r : rules)
		{
			if((r.getAppName().equalsIgnoreCase(app_name) || r.getAppName().equalsIgnoreCase(Rule.VALUE_ALL)) 
					&& r.getLocation().equalsIgnoreCase(Rule.VALUE_ALL) 
					&& (r.getTime().equalsIgnoreCase(time) || r.getTime().equalsIgnoreCase(Rule.VALUE_ALL)) 
					&& r.getActivity().equalsIgnoreCase(Rule.VALUE_ALL) )
			{
				boolean found = true; 
				for(String s : r.getKeywords())
				{
					if(title.contains(s) == false)
					{
						found = false;
						break;
					}
				}
				if(found)
					return true;
			}
		}
		return false;
	}
	
	public boolean predictWithTextAndActivity(String app_name, String title, String activity) throws JSONException
	{
		title = title.toLowerCase(Locale.getDefault());
		for(Rule r : rules)
		{
			if((r.getAppName().equalsIgnoreCase(app_name) || r.getAppName().equalsIgnoreCase(Rule.VALUE_ALL)) 
					&& r.getLocation().equalsIgnoreCase(Rule.VALUE_ALL)
					&& r.getTime().equalsIgnoreCase(Rule.VALUE_ALL) 
					&& (r.getActivity().equalsIgnoreCase(activity) || r.getActivity().equalsIgnoreCase(Rule.VALUE_ALL))  )
			{
				boolean found = true; 
				for(String s : r.getKeywords())
				{
					if(title.contains(s) == false)
					{
						found = false;
						break;
					}
				}
				if(found)
					return true;
			}
		}
		return false;
	}
	
}
