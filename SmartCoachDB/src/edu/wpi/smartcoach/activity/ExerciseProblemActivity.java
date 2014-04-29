package edu.wpi.smartcoach.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.ExerciseProblems;
import edu.wpi.smartcoach.model.ProblemOption;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.solver.ProblemSolver;
import edu.wpi.smartcoach.view.QuestionFragment;
import edu.wpi.smartcoach.view.QuestionFragment.QuestionResponseListener;

public class ExerciseProblemActivity extends FragmentActivity implements QuestionResponseListener{
	
	private static final String TAG = ExerciseProblemActivity.class.getSimpleName();

	private QuestionFragment questionFragment;
	
	
	private ProblemSolver solver  = null;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_problem);
		
		questionFragment = new QuestionFragment();
		questionFragment.setQuestion(ExerciseProblems.BASE_PROBLEM);
		questionFragment.setNextButtonListener(this);
		getSupportFragmentManager().beginTransaction().add(R.id.container, questionFragment).commit();
		
	}

//	private void doSave(){
//		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//		Editor prefEdit = prefs.edit();
//		for(QuestionModel qm:problemQuestions){
//			List<Option> select = qm.getSelectedResponses();
//			String responseStr = "";
//			for(int i = 0; i < select.size(); i++){
//				responseStr += select.get(i).getId();
//				if(i < select.size()-1){
//					responseStr += ",";
//				}
//			}
//			prefEdit.putString(qm.getId(), responseStr);
//		}
//		prefEdit.commit();		
//	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exercise_problem, menu);
		return true;
	}

	@Override
	public void responseEntered(QuestionModel question) {
		Log.d(TAG, "responseEntered "+question.toString());
		boolean submit = true;
		if(question == ExerciseProblems.BASE_PROBLEM){
			solver = ((ProblemOption)question.getSelectedResponses().get(0).getModel()).getSolver();
			submit = false;
		}
		
		QuestionModel newQuestion = null;
		
		if(solver != null && solver.hasNextQuestion()){
		
			if(submit){
				solver.submitResponse(question);
			}

			newQuestion = solver.getNextQuestion();
			
		} else {
			
			newQuestion = solver.getSolution();
			
		}
		
		if(newQuestion != null){
			questionFragment = new QuestionFragment();
			questionFragment.setQuestion(newQuestion);
			questionFragment.setNextButtonListener(this);
			getSupportFragmentManager().beginTransaction().replace(R.id.container, questionFragment).commit();	
		}
		
	}

}
