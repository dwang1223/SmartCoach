package edu.wpi.smartcoach.activity;

import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.R.id;
import edu.wpi.smartcoach.R.layout;
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

public class ShowRemindersActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Reminders");
		setContentView(R.layout.activity_show_reminders);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String reminders = prefs.getString("reminders", "");
		
		final String[] reminder = reminders.split("\\|");
		final boolean empty;
		if(reminder[0].split("#").length == 0){
			empty = true;
		} else {
			empty = false;
		}
		
		ListView list = (ListView)findViewById(R.id.list);
		
		list.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = convertView;
				if(view == null){
					LayoutInflater inflater = LayoutInflater.from(getBaseContext());
					view = inflater.inflate(R.layout.item_reminder, null);					
				}
				
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
				return reminder[position];
			}
			
			@Override
			public int getCount() {
				if(empty){
					return 0;
				}
				return reminder.length;
			}
		});
		
		
	}
}
