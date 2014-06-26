package edu.wpi.smartcoach.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.OptionQuestionModel;

public class QuestionOptionListAdapter extends BaseAdapter {

	public interface ResponseChangedListener{
		public void responseChanged(OptionQuestionModel q);
	}
	
	private static final String TAG = QuestionOptionListAdapter.class
			.getSimpleName();

	private Context context;

	private OptionQuestionModel question;
	
	private ResponseChangedListener responseListener;

	public QuestionOptionListAdapter(Context context, OptionQuestionModel qm) {
		super();
		this.context = context;
		question = qm;
	}
	
	public void setResponseChangedListener(ResponseChangedListener rl){
		responseListener = rl;
		if(responseListener != null){
			responseListener.responseChanged(question);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		View view;

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
		} else {
			view = convertView;
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

		return view;
	}

	@Override
	public int getCount() {
		return question.getOptions().size();
	}

	@Override
	public Option getItem(int position) {
		return question.getOptions().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
