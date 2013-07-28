package com.uauker.apps.lineup.rir.models;

import java.io.Serializable;
import java.util.List;
import android.annotation.SuppressLint;
import com.google.gson.annotations.SerializedName;

@SuppressLint("DefaultLocale")
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
	
	public String name() {
		String eventName = this.weekDay.toUpperCase() + " " + this.date;
		return eventName;
	}
	
}
