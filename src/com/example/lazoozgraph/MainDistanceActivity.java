package com.example.lazoozgraph;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.BarChart.Type;
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
import com.example.lazoozgraph.utils.Utils;




import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint.Align;
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
	public StatsDataMinersDistDayList mStatsDataMonthList;
	public StatsDataMinersDistMonthList mStatsDataYearList;
	private ProgressBar mProgBar;
	public String mStatsDataWeekTotal;
	public String mStatsDataMonthTotal;
	public String mStatsDataYearTotal;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_main_distance);

		mProgBar = (ProgressBar)findViewById(R.id.progbar);
		mProgBar.setVisibility(View.INVISIBLE);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		Utils.setTitleColor(this, getResources().getColor(R.color.white));
		
		mLayoutChart1 = (LinearLayout)findViewById(R.id.report_chart_1);
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
		
		buildChartDist1();
		buildChartDist2();
		buildChartDist3();
		
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
	    
	    XYMultipleSeriesDataset dataset = ChartUtil.buildDataset(chartTitles, x, values);
	    mChartView1 = ChartFactory.getLineChartView(this, dataset, renderer);    
	 
	    mLayoutChart1.removeAllViews();
	    mLayoutChart1.addView(mChartView1, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));

}

	private void buildChartDist2(){
		 
		String[] chartTitles;
		String mainTitle = "Mined Km this month - " + mStatsDataMonthTotal + "";
		String xTitle, yTitle;
 		chartTitles = new String[] { "This Month"};
 		xTitle = "";
 		yTitle = "";
	 		
	    
	    List<double[]> values = new ArrayList<double[]>();


	    values.add(mStatsDataMonthList.getDataDoubleArray());
	    
	    int[] colors = new int[] { Color.WHITE };
	    PointStyle[] styles = new PointStyle[] { PointStyle.DIAMOND};
	    XYMultipleSeriesRenderer renderer = ChartUtil.buildRenderer(colors, styles);
	  
	    

	    ChartUtil.setChartSettings(renderer, mainTitle, xTitle, yTitle, 0.5,
	    		mStatsDataMonthList.getList().size() + 0.5, 0, mStatsDataMonthList.getMaxVal() + 5, Color.GRAY, Color.WHITE, Color.LTGRAY);

	    renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
	    renderer.getSeriesRendererAt(0).setDisplayChartValuesDistance(15);
	    renderer.getSeriesRendererAt(0).setChartValuesTextSize(20);
	    ((XYSeriesRenderer) renderer.getSeriesRendererAt(0)).setFillPoints(true);
	    ((XYSeriesRenderer) renderer.getSeriesRendererAt(0)).setLineWidth(4);
	    
	    renderer.setXLabels(0);
	    
	    List<double[]> x = new ArrayList<double[]>();
	    x.add(mStatsDataMonthList.getXDoubleArray());
	    
	    int i = 1;
	     
	    for(StatsDataMinersDistDay point : mStatsDataMonthList.getList()){
	    		renderer.addXTextLabel(i++, point.getDayInMonth(this));
	    }
	    
	    renderer.setYLabels(10);
	    renderer.setXLabelsAlign(Align.LEFT);
	    renderer.setYLabelsAlign(Align.LEFT);
	    renderer.setPanEnabled(true, false);
	     renderer.setZoomEnabled(false);
	    renderer.setZoomRate(1.1f);
	    renderer.setBarSpacing(0.5f);
	    
	    XYMultipleSeriesDataset dataset = ChartUtil.buildDataset(chartTitles, x, values);
	    mChartView2 = ChartFactory.getLineChartView(this, dataset, renderer);    
	 
	    mLayoutChart2.removeAllViews();
	    mLayoutChart2.addView(mChartView2, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));

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
		
	
	
	
	
	
}
