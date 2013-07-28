package com.uauker.apps.lineup.rir.models;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("serial")
public class Event implements Serializable {
	
	@SerializedName("date")
	public String date;
	
	@SerializedName("weekDay")
	public String weekDay;
	
	@SerializedName("mainEvent")
	public String mainEvent;
	
	@SerializedName("palcos")
	public List<Palco> palcos;
	
}
