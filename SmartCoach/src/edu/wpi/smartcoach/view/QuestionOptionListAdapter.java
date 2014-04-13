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
	
	public QuestionOptionListAdapter(Context context, QuestionModel qm) {
		super();
		this.context = context;
		question = qm; 
		
		if(qm.hasDefault()){
			qm.getDefault().setSelected(true);
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
		cb.setChecked(op.isSelected());

		cb.setText(op.getText());

		cb.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View view) {
				boolean isChecked = !op.isSelected();
				//if in single selection mode or the default item is selected
				if(isChecked && (question.getType() == QuestionType.SINGLE || op.getId() == QuestionModel.DEFAULT)){
					for(OptionModel opm:question.getResponses()){ //deselect everything else
						opm.setSelected(false);
					}
				} else if(op.getId() != QuestionModel.DEFAULT){ // if the selected item is not the default
					if(question.hasDefault()){ //deselect the default
						question.getDefault().setSelected(false);
					}
				}

				op.setSelected(isChecked); // set the item's selection 
				
				if(!isChecked && question.hasDefault()){ //if nothing is selected
					boolean somethingSelected = false;
					for(OptionModel opm:question.getResponses()){
						somethingSelected |= opm.isSelected();
					}
					if(!somethingSelected){ //select the default
						question.getDefault().setSelected(true);
					}
				}
				
				//if the max number of item has been selected
				if(question.getType().equals(QuestionType.MULTIPLE) && question.getLimit() != QuestionModel.NO_LIMIT){
					int count = 0;
					for(OptionModel opm:question.getResponses()){
						if(opm.isSelected())count++;
					}
					if(count > question.getLimit()){
						op.setSelected(false); //do not allow another item to be selected
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
