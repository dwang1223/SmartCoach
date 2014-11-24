package edu.wpi.smartcoach.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.WeightQuestionModel;

public class WeightQuestionFragment extends QuestionFragment {
	
	private WeightQuestionModel question;
	
	private TextView promptText;
	private EditText weightText;
	private Button next;
	private Button back;
	
	private QuestionResponseListener nextListener = null;
	
	public WeightQuestionFragment(){
		
	}
	
	public QuestionFragment setQuestion(WeightQuestionModel question){
		this.question = question;
		return this;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
		View view = inflater.inflate(R.layout.fragment_question_weight, null);
		
		weightText = (EditText)view.findViewById(R.id.weight);
		
		weightText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				try{
					if(Float.parseFloat(s.toString()) < 0){
						weightText.setText((Float.parseFloat(s.toString())*-1)+"");
					}
					setButtonEnabledState(next, true);
				} catch(Exception e){
					setButtonEnabledState(next, false);
				}				
			}
		});
		
		next = (Button)view.findViewById(R.id.nextButton);
		
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(nextListener != null){
					nextListener.responseEntered(question);
				}
				
			}
		});
	
		back = (Button)view.findViewById(R.id.backButton);
		setButtonEnabledState(next, false);
		back.setVisibility(View.GONE);
		
		promptText = (TextView)view.findViewById(R.id.questionText);
		promptText.setText(question.getPrompt());
		
		return view;		
	}

	@Override
	public void setBackEnabled(boolean first) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLast(boolean isLast) {
		// TODO Auto-generated method stub
	}

	@Override
	public QuestionFragment setNextButtonListener(QuestionResponseListener listener) {
		nextListener = listener;
		return this;
	}

	@Override
	public QuestionFragment setBackButtonListener(QuestionResponseListener listener) {
		// TODO Auto-generated method stub
		return this;
	}
	
	private void setButtonEnabledState(Button b, boolean state){
		if(state){
			b.setBackgroundResource(R.drawable.bg_card_button);
			b.setEnabled(true);
		} else {
			b.setBackgroundResource(R.drawable.bg_card_disable);
			next.setEnabled(false);
		}
	}
	
	

}
