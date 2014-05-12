package edu.wpi.smartcoach.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.TimeQuestionModel;

public class TimeQuestionFragment extends QuestionFragment {

	private static final String TAG = TimeQuestionFragment.class.getSimpleName();
	
	private TimeQuestionModel question;
	
	private QuestionResponseListener listener;
	
	private TextView questionView;
	private NumberPicker hourPicker;
	private NumberPicker minutePicker;
	private Button next;
	
	private String[] minutes;
	
	private boolean isLast= false;
	
	public TimeQuestionFragment(){
		
	}
	
	@Override
	public TimeQuestionFragment setNextButtonListener(
			QuestionResponseListener listener) {
		this.listener = listener;
		return this;
	}
	
	public TimeQuestionFragment setQuestion(TimeQuestionModel tqm){
		this.question = tqm;
		return this;
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
		
		hourPicker = (NumberPicker)root.findViewById(R.id.hour);
		minutePicker = (NumberPicker)root.findViewById(R.id.minute);
		
		hourPicker.setMinValue(0);
		hourPicker.setMaxValue(23);
		hourPicker.setValue(0);
		
		minutes = new String[12];
		for(int i = 0; i < minutes.length; i++){
			minutes[i] = String.format("%02d", i * (60/minutes.length));
		}
		minutePicker.setDisplayedValues(minutes);
		minutePicker.setMinValue(0);
		minutePicker.setMaxValue(minutes.length-1);
		minutePicker.setValue(minutes.length/2);
		


		next = (Button)root.findViewById(R.id.nextButton);
		
		if(isLast){
			next.setText("Finish");
		}
		
		next.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Log.d(TAG, String.format("time picked %d:%d", hourPicker.getValue(),
						Integer.parseInt(minutes[minutePicker.getValue()])));
				question.setResponse(hourPicker.getValue(),
						Integer.parseInt(minutes[minutePicker.getValue()]));
				listener.responseEntered(question);
				
			}
		});
		
		questionView.setText(question.getPrompt());
		
		return root;
	}
}
