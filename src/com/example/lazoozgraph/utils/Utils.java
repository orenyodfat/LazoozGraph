package com.example.lazoozgraph.utils;


import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.widget.TextView;

public class Utils {


	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static final Date invalidDate = new Date(0);
	
	public static double getMinValueFromList(List<double[]> values) {
		double min=Double.MAX_VALUE, max=Double.MIN_VALUE;
		for (double[] ds : values) {
		    for (double d : ds) {
		        if (d > max) max=d;
		        if (d < min ) min=d;
		    }
		}
		return min;
	}
	
	public static double getMaxValueFromList(List<double[]> values) {
		double min=Double.MAX_VALUE, max=Double.MIN_VALUE;
		for (double[] ds : values) {
		    for (double d : ds) {
		        if (d > max) max=d;
		        if (d < min ) min=d;
		    }
		}
		return max;
	}
	
	public static String addDays(double xValue) {
		String initalDate="2014-9-28";//can take any date in current format   
		SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
		String convertedDate = null;
		Calendar cal = Calendar.getInstance();    
		try {
			cal.setTime(dateFormat.parse(initalDate));
			cal.add( Calendar.DATE, ((int)xValue));    
			
			Format formatter = new SimpleDateFormat("dd MMM yy");
			convertedDate=formatter.format(cal.getTime()); 
			System.out.println("Date increase by one.."+convertedDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return convertedDate;
	}
	
    public static void setTitleColor(Activity activity, int color){
		int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		TextView titleTV = (TextView)activity.findViewById(titleId); 
		titleTV.setTextColor(color);

    }
 
    public static final Date fromString( String spec ) {
        try {
            return dateFormat.parse( spec );
        } catch( ParseException dfe ) {
            return invalidDate;
        }
    }
    
}
