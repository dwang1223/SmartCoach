package edu.wpi.smartcoach.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import edu.wpi.smartcoach.R;

public class RegistrationIntroActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_intro);
		
		Button next = (Button)findViewById(R.id.next);
		next.bringToFront();//make sure it is on top of the coach image
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(RegistrationIntroActivity.this, RegistrationActivity.class);
				startActivity(intent);
				finish();
			}
		});

	}
}
