package edu.wpi.smartcoach.activity;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.reminders.ReminderReciever;
import edu.wpi.smartcoach.util.DatabaseHelper;

public class MainActivity extends Activity {

	private DatabaseHelper mDatabaseHelp = null;
	
	private View exerciseButton;
	private View profileButton;
	private View dietButton;
	private View remindButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("SmartCoach");
		mDatabaseHelp = DatabaseHelper.getInstance(this);
		
		
		
		Calendar alarm = new GregorianCalendar();
        alarm.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

        alarm.set(Calendar.HOUR_OF_DAY, 20);
        alarm.set(Calendar.MINUTE, 0);

        long alarmTime = alarm.getTimeInMillis();
        //Also change the time to 24 hours.
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        
        Intent intent = new Intent(this, ReminderReciever.class);
        intent.putExtra("id", 9001);
        PendingIntent pending = PendingIntent.getBroadcast(this, 9001, intent, 0);
        
        manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, (long)(7*24*60*60*1000) , pending); 
	
		
		
		
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		// let it run just once
		if (!prefs.getBoolean("init", false)) {
			mDatabaseHelp.initializeFromDefault(this);
			startActivity(new Intent(this, RegistrationIntroActivity.class));
			finish();
		} else {
			exerciseButton = (View)findViewById(R.id.exerciseSolver);
			profileButton = (View)findViewById(R.id.profile);
			dietButton = (View)findViewById(R.id.dietSolver);
			exerciseButton.setOnClickListener(new OnClickListener() {				
				@Override 
				public void onClick(View v) {
					Intent intent = new Intent(getBaseContext(), ExerciseProblemActivity.class);
					startActivity(intent);
				}
			});
			
			profileButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getBaseContext(), ViewProfileActivity.class);
					startActivity(intent);					
				}
			});
			
			dietButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getBaseContext(), DietProblemActivity.class);
					startActivity(intent);					
				}
			});
			
			remindButton = (View)findViewById(R.id.type);
			
			remindButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getBaseContext(), RemindersActivity.class);
					startActivity(intent);					
				}
			});
			
			
		}
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
//		if (id == R.id.time) {
//			return true;
//		}
		startActivity(new Intent(this, CheckinActivity.class));
		
		DatabaseHelper.getInstance().copyToStorage(this);
				
		return super.onOptionsItemSelected(item);
	}
}
