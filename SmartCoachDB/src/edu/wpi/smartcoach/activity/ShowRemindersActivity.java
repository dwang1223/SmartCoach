package edu.wpi.smartcoach.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.wpi.smartcoach.R;

public class ShowRemindersActivity extends Activity {

	private static final String TAG = ShowRemindersActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("SmartCoach Reminders");
		setContentView(R.layout.activity_show_reminders);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Set<String> rSet = prefs.getStringSet("reminders", new HashSet<String>());

		final ArrayList<String> reminders = new ArrayList<String>();
		reminders.addAll(rSet);
		
		ListView list = (ListView)findViewById(R.id.list);
		
		if(reminders.size() == 0){
			TextView emptyHeader = new TextView(this);
			emptyHeader.setText("No current reminders");
			list.addHeaderView(emptyHeader);
		}
		
		list.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = convertView;
				if(view == null){
					LayoutInflater inflater = LayoutInflater.from(getBaseContext());
					view = inflater.inflate(R.layout.item_reminder, null);					
				}
				
				SharedPreferences prefs;
				
				TextView reminder =(TextView) view.findViewById(R.id.reminder);
				TextView days = (TextView)view.findViewById(R.id.days);
				TextView time =(TextView) view.findViewById(R.id.time);
				
				String[] parts = getItem(position).split("#");
				reminder.setText(parts[0]);
				days.setText(parts[1].toUpperCase());
				time.setText(parts[2]);
				
				return view;
				
			}
			
			@Override
			public long getItemId(int position) {
				return position;
			}
			
			@Override
			public String getItem(int position) {
				return reminders.get(position);
			}
			
			@Override
			public int getCount() {
				return reminders.size();
			}
		});
		
		
	}
}
