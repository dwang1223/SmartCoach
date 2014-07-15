package edu.wpi.smartcoach.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.OptionQuestionModel;

public class OptionListAdapter extends BaseAdapter {

	public interface ResponseChangedListener{
		public void responseChanged(OptionQuestionModel q);
	}
	
	private static final String TAG = OptionListAdapter.class
			.getSimpleName();

	private Context context;

	private OptionQuestionModel question;
	
	private ResponseChangedListener responseListener;
	
	private String search = null;
	
	private List<Option> active;

	public OptionListAdapter(Context context, OptionQuestionModel qm) {
		super();
		this.context = context;
		question = qm;
		active = new ArrayList<Option>(qm.getOptions());
	}
	
	public void setResponseChangedListener(ResponseChangedListener rl){
		responseListener = rl;
		if(responseListener != null){
			responseListener.responseChanged(question);
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
					responseListener.responseChanged(question);
				}
				
				notifyDataSetChanged();

			}
		});

		if(search != null && !op.getText().toLowerCase().startsWith(search)){
			view.setVisibility(View.GONE);
		} else {
			view.setVisibility(View.VISIBLE);
		}
		
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
	
	public void setFilter(String search){
		if(search == null || search.isEmpty()){
			this.search = null;
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
		notifyDataSetChanged();
	}

}
