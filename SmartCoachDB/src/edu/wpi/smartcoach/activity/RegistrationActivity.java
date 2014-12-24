package edu.wpi.smartcoach.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.service.PatientInfoService;
import edu.wpi.smartcoach.util.DatabaseHelper;

/**
 * Activity that allows the user to enter in basic profile information
 * @author Akshay
 */
public class RegistrationActivity extends Activity{

	private EditText firstNameField, lastNameField;
	private Spinner genderSpinner;
	
	private Button submit;
	
	private boolean editing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		setTitle("SmartCoach Registration");
		
		if(getIntent().getExtras() != null){
			editing = getIntent().getExtras().getBoolean("edit", false);
		} else {
			editing = false;
		}
		
		DatabaseHelper.getInstance(this);
		
		firstNameField = (EditText) findViewById(R.id.firstName);
		lastNameField = (EditText) findViewById(R.id.lastName);
		genderSpinner = (Spinner) findViewById(R.id.gender);

		
		if(editing){
			String firstName = PatientInfoService.getFirstName(this);
			String lastName = PatientInfoService.getLastName(this);
			String gender = PatientInfoService.getGender(this);
			
			firstNameField.setText(firstName);
			lastNameField.setText(lastName);
			genderSpinner.setSelection((gender.equals("Male")?0:1));
		}

		submit = (Button) findViewById(R.id.submit);

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				submit();
			}
		});
	}

	/**
	 * Save entered data and finish this activity
	 */
	private void submit() {
		String firstName = firstNameField.getText().toString();
		String lastName = lastNameField.getText().toString(); 
		String gender = genderSpinner.getSelectedItem().toString();

		PatientInfoService.setFirstName(firstName, this);
		PatientInfoService.setLastName(lastName, this);
		PatientInfoService.setGender(gender, this);
		
		if(!editing){
			Intent intent = new Intent(this, PatientMetricsActivity.class);
			startActivity(intent);
		}
		
		finish();
	}
}
