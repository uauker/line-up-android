package com.uauker.apps.lineup.rir.models;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("serial")
public class Palco implements Serializable {
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("startAt")
	public String startAt;
	
	@SerializedName("musicians")
	public List<Musician> musicias;

}
