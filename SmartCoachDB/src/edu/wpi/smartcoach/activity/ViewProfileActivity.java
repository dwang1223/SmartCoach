package edu.wpi.smartcoach.activity;

import java.util.List;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;

import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.PatientMetrics;
import edu.wpi.smartcoach.model.PatientProfile;
import edu.wpi.smartcoach.service.PatientMetricsService;
import edu.wpi.smartcoach.service.PatientProfileService;
import edu.wpi.smartcoach.service.WeightService;

public class ViewProfileActivity extends Activity {
	
	private static final String TAG = ViewProfileActivity.class.getSimpleName();
	
	TextView name,gender, birthdate;
	TextView height,weight, goal;
	
	Button editUser;
	Button editMetrics;
	Button editPrefs;
	
	Button weighIn;
	
	LinearLayout graphContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_profile);

		setTitle("SmartCoach Profile");
		
		name = (TextView)findViewById(R.id.name);
		gender = (TextView)findViewById(R.id.gender);
		//birthdate = (TextView)findViewById(R.id.birthdate);
		

		height = (TextView)findViewById(R.id.height);
		weight = (TextView)findViewById(R.id.start_weight);
		goal = (TextView)findViewById(R.id.goal_weight);
		
		editUser = (Button)findViewById(R.id.editUser);
		editMetrics = (Button)findViewById(R.id.editMetrics);
		editPrefs = (Button)findViewById(R.id.editPrefs);
		weighIn = (Button)findViewById(R.id.weigh_in);
		
		weighIn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CheckinActivity.class);
				intent.putExtra("weighInOnly", true);
				startActivity(intent);
			}
		});
		

		PatientProfile profile = PatientProfileService.getInstance().getProfile();
		
		graphContainer = (LinearLayout)findViewById(R.id.graphContainer);
		
		
		editUser.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), RegistrationActivity.class);
				intent.putExtra("edit", true);
				startActivity(intent);
				
			}
		});
		
		editMetrics.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), PatientMetricsActivity.class);
				intent.putExtra("edit", true);
				startActivity(intent);
				
			}
		});
		
		editPrefs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
				intent.putExtra("edit", true);
				startActivity(intent);				
			}
		});
				
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		PatientProfile profile = PatientProfileService.getInstance().getProfile();
		PatientMetrics metrics = PatientMetricsService.getInstance().getMetrics();
		
		name.setText(profile.getFirstName() + " "+ profile.getLastName());
		gender.setText(profile.getGender());
		
		//Date bDate = profile.getPatientBirthday();
		//String bds = String.format("%d/%d/%02d", bDate.getMonth()+1, bDate.getDate(), (bDate.getYear())%100);
		//birthdate.setText(bds);
		
		String heightStr = String.format("%d\' %d\"", (int)metrics.getHeight()/12, (int)metrics.getHeight()%12);
		height.setText(heightStr);
		weight.setText((int)(metrics.getWeight())+" lbs");
		goal.setText((int)(metrics.getGoalWeight())+" lbs");
		
		GraphView graph = new LineGraphView(this, profile.getFirstName()+"'s weight");
		
		List<Entry<Long, Float>> weightData = WeightService.getInstance().getAllDataFromTable();
		Log.d(TAG, weightData.toString());
		GraphViewData gData[] = new GraphViewData[weightData.size()];
		for(Entry<Long, Float> we:weightData){
			gData[weightData.indexOf(we)] = new GraphViewData(we.getKey(), we.getValue());
		}
		
		
		graph.addSeries(new GraphViewSeries("", new GraphViewSeriesStyle(getResources().getColor(R.color.hologreen_color), 3), gData));
		graph.setCustomLabelFormatter(new CustomLabelFormatter() {
			
			@Override
			public String formatLabel(double value, boolean isX) {
				if(isX){
					return (String)DateFormat.format("MM-dd", (long)value);
				} else return null;
			}
		});
		
		graph.getGraphViewStyle().setTextSize(30);
		graphContainer.removeAllViews();
		graphContainer.addView(graph);
	}
}
