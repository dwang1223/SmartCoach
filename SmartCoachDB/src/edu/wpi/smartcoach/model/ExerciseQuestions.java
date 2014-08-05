package edu.wpi.smartcoach.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.util.Log;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.exercise.ExerciseLocation;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.model.exercise.ExerciseTime;
import edu.wpi.smartcoach.service.ExerciseToLocationService;
import edu.wpi.smartcoach.util.QuestionReader;
import edu.wpi.smartcoach.view.Option;

public class ExerciseQuestions {

	private static final String TAG = ExerciseQuestions.class.getSimpleName();
	
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
	
	public TimeQuestionModel getTimeQuestion(String id){
		QuestionModel qm = questions.get(id);
		TimeQuestionModel timeQuestion = null;
		
		if(qm instanceof TimeQuestionModel){
			timeQuestion = (TimeQuestionModel)qm;
		}
		
		return timeQuestion.clone();
	}
	
	public OptionQuestionModel getOptionQuestion(String id){
		QuestionModel qm = questions.get(id);
		OptionQuestionModel optionQuestion = null;
		
		if(qm instanceof OptionQuestionModel){
			optionQuestion = (OptionQuestionModel)qm;
		}
		
		return optionQuestion.clone();
	}
	
	public QuestionModel prepareQuestion(QuestionModel q, ExerciseState state, boolean weekend){
		QuestionModel question = q;
		if(question instanceof TimeQuestionModel){
			question = prepareQuestion((TimeQuestionModel)question, state, weekend);
		} else if (question instanceof OptionQuestionModel){
			question = prepareQuestion((OptionQuestionModel)question, state, weekend);
		}
		return question;
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
		
		//Special cases for options
		if(q.getId().equals("exercise_frequency") && weekend){
			Iterator<Option> iter = options.iterator();
			while(iter.hasNext()){
				Option opt = iter.next();
				Log.d(TAG, opt.getId());
				if(Integer.parseInt(opt.getId()) > 2){
					iter.remove();
				}				
			}
		} else if (q.getId().equals("exercise_location")){
			List<ExerciseLocation> locations = ExerciseToLocationService.getInstance().getLocationListByExercise(state.getExercise().getId());
			options = new ArrayList<Option>();
			for(ExerciseLocation location:locations){
				options.add(new Option(location.getId()+"", location));
			}
		}
		
		List<Option> newOptions = new ArrayList<Option>();	
		
		for(Option option:options){
			Object value = option.getValue();
			Object formatted = value;
			
			if(value instanceof String){
				formatted = prepareString((String)value, state, weekend);
			}
			
			newOptions.add(new Option(option.getId(), formatted));
		}
		
		OptionQuestionModel newModel = new OptionQuestionModel(q.getId(), q.getTitle(), newPrompt, newOptions, q.getType(), q.isSorted(), q.isSearchable());	
		
		return newModel;
	}
	
		
	private String prepareString(String original, ExerciseState state, boolean weekend){
		String modified = original;
		
		if(state != null){
		
			if(state.getExercise() != null){
				modified = original.replaceAll(PLACEHOLDER_EXERCISE, state.getExercise().getName().toLowerCase());
				modified = modified.replaceAll(PLACEHOLDER_EXERCISE_PRESENT, state.getExercise().getFormPresent());
				modified = modified.replaceAll(PLACEHOLDER_EXERCISE_CONTINUOUS, state.getExercise().getFormContinuous());
			}
			
			if(weekend){
				if(state.getWeekendTime() != null){
					modified = modified.replaceAll(PLACEHOLDER_TIME, state.getWeekendTime().getPrepositionIn());
				}
				
				if(state.getWeekendLocation() != null){
					modified = modified.replaceAll(PLACEHOLDER_LOCATION, state.getWeekendLocation().getPreposition());
				}
			} else {
				if(state.getTime() != null){
					modified = modified.replaceAll(PLACEHOLDER_TIME, state.getTime().getPrepositionIn());
				}
				
				if(state.getLocation() != null){
					modified = modified.replaceAll(PLACEHOLDER_LOCATION, state.getLocation().getPreposition());
				}
			}
			
			
			modified = modified.replaceAll(PLACEHOLDER_WEEK, getWeekendString(state, weekend));
			
		}
		return modified;
	}
	
	public String getWeekendString(ExerciseState state, boolean weekend){
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
	
	public void doResponse(QuestionModel response, ExerciseState state, boolean weekend){ 
		Log.d(TAG,"response="+response.getId()+","+weekend);
		Log.d(TAG, "location is now "+state.getWeekendLocation());
		String id = response.getId();
		if(id.equals("exercise_location")){
			ExerciseLocation location = (ExerciseLocation)((OptionQuestionModel)response).getSelectedValue();
			if(weekend){
				Log.d(TAG,"setting location "+ location);
				state.setWeekendLocation(location);
			} else {
				state.setLocation(location);
			}
		} else if (id.equals("exercise_time")){
			ExerciseTime time = (ExerciseTime)((OptionQuestionModel)response).getSelectedValue();
			if(weekend){
				state.setWeekendTime(time);
			} else {
				state.setTime(time);
			}
		} else if(id.equals("exercise_frequency")){
			int frequency = Integer.parseInt(((OptionQuestionModel)response).getSelectedOption().getId());
			if(weekend){
				state.setWeekendFrequency(frequency);
			} else {
				state.setFrequency(frequency);
			}
		} else if(id.equals("exercise_duration")){
			int duration = ((TimeQuestionModel)response).getResponse();
			if(weekend){
				state.setWeekendDuration(duration);
			} else {
				state.setDuration(duration);
			}
		} else if (id.equals("exercise_like")){
			List<Option> selectedOptions = ((OptionQuestionModel)response).getSelectedOptions();
			String selected = "";
			for(Option o:selectedOptions){
				selected += o.getId()+"|";
			}
			
			Log.d(TAG, selected.toString());
			
			if(weekend){
				if(selected.contains("like_like")){
					state.setWeekendExerciseLiked(true);
					state.setWeekendLocationLiked(true);
					state.setWeekendTimeLiked(true);
				}

				if(selected.contains("like_exercise")){
					state.setWeekendExerciseLiked(false);
				}
				
				if(selected.contains("like_location")){
					state.setWeekendLocationLiked(false);
				}
				
				if(selected.contains("like_time")){
					state.setWeekendTimeLiked(false);
				}
				
				if(selected.contains("like_frequency")){
					state.setWeekendIncreaseFrequency(true);
				}
				
				if(selected.contains("like_duration")){
					state.setWeekendIncreaseDuration(true);
				}				
				
			} else {
				if(selected.contains("like_like")){
					state.setExerciseLiked(true);
					state.setLocationLiked(true);
					state.setTimeLiked(true);
				}

				if(selected.contains("like_exercise")){
					state.setExerciseLiked(false);
				}
				
				if(selected.contains("like_location")){
					state.setLocationLiked(false);
				}
				
				if(selected.contains("like_time")){
					state.setTimeLiked(false);
				}
				
				if(selected.contains("like_frequency")){
					state.setIncreaseFrequency(true);
				}
				
				if(selected.contains("like_duration")){
					state.setIncreaseDuration(true);
				}
			}
			
		}
	}

}
