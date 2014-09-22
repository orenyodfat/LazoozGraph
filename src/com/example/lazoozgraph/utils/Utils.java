package com.example.lazoozgraph.utils;


import android.app.Activity;
import android.content.res.Resources;
import android.widget.TextView;

public class Utils {



    public static void setTitleColor(Activity activity, int color){
		int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		TextView titleTV = (TextView)activity.findViewById(titleId); 
		titleTV.setTextColor(color);

    }
 
    
}
