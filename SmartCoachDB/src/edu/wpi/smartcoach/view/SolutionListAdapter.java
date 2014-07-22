package edu.wpi.smartcoach.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.Solution;

public class SolutionListAdapter extends OptionListAdapter {

	public SolutionListAdapter(Context context, OptionQuestionModel qm) {
		super(context, qm);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View view = super.getView(position, convertView, parent);
		
		CheckBox cb = (CheckBox)view.findViewById(R.id.checkBox);
		
		Option option = getItem(position);
		
		if(option.getValue() instanceof Solution && ((Solution)option.getValue()).getType() == Solution.TYPE_COMMUNITY){
			cb.setBackgroundResource(R.drawable.bg_card_highlight_social);
			cb.setTextColor(Color.WHITE);
		} else {
			cb.setBackgroundResource(R.drawable.bg_card);
			cb.setTextColor(Color.BLACK);
		}
		
		return view;
		
	}

}
