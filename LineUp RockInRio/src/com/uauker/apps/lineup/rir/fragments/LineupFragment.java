package com.uauker.apps.lineup.rir.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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

@SuppressLint({ "ValidFragment", "DefaultLocale", "SimpleDateFormat" })
public class LineupFragment extends SherlockFragment implements
		ActionBar.OnNavigationListener {

	public ListView listViewEvent;

	public TextView textViewPalcoRockStreet;

	public List<Palco> palcos;

	public Event event;

	public Activity ownerActivity;

	private String[] palcoNames;

	private long rirStartTime;
	private long timeToStartRir;

	private LinearLayout viewCountDownTimer;

	private TextView textDayCountDownTimer;
	private TextView textDayTimeCountDownTimer;

	private TextView textHourCountDownTimer;
	private TextView textHourTimeCountDownTimer;

	private TextView textMinuteCountDownTimer;
	private TextView textMinuteTimeCountDownTimer;

	private TextView textSecondTimeCountDownTimer;

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

		String stringStartTime = "2013-09-13T00:00:00Z";
		Date date = null;

		try {
			date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
					.parse(stringStartTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		this.rirStartTime = date.getTime();

		Date currentDate = new Date();

		long currentTime = currentDate.getTime();

		this.timeToStartRir = rirStartTime - currentTime;

		RockInRioEvents rirService = new RockInRioEvents(ownerActivity);

		if (this.event == null) {
			this.event = rirService.getFirstEvent();
		}

		this.palcos = this.event.palcos;

		this.palcoNames = getResources().getStringArray(R.array.palco_filter);

		setHasOptionsMenu(true);

		View contentView = inflater.inflate(R.layout.fragment_event, container,
				false);

		setViewCountDownTimer(contentView);
		startTimer();

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

	private void setViewCountDownTimer(View contentView) {

		this.viewCountDownTimer = (LinearLayout) contentView
				.findViewById(R.id.count_down_timer);

		this.textDayCountDownTimer = (TextView) contentView
				.findViewById(R.id.count_down_timer_day_d);
		this.textDayTimeCountDownTimer = (TextView) contentView
				.findViewById(R.id.count_down_timer_day_time);

		this.textHourCountDownTimer = (TextView) contentView
				.findViewById(R.id.count_down_timer_hour_h);
		this.textHourTimeCountDownTimer = (TextView) contentView
				.findViewById(R.id.count_down_timer_hour_time);

		this.textMinuteCountDownTimer = (TextView) contentView
				.findViewById(R.id.count_down_timer_min_m);
		this.textMinuteTimeCountDownTimer = (TextView) contentView
				.findViewById(R.id.count_down_timer_min_time);

		this.textSecondTimeCountDownTimer = (TextView) contentView
				.findViewById(R.id.count_down_timer_second_time);
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

	private void startTimer() {

		new CountDownTimer(timeToStartRir, 1000) {
			public void onTick(long millisUntilFinished) {

				// decompose difference into days, hours, minutes and seconds
				int days = (int) ((millisUntilFinished / 1000) / 86400);
				int hours = (int) (((millisUntilFinished / 1000) - (days * 86400)) / 3600);
				int minutes = (int) (((millisUntilFinished / 1000) - ((days * 86400) + (hours * 3600))) / 60);
				int seconds = (int) ((millisUntilFinished / 1000) % 60);

				if (days > 0) {
					textDayTimeCountDownTimer.setText(days + "");
				} else {
					textDayTimeCountDownTimer.setVisibility(View.GONE);
					textDayCountDownTimer.setVisibility(View.GONE);
				}

				if (hours > 0) {
					textHourTimeCountDownTimer.setText(days + "");
				} else {
					textHourTimeCountDownTimer.setVisibility(View.GONE);
					textHourCountDownTimer.setVisibility(View.GONE);
				}

				if (minutes > 0) {
					textMinuteTimeCountDownTimer.setText(days + "");
				} else {
					textMinuteTimeCountDownTimer.setVisibility(View.GONE);
					textMinuteCountDownTimer.setVisibility(View.GONE);
				}

				textSecondTimeCountDownTimer.setText(seconds + "");

			}

			public void onFinish() {
				viewCountDownTimer.setVisibility(View.GONE);
			}
		}.start();

		// countDownTimer = new CountDownTimer(this.timeToStartRir, 1000) {
		//
		// @Override
		// public void onTick(long leftTimeInMilliseconds) {
		// long seconds = leftTimeInMilliseconds / 1000;
		//
		// if (leftTimeInMilliseconds < timeToStartRir) {
		// // textViewShowTime.setTextAppearance(getApplicationContext(),
		// // R.style.blinkText);
		// // // change the style of the textview .. giving a red
		// // // alert style
		// //
		// // if (blink) {
		// // textViewShowTime.setVisibility(View.VISIBLE);
		// // // if blink is true, textview will be visible
		// // } else {
		// // textViewShowTime.setVisibility(View.INVISIBLE);
		// // }
		// //
		// // blink = !blink; // toggle the value of blink
		// }
		//
		// Calendar cal = Calendar.getInstance();
		// cal.setTimeInMillis(seconds * 1000);
		//
		// String teste = (String.format("%02d", seconds / 60) + ":" + String
		// .format("%02d", seconds % 60));
		//
		// Log.d("timer teste >>> ", cal.getTime().toString());
		//
		// }
		//
		// @Override
		// public void onFinish() {
		// // this function will be called when the timecount is finished
		// }
		//
		// }.start();

	}
}
