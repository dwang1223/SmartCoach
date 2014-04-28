package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import edu.wpi.smartcoach.model.Exercise;
import edu.wpi.smartcoach.model.ExerciseProblems;
import edu.wpi.smartcoach.model.Option;
import edu.wpi.smartcoach.model.OptionModel;
import edu.wpi.smartcoach.model.PatientExercise;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.QuestionModel.QuestionType;
import edu.wpi.smartcoach.model.SimpleOption;
import edu.wpi.smartcoach.service.impl.ExerciseLocationServiceImpl;
import edu.wpi.smartcoach.service.impl.ExerciseServiceImpl;
import edu.wpi.smartcoach.service.impl.ExerciseTimeServiceImpl;

public class MotivationProblemSolver implements ProblemSolver{
	
	private static final String TAG = MotivationProblemSolver.class.getSimpleName();

	private static final int EXERCISE_QUESTION_LOCATION = 0;
	private static final int EXERCISE_QUESTION_TIME = 1;
	private static final int EXERCISE_QUESTION_LIKE = 2; 
	
	private static final int YES = 1;
	private static final int NO = 0;
	
	private static final QuestionModel[] EXERCISE_QUESTIONS = {
		
		new QuestionModel(
				"location",
				"Location",
				"Where did you try <exercise>",
				ExerciseLocationServiceImpl.getInstance().getAllDataFromTable(),
				QuestionType.SINGLE),

		new QuestionModel(
				"time",
				"Time",
				"When did you try <exercise>",
				ExerciseTimeServiceImpl.getInstance().getAllDataFromTable(),
				QuestionType.SINGLE),
				
		new QuestionModel(
				"like",
				"Liked",
				"Did you enjoy <exercise> at <location> in the <time>?",
				new ArrayList<OptionModel>(){{
					add(new SimpleOption(YES, "Yes"));
					add(new SimpleOption(NO, "No"));
				}},
				QuestionType.SINGLE),
	};
	
	private boolean exercisesSubmitted;
	private ArrayList<PatientExercise> exercises;
	
	private int exerciseIndex;
	
	private int exerciseQuestionIndex;
	
	public MotivationProblemSolver(){
		exercisesSubmitted = false;
		exercises = new ArrayList<PatientExercise>();
		
		exerciseIndex = 0;
		exerciseQuestionIndex = 0;
		
		
	}
	

	@Override
	public QuestionModel getNextQuestion() {
		QuestionModel next = null;
		if(hasNextQuestion()){
			if(!exercisesSubmitted){
				next =  new QuestionModel(
						"exercises",
						"Exercises",
						"Which exercises did you try to do?",
						ExerciseServiceImpl.getInstance().getAllDataFromTable(),
						QuestionType.MULTIPLE);
				
			} else {
				PatientExercise current = exercises.get(exerciseIndex);
				switch(exerciseQuestionIndex){
				case EXERCISE_QUESTION_LOCATION:
					next = EXERCISE_QUESTIONS[EXERCISE_QUESTION_LOCATION].clone();
					next.setPrompt(next.getPrompt().replace("<exercise>", current.getExercise().getName()));
					break;
				case EXERCISE_QUESTION_TIME:
					next = EXERCISE_QUESTIONS[EXERCISE_QUESTION_TIME].clone();
					next.setPrompt(next.getPrompt().replace("<exercise>", current.getExercise().getName()));
					break;
				case EXERCISE_QUESTION_LIKE:
					next = EXERCISE_QUESTIONS[EXERCISE_QUESTION_LIKE].clone();
					String prompt = next.getPrompt()
							.replace("<exercise>", current.getExercise().getName())
							.replace("<location>", current.getLocation())
							.replace("<time>", current.getTime());
					next.setPrompt(prompt);			
	
				}
			}
		}

		return next;
	}

	@Override
	public void submitResponse(QuestionModel response) {
		if(!exercisesSubmitted){
			exercisesSubmitted = true;
			List<Option> responseList = response.getSelectedResponses();
			for(Option op:responseList){
				exercises.add(new PatientExercise((Exercise)op.getModel(), null, null, false));
			}
		} else {
			PatientExercise current = exercises.get(exerciseIndex);
			switch(exerciseQuestionIndex){
			case EXERCISE_QUESTION_LOCATION:
				String loc = response.getSelectedResponses().get(0).getModel().getName();
				current.setLocation(loc);
				break;
			case EXERCISE_QUESTION_TIME:
				String time = response.getSelectedResponses().get(0).getModel().getName();
				current.setTime(time);
				break;
			case EXERCISE_QUESTION_LIKE:
				boolean like = response.getSelectedResponses().get(0).getModel().getId() == YES;
				current.setLiked(like);		
				
			}
			
			exerciseQuestionIndex++;
			if(exerciseQuestionIndex == EXERCISE_QUESTIONS.length){
				exerciseQuestionIndex = 0;
				exerciseIndex++;
			}
			Log.d(TAG, "index "+exerciseIndex+":"+exerciseQuestionIndex);
		}
	}

 
	@Override
	public boolean hasNextQuestion() {
		boolean hasNext = !exercisesSubmitted || exerciseIndex < exercises.size();
		Log.d(TAG, "hasNextQuestion "+hasNext);
		return hasNext;
	}

	
	
}
