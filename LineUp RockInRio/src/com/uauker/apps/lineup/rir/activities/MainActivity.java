package com.uauker.apps.lineup.rir.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.uauker.apps.lineup.rir.R;
import com.uauker.apps.lineup.rir.fragments.LineupFragment;
import com.uauker.apps.lineup.rir.helpers.SharedPreferencesHelper;
import com.uauker.apps.lineup.rir.menu.MenuListFragment;

public class MainActivity extends BaseActivity {

	private Fragment mContent;
	private SharedPreferencesHelper sharedPreferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final ActionBar ab = getSupportActionBar();

		// set defaults for logo & home up
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayUseLogoEnabled(true);
		ab.setIcon(R.drawable.ic_menu);

		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		}

		if (mContent == null) {
			mContent = new LineupFragment();
		}

		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuListFragment()).commit();

		// customize the SlidingMenu
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void switchContent(Fragment fragment) {
		mContent = fragment;

		if (mContent != null) {
			this.sm.setOnOpenListener((OnOpenListener) mContent);
			this.sm.setOnCloseListener((OnCloseListener) mContent);
		}

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}

	@Override
	public void finish() {
		super.finish();

		sharedPreferences = SharedPreferencesHelper
				.getInstance(getApplicationContext());

		sharedPreferences.setString(MenuListFragment.SELECTED_MENU_ROW, "");
	}
}
