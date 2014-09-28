package com.example.lazoozgraph;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.XYChart;
import org.achartengine.model.Point;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.lazoozgraph.businessClasses.StatsDataMinersDistDay;
import com.example.lazoozgraph.businessClasses.StatsDataMinersDistDayList;
import com.example.lazoozgraph.businessClasses.StatsDataMinersDistMonth;
import com.example.lazoozgraph.businessClasses.StatsDataMinersDistMonthList;
import com.example.lazoozgraph.utils.ChartUtil;
import com.example.lazoozgraph.utils.PaintView;
import com.example.lazoozgraph.utils.Utils;




import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;

public class MainDistanceActivity extends ActionBarActivity {

	private LinearLayout mLayoutChart1;
	private LinearLayout mLayoutChart2;
	private LinearLayout mLayoutChart3;

	private GraphicalView mChartView1;
	private GraphicalView mChartView2;
	private GraphicalView mChartView3;
	
	private TextView mDistanceTV;
	public StatsDataMinersDistDayList mStatsDataWeekList;
	public StatsDataMinersDistDayList mStatsDataWeekTotalList;
	
	public StatsDataMinersDistDayList mStatsDataDaysList;
	public StatsDataMinersDistDayList mStatsDataDaysTotalList;
	
	public StatsDataMinersDistDayList mStatsDataMonthList;
	public StatsDataMinersDistMonthList mStatsDataYearList;
	private ProgressBar mProgBar;
	public String mStatsDataWeekTotal;
	public String mStatsDataMonthTotal;
	public String mStatsDataYearTotal;
	
