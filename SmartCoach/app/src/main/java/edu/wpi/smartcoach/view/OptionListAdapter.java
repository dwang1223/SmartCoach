package edu.wpi.smartcoach.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.Option;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.util.Callback;

/**
 * Adapter for displaying options from a OptionQuestionModel in a ListView
 * @author Akshay
 *
 */
public class OptionListAdapter extends BaseAdapter {

	private static final String TAG = OptionListAdapter.class.getSimpleName();

	private Context context;
	private OptionQuestionModel question;	
	private Callback<OptionQuestionModel> responseListener;
	private List<Option> active;

	/**
	 * Constructor
	 * @param context Android context
	 * @param qm OptionQuestionModel whose options should be displayed
	 */
	public OptionListAdapter(Context context, OptionQuestionModel qm) {
		super();
		this.context = context;
		question = qm;
		active = new ArrayList<Option>(qm.getOptions());
	}
	
	public void setResponseChangedListener(Callback<OptionQuestionModel> rl){
		responseListener = rl;
		if(responseListener != null){
			responseListener.callback(question);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		View view = convertView;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			view = inflater.inflate(R.layout.layout_option, null);
			if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
				CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox);
				final float scale = context.getResources().getDisplayMetrics().density;
				cb.setPadding(cb.getPaddingLeft()
						+ (int) (20.0f * scale + 0.5f), cb.getPaddingTop(),
						cb.getPaddingRight(), cb.getPaddingBottom());
			}
		}

		final Option op = getItem(position);
		CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox);
		cb.setChecked(op.isSelected());
		cb.setText(op.getText());
		cb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				question.optionSelected(op);
				
				if(responseListener != null){
					responseListener.callback(question);
				}
				
				notifyDataSetChanged();
			}
		});
		
		return view;
	}

	@Override
	public int getCount() {
		return active.size();
	}

	@Override
	public Option getItem(int position) {
		return active.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * Set the search filter for the option list. 
	 * Options whose display name does not start with the filter will be hidden
	 * @param search Search filter
	 */
	public void setFilter(String search){
		if(search == null || search.isEmpty()){
			active.clear();
			active.addAll(question.getOptions());
		} else {
			active.clear();
			for(Option o:question.getOptions()){
				if(o.getText().toLowerCase().startsWith(search.toLowerCase())){
					active.add(o);
				}
			}
		}
	}


}
