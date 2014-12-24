package edu.wpi.smartcoach.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.service.PatientInfoService;
import edu.wpi.smartcoach.service.WeightService;

/**
 * Activity that allows the user to enter in and edit measurements such as height and weight
 * @author Akshay
 */
public class PatientMetricsActivity extends Activity {

	private EditText heightFtField, heightInField, startWeightField, goalWeightField;
	private Button submit;
	
	private boolean editing;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_patient_info);
				
		if(getIntent().getExtras() != null){
			editing = getIntent().getExtras().getBoolean("edit", false);
		} else {
			editing = false;
		}
		
		heightFtField = (EditText)findViewById(R.id.height_ft);
		heightInField = (EditText)findViewById(R.id.height_in);
		startWeightField = (EditText)findViewById(R.id.current);
		goalWeightField = (EditText)findViewById(R.id.goal);
		
		submit = (Button)findViewById(R.id.submit);
		
		submit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				submit();				
			}
		});
		
		if(editing){
			int heightInches = PatientInfoService.getHeight(this);
			float startWeight = PatientInfoService.getStartWeight(this);
			float goalWeight = PatientInfoService.getGoalWeight(this);
			heightFtField.setText((heightInches/12)+"");
			heightInField.setText((heightInches%12)+"");
			startWeightField.setText(startWeight+"");
			goalWeightField.setText(goalWeight+"");
			startWeightField.setEnabled(false);
		}
		
	}
	
	/**
	 * Save the data entered and end this activity
	 */
	public void submit(){
		try{
			int totalHeight = Integer.parseInt(heightFtField.getText().toString())*12 + Integer.parseInt(heightInField.getText().toString());
			int startWeight = Integer.parseInt(startWeightField.getText().toString());
			int goalWeight = (Integer.parseInt(goalWeightField.getText().toString()));
			
			PatientInfoService.setHeight(totalHeight, this);
			PatientInfoService.setStartWeight(startWeight, this);
			PatientInfoService.setGoalWeight(goalWeight, this);
			
			if(!editing){
				WeightService.getInstance().addWeight(System.currentTimeMillis(), startWeight, this);
			}
			
			//Date time = new Date();
			//PatientMetricsService.getInstance().initPatientMetrics(new PatientMetrics(totalHeight, currentWeight, time, goalWeight));
		} catch(Exception e){
			return;
		}
		if(!editing){
			Intent intent = new Intent(this, ProfileActivity.class);
			startActivity(intent);
		}
		finish();
	}
}
