package com.uauker.apps.lineup.rir.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.uauker.apps.lineup.rir.R;
import com.uauker.apps.lineup.rir.models.Event;

public class RockInRioEvents {

	private List<Event> events = new ArrayList<Event>();

	private Context context;

	public RockInRioEvents(Context context) {
		this.context = context;
	}

	public List<Event> getAllEvents() {
		loadEvents();
		return events;
	}
	
	public void loadEvents() {
		try {
			InputStream rawResource = context.getResources().openRawResource(
					R.raw.events);

			String json = readFully(rawResource);
			JsonArray jsonArray = new JsonParser().parse(json).getAsJsonArray();

			Gson gson = new Gson();

			Iterator<JsonElement> it = jsonArray.iterator();

			while (it.hasNext()) {
				JsonElement placeJson = it.next();
				Event event = gson.fromJson(placeJson, Event.class);
				this.events.add(event);
			}			
		} catch (IOException e) {
			Log.e("Erro ao carregar o arquivo de place", e.getMessage());
		}
	}
	
	private String readFully(InputStream inputStream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(buffer)) != -1) {
			baos.write(buffer, 0, length);
		}
		return new String(baos.toByteArray());
	}
	
	public Event getFirstEvent() {
		if (events.size() < 1) {
			loadEvents();
		}
		
		return events.get(0);
	}
}
