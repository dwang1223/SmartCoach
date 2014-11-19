package edu.wpi.smartcoach.activity;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.PatientMetrics;
import edu.wpi.smartcoach.model.PatientProfile;
import edu.wpi.smartcoach.service.PatientMetricsService;
import edu.wpi.smartcoach.service.PatientProfileService;

public class ViewProfileActivity extends Activity {

	TextView name,gender, birthdate;
	TextView height,weight, goal;
	
	Button editUser;
	Button editMetrics;
	Button editPrefs;
	
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
	}
}
