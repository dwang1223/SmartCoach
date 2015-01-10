package edu.wpi.smartcoach.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.reminders.ReminderReciever;
import edu.wpi.smartcoach.service.PatientProfile;
import edu.wpi.smartcoach.util.Callback;
import edu.wpi.smartcoach.util.DatabaseHelper;
import edu.wpi.smartcoach.util.FitbitHelper;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

	private DatabaseHelper mDatabaseHelp = null;
	
	private View exerciseButton;
	private View profileButton;
	private View dietButton;
	private View remindButton;

    private View fitbitButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
		if (!PatientProfile.isInitialized(this)) {
			mDatabaseHelp.initializeFromDefault(this);
			startActivity(new Intent(this, RegistrationIntroActivity.class));
			finish();
		} else {
			exerciseButton = (View)findViewById(R.id.exerciseSolver);
			profileButton = (View)findViewById(R.id.profile);
			dietButton = (View)findViewById(R.id.dietSolver);
            remindButton = (View)findViewById(R.id.type);
            fitbitButton = (View)findViewById(R.id.fitbit);
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

			
			remindButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getBaseContext(), RemindersActivity.class);
					startActivity(intent);					
				}
			});

            if(FitbitHelper.isUserLoggedIn(this)){
                fitbitButton.setVisibility(View.GONE);
            }
            fitbitButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFitbitLogin();
                }
            });
		}
	}

    private void showFitbitLogin(){
        new AlertDialog.Builder(this)
                .setTitle("Fitbit Tracking")
                .setMessage("If you use Fitbit to track your activities & food, SmartCoach can help you keep on track by reminding you when you miss a day.")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FitbitHelper.doLogin(MainActivity.this, new Callback<Boolean>() {
                            @Override
                            public void callback(final Boolean success) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(success && FitbitHelper.isUserLoggedIn(MainActivity.this)){
                                            fitbitButton.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            fitbitButton.setVisibility(View.VISIBLE);
                                            Toast.makeText(getApplicationContext(), "Fitbit login failed, try again later", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
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
        if(id == R.id.checkin){
            startActivity(new Intent(this, CheckinActivity.class));
        } else if (id == R.id.fitbit_logout){
            FitbitHelper.logOut(this);
            Toast.makeText(this, "You have been logged out of Fitbit", Toast.LENGTH_SHORT).show();
            fitbitButton.setVisibility(View.VISIBLE);
        }

		//DatabaseHelper.getInstance(this).copyToStorage(this);
		return super.onOptionsItemSelected(item);
	}
}
