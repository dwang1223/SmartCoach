package edu.wpi.smartcoach.view;

import java.util.ArrayList;
import java.util.Calendar;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import edu.wpi.smartcoach.reminders.Reminder;
import edu.wpi.smartcoach.service.ReminderService;

public class SetReminderFragment extends Fragment {

	private final String[] days = {
			"sun",
			"mon",
			"tue",
			"wed",
			"thu",
			"fri",
			"sat",};
	
	private final int[] calendarDays = {
			Calendar.SUNDAY,
			Calendar.MONDAY,
			Calendar.TUESDAY,
			Calendar.WEDNESDAY,
			Calendar.THURSDAY,
			Calendar.FRIDAY,
			Calendar.SATURDAY,
	};
	
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
		boolean dayChecked = false;
		for(int i = 0; i < days.length; i++){
			if(dayButtons[i].isChecked()){
				s += days[i]+",";
				dayChecked = true;
			}
		}
		if(dayChecked){
			s = s.substring(0, s.length()-1);
		}
		s+=" #";
		
		int hour = time.getCurrentHour();
		String ampm = hour>12?"PM":"AM";
		s += (time.getCurrentHour()%12)+":"+String.format("%02d",time.getCurrentMinute());
		return s;
	}
	
	public String getMessage(){
		return reminder;
	}
	
	public String getDays(){
		String s = "";
		boolean dayChecked = false;
		for(int i = 0; i < days.length; i++){
			if(dayButtons[i].isChecked()){
				s += days[i]+",";
				dayChecked = true;
			}
		}
		if(dayChecked){
			s = s.substring(0, s.length()-1);
		}
		
		return s;
	}
	
	public int getHour(){
		return time.getCurrentHour();
	}
	
	public int getMinute(){
		return time.getCurrentMinute();
	}
	
	public Integer[] getDayInts(){
		ArrayList<Integer> dayList = new ArrayList<Integer>();
		for(int i = 0; i < days.length; i++){
			if(dayButtons[i].isChecked()){
				dayList.add(calendarDays[i]);
			}
		}
		return dayList.toArray(new Integer[]{});
	}
	
	public Reminder createReminder(){
		return new Reminder(ReminderService.getInstance().getNewId(), reminder, getDays(), getHour(), getMinute());
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_set_reminder, null);

		TextView rt = (TextView) root.findViewById(R.id.reminderText);
		rt.setText("Set up reminder : " + reminder);
		Button finish = (Button) root.findViewById(R.id.finish);
		time = (TimePicker)root.findViewById(R.id.numSolutions);
		dayButtons = new ToggleButton[days.length];
		for(int i = 0; i < days.length;i++){
			dayButtons[i] = (ToggleButton)root.findViewById(getActivity().getResources().getIdentifier(days[i], "id", "edu.wpi.smartcoach"));
			final int index = i;
			final Drawable bg = dayButtons[i].getBackground();
			dayButtons[i].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(dayButtons[index].isChecked()){
						dayButtons[index].setTextColor(Color.WHITE);
						dayButtons[index].setBackgroundResource(R.drawable.hologreen_btn_default_holo_light);
					} else {
						dayButtons[index].setTextColor(Color.BLACK);
						dayButtons[index].setBackgroundDrawable(bg);
						
					}
					
				}
			});
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