	private LinkedHashMap<Integer,String> xyValues; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_distance);

		mProgBar = (ProgressBar)findViewById(R.id.progbar);
		mProgBar.setVisibility(View.INVISIBLE);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		Utils.setTitleColor(this, getResources().getColor(R.color.white));
		
		//mLayoutChart1 = (LinearLayout)findViewById(R.id.report_chart_1);
		mLayoutChart2 = (LinearLayout)findViewById(R.id.report_chart_2);
		mLayoutChart3 = (LinearLayout)findViewById(R.id.report_chart_3);
		mDistanceTV = (TextView)findViewById(R.id.main_distance_tv);
		
		float distanceFromServer = 100;
		float distanceLocal = 50;
		float distanceTotal = distanceFromServer + distanceLocal;
		float distanceKMf = distanceTotal / 1000;
		float distanceMf = distanceTotal % 1000;

		
		mDistanceTV.setText(String.format("%.1f", distanceKMf));
		
		
		initData();
		
		//buildChartDist1();
		//buildChartDist2();
		//buildChartDist3();
		openChart();
		openChartTotal();
		//openChart2();
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}

	private void buildChartDist1(){
		 
		String[] chartTitles;
		String mainTitle = "Mined Km this week - " + mStatsDataWeekTotal + "";
		String xTitle, yTitle;
 		chartTitles = new String[] { "This Week"};
 		xTitle = "";
 		yTitle = "";
	 		
	    
	    List<double[]> values = new ArrayList<double[]>();


	    values.add(mStatsDataWeekList.getDataDoubleArray());
	    
	    int[] colors = new int[] { Color.WHITE };
	    PointStyle[] styles = new PointStyle[] { PointStyle.DIAMOND};
	    XYMultipleSeriesRenderer renderer = ChartUtil.buildRenderer(colors, styles);
	  
	    

	    ChartUtil.setChartSettings(renderer, mainTitle, xTitle, yTitle, 0.5,
	    		mStatsDataWeekList.getList().size() + 0.5, 0, mStatsDataWeekList.getMaxVal() + 5, Color.GRAY, Color.WHITE, Color.LTGRAY);

	    renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
	    renderer.getSeriesRendererAt(0).setDisplayChartValuesDistance(15);
	    renderer.getSeriesRendererAt(0).setChartValuesTextSize(20);
	    ((XYSeriesRenderer) renderer.getSeriesRendererAt(0)).setFillPoints(true);
	    ((XYSeriesRenderer) renderer.getSeriesRendererAt(0)).setLineWidth(4);
	    
	    renderer.setXLabels(0);
	    
	    List<double[]> x = new ArrayList<double[]>();
	    x.add(mStatsDataWeekList.getXDoubleArray());
	    
	    int i = 1;
	     
	    for(StatsDataMinersDistDay point : mStatsDataWeekList.getList()){
	    		renderer.addXTextLabel(i++, point.getDayInWeek(this));
	    }
	    
	    renderer.setYLabels(10);
	    renderer.setXLabelsAlign(Align.LEFT);
	    renderer.setYLabelsAlign(Align.LEFT);
	    renderer.setPanEnabled(true, false);
	     renderer.setZoomEnabled(false);
	    renderer.setZoomRate(1.1f);
	    renderer.setBarSpacing(0.5f);
	    
	   // XYMultipleSeriesDataset dataset = ChartUtil.buildDataset(chartTitles, x, values);
	   // mChartView1 = ChartFactory.getLineChartView(this, dataset, renderer);    
	 
	    mLayoutChart1.removeAllViews();
	    mLayoutChart1.addView(mChartView1, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));

}

	private void buildChartDist3(){
		 
		String[] chartTitles;
		String mainTitle = "Total of Km mined - " + mStatsDataYearTotal + "";
		String xTitle, yTitle;
 		chartTitles = new String[] { "Total"};
 		xTitle = "";
 		yTitle = "";
	 		
	    
	    List<double[]> values = new ArrayList<double[]>();


	    values.add(mStatsDataYearList.getDataDoubleArray());
	    
	    int[] colors = new int[] { Color.WHITE };
	    PointStyle[] styles = new PointStyle[] { PointStyle.DIAMOND};
	    XYMultipleSeriesRenderer renderer = ChartUtil.buildRenderer(colors, styles);
	  
	    

	    ChartUtil.setChartSettings(renderer, mainTitle, xTitle, yTitle, 0.5,
	    		mStatsDataYearList.getList().size() + 0.5, 0, mStatsDataYearList.getMaxVal() + 5, Color.GRAY, Color.WHITE, Color.LTGRAY);

	    renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
	    renderer.getSeriesRendererAt(0).setDisplayChartValuesDistance(15);
	    renderer.getSeriesRendererAt(0).setChartValuesTextSize(20);
	    ((XYSeriesRenderer) renderer.getSeriesRendererAt(0)).setFillPoints(true);
	    ((XYSeriesRenderer) renderer.getSeriesRendererAt(0)).setLineWidth(4);
	    
	    renderer.setXLabels(0);
	    
	    List<double[]> x = new ArrayList<double[]>();
	    x.add(mStatsDataYearList.getXDoubleArray());
	    
	    int i = 1;
	     
	    for(StatsDataMinersDistMonth point : mStatsDataYearList.getList()){
	    		renderer.addXTextLabel(i++, point.getMonth(this));
	    }
	    
	    renderer.setYLabels(10);
	    renderer.setXLabelsAlign(Align.LEFT);
	    renderer.setYLabelsAlign(Align.LEFT);
	    renderer.setPanEnabled(true, false);
	     renderer.setZoomEnabled(false);
	    renderer.setZoomRate(1.1f);
	    renderer.setBarSpacing(0.5f);
	    
	    XYMultipleSeriesDataset dataset = ChartUtil.buildDataset(chartTitles, x, values);
	    mChartView3 = ChartFactory.getLineChartView(this, dataset, renderer);    
	 
	    mLayoutChart3.removeAllViews();
	    mLayoutChart3.addView(mChartView3, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));

	}

	private JSONObject readJasonFile(){
        InputStream is;
        String s = "";
		try {
			is = getAssets().open("server_data.json");
	        int size = is.available();
	        byte[] buffer = new byte[size];
	        is.read(buffer);
	        is.close();
	        s = new String(buffer, "UTF-8");
	        return new JSONObject(s);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
			
	}
	
	public void initData() {
		JSONObject jsonReturnObj=null;
		JSONArray statsArray;
		try {
			jsonReturnObj = readJasonFile();
			statsArray = jsonReturnObj.getJSONArray("stats_data_week");
			Log.e("TAG", statsArray.toString());
			mStatsDataWeekList = new StatsDataMinersDistDayList(statsArray);
			
			jsonReturnObj = readJasonFile();
			statsArray = jsonReturnObj.getJSONArray("stats_data_days");
			Log.e("TAG", statsArray.toString());
			mStatsDataDaysList = new StatsDataMinersDistDayList(statsArray);
			
			jsonReturnObj = readJasonFile();
			statsArray = jsonReturnObj.getJSONArray("stats_data_total_days");
			Log.e("TAG", statsArray.toString());
			mStatsDataDaysTotalList = new StatsDataMinersDistDayList(statsArray);
			
			jsonReturnObj = readJasonFile();
			statsArray = jsonReturnObj.getJSONArray("stats_data_week");
			Log.e("TAG", statsArray.toString());
			mStatsDataWeekList = new StatsDataMinersDistDayList(statsArray);
			
			
			statsArray = jsonReturnObj.getJSONArray("stats_data_month");
			Log.e("TAG", statsArray.toString());
			mStatsDataMonthList = new StatsDataMinersDistDayList(statsArray);

			statsArray = jsonReturnObj.getJSONArray("stats_data_year");
			Log.e("TAG", statsArray.toString());
			mStatsDataYearList = new StatsDataMinersDistMonthList(statsArray);
			
			mStatsDataWeekTotal = jsonReturnObj.getString("stats_data_week_total");
			mStatsDataMonthTotal = jsonReturnObj.getString("stats_data_month_total");
			mStatsDataYearTotal = jsonReturnObj.getString("stats_data_year_total");
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	private void openChartTotal(){
		 
		String[] chartTitles;
 		chartTitles = new String[] { "Total"};
		List<double[]> values = new ArrayList<double[]>();
	    values.add(mStatsDataDaysTotalList.getDataDoubleArray());
	    int totalXlength = 0;
	    
    	
	    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	    
        List<double[]> xAxisValues = new ArrayList<double[]>();
	    xAxisValues.add(mStatsDataDaysTotalList.getXDoubleArray());
	    
	    for (int i = 0; i < chartTitles.length; i++) {
	    	XYSeries series = new XYSeries(chartTitles[i]);
	    	double[] xV = xAxisValues.get(i);
	    	double[] yV = values.get(i);
	    	int seriesLength = xV.length;
	      for (int k = 0; k < seriesLength; k++) {
	    	  System.out.println("LOG X is "+xV[k]+ " y is "+yV[k]);
	    	  series.add(xV[k]-1, yV[k]);
	      }
	      dataset.addSeries(series);
	    }
        
        XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
        incomeRenderer.setColor(Color.WHITE);
        incomeRenderer.setPointStyle(PointStyle.CIRCLE);
        incomeRenderer.setFillPoints(true);
        incomeRenderer.setLineWidth(2);
        incomeRenderer.setDisplayChartValues(true);
        incomeRenderer.setDisplayChartValuesDistance(15);
        incomeRenderer.setChartValuesTextSize(20);
        incomeRenderer.setLineWidth(4);
        incomeRenderer.setDisplayBoundingPoints(false); // for hiding the series when we scroll
    	
        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setChartTitle("Total Community Mined Km History");
        multiRenderer.setXTitle("Days");
        multiRenderer.setYTitle("kms");
        multiRenderer.setZoomButtonsVisible(true);
        multiRenderer.setBackgroundColor(0xf7f7f7);
        multiRenderer.setMarginsColor(0xf7f7f7);
        multiRenderer.setMargins(new int[] { 50, 60, 60, 30 });
        multiRenderer.setAxisTitleTextSize(20);
        multiRenderer.setChartTitleTextSize(25);
        multiRenderer.setLabelsTextSize(20);
        
        
        multiRenderer.setXLabelsAlign(Align.CENTER);
        multiRenderer.setYLabelsAlign(Align.RIGHT);
        multiRenderer.setPanEnabled(true, false); // scroll only x axis so true
        multiRenderer.setZoomEnabled(false,false);
        multiRenderer.setPointSize(8);  // increase the width of point size
        multiRenderer.setXLabelsPadding(10);
        
        
        xyValues = new LinkedHashMap<Integer,String>();
        
        for (int i = 0; i < chartTitles.length; i++) {
	    	double[] xV = xAxisValues.get(i);
	    	totalXlength =  xV.length;
	    	System.out.println("LOG len is "+totalXlength);
	    	for(int j=0;j<totalXlength;j++){
	    		multiRenderer.addXTextLabel(j+1, Utils.addDays(xV[j]));  
	    		xyValues.put(j+1, Utils.addDays(xV[j]));
	    	}    	
        }
        
    	multiRenderer.setXLabels(0);
	    multiRenderer.setShowAxes(false);
	    multiRenderer.setXAxisMin(0);
	    multiRenderer.setXAxisMax(10);
	    if(totalXlength < 10){
	    	multiRenderer.setXAxisMax(totalXlength);
	    }
    	multiRenderer.setPanEnabled(true);
    	multiRenderer.setPanLimits(new double [] {0,totalXlength+1,0,0});
    	
    	multiRenderer.setYAxisMin(Utils.getMinValueFromList(values));
    	multiRenderer.setYAxisMax(Utils.getMaxValueFromList(values));
	    multiRenderer.setAxesColor(Color.GRAY);
	    multiRenderer.setLabelsColor(Color.WHITE);
        multiRenderer.addSeriesRenderer(incomeRenderer);
 
        // Creating a Time Chart
        mChartView3 = (GraphicalView) ChartFactory.getTimeChartView(getBaseContext(), dataset, multiRenderer,"dd-MMM-yyyy");
 
        multiRenderer.setClickEnabled(true);
        multiRenderer.setSelectableBuffer(10);
 
        // Setting a click event listener for the graph
        mChartView3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	
                SeriesSelection seriesSelection = mChartView3.getCurrentSeriesAndPoint();
                double[] xy = mChartView3.toRealPoint(0);

                if (seriesSelection != null) {

                    //                  debug
                    Log.d("Punto", seriesSelection.getXValue() + ", " + seriesSelection.getValue());
                    //                  debug
                    Log.d("Chart point", "Chart element in series index " + seriesSelection.getSeriesIndex() + " data point index "
                            + seriesSelection.getPointIndex() + " was clicked" + " closest point value X=" + seriesSelection.getXValue()
                            + ", Y=" + seriesSelection.getValue() + " clicked point value X=" + (float) xy[0] + ", Y=" + (float) xy[1]);
                    
                    Toast.makeText(getBaseContext(),"" +xyValues.get((int)seriesSelection.getXValue()) + " , " + seriesSelection.getValue() +" km" ,Toast.LENGTH_SHORT).show();               }
            }
        });
 
            // Adding the Line Chart to the LinearLayout
        mLayoutChart3.addView(mChartView3);
    }
	private void openChart(){
		 
		String[] chartTitles;
 		chartTitles = new String[] { "Total"};
		List<double[]> values = new ArrayList<double[]>();
	    values.add(mStatsDataDaysList.getDataDoubleArray());
	    int totalXlength = 0;
	    
    	
	    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	    
        List<double[]> xAxisValues = new ArrayList<double[]>();
	    xAxisValues.add(mStatsDataDaysList.getXDoubleArray());
	    
	    for (int i = 0; i < chartTitles.length; i++) {
	    	XYSeries series = new XYSeries(chartTitles[i]);
	    	double[] xV = xAxisValues.get(i);
	    	double[] yV = values.get(i);
	    	int seriesLength = xV.length;
	      for (int k = 0; k < seriesLength; k++) {
	    	  System.out.println("LOG X is "+xV[k]+ " y is "+yV[k]);
	    	  series.add(xV[k]-1, yV[k]);
	      }
	      dataset.addSeries(series);
	    }
        
        XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
        incomeRenderer.setColor(Color.WHITE);
        incomeRenderer.setPointStyle(PointStyle.CIRCLE);
        incomeRenderer.setFillPoints(true);
        incomeRenderer.setLineWidth(2);
        incomeRenderer.setDisplayChartValues(true);
        incomeRenderer.setDisplayChartValuesDistance(15);
        incomeRenderer.setChartValuesTextSize(20);
        incomeRenderer.setLineWidth(4);
        incomeRenderer.setDisplayBoundingPoints(false); // for hiding the series when we scroll
    	
        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setChartTitle("My Mined Km History");
        multiRenderer.setXTitle("Days");
        multiRenderer.setYTitle("kms");
        multiRenderer.setZoomButtonsVisible(true);
        multiRenderer.setBackgroundColor(0xf7f7f7);
        multiRenderer.setMarginsColor(0xf7f7f7);
        multiRenderer.setMargins(new int[] { 50, 60, 60, 30 });
        multiRenderer.setAxisTitleTextSize(20);
        multiRenderer.setChartTitleTextSize(25);
        multiRenderer.setLabelsTextSize(20);
        
        multiRenderer.setXLabelsAlign(Align.CENTER);
        multiRenderer.setYLabelsAlign(Align.RIGHT);
        multiRenderer.setPanEnabled(true, false); // scroll only x axis so true
        multiRenderer.setZoomEnabled(false,false);
        multiRenderer.setPointSize(8);  // increase the width of point size
        multiRenderer.setXLabelsPadding(10);
        
        
        xyValues = new LinkedHashMap<Integer,String>();
        
        for (int i = 0; i < chartTitles.length; i++) {
	    	double[] xV = xAxisValues.get(i);
	    	totalXlength =  xV.length;
	    	System.out.println("LOG len is "+totalXlength);
	    	for(int j=0;j<totalXlength;j++){
	    		multiRenderer.addXTextLabel(j+1, Utils.addDays(xV[j]));  
	    		xyValues.put(j+1, Utils.addDays(xV[j]));
	    	}    	
        }
        
    	multiRenderer.setXLabels(0);
	    multiRenderer.setShowAxes(false);
	    multiRenderer.setXAxisMin(0);
	    multiRenderer.setXAxisMax(10);
	    if(totalXlength < 10){
	    	multiRenderer.setXAxisMax(totalXlength);
	    }
    	multiRenderer.setPanEnabled(true);
    	multiRenderer.setPanLimits(new double [] {0,totalXlength+1,0,0});
    	
    	multiRenderer.setYAxisMin(Utils.getMinValueFromList(values));
    	multiRenderer.setYAxisMax(Utils.getMaxValueFromList(values));
	    multiRenderer.setAxesColor(Color.GRAY);
	    multiRenderer.setLabelsColor(Color.WHITE);
        multiRenderer.addSeriesRenderer(incomeRenderer);
 
        // Creating a Time Chart
        mChartView2 = (GraphicalView) ChartFactory.getTimeChartView(getBaseContext(), dataset, multiRenderer,"dd-MMM-yyyy");
 
        multiRenderer.setClickEnabled(true);
        multiRenderer.setSelectableBuffer(10);
 
        // Setting a click event listener for the graph
        mChartView2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	
                SeriesSelection seriesSelection = mChartView2.getCurrentSeriesAndPoint();
                double[] xy = mChartView2.toRealPoint(0);

                if (seriesSelection != null) {

                    //                  debug
                    Log.d("Punto", seriesSelection.getXValue() + ", " + seriesSelection.getValue());
                    //                  debug
                    Log.d("Chart point", "Chart element in series index " + seriesSelection.getSeriesIndex() + " data point index "
                            + seriesSelection.getPointIndex() + " was clicked" + " closest point value X=" + seriesSelection.getXValue()
                            + ", Y=" + seriesSelection.getValue() + " clicked point value X=" + (float) xy[0] + ", Y=" + (float) xy[1]);
                    
                    Toast.makeText(getBaseContext(),"" +xyValues.get((int)seriesSelection.getXValue()) + " , " + seriesSelection.getValue() +" km" ,Toast.LENGTH_SHORT).show();               }
            }
        });
 
            // Adding the Line Chart to the LinearLayout
        mLayoutChart2.addView(mChartView2);
    }	
	
}
