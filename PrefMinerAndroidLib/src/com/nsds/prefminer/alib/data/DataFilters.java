package com.nsds.prefminer.alib.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DataFilters {


	public Set<String> getUniqueClusterIds(ArrayList<DataInstance> data_instances)
	{
		Set<String> unique_cids = new HashSet<String>();
		for(DataInstance di  : data_instances)
			unique_cids.add(di.getCId());
		return unique_cids;
	}

	public Set<String> getUniqueAppNames(ArrayList<DataInstance> data_instances)
	{
		Set<String> unique_apps = new HashSet<String>();
		for(DataInstance di  : data_instances)
			unique_apps.add(di.getAppName());
		return unique_apps;
	}

	public Set<String> getUniqueLocations(ArrayList<DataInstance> data_instances)
	{
		Set<String> unique_locations = new HashSet<String>();
		for(DataInstance di  : data_instances)
			unique_locations.add(di.getLocation());
		return unique_locations;
	}

	public Set<String> getUniqueTimeValues(ArrayList<DataInstance> data_instances)
	{
		Set<String> unique_times = new HashSet<String>();
		for(DataInstance di  : data_instances)
			unique_times.add(di.getTime());
		return unique_times;
	}

	public Set<String> getUniqueActivities(ArrayList<DataInstance> data_instances)
	{
		Set<String> unique_activities = new HashSet<String>();
		for(DataInstance di  : data_instances)
			unique_activities.add(di.getActivity());
		return unique_activities;
	}
	

	
	
	
	
	public ArrayList<DataInstance> getInstancesWithClusterId(ArrayList<DataInstance> data_instances, String c_id)
	{
		ArrayList<DataInstance> filtered_data = new ArrayList<DataInstance>();
		for(DataInstance di  : data_instances)
			if(di.getCId().equals(c_id))
				filtered_data.add(di);
		return filtered_data;
	}

	public ArrayList<DataInstance> getInstancesWithAppName(ArrayList<DataInstance> data_instances, String app_name)
	{
		ArrayList<DataInstance> filtered_data = new ArrayList<DataInstance>();
		for(DataInstance di  : data_instances)
			if(di.getAppName().equals(app_name))
				filtered_data.add(di);
		return filtered_data;
	}

	public ArrayList<DataInstance> getInstancesWithLocation(ArrayList<DataInstance> data_instances, String location)
	{
		ArrayList<DataInstance> filtered_data = new ArrayList<DataInstance>();
		for(DataInstance di  : data_instances)
			if(di.getLocation().equals(location))
				filtered_data.add(di);
		return filtered_data;
	}

	public ArrayList<DataInstance> getInstancesWithTime(ArrayList<DataInstance> data_instances, String time)
	{
		ArrayList<DataInstance> filtered_data = new ArrayList<DataInstance>();
		for(DataInstance di  : data_instances)
			if(di.getTime().equals(time))
				filtered_data.add(di);
		return filtered_data;
	}

	public ArrayList<DataInstance> getInstancesWithActivity(ArrayList<DataInstance> data_instances, String activity)
	{
		ArrayList<DataInstance> filtered_data = new ArrayList<DataInstance>();
		for(DataInstance di  : data_instances)
			if(di.getActivity().equals(activity))
				filtered_data.add(di);
		return filtered_data;
	}


	public double calculateClickRate(ArrayList<DataInstance> data_instances)
	{
		int cliks = 0;
		for(DataInstance di  : data_instances)
			if(di.isClicked())
				cliks++;
		return cliks / data_instances.size();
	}
}
