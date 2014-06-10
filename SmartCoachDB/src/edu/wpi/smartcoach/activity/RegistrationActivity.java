package edu.wpi.smartcoach.activity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.PatientProfile;
import edu.wpi.smartcoach.service.PatientProfileService;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;

public class RegistrationActivity extends Activity implements OnDateSetListener {

	private EditText first, last;
	//private EditText address, occupation;
	private Spinner gender;
	private TextView birthday;
	
	private Button submit;
	
	private boolean editing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		setTitle("Registration");
		
		if(getIntent().getExtras() != null){
			editing = getIntent().getExtras().getBoolean("edit", false);
		} else {
			editing = false;
		}
		
		
		
		DatabaseHelper.getInstance(this);
		
		first = (EditText) findViewById(R.id.firstName);
		last = (EditText) findViewById(R.id.lastName);
//		address = (EditText) findViewById(R.id.address);
//		occupation = (EditText) findViewById(R.id.occupation);

		gender = (Spinner) findViewById(R.id.gender);
		birthday = (TextView) findViewById(R.id.birthday);

		birthday.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar c = Calendar.getInstance();
				new DatePickerDialog(RegistrationActivity.this,
						RegistrationActivity.this, 1970, 0, 1).show();
			}
		});
		
		if(editing){
			PatientProfile profile = PatientProfileService.getInstance().getProfile();
			first.setText(profile.getFirstName());
			last.setText(profile.getLastName());
			gender.setSelection(profile.getGender().equals("Male")?0:1);
			Date d = profile.getPatientBirthday();
			onDateSet(null, d.getYear(),d.getMonth(), d.getDate());
		}

		submit = (Button) findViewById(R.id.submit);

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				submit();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

	private void submit() {
		String firstName = first.getText().toString();
		String lastName = last.getText().toString();
		//String addressStr = address.getText().toString();
		//String occupationStr = occupation.getText().toString();
 
		String genderStr = gender.getSelectedItem().toString();
		
		Date bd = null;
		try{
			bd = new Date(Date.parse(birthday.getText().toString()));
		}catch(Exception e){
			return;
		}
		
		PatientProfileService.getInstance().initPatientProfile(new PatientProfile(firstName, lastName, genderStr, bd, "address", "occupation"));

		if(!editing){
			Intent intent = new Intent(this, PatientMetricsActivity.class);
			startActivity(intent);
		}
		finish();
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

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		GregorianCalendar calendar = new GregorianCalendar(year, monthOfYear+1, dayOfMonth);
		birthday.setText(String.format("%s/%s/%s", monthOfYear+1, dayOfMonth, year));
		
	}

}
