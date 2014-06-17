package edu.wpi.smartcoach.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.exercise.ExerciseProblems;
import edu.wpi.smartcoach.model.exercise.ExerciseSolution;
import edu.wpi.smartcoach.solver.ProblemSolver;
import edu.wpi.smartcoach.view.OptionQuestionFragment;
import edu.wpi.smartcoach.view.QuestionFragment;
import edu.wpi.smartcoach.view.QuestionResponseListener;

public class ExerciseProblemActivity extends FragmentActivity implements QuestionResponseListener{
	
	private static final String TAG = ExerciseProblemActivity.class.getSimpleName();

	private OptionQuestionFragment questionFragment;
	
	
	private ProblemSolver solver  = null;
	
	private boolean solutionRetrieved;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_problem);
		setTitle("Problem Solving");
		questionFragment = new OptionQuestionFragment();
		questionFragment.setQuestion(ExerciseProblems.BASE_PROBLEM);
		questionFragment.setNextButtonListener(this);
		questionFragment.setBackEnabled(false);
		getSupportFragmentManager().beginTransaction().add(R.id.container, questionFragment).commit();
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exercise_problem, menu);
		return true;
	}

	@Override
	public void responseEntered(QuestionModel q) {;
		Log.d(TAG, "responseEntered "+q.toString());
		
		if(solutionRetrieved){
			OptionQuestionModel solution = (OptionQuestionModel)q;
			if(solution.getSelectedResponses().size() != 0 && solution.getSelectedResponse().getId() != -1){
				Intent intent = new Intent(getBaseContext(), SetReminderActivity.class);
				intent.putExtra("reminder", ((ExerciseSolution)solution.getSelectedResponse().getValue()).getReminder());
				startActivity(intent);
			}
			finish();
			return;
		}
		
		boolean submit = true;
		if(q == ExerciseProblems.BASE_PROBLEM){
			solver = ExerciseProblems.getSolver((((OptionQuestionModel)q).getSelectedResponse()).getId(), this);
			submit = false;
		}
		
		QuestionModel newQuestion = null;
		if(solver != null){
		
			if(submit){
				solver.submitResponse(q);
			}
			
			if(solver.hasNextQuestion()){
				newQuestion = solver.getNextQuestion();
				Log.d(TAG, "next: "+newQuestion.getId());
			} else {
				newQuestion = solver.getSolution(this);
				
				solutionRetrieved = true;
				Log.d(TAG, "get solution");
			}
		}
		
		if(newQuestion != null){
			QuestionFragment questionFragment = QuestionFragment.createQuestion(newQuestion);
			questionFragment.setNextButtonListener(this);
			questionFragment.setBackButtonListener(new QuestionResponseListener() {
				
				@Override
				public void responseEntered(QuestionModel question) {
					navigateBack();
				}
			});
			questionFragment.setBackEnabled(!solutionRetrieved && solver.isBackAllowed());
			questionFragment.setLast(solutionRetrieved);
			getSupportFragmentManager().beginTransaction().replace(R.id.container, questionFragment).commit();	
		}
		
	}
	
	private void navigateBack(){
		solver.back();
		QuestionModel newQuestion = solver.getNextQuestion();
		
		QuestionFragment questionFragment = QuestionFragment.createQuestion(newQuestion);
		questionFragment.setNextButtonListener(this);
		questionFragment.setBackButtonListener(new QuestionResponseListener() {
			
			@Override
			public void responseEntered(QuestionModel question) {
				navigateBack();
			}
		});
		questionFragment.setBackEnabled(solver.isBackAllowed());
		questionFragment.setLast(solutionRetrieved);
		getSupportFragmentManager().beginTransaction().replace(R.id.container, questionFragment).commit();	
	}

}
