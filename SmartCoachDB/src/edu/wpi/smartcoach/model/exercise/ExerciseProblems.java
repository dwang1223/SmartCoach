package edu.wpi.smartcoach.model.exercise;

import java.util.ArrayList;

import edu.wpi.smartcoach.model.ProblemOption;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.QuestionModel.QuestionType;
import edu.wpi.smartcoach.service.ExerciseLocationService;
import edu.wpi.smartcoach.service.ExerciseService;
import edu.wpi.smartcoach.service.ExerciseTimeService;
import edu.wpi.smartcoach.solver.MotivationProblemSolver;


public class ExerciseProblems {

	private static final int PROBLEM_TIME = 0;
	private static final int PROBLEM_MOTIVATION = 1;
	private static final int PROBLEM_BORED = 2;
	private static final int PROBLEM_INJURY = 3;
	
	public static final QuestionModel EXERCISE_TIME = new QuestionModel(
			"profile_exercise_when",
			"Time",
			"When do you prefer to exercise?",
			ExerciseTimeService.getInstance().getAllDataFromTable(),
			QuestionType.MULTIPLE);
	

	public static final QuestionModel EXERCISE_MOTIVATION = new QuestionModel(
			"profile_exercises_try",
			"Tried", 
			"Which exercises did oyu try?", 
			ExerciseService.getInstance().getAllDataFromTable(),
			QuestionType.MULTIPLE 
			);
	
	public static final QuestionModel EXERCISE_LOCATION = new QuestionModel(
			"profile_exercise_where",
			"Location", 
			"Where do you prefer to exercise?", 
			ExerciseLocationService.getInstance().getAllDataFromTable(),
			QuestionType.MULTIPLE 
			);
	
	public static final QuestionModel EXERCISE_INJURY = new QuestionModel(
			"profile_exercise_like",
			"Possible Exercises", 
			"Which exercises can you still do with your injury?", 
			ExerciseService.getInstance().getAllDataFromTable(),
			QuestionType.MULTIPLE 
			);
	

	
	
	
	public static final ArrayList<ProblemOption> problems = new  ArrayList<ProblemOption>(){{
		add(new ProblemOption(PROBLEM_TIME, "I don't have time to exercise", null));
		add(new ProblemOption(PROBLEM_MOTIVATION, "I find it hard to get started", new MotivationProblemSolver()));
		add(new ProblemOption(PROBLEM_BORED, "I get bored while exercising", null));
		add(new ProblemOption(PROBLEM_INJURY, "I have an injury that prevents me from exercising", null));
	}};
	
	public static final QuestionModel BASE_PROBLEM = new QuestionModel(
			"problem_base",
			"Problem",
			"What is the biggest problem when exercising?",
			problems,
			QuestionType.MULTIPLE,1,QuestionModel.NO_LIMIT);

}
