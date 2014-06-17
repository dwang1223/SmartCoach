package edu.wpi.smartcoach.activity;

import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.R.id;
import edu.wpi.smartcoach.R.layout;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class SetReminderActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Set a reminder");
		setContentView(R.layout.fragment_set_reminder);
		
		final String reminder = getIntent().getExtras().getString("reminder");
		
		TextView rt = (TextView)findViewById(R.id.reminderText);
		rt.setText("Set up reminder : "+reminder);
		
		findViewById(R.id.finish).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "Reminder set : "+reminder, Toast.LENGTH_SHORT).show();
				finish();
				
			}
		});
		
	}
}
