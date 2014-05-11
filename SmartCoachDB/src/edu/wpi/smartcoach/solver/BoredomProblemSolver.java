package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.content.Context;
import edu.wpi.smartcoach.model.ExerciseSolution;
import edu.wpi.smartcoach.model.OptionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.SimpleOption;
import edu.wpi.smartcoach.model.exercise.Exercise;
import edu.wpi.smartcoach.model.exercise.ExerciseLocation;
import edu.wpi.smartcoach.model.exercise.ExerciseState;

public class BoredomProblemSolver implements ProblemSolver{

	protected ArrayList<Exercise> exercises;
	protected HashMap<Exercise, Queue<QuestionModel>> questions;
	protected HashMap<Exercise, ExerciseState> state;
	
	protected Exercise current;
	protected QuestionModel nextQuestion;
	
	private boolean exercisesSubmitted;
	
	public BoredomProblemSolver(){
		exercises = new ArrayList<Exercise>();
		questions = new HashMap<Exercise, Queue<QuestionModel>>();
		state = new HashMap<Exercise, ExerciseState>();
		
		exercisesSubmitted = false;
	}
	
	@Override
	public QuestionModel getNextQuestion() {
		QuestionModel next;
		if (!exercisesSubmitted) {
			
			next = ExerciseQuestionListBuilder.getExerciseListQuestion();

		} else {
			
			next = nextQuestion;
		}
		return next;
	}

	@Override
	public void submitResponse(QuestionModel response) {
		if(!exercisesSubmitted){
			exercisesSubmitted = true;
			
			List<OptionModel> responseList = ((OptionQuestionModel)response).getSelectedResponses();
			for (OptionModel op : responseList) {
				Exercise e = (Exercise)op.getValue();
				exercises.add(e);
				ExerciseState eState = new ExerciseState();
				eState.setExercise(e);
				state.put(e, eState);
				
				LinkedList<QuestionModel> qList = new LinkedList<QuestionModel>();

				qList.add(ExerciseQuestionListBuilder.getLocationQuestion(e));
				
				questions.put(e, qList);
			}
			current = exercises.get(0);
			
		} else {
			OptionQuestionModel locResponse = (OptionQuestionModel)response;
			state.get(current).setLocation((ExerciseLocation)locResponse.getSelectedResponse().getValue());
		}
		
		
		if(questions.get(current).isEmpty()){
			if(exercises.indexOf(current)+1 < exercises.size()){
				current = exercises.get(exercises.indexOf(current)+1);
			} 
		}
		nextQuestion = questions.get(current).poll();	
		
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
		List<ExerciseSolution> solutions = Solutions.getBoredomSolutions(new ArrayList<ExerciseState>(state.values()), ctx);
		
		ArrayList<OptionModel> options = new ArrayList<OptionModel>();
		
		for(ExerciseSolution s:solutions){
			options.add(new SimpleOption(s.getId(), s));
		}
				
		return new OptionQuestionModel("solutions", "Solutions", 
				"Try some of these to make exercise a little more interesting",
				options, 
				QuestionType.MULTIPLE);
	}

}
