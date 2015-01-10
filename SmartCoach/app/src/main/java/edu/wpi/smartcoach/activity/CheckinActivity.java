package edu.wpi.smartcoach.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.Option;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.WeightQuestionModel;
import edu.wpi.smartcoach.reminders.Reminder;
import edu.wpi.smartcoach.service.ReminderService;
import edu.wpi.smartcoach.service.WeightService;
import edu.wpi.smartcoach.util.Callback;
import edu.wpi.smartcoach.view.QuestionFragment;

public class CheckinActivity extends FragmentActivity implements Callback<QuestionModel> {
	
	private List<QuestionModel> questions;
    private int index = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkin);
		
		boolean weighInOnly = getIntent().getBooleanExtra("weighInOnly", false);
		
		questions = new ArrayList<QuestionModel>();
		questions.add(new WeightQuestionModel("weight", "What is your current weight?"));
	
		List<Reminder> reminders = ReminderService.getInstance().getAllDataFromTable(this);
		
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
		
		if(!weighInOnly){
				
			if(reminders.size() > 0){
				questions.add(successes);
				questions.add(repeat);
			}
            questions.add(doDiet);
			questions.add(doExercise);
		}
		
		QuestionFragment qFrag = QuestionFragment.createQuestion(questions.get(0));
		qFrag.setNextButtonListener(this);
		getSupportFragmentManager()
			.beginTransaction()
			.add(R.id.container, qFrag)
			.commit();

	}

    /**
     * Called when a question is responded to
     * @param question
     */
	@Override
	public void callback(QuestionModel question) {
		
		if(question.getId().equals("diet")){
			if(((OptionQuestionModel)question).getSelectedOption().getId().equals("yes")){
				startActivity(new Intent(this, DietProblemActivity.class));
			}				
		} else if (question.getId().equals("exercise")){
			if(((OptionQuestionModel)question).getSelectedOption().getId().equals("yes")){
				startActivity(new Intent(this, ExerciseProblemActivity.class));
			}
		} else if(question.getId().equals("weight")){
			
			float weight = ((WeightQuestionModel)question).getWeight();
			long id = System.currentTimeMillis();
			if(weight > 0){
				WeightService.getInstance().addWeight(id, weight, this);
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
