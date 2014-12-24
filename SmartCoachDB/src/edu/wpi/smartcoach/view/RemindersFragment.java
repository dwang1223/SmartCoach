package edu.wpi.smartcoach.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.TimeQuestionModel;
import edu.wpi.smartcoach.reminders.Reminder;
import edu.wpi.smartcoach.service.ReminderService;

public class RemindersFragment extends Fragment {

	private static final String TAG = RemindersFragment.class.getSimpleName();

	private TextView none;
	private ArrayAdapter<Reminder> adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_show_reminders, null);
		
//		List<Reminder> reminders = ReminderService.getInstance().getAllDataFromTable();
		
		Button checkin = (Button)view.findViewById(R.id.checkin);
		ListView list = (ListView)view.findViewById(R.id.list);
		

		none = (TextView)view.findViewById(R.id.none);
		
//		if(reminders.size() > 0){
//			none.setVisibility(View.GONE);
//		}
		
		adapter = new ArrayAdapter<Reminder>(getActivity(), R.layout.item_reminder) {
			
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
				days.setText(item.getDays().toUpperCase());
				time.setText(TimeQuestionModel.formatTime(item.getHour()*60+item.getMinute()));
				
				return view;
				
			}			
		};
//		adapter.addAll(reminders);
		list.setAdapter(adapter);
		
		return view;
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		adapter.clear();
		adapter.addAll(ReminderService.getInstance().getAllDataFromTable(getActivity()));
		adapter.notifyDataSetChanged();
		
		if(adapter.getCount() > 0){
			none.setVisibility(View.GONE);
		} else {
			none.setVisibility(View.VISIBLE);
		}
		
	}
}
