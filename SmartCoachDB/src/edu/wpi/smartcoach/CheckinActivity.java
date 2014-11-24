package edu.wpi.smartcoach;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import edu.wpi.smartcoach.activity.DietProblemActivity;
import edu.wpi.smartcoach.activity.ExerciseProblemActivity;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.WeightQuestionModel;
import edu.wpi.smartcoach.reminders.Reminder;
import edu.wpi.smartcoach.service.ReminderService;
import edu.wpi.smartcoach.view.Option;
import edu.wpi.smartcoach.view.QuestionFragment;
import edu.wpi.smartcoach.view.QuestionResponseListener;

public class CheckinActivity extends FragmentActivity implements QuestionResponseListener {
	
	List<QuestionModel> questions;
	int index = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkin);
		setTitle("SmartCoach Check-In");
		
		questions = new ArrayList<QuestionModel>();
		questions.add(new WeightQuestionModel("weight", "What is your current weight?"));
	
		List<Reminder> reminders = ReminderService.getInstance().getAllDataFromTable();
		
		List<Option> successesOptions = new ArrayList<Option>();
		List<Option> repeatOptions = new ArrayList<Option>();
		
		for(Reminder r:reminders){
			successesOptions.add(new Option(r.getId()+"", r));
			repeatOptions.add(new Option(r.getId()+"", r));
		}

		List<Option> dietYesNo = new ArrayList<Option>(){
			{
				add(new Option("yes", "Yes"));
				add(new Option("no", "No"));
			}
		};
		
		List<Option> exerciseYesNo = new ArrayList<Option>(){
			{
				add(new Option("yes", "Yes"));
				add(new Option("no", "No"));
			}
		};
		
		QuestionModel successes = new OptionQuestionModel("successes", "", 
				"Which of these did you accomplish this week?",
				successesOptions, OptionQuestionModel.QuestionType.MULTIPLE, false, false);
		
		QuestionModel repeat = new OptionQuestionModel("repeat", "",
				"Check all the reminders you want to reschedule for the coming week.", 
				repeatOptions, QuestionType.MULTIPLE, false, false);
		
		QuestionModel doDiet = new OptionQuestionModel("diet", "", 
				"Do you want to find new diet solutions?",dietYesNo, QuestionType.SINGLE, false, false);
		QuestionModel doExercise = new OptionQuestionModel("exercise", "",
				"Do you want to find new exercise solutions?", exerciseYesNo, QuestionType.SINGLE, false, false);
		
		questions.add(successes);
		questions.add(repeat);
		questions.add(doDiet);
		questions.add(doExercise);
		
		QuestionFragment qFrag = QuestionFragment.createQuestion(questions.get(0));
		qFrag.setNextButtonListener(this);
		getSupportFragmentManager()
			.beginTransaction()
			.add(R.id.container, qFrag)
			.commit();
//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
//		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkin, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_checkin, container, false);
			return rootView;
		}
	}

	@Override
	public void responseEntered(QuestionModel question) {
		
		if(question.getId().equals("diet")){
			if(((OptionQuestionModel)question).getSelectedOption().getId().equals("yes")){
				startActivity(new Intent(this, DietProblemActivity.class));
			}				
		} else if (question.getId().equals("exercise")){
			if(((OptionQuestionModel)question).getSelectedOption().getId().equals("yes")){
				startActivity(new Intent(this, ExerciseProblemActivity.class));
			}
		}
			
		index++;
		if(index < questions.size()){
			QuestionFragment qFrag = QuestionFragment.createQuestion(questions.get(index));
			qFrag.setNextButtonListener(this);
			getSupportFragmentManager()
			.beginTransaction()
			.add(R.id.container, qFrag)
			.commit();
		} else {
			finish();
		}
		
	}
}
