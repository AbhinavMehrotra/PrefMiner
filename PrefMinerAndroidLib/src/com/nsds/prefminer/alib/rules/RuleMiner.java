package com.nsds.prefminer.alib.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;

import com.nsds.prefminer.alib.data.Data;
import com.nsds.prefminer.alib.data.DataFilters;
import com.nsds.prefminer.alib.data.DataInstance;
import com.nsds.prefminer.alib.exception.DataProcessingException;

public class RuleMiner {

	private final DataFilters data_filter;
	private final Data data;
	private final int total_items;
	private final double support;
	private final double confidence;
	private final int min_points;

	public RuleMiner(Data data, double support, double confidence, int min_points) throws DataProcessingException 
	{
		if(data.isProcessed() == false)
			throw new DataProcessingException();
		this.data_filter = new DataFilters();
		this.data = data;
		this.total_items = data.getAllData().size();
		this.support = support;
		this.confidence = confidence;
		this.min_points = min_points;
	}



	public ArrayList<Rule> createTextOnlyRules()
	{	
		ArrayList<DataInstance> instances = data.getAllData();
		ArrayList<Rule> rules = new ArrayList<Rule>();
		Set<String> text_cids = createRules(instances);
		for(String cid : text_cids)
		{
			ArrayList<DataInstance> cid_instances = data_filter.getInstancesWithClusterId(instances, cid);
			Set<String> filtered_titles = getFilteredTitles(cid_instances);
			Set<String> keywords = findKeywords(filtered_titles);
			rules.add(new Rule("all", "all", "all", cid, cid_instances.get(0).getAppName(), 
					setToJSONArray(keywords), setToJSONArray(filtered_titles)));
		}
		return rules;
	}


	public ArrayList<Rule> createTextAndLocationRules()
	{	
		ArrayList<DataInstance> instances = data.getAllData();
		System.out.println("Count: " + instances.size());
		ArrayList<Rule> rules = new ArrayList<Rule>();

		// text rules
		Set<String> text_cids = createRules(instances);
		for(String cid : text_cids)
		{
			System.out.println("New Text Rule: " + cid);
			ArrayList<DataInstance> cid_instances = data_filter.getInstancesWithClusterId(instances, cid);
			Set<String> filtered_titles = getFilteredTitles(cid_instances);
			System.out.println("Filtered titles: " + filtered_titles);
			Set<String> keywords = findKeywords(filtered_titles);
			System.out.println("Keywords: " + keywords);
			rules.add(new Rule("all", "all", "all", cid, cid_instances.get(0).getAppName(), 
					setToJSONArray(keywords), setToJSONArray(getTitles(cid_instances))));
		}

		// location rules
		Set<String> unique_locations = data_filter.getUniqueLocations(instances);
		for(String location : unique_locations)
		{
			ArrayList<DataInstance> filtered_instances = data_filter.getInstancesWithLocation(instances, location);
			Set<String> spam_cids = createRules(filtered_instances);
			for(String cid : spam_cids)
			{
				if(text_cids.contains(cid) == false)
				{
					System.out.println("New Location Rule: " + cid);
					ArrayList<DataInstance> cid_instances = data_filter.getInstancesWithClusterId(instances, cid);
					Set<String> filtered_titles = getFilteredTitles(cid_instances);
					System.out.println("Filtered titles: " + filtered_titles);
					Set<String> keywords = findKeywords(filtered_titles);
					System.out.println("Keywords: " + keywords);
					rules.add(new Rule(location, "all", "all", cid, cid_instances.get(0).getAppName(), 
							setToJSONArray(keywords), setToJSONArray(getTitles(cid_instances))));
				}
			}
		}

		return rules;
	}


