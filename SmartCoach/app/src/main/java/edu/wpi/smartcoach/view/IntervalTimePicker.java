package edu.wpi.smartcoach.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TimePicker;

public class IntervalTimePicker extends TimePicker {
	

	private static final int TIME_PICKER_INTERVAL=15;
	

	private boolean mIgnoreEvent=false;
	
	public IntervalTimePicker(Context c, AttributeSet attrs){
		super(c, attrs);

		TimePicker.OnTimeChangedListener mTimePickerListener=new TimePicker.OnTimeChangedListener(){
		    public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute){
		        if (mIgnoreEvent)
		            return;
		        if (minute%TIME_PICKER_INTERVAL!=0){
		            int minuteFloor=minute-(minute%TIME_PICKER_INTERVAL);
		            minute=minuteFloor + (minute==minuteFloor+1 ? TIME_PICKER_INTERVAL : 0);
		            if (minute==60)
		                minute=0;
		            mIgnoreEvent=true;
		            timePicker.setCurrentMinute(minute);
		            
		            mIgnoreEvent=false;
		        }

		    }
		};
		
		setOnTimeChangedListener(mTimePickerListener);
	}

}
