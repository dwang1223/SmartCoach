package edu.wpi.smartcoach.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.Solution;

public class SolutionFragment extends OptionQuestionFragment {
	
	private static final String TAG = SolutionFragment.class.getSimpleName();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setLayout(R.layout.fragment_solutions);
		View view = super.onCreateView(inflater, container, savedInstanceState);
		
		Button suggest = (Button)view.findViewById(R.id.suggest);
		final Button comm = (Button)view.findViewById(R.id.community);
		ListView list = (ListView)view.findViewById(R.id.optionList);
		
		final SolutionListAdapter adapter = new SolutionListAdapter(getActivity(), question);
		list.setAdapter(adapter);
		Log.d(TAG, "asdfasdsjfgdfghserb");
		comm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(TAG, "click");
				int amount = 2 + (int)(Math.random()*2);
				
				for(int i = amount; i > 0; i--){
					question.options.add(0, new Option("com"+i, new Solution(Solution.TYPE_COMMUNITY, "[Community suggested solution "+(i+1)+"]")));		

				} 
				adapter.setFilter(null);
				adapter.notifyDataSetChanged();
				comm.setEnabled(false);
				comm.setBackgroundResource(R.drawable.bg_card_disable); 
				
			}
		});
		
		suggest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final EditText input = new EditText(getActivity());
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Enter in a custom solution:")
				.setView(input)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String text = input.getText().toString();
						if(text.isEmpty()){
							text = "Your custom solution";
						}
						question.options.add(0, new Option("custom"+question.options.size(), new Solution(Solution.TYPE_COMMUNITY, text)));
						adapter.setFilter(null);
						adapter.notifyDataSetChanged();
					}

				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				})
				.create().show();
				
				
			}
		});
	
		return view;
	}

}
