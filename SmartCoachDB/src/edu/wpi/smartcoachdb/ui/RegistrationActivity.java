package edu.wpi.smartcoachdb.ui;

import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
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
import edu.wpi.smartcoachdb.R;

public class RegistrationActivity extends Activity implements OnDateSetListener {

	private EditText first, last, address, occupation;
	private Spinner gender;
	private TextView birthday;

	private Button submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		first = (EditText) findViewById(R.id.firstName);
		last = (EditText) findViewById(R.id.lastName);
		address = (EditText) findViewById(R.id.address);
		occupation = (EditText) findViewById(R.id.occupation);

		gender = (Spinner) findViewById(R.id.gender);
		birthday = (TextView) findViewById(R.id.birthday);

		birthday.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Date now = new Date(System.currentTimeMillis());
				
				new DatePickerDialog(RegistrationActivity.this,
						RegistrationActivity.this, now.getYear(), now
								.getMonth(), now.getDay()).show();
			}
		});

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
		String lastName = first.getText().toString();
		String addressStr = first.getText().toString();
		String occupationStr = first.getText().toString();
 
		String genderStr = gender.getSelectedItem().toString();
		String bd = birthday.getText().toString();

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
		birthday.setText(new Date(year, monthOfYear, dayOfMonth).toString());
	}

}
