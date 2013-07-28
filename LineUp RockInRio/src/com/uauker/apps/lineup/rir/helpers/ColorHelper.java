package com.uauker.apps.lineup.rir.helpers;

import android.R;
import android.app.Activity;

public class ColorHelper {
	
	public static int findByValue(Activity activity, String value) {
		value = value.replaceAll("\\D", "_");
		value = "day_" + value;
		
		int id = activity.getResources().getIdentifier(value, "color",
				activity.getPackageName());

		int idColor = (id == 0) ? R.color.darker_gray : id;

		return activity.getResources().getColor(idColor);
	}
	
}
