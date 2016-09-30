package com.nsds.prefminer.alib.text.clustering;

import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

import android.content.Context;

import com.nsds.prefminer.alib.stemmer.englishStemmer;

public class TextFilters {

	private Set<String> dict_set;
	private Set<String> stop_words;
	private englishStemmer stemmer;
	
	protected TextFilters(Context context, String dict_file_asset, String stopwords_file_asset) throws IOException  
	{
		Scanner dict_scanner = new Scanner(context.getAssets().open(dict_file_asset));
		dict_set = new HashSet<String>();
		while(dict_scanner.hasNext())
			dict_set.add(dict_scanner.nextLine());
		dict_scanner.close();

		Scanner stop_word_scanner = new Scanner(context.getAssets().open(stopwords_file_asset));
		stop_words = new HashSet<String>();
		while(stop_word_scanner.hasNext())
			stop_words.add(stop_word_scanner.nextLine());
		stop_word_scanner.close();
		
		stemmer = new englishStemmer();
	}
	
	
	protected String[] removeNonAlphaAndToLower(String[] line) 
	{
		for ( int i = 0; i < line.length; i++) 
			line[i] = line[i].toLowerCase(Locale.getDefault()).replaceAll("[^a-z]", "");
		return line;
	}


	protected String stemWord(String word)
	{
		stemmer.setCurrent(word);
		if(stemmer.stem())
			return stemmer.getCurrent();
		else 
			return word;
	}
	
	
	protected boolean isPresentInDictionary(String word) 
	{		
		return dict_set.contains(word.toLowerCase(Locale.getDefault()).trim());
	}

	protected boolean isStopWordOrRemovalWord(String word, String removal_word)
	{
		return stop_words.contains(word) || word.equalsIgnoreCase(removal_word);
	}
	
	
}
