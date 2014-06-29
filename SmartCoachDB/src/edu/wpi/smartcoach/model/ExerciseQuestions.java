package edu.wpi.smartcoach.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.util.QuestionReader;
import edu.wpi.smartcoach.view.Option;

public class ExerciseQuestions {

	private static Context context;
	private static ExerciseQuestions instance;
	
	private static final String PLACEHOLDER_EXERCISE = "\\[exercise\\]";
	private static final String PLACEHOLDER_EXERCISE_PRESENT = "\\[exercise-present\\]";
	private static final String PLACEHOLDER_EXERCISE_CONTINUOUS = "\\[exercise-continuous\\]";
	
	private static final String PLACEHOLDER_TIME = "\\[time-in\\]";
	private static final String PLACEHOLDER_LOCATION = "\\[location\\]";
	
	private static final String PLACEHOLDER_WEEK = "\\[week\\]";
	
	private static final String ON_WEEKENDS = " on weekends";
	private static final String ON_WEEKDAYS = " on weekdays";
	
	private HashMap<String, QuestionModel> questions;
	
	public static void setContext(Context ctx){
		context = ctx;
	}
	
	public static ExerciseQuestions getInstance(){
		if(instance == null){
			instance = new ExerciseQuestions();
		}
		return instance;
	}
	
	private ExerciseQuestions(){
		questions = new HashMap<String, QuestionModel>();
		
		List<QuestionModel> qList = QuestionReader.readQuestions(R.raw.exercise_questions, context);
		
		for(QuestionModel q:qList){
			questions.put(q.getId(), q);
		}
		
	}
	
	public QuestionModel getRawQuestion(String id){
		return questions.get(id);		
	}
	
	public OptionQuestionModel getOptionQuestion(String id, ExerciseState state, boolean weekend){
		QuestionModel qm = questions.get(id);
		OptionQuestionModel oqm = null;
		
		if(qm != null && qm instanceof OptionQuestionModel){
			oqm = prepareQuestion((OptionQuestionModel)qm, state, weekend);
		}
		
		return oqm;
	}
	
	public TimeQuestionModel getTimeQuestion(String id, ExerciseState state, boolean weekend){
		QuestionModel qm = questions.get(id);
		TimeQuestionModel tqm = null;
		
		if(qm != null && qm instanceof TimeQuestionModel){
			tqm = prepareQuestion((TimeQuestionModel)qm, state, weekend);
		}
		
		return tqm;
	}
	
	public TimeQuestionModel prepareQuestion(TimeQuestionModel q, ExerciseState state, boolean weekend){

		String prompt = q.getPrompt();
		String newPrompt = prepareString(prompt, state, weekend);
		
		TimeQuestionModel newModel = new TimeQuestionModel(q.getId(), q.getTitle(), newPrompt);
		
		return newModel;
	}
	
	public OptionQuestionModel prepareQuestion(OptionQuestionModel q, ExerciseState state, boolean weekend){
		
		String prompt = q.getPrompt();
		String newPrompt = prepareString(prompt, state, weekend);
		
		List<Option> options = q.getOptions();
		List<Option> newOptions = new ArrayList<Option>();	
		
		for(Option option:options){
			Object value = option.getValue();
			Object formatted = value;
			
			if(value instanceof String){
				formatted = prepareString((String)value, state, weekend);
			}
			
			newOptions.add(new Option(option.getId(), formatted));
		}
		
		OptionQuestionModel newModel = new OptionQuestionModel(q.getId(), q.getTitle(), newPrompt, newOptions, q.getType(), q.isSorted());	
		
		return newModel;
	}
	
		
	private String prepareString(String original, ExerciseState state, boolean weekend){
		String modified = original
				.replaceAll(PLACEHOLDER_EXERCISE, state.getExercise().getName().toLowerCase())
				.replaceAll(PLACEHOLDER_EXERCISE_PRESENT, state.getExercise().getFormPresent())
				.replaceAll(PLACEHOLDER_EXERCISE_CONTINUOUS, state.getExercise().getFormContinuous())
				.replaceAll(PLACEHOLDER_TIME, state.getTime().getPrepositionIn())
				.replaceAll(PLACEHOLDER_LOCATION, state.getLocation().getPreposition())
				.replaceAll(PLACEHOLDER_WEEK, getWeekendString(state, weekend));
		
		return modified;
	}
	
	private String getWeekendString(ExerciseState state, boolean weekend){
		String wkEndString = "";
		if(state.isOnWeekdays() && state.isOnWeekends()){
			if(weekend){
				wkEndString = ON_WEEKENDS;
			} else {
				wkEndString = ON_WEEKDAYS;
			}
		}
		return wkEndString;
	}

}
