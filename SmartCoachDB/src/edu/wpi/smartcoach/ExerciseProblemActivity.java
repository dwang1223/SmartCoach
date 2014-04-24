package edu.wpi.smartcoach;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import edu.wpi.smartcoach.model.Exercise;
import edu.wpi.smartcoach.model.ExerciseProblems;
import edu.wpi.smartcoach.model.Option;
import edu.wpi.smartcoach.model.ProblemOption;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.service.ExerciseService;
import edu.wpi.smartcoach.view.QuestionFragment;
import edu.wpi.smartcoach.view.QuestionFragment.QuestionResponseListener;

public class ExerciseProblemActivity extends FragmentActivity implements QuestionResponseListener{
	
	private static final String TAG = ExerciseProblemActivity.class.getSimpleName();

	private QuestionFragment questionFragment;
	
	private ArrayList<Exercise> exercises;
	
	private ArrayList<QuestionModel> problemQuestions;
	int index = -1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_problem);
		
		problemQuestions = new ArrayList<QuestionModel>();
		
		questionFragment = new QuestionFragment();
		questionFragment.setQuestion(ExerciseProblems.BASE_PROBLEM);
		questionFragment.setNextButtonListener(this);
		getSupportFragmentManager().beginTransaction().add(R.id.container, questionFragment).commit();
		
	}

	private void doSave(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Editor prefEdit = prefs.edit();
		for(QuestionModel qm:problemQuestions){
			List<Option> select = qm.getSelectedResponses();
			String responseStr = "";
			for(int i = 0; i < select.size(); i++){
				responseStr += select.get(i).getId();
				if(i < select.size()-1){
					responseStr += ",";
				}
			}
			prefEdit.putString(qm.getId(), responseStr);
		}
		prefEdit.commit();		
	}
	
	private void createFilteredList(){
		List<Exercise> exercisesList = ExerciseService.getInstance().getAllDataFromTable();
		exercises.addAll(exercisesList);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exercise_problem, menu);
		return true;
	}

	@Override
	public void responseEntered(QuestionModel question) {
		if(question == ExerciseProblems.BASE_PROBLEM){
			List<Option> selectedProblems = question.getSelectedResponses();
			for(Option o:selectedProblems){
				ProblemOption po = ((ProblemOption)o.getModel());
				if(po.getNextQuestion() != null){
					problemQuestions.add(po.getNextQuestion());
				}
			}
			index = -1;
		}
		
		index++;
		if(index >= problemQuestions.size()){
			doSave();
			createFilteredList();
			return;
		}
		questionFragment = new QuestionFragment();
		questionFragment.setQuestion(problemQuestions.get(index));
		questionFragment.setNextButtonListener(this);
		getSupportFragmentManager().beginTransaction().replace(R.id.container, questionFragment).commit();		
		
	}

}
