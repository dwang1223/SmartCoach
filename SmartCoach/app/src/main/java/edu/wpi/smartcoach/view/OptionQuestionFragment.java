package edu.wpi.smartcoach.view;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.view.OptionListAdapter.ResponseChangedListener;

/**
 * Fragment that displays an OptionQuestionModel
 * @author Akshay
 *
 */
public class OptionQuestionFragment extends QuestionFragment implements ResponseChangedListener {
	
	private static final String TAG  = OptionQuestionFragment.class.getSimpleName();

	private TextView questionView;
	private ListView optionListView;
	private OptionListAdapter optionAdapter;
	
	private TextView instructionsView;
	private TextView searchView;
	
	
	private Button nextButton;
	private Button backButton;
	private QuestionResponseListener nextListener;
	private QuestionResponseListener backListener;
	
	private boolean backEnabled = false;
	private boolean isLast = false;
	
	protected OptionQuestionModel question;

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_question, null);
		
		questionView = (TextView)view.findViewById(R.id.questionText);
		optionListView = (ListView)view.findViewById(R.id.optionList);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		    optionListView.setFastScrollAlwaysVisible(true);
		}
		
		instructionsView = (TextView)view.findViewById(R.id.genderLbl);
		searchView = (TextView)view.findViewById(R.id.search);
		
		nextButton = (Button)view.findViewById(R.id.nextButton);
		backButton = (Button)view.findViewById(R.id.backButton);
				
		nextButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(question.hasMinimumResponses() && nextListener != null){ 
					nextListener.responseEntered(question);
				} 
			}
		});
		
		backButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(backListener != null){
					backListener.responseEntered(question);
				}
			}
		});
		
	
		String instructionText = "";
		
		switch(question.getType()){
			case SINGLE:
				instructionText = "Pick one:";
				break;
			case MULTIPLE:
				instructionText = "Pick all that apply:";
				break;
			case AT_LEAST_ONE:
				instructionText = "Pick at least one:";
				break;
			default:
				instructionText = "";			
				break;
		}
		
		instructionsView.setText(instructionText);
		
		if (question.getOptions().size() == 0) {
			instructionsView.setText("");
		}	
		
		if(!backEnabled){
			backButton.setVisibility(View.INVISIBLE);
		}
		
		if(isLast){
			nextButton.setText("Finish");
		}
		
		if(searchView != null){
			if(question.isSearchable()){
				searchView.setVisibility(View.VISIBLE);
			} else {
				searchView.setVisibility(View.GONE);
			}
			
			searchView.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {				
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					try{
						optionAdapter.setFilter(s.toString());
						optionAdapter.notifyDataSetChanged();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
		}
		
		optionAdapter = new OptionListAdapter(getActivity(), question);
		optionListView.setAdapter(optionAdapter);
		
		
		questionView.setText(question.getPrompt());	
		
		optionAdapter.setResponseChangedListener(this);
		
		optionAdapter.notifyDataSetChanged();

		return view;
	}

	@Override
	public void responseChanged(OptionQuestionModel q) {
		if(q.hasMinimumResponses()){
			nextButton.setBackgroundResource(R.drawable.bg_card_button);
		} else {
			nextButton.setBackgroundResource(R.drawable.bg_card_disable);
		}
	}


	
}