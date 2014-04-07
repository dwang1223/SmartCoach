package edu.wpi.smartcoach.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.OptionModel;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.QuestionModel.QuestionType;

public class QuestionOptionListAdapter extends BaseAdapter {
	
	private static final String TAG = QuestionOptionListAdapter.class.getSimpleName();
	
	private Context context;
	
	private QuestionModel question;
	
	private OptionModel defaultOption;
	
	public QuestionOptionListAdapter(Context context, QuestionModel qm) {
		super();
		this.context = context;
		question = qm; 
		
		for(OptionModel opm:qm.getResponses()){
			if(opm.getId() == QuestionModel.DEFAULT){
				defaultOption = opm;
				defaultOption.setSelected(true);
			}
		}
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup group){
		View view;
		
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.layout_option, null);			
		} else {
			view = convertView;			
		}
	
		final OptionModel op = getItem(position);
		CheckBox cb = (CheckBox)view.findViewById(R.id.checkBox);
		Log.d(TAG, "getView " + op.getId());
		cb.setChecked(op.isSelected());

		cb.setText(op.getText());

		cb.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View view) {
				boolean isChecked = !op.isSelected();
				if(isChecked && (question.getType() == QuestionType.SINGLE || op.getId() == QuestionModel.DEFAULT)){
					for(OptionModel opm:question.getResponses()){
						opm.setSelected(false);
					}
				} else if(question.getId() != QuestionModel.DEFAULT){
					for(OptionModel opm:question.getResponses()){
						defaultOption.setSelected(false);
					}
				}

				op.setSelected(isChecked);
				
				if(!isChecked){
					boolean somethingSelected = false;
					for(OptionModel opm:question.getResponses()){
						somethingSelected |= opm.isSelected();
					}
					if(!somethingSelected){
						defaultOption.setSelected(true);
					}
				}
				
				notifyDataSetChanged();			
				
			}
		});	
		
		return view;
	}

	@Override
	public int getCount() {
		return question.getResponses().size();
	}

	@Override
	public OptionModel getItem(int position) {
		return question.getResponses().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
