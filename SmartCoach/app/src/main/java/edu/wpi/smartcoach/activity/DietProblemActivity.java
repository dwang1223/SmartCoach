package edu.wpi.smartcoach.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.Session;
import edu.wpi.smartcoach.model.SocialNetworkSubmission;
import edu.wpi.smartcoach.model.Solution;
import edu.wpi.smartcoach.service.SessionService;
import edu.wpi.smartcoach.solver.DialogXMLSolver;
import edu.wpi.smartcoach.util.Callback;
import edu.wpi.smartcoach.util.DialogXMLReader;
import edu.wpi.smartcoach.view.OptionQuestionFragment;
import edu.wpi.smartcoach.view.QuestionFragment;
import edu.wpi.smartcoach.view.SolutionFragment;

public class DietProblemActivity extends FragmentActivity implements Callback<QuestionModel> {

	private DialogXMLSolver solver;
	private OptionQuestionFragment questionFragment;
	private List<Solution> solutions;
	private boolean solved;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_problem);
		
		solver = DialogXMLReader.readXML(R.raw.diet, this);
		questionFragment = new OptionQuestionFragment();
		questionFragment.setQuestion((OptionQuestionModel)solver.getNextQuestion());
		questionFragment.setNextButtonListener(this);
		questionFragment.setBackEnabled(false);
		getSupportFragmentManager().beginTransaction().add(R.id.container, questionFragment).commit();
		solutions = new ArrayList<Solution>();
	}

    /**
     * Called when a question is responded to
     * @param question
     */
	@Override
	public void callback(QuestionModel question) {
		
		if(solved){
//			OptionQuestionModel sln = (OptionQuestionModel)question;
//			String[] reminder = sln.getSelectedValues().toArray(new String[]{});
//			if(reminder.length > 0){
//				Intent intent = new Intent(this, SetReminderActivity.class);
//				intent.putExtra("reminder", reminder);
//				startActivity(intent);
//			}
			SessionService.getInstance().addSession(new Session(System.currentTimeMillis(), "Diet", solutions), getApplicationContext());
			finish();
			return;
		}
		
		solver.submitResponse(question);
		
		QuestionModel nextQuestion = null;
		
		
		if(solver.hasNextQuestion()){
			nextQuestion = solver.getNextQuestion();
		} else {
			solutions = solver.getSolution(getBaseContext());
			showSolution(solutions);
			solved = true;
		}
		
		if(!solved){
			questionFragment = (OptionQuestionFragment)QuestionFragment.createQuestion(nextQuestion);
			questionFragment.setQuestion((OptionQuestionModel)nextQuestion);
			questionFragment.setNextButtonListener(this);
			questionFragment.setBackButtonListener(new Callback<QuestionModel>() {
				
				@Override
				public void callback(QuestionModel question) {
					navigateBack();
				}
			});
			questionFragment.setBackEnabled(solver.isBackAllowed());
			questionFragment.setLast(false);
			getSupportFragmentManager().beginTransaction().replace(R.id.container, questionFragment).commit();	
		}
	}
	
	private void showSolution(List<Solution> solutions){
		SolutionFragment solutionFragment = new SolutionFragment();
		solutionFragment.setSolutions(solutions);
		solutionFragment.setSolver(SocialNetworkSubmission.CATEGORY_DIET, solver);
		solutionFragment.setNextButtonListener(this);
		getSupportFragmentManager().beginTransaction().replace(R.id.container, solutionFragment).commit();	
		
	}
	
	private void navigateBack(){
		solver.back();
		QuestionModel newQuestion = solver.getNextQuestion();
		
		QuestionFragment questionFragment = QuestionFragment.createQuestion(newQuestion);
		questionFragment.setNextButtonListener(this);
		questionFragment.setBackButtonListener(new Callback<QuestionModel>() {
			
			@Override
			public void callback(QuestionModel question) {
				navigateBack();
			}
		});
		questionFragment.setBackEnabled(solver.isBackAllowed());
		questionFragment.setLast(false);
		getSupportFragmentManager().beginTransaction().replace(R.id.container, questionFragment).commit();	

	}

}
