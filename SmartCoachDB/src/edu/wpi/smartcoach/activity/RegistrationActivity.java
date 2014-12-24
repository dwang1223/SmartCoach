package edu.wpi.smartcoach.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.service.PatientProfile;
import edu.wpi.smartcoach.service.WeightService;

/**
 * Activity that allows the user to enter in basic profile information
 * @author Akshay
 */
public class RegistrationActivity extends Activity implements TextWatcher {

	private static final String TAG = RegistrationActivity.class.getSimpleName();
	
	private EditText firstNameField, lastNameField;
	private EditText heightFtField, heightInField, startWeightField, goalWeightField;
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
		
		submit = (Button) findViewById(R.id.submit);

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				submit();
			}
		});
		
		firstNameField = (EditText) findViewById(R.id.firstName);
		lastNameField = (EditText) findViewById(R.id.lastName);
		genderSpinner = (Spinner) findViewById(R.id.gender);
		
		heightFtField = (EditText)findViewById(R.id.height_ft);
		heightInField = (EditText)findViewById(R.id.height_in);
		startWeightField = (EditText)findViewById(R.id.current);
		goalWeightField = (EditText)findViewById(R.id.goal);

		firstNameField.addTextChangedListener(this);
		startWeightField.addTextChangedListener(this);
				
		if(editing){ //if editing, get previous values
			String firstName = PatientProfile.getFirstName(this);
			String lastName = PatientProfile.getLastName(this);
			String gender = PatientProfile.getGender(this);
			
			int heightInches = PatientProfile.getHeight(this);
			float startWeight = PatientProfile.getStartWeight(this);
			float goalWeight = PatientProfile.getGoalWeight(this);
			
			firstNameField.setText(firstName);
			lastNameField.setText(lastName);
			genderSpinner.setSelection((gender.equals("Male")?0:1));

			if(heightInches > 0){
				heightFtField.setText((heightInches/12)+"");
				heightInField.setText((heightInches%12)+"");
			}
			startWeightField.setText(startWeight+"");
			
			if(goalWeight > 0){
				goalWeightField.setText(goalWeight+"");
			}
			startWeightField.setEnabled(false);
			
			submit.setEnabled(true);
			submit.setBackgroundResource(R.drawable.bg_card_highlight);
			
		} else {
			submit.setEnabled(false);
			submit.setBackgroundResource(R.drawable.bg_card_disable);	
		}
	}

	/**
	 * Save entered data and finish this activity
	 */
	private void submit() {
		String firstName = firstNameField.getText().toString();
		String lastName = lastNameField.getText().toString(); 
		String gender = genderSpinner.getSelectedItem().toString();

		float startWeight = Float.parseFloat(startWeightField.getText().toString());
		
		PatientProfile.setFirstName(firstName, this);
		PatientProfile.setLastName(lastName, this);
		PatientProfile.setGender(gender, this);
		
		PatientProfile.setStartWeight(startWeight, this);
		
		try {
			int totalHeight = Integer.parseInt(heightFtField.getText().toString())*12 + Integer.parseInt(heightInField.getText().toString());
			int goalWeight = (Integer.parseInt(goalWeightField.getText().toString()));

			PatientProfile.setHeight(totalHeight, this);
			PatientProfile.setGoalWeight(goalWeight, this);
		} catch (Exception e){
		}
		
		if(!editing){	
			WeightService.getInstance().addWeight(System.currentTimeMillis(), startWeight, this);
			
			Intent intent = new Intent(this, ProfileActivity.class);
			startActivity(intent);
		}
		
		finish();
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		boolean firstValid = false;
		boolean startValid = false;
		
		try {
			Float.parseFloat(startWeightField.getText().toString());
			startValid = true;
		} catch (Exception e){
			Log.d(TAG, "start not valid");
			startValid = false;
		}
		
		firstValid = !firstNameField.getText().toString().isEmpty();
				
		if(editing){
			startValid = true;
		}
		
		if(firstValid && startValid){
			submit.setEnabled(true);
			submit.setBackgroundResource(R.drawable.bg_card_highlight);
		} else {
			submit.setEnabled(false);
			submit.setBackgroundResource(R.drawable.bg_card_disable);			
		}
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}


}
