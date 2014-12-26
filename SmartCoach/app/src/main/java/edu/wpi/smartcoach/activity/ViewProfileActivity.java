package edu.wpi.smartcoach.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.List;
import java.util.Map.Entry;

import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.service.PatientProfile;
import edu.wpi.smartcoach.service.WeightService;

/**
 * Activity where the user can view and edit their profile information and perform weight tracking
 * @author Akshay
 */
public class ViewProfileActivity extends Activity {
	
	private static final String TAG = ViewProfileActivity.class.getSimpleName();
	
	TextView nameView,genderView;
	TextView heightView,startWeightView, goalWeightView;
	
	Button editUserButton;
	Button editPrefsButton;
	
	Button weighInButton;
	
	LinearLayout graphContainerLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_profile);

		setTitle("SmartCoach Profile");
		
		nameView = (TextView)findViewById(R.id.name);
		genderView = (TextView)findViewById(R.id.gender);
		

		heightView = (TextView)findViewById(R.id.goalLbl);
		startWeightView = (TextView)findViewById(R.id.firstLbl);
		goalWeightView = (TextView)findViewById(R.id.goal_weight);
		
		editUserButton = (Button)findViewById(R.id.editUser);
		editPrefsButton = (Button)findViewById(R.id.editPrefs);
		weighInButton = (Button)findViewById(R.id.weigh_in);
		
		weighInButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CheckinActivity.class);
				intent.putExtra("weighInOnly", true);
				startActivity(intent);
			}
		});
				
		graphContainerLayout = (LinearLayout)findViewById(R.id.graphContainer);
		
		
		editUserButton.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), RegistrationActivity.class);
				intent.putExtra("edit", true);
				startActivity(intent);
				
			}
		});
		
		editPrefsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
				intent.putExtra("edit", true);
				startActivity(intent);				
			}
		});
				
	}
	
	@SuppressLint("DefaultLocale")
	@Override
	protected void onResume(){
		super.onResume();
		
		//get info from profile
		String firstName = PatientProfile.getFirstName(this);
		String lastName = PatientProfile.getLastName(this);
		String gender = PatientProfile.getGender(this);
		
		int height = PatientProfile.getHeight(this);
		String heightStr = String.format("%d\' %d\"", height/12, height%12);
		
		if(height < 0){
			heightStr = "???";
		}
		
		float startWeight = PatientProfile.getStartWeight(this);
		float goalWeight = PatientProfile.getGoalWeight(this);
		
		//set text fields
		nameView.setText(firstName + " "+ lastName);
		genderView.setText(gender);
		
		heightView.setText(heightStr);
		startWeightView.setText(startWeight+" lbs");
		
		if(goalWeight > 0){
			goalWeightView.setText(goalWeight+" lbs");
		} else {
			goalWeightView.setText("??? lbs");
		}
		
		//set up weight graph
		GraphView graph = new LineGraphView(this, firstName+"'s weight");
		
		//set graph boundaries to 10 pounds above & below start and goal weight
		graph.setManualYAxisBounds(startWeight+10, goalWeight-10);
		
		List<Entry<Long, Float>> weightData = WeightService.getInstance().getAllDataFromTable(this);
		Log.d(TAG, weightData.toString());
		GraphViewData gData[] = new GraphViewData[weightData.size()];
		for(Entry<Long, Float> we:weightData){
			gData[weightData.indexOf(we)] = new GraphViewData(we.getKey(), we.getValue());
		}
				
		graph.addSeries(new GraphViewSeries("", new GraphViewSeriesStyle(getResources().getColor(R.color.hologreen_color), 3), gData));
		
		//format horizontal axis dates from millisecond time to month/day
		graph.setCustomLabelFormatter(new CustomLabelFormatter() {
			
			@Override
			public String formatLabel(double value, boolean isX) {
				if(isX){
					return (String)DateFormat.format("MM-dd", (long)value);
				} else return null;
			}
		});
		
		graph.getGraphViewStyle().setTextSize(30);
		graphContainerLayout.removeAllViews();
		graphContainerLayout.addView(graph);
	}
}