	public ArrayList<Rule> createTextAndTimeRules()
	{	
		ArrayList<DataInstance> instances = data.getAllData();
		ArrayList<Rule> rules = new ArrayList<Rule>();

		// text rules
		Set<String> text_cids = createRules(instances);
		for(String cid : text_cids)
		{
			ArrayList<DataInstance> cid_instances = data_filter.getInstancesWithClusterId(instances, cid);
			Set<String> filtered_titles = getFilteredTitles(cid_instances);
			Set<String> keywords = findKeywords(filtered_titles);
			rules.add(new Rule("all", "all", "all", cid, cid_instances.get(0).getAppName(), 
					setToJSONArray(keywords), setToJSONArray(filtered_titles)));
		}

		// time rules
		Set<String> unique_times = data_filter.getUniqueTimeValues(instances);
		for(String time : unique_times)
		{
			ArrayList<DataInstance> filtered_instances = data_filter.getInstancesWithTime(instances, time);
			Set<String> spam_cids = createRules(filtered_instances);
			for(String cid : spam_cids)
			{
				if(text_cids.contains(cid) == false)
				{
					ArrayList<DataInstance> cid_instances = data_filter.getInstancesWithClusterId(instances, cid);
					Set<String> filtered_titles = getFilteredTitles(cid_instances);
					Set<String> keywords = findKeywords(filtered_titles);
					rules.add(new Rule("all", time, "all", cid, cid_instances.get(0).getAppName(), 
							setToJSONArray(keywords), setToJSONArray(filtered_titles)));
				}
			}
		}

		return rules;
	}


	public ArrayList<Rule> createTextAndActivityRules()
	{	
		ArrayList<DataInstance> instances = data.getAllData();
		ArrayList<Rule> rules = new ArrayList<Rule>();

		// text rules
		Set<String> text_cids = createRules(instances);
		for(String cid : text_cids)
		{
			ArrayList<DataInstance> cid_instances = data_filter.getInstancesWithClusterId(instances, cid);
			Set<String> filtered_titles = getFilteredTitles(cid_instances);
			Set<String> keywords = findKeywords(filtered_titles);
			rules.add(new Rule("all", "all", "all", cid, cid_instances.get(0).getAppName(), 
					setToJSONArray(keywords), setToJSONArray(filtered_titles)));
		}

		// activity rules
		Set<String> unique_activities = data_filter.getUniqueActivities(instances);
		for(String activity : unique_activities)
		{
			ArrayList<DataInstance> filtered_instances = data_filter.getInstancesWithActivity(instances, activity);
			Set<String> spam_cids = createRules(filtered_instances);
			for(String cid : spam_cids)
			{
				if(text_cids.contains(cid) == false)
				{
					ArrayList<DataInstance> cid_instances = data_filter.getInstancesWithClusterId(instances, cid);
					Set<String> filtered_titles = getFilteredTitles(cid_instances);
					Set<String> keywords = findKeywords(filtered_titles);
					rules.add(new Rule("all", "all", activity, cid, cid_instances.get(0).getAppName(), 
							setToJSONArray(keywords), setToJSONArray(filtered_titles)));
				}
			}
		}

		return rules;
	}




	private Set<String> createRules(ArrayList<DataInstance> instances)
	{	
		Set<String> unique_cids = data_filter.getUniqueClusterIds(instances);

		Set<String> spam_cids = new HashSet<String>();

		for(String c_id : unique_cids)
		{
			ArrayList<DataInstance> filtered_instances = data_filter.getInstancesWithClusterId(instances, c_id);

			// check for support 
			double sup =  (double)filtered_instances.size() / total_items;
			System.out.println("Count: " + filtered_instances.size());
			System.out.println("Support: " + sup);
			if(filtered_instances.size() >= min_points && sup > support && filtered_instances.size() >= 3)
			{
				// check for dismissal confidence
				double click_rate = data_filter.calculateClickRate(filtered_instances);
				double conf = 1 - click_rate;
				System.out.println("Click rate: " + click_rate);
				System.out.println("Confidence: " + conf);
				if(conf > confidence)
					spam_cids.add(c_id);
			}
		}
		return spam_cids;
	}

	private Set<String> getFilteredTitles(ArrayList<DataInstance> instances)
	{
		Set<String> filtered_titles = new HashSet<String>();
		for(DataInstance di : instances)
			filtered_titles.add(di.getFilteredTitle());
		return filtered_titles;
	}

	private Set<String> getTitles(ArrayList<DataInstance> instances)
	{
		Set<String> titles = new HashSet<String>();
		for(DataInstance di : instances)
			titles.add(di.getTitle());
		return titles;
	}

	private Set<String> findKeywords(Set<String> filtered_titles)
	{	
		Map<String, Integer> keywords_map = new HashMap<String, Integer>();
		for(String s :filtered_titles)
		{
			String[] words = s.split(" ");
			for(String w : words)
			{
				if( keywords_map.keySet().contains(w) )
				{
					int freq = keywords_map.get(w);
					keywords_map.put(w, freq+1);
				}
				else
				{
					keywords_map.put(w, 1);
				}
			}
		}

		Set<String> keywords = new HashSet<String>();
		int threshold = (int) (filtered_titles.size() * 0.5);
		for(String s: keywords_map.keySet())
		{
			if(keywords_map.get(s) >= threshold)
				keywords.add(s);
		}
		return keywords;
	}

