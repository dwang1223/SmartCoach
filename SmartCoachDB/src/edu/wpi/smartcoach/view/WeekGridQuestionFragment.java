package edu.wpi.smartcoach.view;

import java.util.ArrayList;

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
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.view.WeekGridListAdapter.ResponseChangedListener;

public class WeekGridQuestionFragment extends QuestionFragment implements ResponseChangedListener {
	
	private static final String TAG  = OptionQuestionFragment.class.getSimpleName();

	private TextView questionView;
	private ListView optionListView;
	private WeekGridListAdapter adapter;
	
	private TextView instructions;
	
	private Button next;
	private Button back;
	private QuestionResponseListener nextListener;
	private QuestionResponseListener backListener;
	private boolean backEnabled = false;
	private boolean isLast = false;
	
	private OptionQuestionModel question;

	
	public WeekGridQuestionFragment setQuestion(OptionQuestionModel q){
		question = q;
		return this;
	}
	
	public WeekGridQuestionFragment setNextButtonListener(QuestionResponseListener ocl){		
		nextListener = ocl;
		return this;
	}
	
	public WeekGridQuestionFragment setBackButtonListener(QuestionResponseListener ocl){
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
		
		instructions = (TextView)rootView.findViewById(R.id.instructions);
		
		next = (Button)rootView.findViewById(R.id.nextButton);
		back = (Button)rootView.findViewById(R.id.backButton);
		
		Button suggest = (Button)rootView.findViewById(R.id.suggest);
		Button comm = (Button)rootView.findViewById(R.id.community);
		

			suggest.setVisibility(View.GONE);
			comm.setVisibility(View.GONE);
		
		
		next.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(nextListener != null){ 
					nextListener.responseEntered(question);
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
		
		instructions.setText("Pick at least one per exercise:");
		
		if(!backEnabled){
			back.setVisibility(View.INVISIBLE);
		}
		
		if(isLast){
			next.setText("Finish");
		}
		
		ArrayList<ExerciseState> states = new ArrayList<ExerciseState>();
		
		for(Option op:question.getOptions()){
			states.add((ExerciseState)op.getValue());
		}
		
		adapter = new WeekGridListAdapter(getActivity());
		adapter.addAll(states);
		optionListView.setAdapter(adapter);
		
		
		questionView.setText(question.getPrompt());	
		
		adapter.setResponseChangedListener(this);
		
		adapter.notifyDataSetChanged();

		return rootView;
	}

	@Override
	public void responseChanged() {
		boolean hasSelections = true;
		for(int i  = 0; i < adapter.getCount(); i++){
			ExerciseState state = adapter.getItem(i);
			if(!(state.isOnWeekdays() || state.isOnWeekends())){
				hasSelections = false;
			}
		}
		
		next.setEnabled(hasSelections);
		if(hasSelections){
			next.setBackgroundResource(R.drawable.bg_card_button);
		} else {
			next.setBackgroundResource(R.drawable.bg_card_disable);
		}
	}


	
}