package com.uauker.apps.lineup.rir.fragments;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.uauker.apps.lineup.rir.R;
import com.uauker.apps.lineup.rir.models.Event;
import com.uauker.apps.lineup.rir.models.Palco;
import com.uauker.apps.lineup.rir.services.RockInRioEvents;

@SuppressLint("ValidFragment")
public class LineupFragment extends SherlockFragment {

	
	public ListView eventListView;
	
	public List<Palco> palcos;
	
	public Event event;

	public Activity ownerActivity;

	public LineupFragment() {
		super();
	}
	
	public LineupFragment(Event event) {
		super();
		
		this.event = event;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		RockInRioEvents rirService = new RockInRioEvents(ownerActivity);
		
		if (this.event == null) {
			this.event = rirService.getFirstEvent();
		}

		// final ActionBar ab = ((SherlockFragmentActivity)
		// ownerActivity).getSupportActionBar();
		// ab.setBackgroundDrawable(this.editoriaColor);

		setHasOptionsMenu(true);

		View contentView = inflater.inflate(R.layout.fragment_event,
				container, false);

		this.eventListView = (ListView) contentView
				.findViewById(R.id.event_list_view);

		return contentView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		this.ownerActivity = activity;
	}
}
