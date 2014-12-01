package edu.wpi.smartcoach.activity;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.TimeQuestionModel;
import edu.wpi.smartcoach.reminders.Reminder;
import edu.wpi.smartcoach.service.ReminderService;
import edu.wpi.smartcoach.view.TimeQuestionFragment;

public class RemindersFragment extends Fragment {

	private static final String TAG = RemindersFragment.class.getSimpleName();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_show_reminders, null);
		
		List<Reminder> reminders = ReminderService.getInstance().getAllDataFromTable();
		
		ListView list = (ListView)view.findViewById(R.id.list);
		
		if(reminders.size() > 0){
			TextView none = (TextView)view.findViewById(R.id.none);
			none.setVisibility(View.GONE);
		}
		
		ArrayAdapter<Reminder> adapter = new ArrayAdapter<Reminder>(getActivity(), R.layout.item_reminder) {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = convertView;
				if(view == null){
					LayoutInflater inflater = LayoutInflater.from(getActivity());
					view = inflater.inflate(R.layout.item_reminder, null);					
				}
				
				
				TextView reminder =(TextView) view.findViewById(R.id.type);
				TextView days = (TextView)view.findViewById(R.id.date);
				TextView time =(TextView) view.findViewById(R.id.numSolutions);
				
				Reminder item = getItem(position);
				reminder.setText(item.getMessage());
				days.setText(item.getDays());
				time.setText(TimeQuestionModel.formatTime(item.getHour()*60+item.getMinute()));
				
				return view;
				
			}			
		};
		adapter.addAll(reminders);
		list.setAdapter(adapter);
		
		return view;
		
	}
}
