package edu.wpi.smartcoach.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.view.OptionListAdapter.ResponseChangedListener;

public class OptionQuestionFragment extends QuestionFragment implements ResponseChangedListener {
	
	private static final String TAG  = OptionQuestionFragment.class.getSimpleName();

	private TextView questionView;
	private ListView optionListView;
	private OptionListAdapter adapter;
	
	private TextView instructions;
	private TextView search;
	
	
	private Button next;
	private Button back;
	private QuestionResponseListener nextListener;
	private QuestionResponseListener backListener;
	private boolean backEnabled = false;
	private boolean isLast = false;
	private boolean social = false;
	private int layout = R.layout.fragment_question;
	
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
	
	public void setShowSocial(boolean show){
		social = show;
	}
	
	protected void setLayout(int layout){
		this.layout = layout;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(layout, null);
		
		questionView = (TextView)rootView.findViewById(R.id.questionText);
		optionListView = (ListView)rootView.findViewById(R.id.optionList);
		
		instructions = (TextView)rootView.findViewById(R.id.instructions);
		search = (TextView)rootView.findViewById(R.id.search);
		
		next = (Button)rootView.findViewById(R.id.nextButton);
		back = (Button)rootView.findViewById(R.id.backButton);
				
		next.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(question.hasMinimumResponses() && nextListener != null){ 
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
		
		if(question.getType() == QuestionType.SINGLE){
			 instructions.setText("Pick one:");
		} else if (question.getType() == QuestionType.MULTIPLE){
			instructions.setText("Pick all that apply:");
		} else if (question.getType() == QuestionType.AT_LEAST_ONE){
			instructions.setText("Pick at least one:");
		}
		
		 if(question.getOptions().size() == 0){
				instructions.setText("");
			} 
	
		
		if(!backEnabled){
			back.setVisibility(View.INVISIBLE);
		}
		
		if(isLast){
			next.setText("Finish");
		}
		
		if(search != null){
			if(question.isSearchable()){
				Log.d(TAG, "showing search");
				search.setVisibility(View.VISIBLE);
			} else {
				Log.d(TAG, "hiding search");
				search.setVisibility(View.GONE);
			}
			
			search.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {				
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					try{
						adapter.setFilter(s.toString());
						adapter.notifyDataSetChanged();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
		}
		
		adapter = new OptionListAdapter(getActivity(), question);
		optionListView.setAdapter(adapter);
		
		
		questionView.setText(question.getPrompt());	
		
		adapter.setResponseChangedListener(this);
		
		adapter.notifyDataSetChanged();

		return rootView;
	}

	@Override
	public void responseChanged(OptionQuestionModel q) {
		if(q.hasMinimumResponses()){
			next.setBackgroundResource(R.drawable.bg_card_button);
		} else {
			next.setBackgroundResource(R.drawable.bg_card_disable);
		}
	}


	
}