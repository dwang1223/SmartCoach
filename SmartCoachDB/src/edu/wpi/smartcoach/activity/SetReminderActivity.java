package edu.wpi.smartcoach.activity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.reminders.Reminder;
import edu.wpi.smartcoach.reminders.ReminderReciever;
import edu.wpi.smartcoach.service.ReminderService;
import edu.wpi.smartcoach.view.SetReminderFragment;

public class SetReminderActivity extends FragmentActivity {
	
	private static final String TAG = SetReminderActivity.class.getSimpleName();

	ReminderPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;

	String[] reminders;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		setTitle("SmartCoach Set Reminders");
		
		reminders = getIntent().getExtras().getStringArray("reminder");
		
		for(int i = 0; i < reminders.length; i++){
			if(reminders[i] == null){
				reminders[i] = "";
			}
		}
		
		mSectionsPagerAdapter = new ReminderPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}
	
	private void doFinish(){
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

		 Set<String> reminders = prefs.getStringSet("reminders", new HashSet<String>());		
		
		for(int i = 0; i < mSectionsPagerAdapter.getCount(); i++){
			SetReminderFragment f = (SetReminderFragment)mSectionsPagerAdapter.getItem(i);
			reminders.add(f.getSaveString());
			
			Reminder r = f.createReminder();
			Integer[] reminderDays = f.getDayInts();
			ReminderService.getInstance().addReminder(r);
			
			for(Integer d:reminderDays){
				setAlarm(d, r.getHour(), r.getMinute(), r.getId());
			}
			
			
			
			//Log.d(TAG, ReminderService.getInstance().getAllDataFromTable().toString());
			
			//ReminderService.getInstance().addReminder(f.getSaveString());
		}
		
		prefs.edit().putStringSet("reminders", reminders).commit();
		
		//Toast.makeText(getBaseContext(), reminders.size()+" new reminder(s) set.", Toast.LENGTH_SHORT).show();
		finish();
	}
	
	public void setAlarm(int dayOfWeek, int hour, int minute, int id) {
		Calendar alarm = new GregorianCalendar();
        alarm.set(Calendar.DAY_OF_WEEK, dayOfWeek);

        alarm.set(Calendar.HOUR_OF_DAY, hour);
        alarm.set(Calendar.MINUTE, minute);
        alarm.set(Calendar.SECOND, 0);

        long alarmTime = alarm.getTimeInMillis();
        //Also change the time to 24 hours.
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        
        Intent intent = new Intent(this, ReminderReciever.class);
        intent.putExtra("id", id);
        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, 0);
        
        manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, (long)(7*24*60*60*1000) , pending); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	public class ReminderPagerAdapter extends FragmentPagerAdapter {
		
		private HashMap<Integer, SetReminderFragment> frags;

		public ReminderPagerAdapter(FragmentManager fm) {
			super(fm);
			frags = new HashMap<Integer, SetReminderFragment>();
		}

		@Override
		public Fragment getItem(final int position) {
			SetReminderFragment f;
			if(!frags.containsKey(position)){
				f = new SetReminderFragment();
				f.setPosition(position, reminders.length);
				f.setReminder(reminders[position]);
				f.setListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(position != reminders.length-1){
							mViewPager.setCurrentItem(position+1);
						}else  {
							doFinish();
						}
						
					}
				});
				frags.put(position, f);
			} else {
				f = frags.get(position);
			}
			
			return f;
		}

		@Override
		public int getCount() {
			return SetReminderActivity.this.reminders.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return SetReminderActivity.this.reminders[position];
		}
	}

	


}
