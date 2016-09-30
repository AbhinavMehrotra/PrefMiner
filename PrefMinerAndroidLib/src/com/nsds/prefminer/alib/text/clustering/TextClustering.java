package com.nsds.prefminer.alib.text.clustering;

import java.io.IOException;
import java.util.ArrayList;

import weka.clusterers.DBSCAN;
import weka.core.Instances;
import weka.core.SerializationHelper;
import android.content.Context;

public class TextClustering {

	private DataPreprocessor data_processor;
	private Instances data_instances;
	private String app_name;
	private ArrayList<String> cids;

	public TextClustering(Context context, String dict_file_asset, String stopwords_file_asset) throws IOException  
	{
		this.data_processor = new DataPreprocessor(context, dict_file_asset, stopwords_file_asset);
	}

	
	public Instances setData(ArrayList<String> data, String app_name) throws Exception
	{
		this.app_name = app_name;
		this.data_instances = this.data_processor.createInstances(data, app_name);
		return data_instances;
	}
	
	
	public DBSCAN buildModel(double eps, int min_pts) throws Exception
	{
		// build model
		DBSCAN model = new DBSCAN();
		model.setEpsilon(eps);
		model.setMinPoints(min_pts);
		model.buildClusterer(data_instances);
		
		return model;
	}

	public ArrayList<String> clusterData(DBSCAN model) throws Exception
	{
		cids = new ArrayList<String>();
		for(int i = 0; i < data_instances.numInstances(); i ++)
		{
			try
			{
				int cid = 1 + model.clusterInstance(data_instances.instance(0));
				cids.add(app_name + "_" + cid);
			}
			catch(Exception e)
			{
				cids.add(app_name + "_" + 0);
			}
		}
		return cids;
	}
	
	
	public void saveModel(String model_file_path, DBSCAN model) throws Exception
	{
		SerializationHelper.write(model_file_path, model);
	}
	
	public DBSCAN getModel(String model_file_path) throws Exception
	{
		return (DBSCAN) SerializationHelper.read(model_file_path);
	}
	
	
}


//public class TextClustering {
//
//	private DataPreprocessor data_processor;
//	private Instances data_instances;
//	private String app_name;
//	private ArrayList<String> cids;
//
//	public TextClustering(Context context, String dict_file_asset, String stopwords_file_asset) throws IOException  
//	{
//		this.data_processor = new DataPreprocessor(context, dict_file_asset, stopwords_file_asset);
//	}
//
//	
//	public Instances setData(ArrayList<String> data, String app_name) throws Exception
//	{
//		this.app_name = app_name;
//		this.data_instances = this.data_processor.createInstances(data, app_name);
//		return data_instances;
//	}
//	
//	
//	
//	public DBSCAN buildModel(double eps, int min_pts) throws Exception
//	{
//		// build model
//		DBSCAN model = new DBSCAN();
//		model.setEpsilon(eps);
//		model.setMinPoints(min_pts);
//		model.buildClusterer(data_instances);
//		
//		return model;
//	}
//
//	public ArrayList<String> clusterData(DBSCAN model) throws Exception
//	{
//		cids = new ArrayList<String>();
//		for(int i = 1; i < data_instances.numInstances(); i ++)
//		{
//			try
//			{
//				int cid = 1 + model.clusterInstance(data_instances.instance(0));
//				cids.add(app_name + "_" + cid);
//			}
//			catch(Exception e)
//			{
//				cids.add(app_name + "_" + 0);
//			}
//		}
//		return cids;
//	}
//	
//	
//	public void saveModel(String model_file_path, DBSCAN model) throws Exception
//	{
//		SerializationHelper.write(model_file_path, model);
//	}
//	
//	public DBSCAN getModel(String model_file_path) throws Exception
//	{
//		return (DBSCAN) SerializationHelper.read(model_file_path);
//	}
//	
//	
//}
