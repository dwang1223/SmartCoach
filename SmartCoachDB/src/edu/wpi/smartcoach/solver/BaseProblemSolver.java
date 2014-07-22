package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import edu.wpi.smartcoach.model.ExerciseQuestions;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.TimeQuestionModel;
import edu.wpi.smartcoach.model.exercise.Exercise;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.view.Option;

public class BaseProblemSolver implements ProblemSolver {
	
	private static final String TAG = BaseProblemSolver.class.getSimpleName();

	//protected ArrayList<Exercise> exercises;
	protected ArrayList<ExerciseState> states;
	protected ArrayList<QuestionStateEntry> questions;

	protected QuestionStateEntry current = null;
	
	protected OptionQuestionModel weekQuestion;

	private boolean isDone = false;
	
	public BaseProblemSolver(){
		init();
	}
	
	public void init(){
		//exercises = new ArrayList<Exercise>();
		states = new ArrayList<ExerciseState>();
		questions = new ArrayList<QuestionStateEntry>();
		
		OptionQuestionModel exercisesQuestion = ExerciseQuestions.getInstance().getOptionQuestion("exercises");
		QuestionStateEntry exerciseEntry = new QuestionStateEntry(exercisesQuestion, null, false);
		weekQuestion = ExerciseQuestions.getInstance().getOptionQuestion("exercises_week_grid");
		
		questions.add(exerciseEntry);
		questions.add(new QuestionStateEntry(weekQuestion, null, false));
		
		current = exerciseEntry;
		
		isDone = false;
	}
	
	@Override
	public QuestionModel getNextQuestion() {
		return ExerciseQuestions.getInstance().prepareQuestion(current.getQuestion(), current.getState(),current.isWeekend());
	}

	@Override
	public void submitResponse(QuestionModel response) {
		String id = response.getId();
		
		if(id.equals("exercises")){
			List<Object> selected = ((OptionQuestionModel)response).getSelectedValues();
			List<Option> options = new ArrayList<Option>();
			for(Object ob:selected){
				if(ob instanceof Exercise){
					ExerciseState newState = new ExerciseState();
					newState.setExercise((Exercise)ob);
					states.add(newState);
					options.add(new Option(newState.getExercise().getName(), newState));
				}
			}
			weekQuestion.setOptions(options);
		} else if(id.equals("exercises_week_grid")){
			
			while(questions.size() > questions.indexOf(current)+1){
				questions.remove(questions.indexOf(current)+1);
			}
			
			for(ExerciseState state:states){
				if(state.isOnWeekdays()){
					addStateQuestions(state, false);
				}
				
				if(state.isOnWeekends()){
					addStateQuestions(state, true);
				}
			}
		} else {
			ExerciseQuestions.getInstance().doResponse(response, current.getState(), current.isWeekend());
		}
		
		int nextIndex = questions.indexOf(current)+1;
		if(nextIndex < questions.size()){
			current = questions.get(nextIndex);
		} else {
			isDone = true;
		}
	}
	
	protected void addStateQuestions(ExerciseState state, boolean weekend){
		OptionQuestionModel location = ExerciseQuestions.getInstance().getOptionQuestion("exercise_location");
		OptionQuestionModel time = ExerciseQuestions.getInstance().getOptionQuestion("exercise_time");
		OptionQuestionModel frequency = ExerciseQuestions.getInstance().getOptionQuestion("exercise_frequency");
		TimeQuestionModel duration = ExerciseQuestions.getInstance().getTimeQuestion("exercise_duration");
		OptionQuestionModel like = ExerciseQuestions.getInstance().getOptionQuestion("exercise_like");

		questions.add(new QuestionStateEntry(location, state, weekend));
		questions.add(new QuestionStateEntry(time, state, weekend));
		questions.add(new QuestionStateEntry(frequency, state, weekend));
		questions.add(new QuestionStateEntry(duration, state, weekend));
		questions.add(new QuestionStateEntry(like, state, weekend));
		
	}
	
	@Override
	public void back(){	
		int prevIndex = questions.indexOf(current)-1;
		
		if(prevIndex >= 0){
			current = questions.get(prevIndex);
		}
		
		if(prevIndex == 0){
			init();
		}
	}
	
	@Override
	public boolean isBackAllowed(){
		return questions.indexOf(current) != 0;
	}
	
	@Override
	public boolean hasNextQuestion() {
		return !isDone;
	}
	
	@Override
	public QuestionModel getSolution(Context ctx) {
		return null;
	}
}
