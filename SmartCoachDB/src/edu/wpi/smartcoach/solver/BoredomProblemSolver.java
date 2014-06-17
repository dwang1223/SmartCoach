package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import edu.wpi.smartcoach.model.OptionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.SimpleOption;
import edu.wpi.smartcoach.model.exercise.Exercise;
import edu.wpi.smartcoach.model.exercise.ExerciseLocation;
import edu.wpi.smartcoach.model.exercise.ExerciseSolution;
import edu.wpi.smartcoach.model.exercise.ExerciseState;

public class BoredomProblemSolver implements ProblemSolver{

	private static final String TAG = BoredomProblemSolver.class.getSimpleName();
	
	protected ArrayList<Exercise> exercises;
	protected HashMap<Exercise, ArrayList<QuestionModel>> questions;
	protected HashMap<Exercise, ExerciseState> state;
	
	protected Exercise current;
	protected int questionIndex = 0;
	protected QuestionModel currentQuestion;
	
	private boolean exercisesSubmitted;
	
	private boolean done;
	
	public BoredomProblemSolver(){
		init();
	}
	
	public void init(){
		exercises = new ArrayList<Exercise>();
		questions = new HashMap<Exercise, ArrayList<QuestionModel>>();
		state = new HashMap<Exercise, ExerciseState>();

		questionIndex = 0;
		exercisesSubmitted = false;
		done = false;
	}
	
	@Override
	public QuestionModel getNextQuestion() {
		QuestionModel next;
		if (!exercisesSubmitted) {
			
			next = ExerciseQuestionBuilder.getExerciseListQuestion();

		} else {
			
			next = currentQuestion;
		}
		return next;
	}

	@Override
	public void submitResponse(QuestionModel response) {
		
		boolean last = false;
		
		if(!exercisesSubmitted){
			exercisesSubmitted = true;
			
			List<OptionModel> responseList = ((OptionQuestionModel)response).getSelectedResponses();
			for (OptionModel op : responseList) {
				Exercise e = (Exercise)op.getValue();
				exercises.add(e);
				ExerciseState eState = new ExerciseState();
				eState.setExercise(e);
				state.put(e, eState);
				
				ArrayList<QuestionModel> qList = new ArrayList<QuestionModel>();

				qList.add(ExerciseQuestionBuilder.getLocationQuestion(e, false));
				
				questions.put(e, qList);
			}
			current = exercises.get(0);
						
		} else {
			OptionQuestionModel locResponse = (OptionQuestionModel)response;
			state.get(current).setLocation((ExerciseLocation)locResponse.getSelectedResponse().getValue());
			last = true;
		}
		
		
		//Log.d(TAG, questions.get(current).size()+"  PEEK  "+ questions.get(current).peek().toString());
		if(last){		
			if(questionIndex == questions.get(current).size()){
				if(exercises.indexOf(current) < exercises.size()-1){
					current = exercises.get(exercises.indexOf(current)+1);
					questionIndex = 0;
				} else {
					done = true;
				} 
			}
		}
		if(!done){
			currentQuestion = questions.get(current).get(questionIndex);
			questionIndex++;
		}
	}
	
	@Override
	public void back(){

		questionIndex = questions.get(current).indexOf(currentQuestion)-1;

		
		if(questionIndex == -1){
			if(exercises.indexOf(current) == 0){
				init();
				return;
			} else {
				current = exercises.get(exercises.indexOf(current)-1);
				questionIndex = questions.get(current).size()-1;
			}
		}
		
		currentQuestion = questions.get(current).get(questionIndex);
		questionIndex++;
	}
	
	@Override
	public boolean isBackAllowed(){
		return exercisesSubmitted;
	}

	@Override
	public boolean hasNextQuestion() {
		return !done;
	}

	@Override
	public QuestionModel getSolution(Context ctx) {
		List<ExerciseSolution> solutions = Solutions.getBoredomSolutions(new ArrayList<ExerciseState>(state.values()), ctx);

		solutions.addAll(Solutions.getNewExerciseRecommendation(new ArrayList<ExerciseState>(), ctx));
		ArrayList<OptionModel> options = new ArrayList<OptionModel>();
		
		for(ExerciseSolution s:solutions){
			options.add(new SimpleOption(solutions.size(), s));
		}
				
		return new OptionQuestionModel("solutions", "Solutions", 
				"Try some of these to make exercise a little more interesting",
				options, 
				QuestionType.MULTIPLE, false);
	}

}
