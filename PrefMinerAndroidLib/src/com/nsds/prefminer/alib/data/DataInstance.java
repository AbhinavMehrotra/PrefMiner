package com.nsds.prefminer.alib.data;

public class DataInstance 
{

	private boolean is_clicked;
	private String title;
	private String app_name;
	private String location;
	private String time;
	private String activity;
	private String c_id;
	private String filtered_title;
	
	public DataInstance(boolean is_clicked, String title, String app_name, String location, String time, String activity) 
	{
		this.is_clicked = is_clicked;
		this.title = title;
		this.app_name = app_name;
		this.location = location;
		this.time = time;
		this.activity = activity;
	}

	// getters
	public boolean isClicked() {
		return is_clicked;
	}

	public String getTitle() {
		return title;
	}

	public String getAppName() {
		return app_name;
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
	
	public String getFilteredTitle() {
		return filtered_title;
	}

	
	// setters
	public void setIsClicked(boolean is_clicked) {
		this.is_clicked = is_clicked;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAppName(String app_name) {
		this.app_name = app_name;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public void setCId(String c_id) {
		this.c_id = c_id;
	}

	public void setFilteredTitle(String filtered_title) {
		this.filtered_title = filtered_title;
	}


}
