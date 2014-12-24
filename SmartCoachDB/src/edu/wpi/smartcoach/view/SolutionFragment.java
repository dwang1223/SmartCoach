package edu.wpi.smartcoach.view;

import java.util.ArrayList;
import java.util.List;

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
import edu.wpi.smartcoach.model.QuestionResponseOutline;
import edu.wpi.smartcoach.model.SocialNetworkSubmission;
import edu.wpi.smartcoach.model.Solution;
import edu.wpi.smartcoach.solver.ProblemSolver;
import edu.wpi.smartcoach.util.SocialHelper;
import edu.wpi.smartcoach.util.SocialHelper.SuggestionListener;

/**
 * A fragment that displays a list of solutions that the user can select and set reminders for.
 * @author Akshay
 *
 */
public class SolutionFragment extends QuestionFragment implements SuggestionListener {
	
	private static final String TAG = SolutionFragment.class.getSimpleName();
	
	private String category;
	private ProblemSolver solver;
	
	private List<Solution> solutions;
	
	private QuestionResponseListener listener;
	private Button next;

	SolutionListAdapter adapter;
	public SolutionFragment(){
		solutions = new ArrayList<Solution>();
	}
	
	public void setSolutions(List<Solution> solutions){
		this.solutions = solutions;
	}
	
	public void setSolver(String category, ProblemSolver solver){
		this.category = category;
		this.solver = solver;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			
		View view = inflater.inflate(R.layout.fragment_solutions, null);
		
		next = (Button)view.findViewById(R.id.nextButton);
		
		Button suggest = (Button)view.findViewById(R.id.suggest);
		final Button comm = (Button)view.findViewById(R.id.community);
		final ListView list = (ListView)view.findViewById(R.id.optionList);
		
	 adapter = new SolutionListAdapter(getActivity(), solutions);
		
		list.setAdapter(adapter);
		
		comm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(TAG, "click");

				SocialHelper.getSuggestions(category, solver.getOutline(), SolutionFragment.this);								
				//list.setSelection(adapter.getCount()-1);
				
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
						solutions.add(new Solution(Solution.TYPE_COMMUNITY, text, null));
						

						QuestionResponseOutline[] outline = solver.getOutline();
						
						SocialNetworkSubmission submission = new SocialNetworkSubmission(category, text, solver.getOutline());
						
						SocialHelper.submitSolution(submission);
						
						adapter.notifyDataSetChanged();
						list.setSelection(adapter.getCount()-1);
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
		
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener != null){
					listener.responseEntered(null);
				}
				
			}
		});
	
		return view;
	}

	@Override
	public void setBackEnabled(boolean first) {
			
	}

	@Override
	public void setLast(boolean isLast) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public QuestionFragment setNextButtonListener(final QuestionResponseListener listener) {
		this.listener = listener;

		return this;
	}

	@Override
	public QuestionFragment setBackButtonListener(QuestionResponseListener listener) {
		return this;
	}

	@Override
	public void suggestionsRecieved(final String[] solutions) {
		getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				for(String s:solutions){
					adapter.solutions.add(new Solution(Solution.TYPE_COMMUNITY, s, null));
				}
				adapter.notifyDataSetChanged();
			}
		});
		
	}

}
