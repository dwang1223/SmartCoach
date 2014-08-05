package edu.wpi.smartcoach.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.activity.SetReminderActivity;
import edu.wpi.smartcoach.model.Solution;

public class SolutionListAdapter extends BaseAdapter {

	Context context;
	List<Solution> solutions;
	
	public SolutionListAdapter(Context context, List<Solution> solutions) {
		this.context = context;
		this.solutions = solutions;
		
		if(this.solutions == null){
			this.solutions = new ArrayList<Solution>();
		}
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent){
		View view = convertView;
		
		if(view == null){
			LayoutInflater inflater = LayoutInflater.from(context);
			view = inflater.inflate(R.layout.layout_option_solution, null);
		}
		
		final Solution item = getItem(position);
		
		TextView solutionText = (TextView)view.findViewById(R.id.solution);
		
		Button info = (Button)view.findViewById(R.id.info);
		Button set = (Button)view.findViewById(R.id.set);
		
		set.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, SetReminderActivity.class);
				intent.putExtra("reminder", new String[]{solutions.get(position).toString()});
				context.startActivity(intent);
				
			}
		});
		
		solutionText.setText(solutions.get(position).toString());
		
		View card = view.findViewById(R.id.card);
		View sep = view.findViewById(R.id.separator);
		if(solutions.get(position).getType() == Solution.TYPE_COMMUNITY){
			card.setBackgroundResource(R.drawable.bg_card_highlight_social);
			solutionText.setTextColor(Color.WHITE);
			info.setTextColor(Color.WHITE);
			set.setTextColor(Color.WHITE);
			sep.setBackgroundColor(0xff0099cc);
		} else {
			card.setBackgroundResource(R.drawable.bg_card);
			solutionText.setTextColor(Color.BLACK);
			info.setTextColor(Color.BLACK);
			set.setTextColor(Color.BLACK);
			sep.setBackgroundColor(0xffcccccc);
		}
		
		if(item.getInfo() != null){
			info.setVisibility(View.VISIBLE);
			info.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getInfo()));
					context.startActivity(intent);
				}
			});
		} else {
			info.setVisibility(View.GONE);
		}
		
		return view;
		
	}

	@Override
	public int getCount() {
		return solutions.size();
	}

	@Override
	public Solution getItem(int position) {
		return solutions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
