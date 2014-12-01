package edu.wpi.smartcoach.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.Session;
import edu.wpi.smartcoach.model.SocialNetworkSubmission;
import edu.wpi.smartcoach.model.Solution;
import edu.wpi.smartcoach.model.TimeQuestionModel;
import edu.wpi.smartcoach.service.SessionService;
import edu.wpi.smartcoach.solver.DialogXMLSolver;
import edu.wpi.smartcoach.util.DialogXMLReader;
import edu.wpi.smartcoach.view.OptionQuestionFragment;
import edu.wpi.smartcoach.view.QuestionFragment;
import edu.wpi.smartcoach.view.QuestionResponseListener;
import edu.wpi.smartcoach.view.SolutionFragment;

public class ExerciseProblemActivity extends FragmentActivity implements QuestionResponseListener {

	private final static String TAG = ExerciseProblemActivity.class.getSimpleName();
	
	private final static int TWO_HOURS = 60*60*2;
	
	private DialogXMLSolver solver;
	private QuestionFragment questionFragment;
	private List<Solution> solutions;
	private boolean solved;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_problem);
		setTitle("SmartCoach Problem Solving");
		
		solver = DialogXMLReader.readXML(R.raw.exercise, this);
		questionFragment = new OptionQuestionFragment();
		((OptionQuestionFragment)questionFragment).setQuestion((OptionQuestionModel)solver.getNextQuestion());
		questionFragment.setNextButtonListener(this);
		questionFragment.setBackEnabled(false);
		getSupportFragmentManager().beginTransaction().add(R.id.container, questionFragment).commit();
		solutions = new ArrayList<Solution>();
	}
	 
	@Override
	public void responseEntered(QuestionModel question) {
		
		if(solved){
//			OptionQuestionModel sln = (OptionQuestionModel)question;
//			String[] reminder = sln.getSelectedValues().toArray(new String[]{});
//			if(reminder.length > 0){
//				Intent intent = new Intent(this, SetReminderActivity.class);
//				intent.putExtra("reminder", reminder);
//				startActivity(intent);
//			}
			
			Session session = new Session(System.currentTimeMillis(), "Exercise", solutions);
			SessionService.getInstance().addSession(session, getApplicationContext());
			finish();
			return;
		}
		
		solver.submitResponse(question);
		
		QuestionModel nextQuestion = null;
		
		
		if(solver.hasNextQuestion()){
			nextQuestion = solver.getNextQuestion();
		} else {
			
			solutions = getExerciseSolutions();
			showSolution(solutions);
			solved = true;
		}
		
		if(!solved){ 
			questionFragment = QuestionFragment.createQuestion(nextQuestion);
			//questionFragment.setQuestion((QuestionModel)nextQuestion);
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
	
	private void showSolution(List<Solution> solutions){
		SolutionFragment solutionFragment = new SolutionFragment();
		solutionFragment.setSolutions(solutions);
		solutionFragment.setSolver(SocialNetworkSubmission.DIET, solver);
		solutionFragment.setNextButtonListener(this);
		getSupportFragmentManager().beginTransaction().replace(R.id.container, solutionFragment).commit();	
		
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
		questionFragment.setLast(false);
		getSupportFragmentManager().beginTransaction().replace(R.id.container, questionFragment).commit();	

	}
	
	private List<Solution> getExerciseSolutions(){
		List<Solution> solutions = solver.getSolution(this);
		
		int timeSleep = ((TimeQuestionModel)solver.getQuestionById("2")).getResponse();
		int timeWakeup = ((TimeQuestionModel)solver.getQuestionById("3")).getResponse();
		int timeLeave = ((TimeQuestionModel)solver.getQuestionById("4")).getResponse();
		int timeReturn = ((TimeQuestionModel)solver.getQuestionById("5")).getResponse();
		
		
		int morningGap = timeLeave - timeWakeup;
		int eveningGap = timeSleep - timeReturn;
		
		if(morningGap >= TWO_HOURS || eveningGap >= TWO_HOURS){
			String start = "";
			String end = "";
			if(morningGap > eveningGap){
				start = TimeQuestionModel.formatTime(timeWakeup);
				end = TimeQuestionModel.formatTime(timeLeave);
			} else {
				start = TimeQuestionModel.formatTime(timeReturn);
				end = TimeQuestionModel.formatTime(timeSleep);				
			}
			Solution gapSolution = solver.getSolutionById("8").clone();
			gapSolution.setMessage(
					gapSolution.getMessage()
					.replace("[start]", start)
					.replace("[end]", end)
					);
			
			solutions.add(gapSolution);
			
		} else if (solver.hasCondition("time.no_days_off")){
			solutions.add(solver.getSolutionById("43"));
			solutions.add(solver.getSolutionById("36"));
			//no gap
		}
		
		return solutions;
				
	}

}
