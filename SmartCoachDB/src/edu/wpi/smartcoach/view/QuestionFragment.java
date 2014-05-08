package edu.wpi.smartcoach.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.R;

public class QuestionFragment extends Fragment {
	
	private static final String TAG  = QuestionFragment.class.getSimpleName();

	private TextView questionView;
	private ListView optionListView;
	private QuestionOptionListAdapter adapter;
	
	private Button next;
	private QuestionResponseListener listener;
	private boolean isLast = false;
	
	private OptionQuestionModel question;

	public QuestionFragment() {}
	
	public QuestionFragment setQuestion(OptionQuestionModel q){
		question = q;
		return this;
	}
	
	public QuestionFragment setNextButtonListener(QuestionResponseListener ocl){		
		listener = ocl;
		return this;
	}
	
	public QuestionFragment setLast(boolean last){
		isLast = last;
		return this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_question, null);
		
		questionView = (TextView)rootView.findViewById(R.id.questionText);
		optionListView = (ListView)rootView.findViewById(R.id.optionList);
		
		next = (Button)rootView.findViewById(R.id.nextButton);
		next.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(question.hasMinimumResponses() && listener != null){
					listener.responseEntered(question);;
				}
			}
		});
		
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