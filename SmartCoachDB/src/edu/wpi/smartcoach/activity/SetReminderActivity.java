package edu.wpi.smartcoach.activity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
import android.widget.Toast;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.service.ReminderService;
import edu.wpi.smartcoach.view.SetReminderFragment;

public class SetReminderActivity extends FragmentActivity {
	

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
			//ReminderService.getInstance().addReminder(f.getSaveString());
		}
		
		prefs.edit().putStringSet("reminders", reminders).commit();
		
		//Toast.makeText(getBaseContext(), reminders.size()+" new reminder(s) set.", Toast.LENGTH_SHORT).show();
		finish();
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
