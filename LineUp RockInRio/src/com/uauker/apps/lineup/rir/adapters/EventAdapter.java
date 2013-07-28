package com.uauker.apps.lineup.rir.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uauker.apps.lineup.rir.R;
import com.uauker.apps.lineup.rir.models.Musician;

public class EventAdapter extends ArrayAdapter<Musician> {

	private List<Musician> datasource;
	private LayoutInflater inflater;
//	private Activity ownerActivity;
	
	public EventAdapter(Context context, int layoutResourceId,
			List<Musician> objects) {
		super(context, layoutResourceId, objects);
		
//		this.ownerActivity = (Activity) context;
		this.inflater = LayoutInflater.from(context);
		this.datasource = objects;
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;

		rowView = inflater.inflate(R.layout.adapter_event, parent, false);

			final Musician musician = getMusician(position);

			TextView musicianName = (TextView) rowView
					.findViewById(R.id.adapter_event_musician_name);
			musicianName.setText(musician.name);

//			rowView.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//				}
//			});

		return rowView;
	}

	@Override
	public int getCount() {
		return this.datasource.size();
	}

	public Musician getMusician(int position) {
		return this.datasource.get(position);
	}


}
