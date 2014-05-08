package edu.wpi.smartcoach.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.TimeQuestionModel;

public class TimeQuestionFragment extends Fragment {

	private static final String TAG = TimeQuestionFragment.class.getSimpleName();
	
	private TimeQuestionModel question;
	
	private QuestionResponseListener listener;
	
	private TextView questionView;
	private TimePicker picker;
	private Button next;
	
	public TimeQuestionFragment(){
		
	}
	
	public void setQuestion(TimeQuestionModel tqm){
		this.question = tqm;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View root = inflater.inflate(R.layout.fragment_question_time, null);
		
		questionView = (TextView)root.findViewById(R.id.questionText);
		
		picker = (TimePicker)root.findViewById(R.id.picker);
		picker.setIs24HourView(false);
		picker.setCurrentHour(0);
		picker.setCurrentMinute(30);

		next = (Button)root.findViewById(R.id.nextButton);
		
		next.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				question.setResponse(picker.getCurrentHour(), picker.getCurrentMinute());
				listener.responseEntered(question);
				
			}
		});
		
		questionView.setText(question.getPrompt());
		
		return root;
	}

}