	private JSONArray setToJSONArray(Set<String> set)
	{
		JSONArray ja = new JSONArray();
		for(String s : set)
			ja.put(s);
		return ja;
	}


	//
	//	public ArrayList<Rule> createTextOnlyRules()
	//	{	
	//		ArrayList<DataInstance> instances = data.getAllData();
	//		ArrayList<Rule> rules = new ArrayList<Rule>();
	//		Set<String> text_cids = createRules(instances);
	//		for(String cid : text_cids)
	//		{
	//			ArrayList<DataInstance> cid_instances = data_filter.getInstancesWithClusterId(instances, cid);
	//			Set<String> filtered_titles = getFilteredTitles(cid_instances);
	//			Set<String> keywords = findKeywords(filtered_titles);
	//			rules.add(new Rule("all", "all", "all", cid, cid_instances.get(0).getAppName(), 
	//					setToJSONArray(keywords), setToJSONArray(filtered_titles)));
	//		}
	//		return rules;
	//	}
	//
	//
	//	public ArrayList<Rule> createTextAndLocationRules()
	//	{	
	//		ArrayList<DataInstance> instances = data.getAllData();
	//		ArrayList<Rule> rules = new ArrayList<Rule>();
	//
	//		// text rules
	//		Set<String> text_cids = createRules(instances);
	//		for(String cid : text_cids)
	//		{
	//			ArrayList<DataInstance> cid_instances = data_filter.getInstancesWithClusterId(instances, cid);
	//			Set<String> filtered_titles = getFilteredTitles(cid_instances);
	//			Set<String> keywords = findKeywords(filtered_titles);
	//			rules.add(new Rule("all", "all", "all", cid, cid_instances.get(0).getAppName(), 
	//					setToJSONArray(keywords), setToJSONArray(filtered_titles)));
	//		}
	//
	//		// location rules
	//		Set<String> unique_locations = data_filter.getUniqueLocations(instances);
	//		for(String location : unique_locations)
	//		{
	//			ArrayList<DataInstance> filtered_instances = data_filter.getInstancesWithLocation(instances, location);
	//			Set<String> spam_cids = createRules(filtered_instances);
	//			for(String cid : spam_cids)
	//			{
	//				if(text_cids.contains(cid) == false)
	//				{
	//					ArrayList<DataInstance> cid_instances = data_filter.getInstancesWithClusterId(instances, cid);
	//					Set<String> filtered_titles = getFilteredTitles(cid_instances);
	//					Set<String> keywords = findKeywords(filtered_titles);
	//					rules.add(new Rule(location, "all", "all", cid, cid_instances.get(0).getAppName(), 
	//							setToJSONArray(keywords), setToJSONArray(filtered_titles)));
	//				}
	//			}
	//		}
	//
	//		return rules;
	//	}
	//
	//
	//	public ArrayList<Rule> createTextAndTimeRules()
	//	{	
	//		ArrayList<DataInstance> instances = data.getAllData();
	//		ArrayList<Rule> rules = new ArrayList<Rule>();
	//
	//		// text rules
	//		Set<String> text_cids = createRules(instances);
	//		for(String cid : text_cids)
	//		{
	//			ArrayList<DataInstance> cid_instances = data_filter.getInstancesWithClusterId(instances, cid);
	//			Set<String> filtered_titles = getFilteredTitles(cid_instances);
	//			Set<String> keywords = findKeywords(filtered_titles);
	//			rules.add(new Rule("all", "all", "all", cid, cid_instances.get(0).getAppName(), 
	//					setToJSONArray(keywords), setToJSONArray(filtered_titles)));
	//		}
	//
	//		// time rules
	//		Set<String> unique_times = data_filter.getUniqueTimeValues(instances);
	//		for(String time : unique_times)
	//		{
	//			ArrayList<DataInstance> filtered_instances = data_filter.getInstancesWithTime(instances, time);
	//			Set<String> spam_cids = createRules(filtered_instances);
	//			for(String cid : spam_cids)
	//			{
	//				if(text_cids.contains(cid) == false)
	//				{
	//					ArrayList<DataInstance> cid_instances = data_filter.getInstancesWithClusterId(instances, cid);
	//					Set<String> filtered_titles = getFilteredTitles(cid_instances);
	//					Set<String> keywords = findKeywords(filtered_titles);
	//					rules.add(new Rule("all", time, "all", cid, cid_instances.get(0).getAppName(), 
	//							setToJSONArray(keywords), setToJSONArray(filtered_titles)));
	//				}
	//			}
	//		}
	//
	//		return rules;
	//	}
	//
	//
	//	public ArrayList<Rule> createTextAndActivityRules()
	//	{	
	//		ArrayList<DataInstance> instances = data.getAllData();
	//		ArrayList<Rule> rules = new ArrayList<Rule>();
	//
	//		// text rules
	//		Set<String> text_cids = createRules(instances);
	//		for(String cid : text_cids)
	//		{
	//			ArrayList<DataInstance> cid_instances = data_filter.getInstancesWithClusterId(instances, cid);
	//			Set<String> filtered_titles = getFilteredTitles(cid_instances);
	//			Set<String> keywords = findKeywords(filtered_titles);
	//			rules.add(new Rule("all", "all", "all", cid, cid_instances.get(0).getAppName(), 
	//					setToJSONArray(keywords), setToJSONArray(filtered_titles)));
	//		}
	//
	//		// activity rules
	//		Set<String> unique_activities = data_filter.getUniqueActivities(instances);
	//		for(String activity : unique_activities)
	//		{
	//			ArrayList<DataInstance> filtered_instances = data_filter.getInstancesWithActivity(instances, activity);
	//			Set<String> spam_cids = createRules(filtered_instances);
	//			for(String cid : spam_cids)
	//			{
	//				if(text_cids.contains(cid) == false)
	//				{
	//					ArrayList<DataInstance> cid_instances = data_filter.getInstancesWithClusterId(instances, cid);
	//					Set<String> filtered_titles = getFilteredTitles(cid_instances);
	//					Set<String> keywords = findKeywords(filtered_titles);
	//					rules.add(new Rule("all", "all", activity, cid, cid_instances.get(0).getAppName(), 
	//							setToJSONArray(keywords), setToJSONArray(filtered_titles)));
	//				}
	//			}
	//		}
	//
	//		return rules;
	//	}
	//
	//
	//	
	//
	//	private Set<String> createRules(ArrayList<DataInstance> instances)
	//	{	
	//		Set<String> unique_cids = data_filter.getUniqueClusterIds(instances);
	//
	//		Set<String> spam_cids = new HashSet<String>();
	//
	//		for(String c_id : unique_cids)
	//		{
	//			ArrayList<DataInstance> filtered_instances = data_filter.getInstancesWithClusterId(instances, c_id);
	//
	//			// check for support 
	//			double sup = 100 * filtered_instances.size() / instances.size();
	//			if( sup > support)
	//			{
	//				// check for dismissal confidence
	//				double click_rate = data_filter.calculateClickRate(filtered_instances);
	//				double conf = 100 * ( 1 - click_rate );
	//				if(conf > confidence)
	//					spam_cids.add(c_id);
	//			}
	//		}
	//		return spam_cids;
	//	}
	//	
	//	private Set<String> getFilteredTitles(ArrayList<DataInstance> instances)
	//	{
	//		Set<String> filtered_titles = new HashSet<String>();
	//		for(DataInstance di : instances)
	//			filtered_titles.add(di.getFilteredTitle());
	//		return filtered_titles;
	//	}
	//	
	//	private Set<String> findKeywords(Set<String> filtered_titles)
	//	{	
	//		Map<String, Integer> keywords_map = new HashMap<String, Integer>();
	//		for(String s :filtered_titles)
	//		{
	//			String[] words = s.split(" ");
	//			for(String w : words)
	//			{
	//				if( keywords_map.keySet().contains(w) )
	//				{
	//					int freq = keywords_map.get(w);
	//					keywords_map.put(w, freq+1);
	//				}
	//				else
	//				{
	//					keywords_map.put(w, 1);
	//				}
	//			}
	//		}
	//		
	//		Set<String> keywords = new HashSet<String>();
	//		int threshold = (int) (filtered_titles.size() * 0.5);
	//		for(String s: keywords_map.keySet())
	//		{
	//			if(keywords_map.get(s) >= threshold)
	//				keywords.add(s);
	//		}
	//		return keywords;
	//	}
	//
	//	private JSONArray setToJSONArray(Set<String> set)
	//	{
	//		JSONArray ja = new JSONArray();
	//		for(String s : set)
	//			ja.put(s);
	//		return ja;
	//	}

}
