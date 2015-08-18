package com.utils;

import com.arc.kogi.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

public class UtilsGeneral {
	
	// Obtiene los detalles del ActionBar
		public static void restoreActionBar(Activity activity) {
			ActionBar actionBar = activity.getActionBar();
			actionBar.setBackgroundDrawable(new ColorDrawable(activity
					.getResources().getColor(android.R.color.white)));
			// actionBar.setIcon(activity.getResources().getDrawable(
			// R.drawable.logo_action_bar));
			 actionBar.setLogo(activity.getResources().getDrawable(R.drawable.ic_menu_home));
			// Muestra el nombre de la actividad
			actionBar.setDisplayShowTitleEnabled(true);
			// AppCompat v7 lo deshabilita, por ello, no funcionaba
			actionBar.setDisplayShowHomeEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(false);
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayShowCustomEnabled(true);
			actionBar.setDisplayUseLogoEnabled(true);
			
			int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
			if (actionBarTitleId > 0) {
			    TextView title = (TextView) activity.findViewById(actionBarTitleId);
			    if (title != null) {
			        title.setTextColor(activity.getResources().getColor(R.color.my_red));
			    }
			}
			
			actionBar.show();
		}

}
