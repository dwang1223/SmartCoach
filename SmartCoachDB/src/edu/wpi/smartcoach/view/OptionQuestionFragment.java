package edu.wpi.smartcoach.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.OptionQuestionModel;

public class OptionQuestionFragment extends QuestionFragment {
	
	private static final String TAG  = OptionQuestionFragment.class.getSimpleName();

	private TextView questionView;
	private ListView optionListView;
	private QuestionOptionListAdapter adapter;
	
	private Button next;
	private Button back;
	private QuestionResponseListener nextListener;
	private QuestionResponseListener backListener;
	private boolean backEnabled = false;
	private boolean isLast = false;
	
	private OptionQuestionModel question;

	public OptionQuestionFragment() {}
	
	public OptionQuestionFragment setQuestion(OptionQuestionModel q){
		question = q;
		return this;
	}
	
	public OptionQuestionFragment setNextButtonListener(QuestionResponseListener ocl){		
		nextListener = ocl;
		return this;
	}
	
	public OptionQuestionFragment setBackButtonListener(QuestionResponseListener ocl){
		backListener = ocl;
		return this;
	}
	
	public void setBackEnabled(boolean enabled){
		backEnabled = enabled;
	}
	
	public void setLast(boolean last){
		isLast = last;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_question, null);
		
		questionView = (TextView)rootView.findViewById(R.id.questionText);
		optionListView = (ListView)rootView.findViewById(R.id.optionList);
		
		next = (Button)rootView.findViewById(R.id.nextButton);
		back = (Button)rootView.findViewById(R.id.backButton);
		
		next.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(question.hasMinimumResponses() && nextListener != null){
					nextListener.responseEntered(question);;
				}
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(backListener != null){
					backListener.responseEntered(question);
				}
			}
		});
		
		if(!backEnabled){
			back.setVisibility(View.INVISIBLE);
		}
		
		if(isLast){
			next.setText("Finish");
		}
		
		adapter = new QuestionOptionListAdapter(getActivity(), question);
		optionListView.setAdapter(adapter);
		
		
		questionView.setText(question.getPrompt());	
		
		adapter.notifyDataSetChanged();

		return rootView;
	}


	
}