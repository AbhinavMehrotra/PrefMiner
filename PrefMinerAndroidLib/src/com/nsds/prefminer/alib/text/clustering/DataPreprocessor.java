package com.nsds.prefminer.alib.text.clustering;

import java.io.IOException;
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import android.content.Context;

public class DataPreprocessor {

	private TextFilters filter;

	public DataPreprocessor(Context context, String dict_file_asset, String stopwords_file_asset) throws IOException  
	{
		this.filter = new TextFilters(context, dict_file_asset, stopwords_file_asset);
	}


//	protected Instances createInstances(ArrayList<String> data, String app_name) throws Exception
//	{
//		// create raw data instances
//		FastVector fv = new FastVector();
//		fv.addElement(new Attribute("attr", (FastVector) null));
//		Instances raw_instances = new Instances("text_instances", fv, data.size());
//		for(String title : data)
//		{
//			String[] words = filter.removeNonAlphaAndToLower(title.split(" "));
//			String new_title = "";
//			for(String word : words)
//			{
//				word = filter.stemWord(word);
//				if(filter.isPresentInDictionary(word) &&  ! filter.isStopWordOrRemovalWord(word, app_name))
//					new_title += word + " ";
//			}
//
//			Instance i = new Instance(1);
//			i.setValue((Attribute) fv.elementAt(0), new_title.trim());
//			raw_instances.add(i);
//		}
//
//		// apply the StringToWordVector
//		StringToWordVector filter = new StringToWordVector();
//		filter.setInputFormat(raw_instances);
//		filter.setMinTermFreq( (int) (raw_instances.numInstances() * 0.05) );
//
//		// filter raw data
//		Instances filtered_instances = Filter.useFilter(raw_instances, filter);
//		return filtered_instances;
//
//	}
	
	
	protected Instances createInstances(ArrayList<String> data, String app_name) throws Exception
	{
		// create raw data instances
		FastVector fv = new FastVector();
		fv.addElement(new Attribute("attr", (FastVector) null));
		Instances raw_instances = new Instances("text_instances", fv, data.size());
		for(String title : data)
		{
			String new_title = filterTitle(title, app_name);
			Instance i = new Instance(1);
			i.setValue((Attribute) fv.elementAt(0), new_title);
			raw_instances.add(i);
		}

		// apply the StringToWordVector
		StringToWordVector filter = new StringToWordVector();
		filter.setInputFormat(raw_instances);
		filter.setMinTermFreq( (int) (raw_instances.numInstances() * 0.05) );

		// filter raw data
		Instances filtered_instances = Filter.useFilter(raw_instances, filter);
		return filtered_instances;

	}
	


	public String filterTitle(String s, String app_name)
	{
		String[] words = filter.removeNonAlphaAndToLower(s.split(" "));
		String new_title = "";
		for(String word : words)
		{
			word = filter.stemWord(word);
			if(filter.isPresentInDictionary(word) &&  ! filter.isStopWordOrRemovalWord(word, app_name))
				new_title += word + " ";
		}
		new_title = new_title.trim().toLowerCase();
		if(new_title.length() == 0)
			new_title = s;
		return new_title;
	}
}
