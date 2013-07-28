package com.uauker.apps.lineup.rir.fragments;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.uauker.apps.lineup.rir.R;
import com.uauker.apps.lineup.rir.adapters.EventAdapter;
import com.uauker.apps.lineup.rir.models.Event;
import com.uauker.apps.lineup.rir.models.Palco;
import com.uauker.apps.lineup.rir.services.RockInRioEvents;

@SuppressLint({ "ValidFragment", "DefaultLocale" })
public class LineupFragment extends SherlockFragment implements
		ActionBar.OnNavigationListener {

	public ListView listViewEvent;

	public TextView textViewPalcoRockStreet;

	public List<Palco> palcos;

	public Event event;

	public Activity ownerActivity;

	private String[] palcoNames;

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

		this.palcos = this.event.palcos;

		this.palcoNames = getResources().getStringArray(R.array.palco_filter);

		setHasOptionsMenu(true);

		View contentView = inflater.inflate(R.layout.fragment_event, container,
				false);

		this.listViewEvent = (ListView) contentView
				.findViewById(R.id.event_list_view);

		this.textViewPalcoRockStreet = (TextView) contentView
				.findViewById(R.id.event_text_view_palco_rock_street);

		loadMusiciansFromPalco(palcos.get(0));

		ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(
				ownerActivity, R.array.palco_filter,
				R.layout.actionbar_dropdown_filter);
		list.setDropDownViewResource(R.layout.actionbar_dropdown_filter);

		final ActionBar ab = ((SherlockFragmentActivity) ownerActivity)
				.getSupportActionBar();
		ab.setTitle(this.event.name());
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		ab.setListNavigationCallbacks(list, this);

		return contentView;
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		sortMusicians(palcoNames[itemPosition]);
		return true;
	}

	private void loadMusiciansFromPalco(Palco palco) {
		EventAdapter eventAdapter = new EventAdapter(ownerActivity,
				R.layout.adapter_event, palco.musicias);
		this.listViewEvent.setAdapter(eventAdapter);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		this.ownerActivity = activity;
	}

	protected void sortMusicians(String selection) {
		String palcoSelected = selection.toLowerCase();
		if (palcoSelected.equals("rock street")) {
			this.listViewEvent.setVisibility(View.GONE);
			this.textViewPalcoRockStreet.setVisibility(View.VISIBLE);
		} else {
			this.textViewPalcoRockStreet.setVisibility(View.GONE);
			this.listViewEvent.setVisibility(View.VISIBLE);
			for (Palco palco : this.palcos) {
				if (palcoSelected.contains(palco.name)) {
					loadMusiciansFromPalco(palco);
				}
			}
		}

	}
}
