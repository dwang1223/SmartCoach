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
import edu.wpi.smartcoach.model.PatientInfo;
import edu.wpi.smartcoach.service.PatientInfoService;

public class PatientInfoActivity extends Activity {

	private EditText heightFt, heightIn, current, goal;
	private Button submit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_info);

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
		
	}
	
	public void submit(){
		int totalHeight = Integer.parseInt(heightFt.getText().toString())*12 + Integer.parseInt(heightIn.getText().toString());
		int currentWeight = Integer.parseInt(current.getText().toString());
		int goalWeight = (Integer.parseInt(goal.getText().toString()));
		
		int age = 0; //TODO: get age from database
		Date time = new Date();
		
		PatientInfoService.getInstance().initPatientInfo(new PatientInfo(totalHeight, currentWeight, age, time, goalWeight));
		
		Intent intent = new Intent(this, ProfileActivity.class);
		startActivity(intent);
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
