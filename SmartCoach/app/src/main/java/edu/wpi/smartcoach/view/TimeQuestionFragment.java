package edu.wpi.smartcoach.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.TimeQuestionModel;
import edu.wpi.smartcoach.util.Callback;

/**
 * A Question Fragment to prompt the user to input a time of day
 * @author Akshay
 *
 */
public class TimeQuestionFragment extends QuestionFragment {

	private static final String TAG = TimeQuestionFragment.class.getSimpleName();
	
	private TimeQuestionModel question;
	
	private Callback<QuestionModel> nextListener;
	private Callback<QuestionModel> backListener;
	private boolean backEnabled = false;
	
	private TextView questionView;
//	private NumberPicker hourPicker;
//	private NumberPicker minutePicker;
	
	private TimePicker timePicker;
	
	private Button nextButton;
	private Button backButton;
	
	private String[] minutes;
	
	private boolean isLast= false;
	
	public TimeQuestionFragment(){
		
	}
	
	@Override
	public TimeQuestionFragment setNextButtonListener(
			Callback<QuestionModel> listener) {
		this.nextListener = listener;
		return this;
	}
	
	public TimeQuestionFragment setQuestion(TimeQuestionModel tqm){
		this.question = tqm;
		return this;
	}
	
	public TimeQuestionFragment setBackButtonListener(Callback<QuestionModel> ocl){
		backListener = ocl;
		return this;
	}
	
	public void setBackEnabled(boolean enabled){
		backEnabled = enabled;
	}
	
	@Override
	public void setLast(boolean isLast){
		this.isLast = isLast;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View root = inflater.inflate(R.layout.fragment_question_time, null);
		
		questionView = (TextView)root.findViewById(R.id.questionText);
		
//		hourPicker = (NumberPicker)root.findViewById(R.id.hour);
//		minutePicker = (NumberPicker)root.findViewById(R.id.minute);
//		
//		hourPicker.setMinValue(0);
//		hourPicker.setMaxValue(23);
//		hourPicker.setValue(0);
//		
//		minutes = new String[12];
//		for(int i = 0; i < minutes.length; i++){
//			minutes[i] = String.format("%02d", i * (60/minutes.length));
//		}
//		minutePicker.setDisplayedValues(minutes);
//		minutePicker.setMinValue(0);
//		minutePicker.setMaxValue(minutes.length-1);
//		minutePicker.setValue(minutes.length/2);
		
		timePicker = (TimePicker)root.findViewById(R.id.numSolutions);

		nextButton = (Button)root.findViewById(R.id.nextButton);
		backButton = (Button)root.findViewById(R.id.backButton);
		
		if(isLast){
			nextButton.setText("Finish");
		}
		
		nextButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				//Log.d(TAG, String.format("time picked %d:%d", hourPicker.getValue(),Integer.parseInt(minutes[minutePicker.getValue()])));
				//question.setResponse(hourPicker.getValue(), Integer.parseInt(minutes[minutePicker.getValue()]));
				int hour = timePicker.getCurrentHour();
				int minute = timePicker.getCurrentMinute();
				Log.d(TAG, String.format("Time picked %d:%d", hour, minute));
				question.setResponse(hour, minute);
				nextListener.callback(question);
				
			}
		});
		
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(backListener != null){
					backListener.callback(question);
				}
			}
		});
		
		if(!backEnabled){
			backButton.setVisibility(View.INVISIBLE);
		}
		
		questionView.setText(question.getPrompt());
		
		return root;
	}
}
