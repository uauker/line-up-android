package com.uauker.apps.lineup.rir.menu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uauker.apps.lineup.rir.R;
import com.uauker.apps.lineup.rir.activities.MainActivity;
import com.uauker.apps.lineup.rir.fragments.LineupFragment;
import com.uauker.apps.lineup.rir.helpers.ColorHelper;
import com.uauker.apps.lineup.rir.helpers.SharedPreferencesHelper;

public class MenuListFragment extends ListFragment {

	String[] eventDays = { "13/09", "14/09", "15/09", "19/09", "20/09",
			"21/09", "22/09" };
	String[] eventWeekDays = { "SEXTA", "SÁBADO", "DOMINGO", "QUINTA", "SEXTA",
			"SÁBADO", "DOMINGO" };

	private Activity ownerActivity;

	public final static String SELECTED_MENU_ROW = "selectedMenuRow";

	MenuAdapter adapter;

	SharedPreferencesHelper sharedPreferences;

	List<EventItemMenu> events = new ArrayList<EventItemMenu>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		sharedPreferences = SharedPreferencesHelper.getInstance(ownerActivity);
		return inflater.inflate(R.layout.menu_list, null);

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		this.ownerActivity = activity;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new MenuAdapter(ownerActivity);

		for (EventItemMenu item : getEventsFromMenu()) {
			adapter.add(item);
		}

		setListAdapter(adapter);
	}

	private List<EventItemMenu> getEventsFromMenu() {

		for (int i = 0; i < eventDays.length; i++) {
			String eventName = eventWeekDays[i] + eventDays[i];
			int eventColor = ColorHelper.findByValue(ownerActivity, eventDays[i]);
			
			events.add(new EventItemMenu(eventName, eventColor));
		}

		return events;
	}

	private void switchFragment(Fragment fragment) {
		if (getActivity() == null) {
			return;
		}

		MainActivity fca = (MainActivity) getActivity();
		fca.switchContent(fragment);
	}

	public class EventItemMenu {
		public String name;
		public int color;

		public EventItemMenu(String name, int eventColor) {
			this.name = name;
			this.color = eventColor;
		}
	}

	public class MenuAdapter extends ArrayAdapter<EventItemMenu> {

		public MenuAdapter(Context context) {
			super(context, 0);
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.adapter_menu, null);
			}

			final EventItemMenu item = getItem(position);

			String editoriaName = sharedPreferences
					.getString(SELECTED_MENU_ROW);

			final TextView title = (TextView) convertView
					.findViewById(R.id.row_title);
			title.setText(item.name);

			if (item.name.equalsIgnoreCase(editoriaName)) {
				convertView.setBackgroundColor(item.color);
				title.setTextColor(getResources().getColor(R.color.white));
			} else {
				convertView.setBackgroundColor(ownerActivity.getResources()
						.getColor(R.color.white));
				title.setTextColor(getResources().getColor(R.color.black));
			}

			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					LineupFragment newContent = null;

					sharedPreferences.setString(SELECTED_MENU_ROW, item.name);

					newContent = new LineupFragment();

					if (newContent != null) {
						switchFragment(newContent);
					}

					title.setTextColor(getResources().getColor(R.color.white));

					notifyDataSetChanged();
				}
			});

			return convertView;
		}

	}

}
