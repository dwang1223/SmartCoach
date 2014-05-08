package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import edu.wpi.smartcoach.model.Option;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.SimpleOption;
import edu.wpi.smartcoach.model.TimeQuestionModel;
import edu.wpi.smartcoach.model.exercise.Exercise;
import edu.wpi.smartcoach.model.exercise.ExerciseLocation;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.model.exercise.ExerciseTime;
import edu.wpi.smartcoach.service.ExerciseService;

public class TimeProblemSolver implements ProblemSolver {
	
	private static final int YES = 1;
	private static final int NO = 0;
	
	private ArrayList<Exercise> exercises;
	private HashMap<Exercise, Queue<QuestionModel>> questions;
	private HashMap<Exercise, ExerciseState> state;

	private String currentQuestionId;
	
	private boolean exercisesSubmitted;
	private Exercise current = null;
	
	public TimeProblemSolver(){
		exercises = new ArrayList<Exercise>();
		questions = new HashMap<Exercise, Queue<QuestionModel>>();
		state = new HashMap<Exercise, ExerciseState>();
	}
	
	@Override
	public QuestionModel getNextQuestion() {
		QuestionModel next;
		if (!exercisesSubmitted) {
			ArrayList<SimpleOption> exerciseOptions = new ArrayList<SimpleOption>();
			List<Exercise> exerciseList = ExerciseService.getInstance().getAllDataFromTable();
			for(Exercise e:exerciseList){
				exerciseOptions.add(new SimpleOption(e.getId(), e));
			}
			next = new OptionQuestionModel("exercises", "Exercises",
					"Which exercises did you try to do?", exerciseOptions,
					QuestionType.MULTIPLE);

		} else {
			
			return questions.get(current).poll();
		}
		return null;
	}

	@Override
	public void submitResponse(QuestionModel response) {
		if (!exercisesSubmitted) {
			exercisesSubmitted = true;
			
			List<Option> responseList = ((OptionQuestionModel)response).getSelectedResponses();
			for (Option op : responseList) {
				Exercise e = (Exercise)op.getModel().getValue();
				exercises.add(e);
				state.put(e, new ExerciseState());
				questions.put(e, ExerciseQuestionListBuilder.buildBasicQuestionList(e));
			}
			current = exercises.get(0);
		} else {
			String id = response.getId();
			if(id.equals("location")){
				OptionQuestionModel locationResponse =  (OptionQuestionModel)response;
				state.get(current).setLocation((ExerciseLocation)locationResponse.getSelectedResponses().get(0).getModel().getValue());
			} else if(id.equals("time")){
				OptionQuestionModel timeResponse = (OptionQuestionModel)response;
				state.get(current).setTime((ExerciseTime)timeResponse.getSelectedResponses().get(0).getModel().getValue());
				addLikeQuestion();
			} else if(id.equals("frequency")){
				OptionQuestionModel freqResponse = (OptionQuestionModel)response;
				state.get(current).setFrequency(freqResponse.getSelectedResponses().get(0).getId());
			} else if (id.equals("duration")){
				TimeQuestionModel durationResponse = (TimeQuestionModel)response;
				state.get(current).setDuration(durationResponse.getResponse());
			} else if (id.equals("like")){
				OptionQuestionModel likeResponse = (OptionQuestionModel)response;
				boolean liked = likeResponse.getSelectedResponses().get(0).getId() == YES;
				if(liked){
					state.get(current).setExerciseLiked(true);
					state.get(current).setLocationLiked(true);
					state.get(current).setTimeLiked(true);
				} else {
					
				}
			}
			
		}
		
	}
	
	private void addLikeQuestion(){
		
	}

	@Override
	public boolean hasNextQuestion() {
		if(!exercisesSubmitted){
			return true;
		} else {
			return current == exercises.get(exercises.size()-1) && questions.get(current).isEmpty();
		}
	}

	@Override
	public QuestionModel getSolution() {
		// TODO Auto-generated method stub
		return null;
	}


}
