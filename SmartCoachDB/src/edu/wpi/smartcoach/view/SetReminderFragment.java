package edu.wpi.smartcoach.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;
import edu.wpi.smartcoach.R;

public class SetReminderFragment extends Fragment {

	private final String[] days = {
			"sun",
			"mon",
			"tue",
			"wed",
			"thu",
			"fri",
			"sat",};
	
	int position;
	int total;
	String reminder;
	OnClickListener listener;
	private TimePicker time;
	private ToggleButton[] dayButtons;

	public void setPosition(int p, int total) {
		position = p;
		this.total = total;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	public void setListener(View.OnClickListener l) {
		listener = l;
	}
	
	public String getSaveString(){
		String s = reminder;
		s += "#";
		for(int i = 0; i < days.length; i++){
			if(dayButtons[i].isChecked()){
				s += days[i]+",";
			}
		}
		s = s.substring(0, s.length()-1);
		s+=" #";
		
		int hour = time.getCurrentHour();
		String ampm = hour>12?"PM":"AM";
		s += (time.getCurrentHour()%12)+":"+time.getCurrentMinute();
		return s;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_set_reminder, null);

		TextView rt = (TextView) root.findViewById(R.id.reminderText);
		rt.setText("Set up reminder : " + reminder);
		Button finish = (Button) root.findViewById(R.id.finish);
		time = (TimePicker)root.findViewById(R.id.time);
		dayButtons = new ToggleButton[days.length];
		for(int i = 0; i < days.length;i++){
			dayButtons[i] = (ToggleButton)root.findViewById(getActivity().getResources().getIdentifier(days[i], "id", "edu.wpi.smartcoach"));
		}
		
		if (position != total - 1) {
			finish.setText("Next");
		}
		finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listener != null)
					listener.onClick(v);
			}
		});
		return root;

	}

}