package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import android.content.Context;
import android.util.Log;
import edu.wpi.smartcoach.model.OptionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.SimpleOption;
import edu.wpi.smartcoach.model.TimeQuestionModel;
import edu.wpi.smartcoach.model.exercise.Exercise;
import edu.wpi.smartcoach.model.exercise.ExerciseLocation;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.model.exercise.ExerciseTime;

public class BaseProblemSolver implements ProblemSolver {
	
	private static final String TAG = BaseProblemSolver.class.getSimpleName();
	
	public static final int YES = 1;
	public static final int NO = 0;
	
	public static final int EXERCISE = 0;
	public static final int TIME = 1;
	public static final int LOCATION = 2;
	
	protected ArrayList<Exercise> exercises;
	protected HashMap<Exercise, Queue<QuestionModel>> questions;
	protected HashMap<Exercise, ExerciseState> state;

	protected boolean exercisesSubmitted = false;
	protected Exercise current = null;
	protected QuestionModel nextQuestion = null;
	
	public BaseProblemSolver(){
		exercises = new ArrayList<Exercise>();
		questions = new HashMap<Exercise, Queue<QuestionModel>>();
		state = new HashMap<Exercise, ExerciseState>();
	}
	
	@Override
	public QuestionModel getNextQuestion() {
		QuestionModel next;
		if (!exercisesSubmitted) {
			
			next = ExerciseQuestionBuilder.getExerciseListQuestion();

		} else {
			
			next = nextQuestion;
		}
		return next;
	}

	@Override
	public void submitResponse(QuestionModel response) {
		if (!exercisesSubmitted) {
			exercisesSubmitted = true;
			
			List<OptionModel> responseList = ((OptionQuestionModel)response).getSelectedResponses();
			for (OptionModel op : responseList) {
				Exercise e = (Exercise)op.getValue();
				exercises.add(e);
				ExerciseState eState = new ExerciseState();
				eState.setExercise(e);
				state.put(e, eState);
				questions.put(e, ExerciseQuestionBuilder.buildBasicQuestionList(e));
			}
			current = exercises.get(0);
		} else {
			String id = response.getId();
			if(id.equals("location")){
				
				OptionQuestionModel locationResponse =  (OptionQuestionModel)response;
				state.get(current).setLocation((ExerciseLocation)locationResponse.getSelectedResponse().getValue());
				
			} else if(id.equals("time")){
				
				OptionQuestionModel timeResponse = (OptionQuestionModel)response;
				state.get(current).setTime((ExerciseTime)timeResponse.getSelectedResponse().getValue());
				addLikeQuestion();
				
			} else if(id.equals("frequency")){
				
				OptionQuestionModel freqResponse = (OptionQuestionModel)response;
				state.get(current).setFrequency(freqResponse.getSelectedResponse().getId());
				
			} else if (id.equals("duration")){
				
				TimeQuestionModel durationResponse = (TimeQuestionModel)response;
				state.get(current).setDuration(durationResponse.getResponse());
				
			} else if (id.equals("like")){
				
				OptionQuestionModel likeResponse = (OptionQuestionModel)response;
				boolean liked = likeResponse.getSelectedResponse().getId() == YES;
				state.get(current).setExerciseLiked(true);
				state.get(current).setLocationLiked(true);
				state.get(current).setTimeLiked(true);
				if(liked){
					addIncreaseExerciseQuestion();
				} else {
					addAspectQuestion();
				}
				
			} else if(id.equals("increase")){
				
				OptionQuestionModel increaseResponse = (OptionQuestionModel)response;
				boolean wouldIncrease = increaseResponse.getSelectedResponse().getId() == YES;
				state.get(current).setWouldIncrease(wouldIncrease);
				
			} else if(id.equals("aspect")){
				
				int problem = ((OptionQuestionModel)response).getSelectedResponse().getId();
				switch (problem) {
					case EXERCISE:
						state.get(current).setExerciseLiked(false);
						break;
					case TIME:
						state.get(current).setTimeLiked(false);
						break;
					case LOCATION:
						state.get(current).setLocationLiked(false);
						break;
					default:
						break;
				}
			}			
		}
		
		//Log.d(TAG, questions.get(current).size()+"  PEEK  "+ questions.get(current).peek().toString());
		if(questions.get(current).isEmpty()){
			if(exercises.indexOf(current)+1 < exercises.size()){
				current = exercises.get(exercises.indexOf(current)+1);
			} 
		}
		nextQuestion = questions.get(current).poll();	
		
	}
	
	private void addLikeQuestion(){
		ExerciseState cState = state.get(current);
		Log.d(TAG, "adding like question");
		String prompt = String.format("Did you enjoy %s %s in the %s?", cState.getExercise().getName().toLowerCase(),
				cState.getLocation().getPreposition(), 
				cState.getTime().getTime().toLowerCase());
		
		ArrayList<OptionModel> yesNoOptions = new ArrayList<OptionModel>();
		yesNoOptions.add(new SimpleOption(YES, "Yes"));
		yesNoOptions.add(new SimpleOption(NO, "No"));
		
		questions.get(current).add(
				new OptionQuestionModel("like", "Liked", prompt,
					yesNoOptions,
					QuestionType.SINGLE, 1, OptionQuestionModel.NO_LIMIT));	
	}
	
	private void addIncreaseExerciseQuestion(){
		ExerciseState cState = state.get(current);
		Log.d(TAG, "adding increase question");
		String prompt = String.format("Would you consider %s more often?", cState.getExercise().getName().toLowerCase());

		ArrayList<OptionModel> yesNoOptions = new ArrayList<OptionModel>();
		yesNoOptions.add(new SimpleOption(YES, "Yes"));
		yesNoOptions.add(new SimpleOption(NO, "No"));
		
		questions.get(current).add(	new OptionQuestionModel("increase", "Increase", prompt,
				yesNoOptions,
				QuestionType.SINGLE, 1, OptionQuestionModel.NO_LIMIT));
	}

	private void addAspectQuestion(){
		Log.d(TAG, "adding aspect question");
		ExerciseState cState = state.get(current);
		ArrayList<OptionModel> options = new ArrayList<OptionModel>();
		options.add(new SimpleOption(EXERCISE, "Exercise"));
		options.add(new SimpleOption(TIME, "Time"));
		options.add(new SimpleOption(LOCATION, "Location"));
		
		String prompt = String.format("Which of the following was the problem with %s?", cState.getExercise().getName().toLowerCase());
		
		questions.get(current).add(new OptionQuestionModel("aspect", "Problem", prompt, 
				options,
				QuestionType.SINGLE, 1, OptionQuestionModel.NO_LIMIT));
	}
	
	@Override
	public boolean hasNextQuestion() {
		if(!exercisesSubmitted){
			return true;
		} else {
		if(current == exercises.get(exercises.size()-1) && questions.get(current).isEmpty() && nextQuestion == null){
				return false;
			} else {
				return true;
			}
		}
	}
	
	@Override
	public QuestionModel getSolution(Context ctx) {
		return null;
	}
}
