package edu.wpi.smartcoach.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.Option;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.QuestionModel.QuestionType;

public class QuestionOptionListAdapter extends BaseAdapter {

	private static final String TAG = QuestionOptionListAdapter.class
			.getSimpleName();

	private Context context;

	private QuestionModel question;

	public QuestionOptionListAdapter(Context context, QuestionModel qm) {
		super();
		this.context = context;
		question = qm;

		if (qm.hasDefault()) {
			qm.getDefault().setSelected(true);
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
				boolean isChecked = !op.isSelected();
				// if in single selection mode or the default item is selected
				if (isChecked
						&& (question.getType() == QuestionType.SINGLE || op
								.getId() == QuestionModel.DEFAULT)) {

					for (Option opm : question.getResponses()) { // deselect
																	// everything
																	// else
						opm.setSelected(false);
					}
				} else if (op.getId() != QuestionModel.DEFAULT) { // if the
																	// selected
																	// item is
																	// not the
																	// default
					if (question.hasDefault()) { // deselect the default
						question.getDefault().setSelected(false);
					}
				}

				op.setSelected(isChecked); // set the item's selection

				if (!isChecked && question.hasDefault()) { // if nothing is
															// selected
					boolean somethingSelected = false;
					for (Option opm : question.getResponses()) {
						somethingSelected |= opm.isSelected();
					}
					if (!somethingSelected) { // select the default
						question.getDefault().setSelected(true);
					}
				}

				// if the max number of item has been selected
				if (question.getType().equals(QuestionType.MULTIPLE)
						&& question.getMax() != QuestionModel.NO_LIMIT) {
					int count = 0;
					for (Option opm : question.getResponses()) {
						if (opm.isSelected())
							count++;
					}
					if (count > question.getMax()) {
						op.setSelected(false); // do not allow another item to
												// be selected
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
	public Option getItem(int position) {
		return question.getResponses().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
