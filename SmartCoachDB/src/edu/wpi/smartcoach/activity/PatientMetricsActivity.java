package edu.wpi.smartcoach.activity;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.PatientMetrics;
import edu.wpi.smartcoach.service.PatientMetricsService;

public class PatientMetricsActivity extends Activity {

	private EditText heightFt, heightIn, current, goal;
	private Button submit;
	
	private boolean editing;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_patient_info);
		
		setTitle("Basic Information");
		
		if(getIntent().getExtras() != null){
			editing = getIntent().getExtras().getBoolean("edit", false);
		} else {
			editing = false;
		}
		
		heightFt = (EditText)findViewById(R.id.height_ft);
		heightIn = (EditText)findViewById(R.id.height_in);
		current = (EditText)findViewById(R.id.current);
		goal = (EditText)findViewById(R.id.goal);
		
		submit = (Button)findViewById(R.id.submit);
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				submit();
				
			}
		});
		
		if(editing){
			PatientMetrics metrics = PatientMetricsService.getInstance().getMetrics();
			heightFt.setText((int)metrics.getHeight()/12 + "");
			heightIn.setText((int)metrics.getHeight()%12 + "");
			current.setText((int)metrics.getWeight()+"");
			goal.setText(((int)metrics.getGoalWeight())+"");
		}
		
	}
	
	public void submit(){

		try{
			int totalHeight = Integer.parseInt(heightFt.getText().toString())*12 + Integer.parseInt(heightIn.getText().toString());
			int currentWeight = Integer.parseInt(current.getText().toString());
			int goalWeight = (Integer.parseInt(goal.getText().toString()));
			
			Date time = new Date();
			PatientMetricsService.getInstance().initPatientMetrics(new PatientMetrics(totalHeight, currentWeight, time, goalWeight));
		} catch(Exception e){
			return;
		}
		if(!editing){
			Intent intent = new Intent(this, ProfileActivity.class);
			startActivity(intent);
		}
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.time) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
