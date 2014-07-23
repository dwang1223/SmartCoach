package edu.wpi.smartcoach.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.solver.DialogScriptSolver;
import edu.wpi.smartcoach.util.DialogScriptReader;
import edu.wpi.smartcoach.view.OptionQuestionFragment;
import edu.wpi.smartcoach.view.QuestionFragment;
import edu.wpi.smartcoach.view.QuestionResponseListener;

public class DietProblemActivity extends FragmentActivity implements QuestionResponseListener {

	private DialogScriptSolver solver;
	private OptionQuestionFragment questionFragment;
	
	private boolean solved;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_problem);
		setTitle("SmartCoach Problem Solving");
		
		solver = DialogScriptReader.readScript(getResources().openRawResource(R.raw.diet));
		questionFragment = new OptionQuestionFragment();
		questionFragment.setQuestion((OptionQuestionModel)solver.getNextQuestion());
		questionFragment.setNextButtonListener(this);
		questionFragment.setBackEnabled(false);
		getSupportFragmentManager().beginTransaction().add(R.id.container, questionFragment).commit();
		
	}
	
	@Override
	public void responseEntered(QuestionModel question) {
		
		if(solved){
			OptionQuestionModel sln = (OptionQuestionModel)question;
			String[] reminder = sln.getSelectedValues().toArray(new String[]{});
			Intent intent = new Intent(this, SetReminderActivity.class);
			intent.putExtra("reminder", reminder);
			startActivity(intent);
			finish();
			return;
		}
		
		solver.submitResponse(question);
		
		QuestionModel nextQuestion;
		
		
		if(solver.hasNextQuestion()){
			nextQuestion = solver.getNextQuestion();
		} else {
			nextQuestion = solver.getSolution(getBaseContext());
			solved = true;
		}
		questionFragment = (OptionQuestionFragment)QuestionFragment.createQuestion(nextQuestion, solved);
		questionFragment.setQuestion((OptionQuestionModel)nextQuestion);
		questionFragment.setNextButtonListener(this);
		questionFragment.setBackButtonListener(new QuestionResponseListener() {
			
			@Override
			public void responseEntered(QuestionModel question) {
				navigateBack();
			}
		});
		questionFragment.setBackEnabled(solver.isBackAllowed());
		questionFragment.setLast(solved);
		getSupportFragmentManager().beginTransaction().replace(R.id.container, questionFragment).commit();	
		
	}
	
	private void navigateBack(){
		solver.back();
		QuestionModel newQuestion = solver.getNextQuestion();
		
		QuestionFragment questionFragment = QuestionFragment.createQuestion(newQuestion, solved);
		questionFragment.setNextButtonListener(this);
		questionFragment.setBackButtonListener(new QuestionResponseListener() {
			
			@Override
			public void responseEntered(QuestionModel question) {
				navigateBack();
			}
		});
		questionFragment.setBackEnabled(solver.isBackAllowed());
		questionFragment.setLast(false);
		getSupportFragmentManager().beginTransaction().replace(R.id.container, questionFragment).commit();	

	}

}
