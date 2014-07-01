package edu.wpi.smartcoach.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.exercise.ExerciseState;

public class WeekGridListAdapter extends ArrayAdapter<ExerciseState>{

	interface ResponseChangedListener{
		public void responseChanged();		
	}
	
	private ResponseChangedListener listener;
	
	public WeekGridListAdapter(Context context) {
		super(context, R.layout.layout_option_week_grid);
	}
	
	
	public void setResponseChangedListener(ResponseChangedListener rl){
		listener = rl;
		if(listener != null){
			listener.responseChanged();
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		View view = convertView;
		
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.layout_option_week_grid, null);
		}
		
		TextView exerciseName = (TextView)view.findViewById(R.id.exercise);
		CheckBox weekdayCheck = (CheckBox)view.findViewById(R.id.weekdays);
		CheckBox weekendCheck = (CheckBox)view.findViewById(R.id.weekends);
		
		final ExerciseState item = getItem(position);
		
		exerciseName.setText(item.getExercise().getName());
		
		weekdayCheck.setChecked(item.isOnWeekdays());
		weekendCheck.setChecked(item.isOnWeekends());
		
		weekdayCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				item.setOnWeekdays(isChecked);
				if(listener != null){
					listener.responseChanged();
				}
				
			}
		});
		
		weekendCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				item.setOnWeekends(isChecked);	
				if(listener != null){
					listener.responseChanged();
				}
			}
		});
		
		return view;
	}

}
