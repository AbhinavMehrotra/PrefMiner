package com.nsds.prefminer.alib.data;

import java.util.ArrayList;
import java.util.Set;

import weka.clusterers.DBSCAN;
import weka.core.Instances;
import android.content.Context;

import com.nsds.prefminer.alib.text.clustering.DataPreprocessor;
import com.nsds.prefminer.alib.text.clustering.TextClustering;

public class Data 
{
	private ArrayList<DataInstance> data_instances;
	private boolean is_processed;

	public Data() 
	{
		this.is_processed = false;
		this.data_instances = new ArrayList<DataInstance>();
	}

	public Data(ArrayList<DataInstance> di_list) 
	{
		this.data_instances = new ArrayList<DataInstance>();
		data_instances.addAll(di_list);
	}

	public ArrayList<DataInstance> getAllData()
	{
		return this.data_instances;
	}

	public void addDataIndtance(DataInstance di)
	{
		this.data_instances.add(di);
	}

	public void addDataIndtances(ArrayList<DataInstance> di_list)
	{
		this.data_instances.addAll(di_list);
	}

	// for adding cluster ids and filtered titles
	public void processData(Context context, String dict_file_asset, String stopwords_file_asset) throws Exception
	{
		DataPreprocessor pd = new DataPreprocessor(context, dict_file_asset, stopwords_file_asset);
		ArrayList<DataInstance> processed_data_instances = new ArrayList<DataInstance>();

		TextClustering clustering = new TextClustering(context, dict_file_asset, stopwords_file_asset);

		DataFilters filter = new DataFilters();

		Set<String> apps = filter.getUniqueAppNames(data_instances);

		for(String app : apps)
		{
			System.out.println("App name -- " + app);
			ArrayList<DataInstance> app_data = filter.getInstancesWithAppName(data_instances, app);

			ArrayList<String> titles = new ArrayList<String>();
			for(DataInstance di : app_data)
				titles.add(di.getTitle());

			Instances processed_data = clustering.setData(titles, app);
			ArrayList<String> cids = new ArrayList<String>();
			if(app_data.size() >= 5)
			{
				double eps = 1;
				int min_pts = (int)  (app_data.size() * 0.05);
				DBSCAN model = clustering.buildModel(eps, min_pts);
				cids = clustering.clusterData(model);
			}
			else
			{
				for(DataInstance di : app_data)
				{
					cids.add(app + "_1");
				}
			}

			for(int i = 0; i < app_data.size(); i++)
			{
				DataInstance app_instance = app_data.get(i);
				String filtered_title = pd.filterTitle(app_instance.getTitle(), app_instance.getAppName());
				String cid = cids.get(i);
				app_instance.setFilteredTitle(filtered_title);
				app_instance.setCId(cid);
				processed_data_instances.add(app_instance);
			}
		}

		data_instances = new ArrayList<DataInstance>();
		data_instances.addAll(processed_data_instances);
		is_processed = true;
	}

	//	// for adding cluster ids and filtered titles
	//	public void processData(Context context, String dict_file_asset, String stopwords_file_asset, double eps, int min_pts) throws Exception
	//	{
	//		ArrayList<DataInstance> processed_data_instances = new ArrayList<DataInstance>();
	//		
	//		TextClustering clustering = new TextClustering(context, dict_file_asset, stopwords_file_asset);
	//		
	//		DataFilters filter = new DataFilters();
	//		
	//		Set<String> apps = filter.getUniqueAppNames(data_instances);
	//		
	//		for(String app : apps)
	//		{
	//			ArrayList<DataInstance> app_data = filter.getInstancesWithAppName(data_instances, app);
	//			
	//			ArrayList<String> titles = new ArrayList<String>();
	//			for(DataInstance di : app_data)
	//				titles.add(di.getTitle());
	//			
	//			Instances processed_data = clustering.setData(titles, app);
	//			DBSCAN model = clustering.buildModel(eps, min_pts);
	//			ArrayList<String> cids = clustering.clusterData(model);
	//			
	//			for(int i = 0; i < app_data.size(); i++)
	//			{
	//				DataInstance app_instance = app_data.get(i);
	//				String filtered_title = processed_data.instance(i).toString();
	//				String cid = cids.get(i);
	//				app_instance.setFilteredTitle(filtered_title);
	//				app_instance.setCId(cid);
	//				processed_data_instances.add(app_instance);
	//			}
	//		}
	//		
	//		data_instances = new ArrayList<DataInstance>();
	//		data_instances.addAll(processed_data_instances);
	//		is_processed = true;
	//	}

	public boolean isProcessed()
	{
		return is_processed;
	}
}

