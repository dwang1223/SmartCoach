package edu.wpi.smartcoach.model;

import java.util.ArrayList;

import edu.wpi.smartcoach.model.QuestionModel.QuestionType;
import edu.wpi.smartcoach.service.ExerciseService;
import edu.wpi.smartcoach.service.ExerciseTimeService;

public class ExerciseProblems {
	
	private static final int PROBLEM_NOLIKE = 0;
	private static final int PROBLEM_TIME = 1;
	private static final int PROBLEM_MOTIVATION = 2;
	private static final int PROBLEM_INJURY = 3;
	
	public static final QuestionModel EXERCISE_TIME = new QuestionModel(
			"profile_exercise_when",
			"Time",
			"When do you prefer to exercise?",
			ExerciseTimeService.getInstance().getAllDataFromTable(),
			QuestionType.MULTIPLE);
	
	public static final QuestionModel EXERCISE_NOLIKE = new QuestionModel(
			"profile_exercise_hate",
			"Least Favorites", 
			"Which exercises have tried and not liked?", 
			ExerciseService.getInstance().getAllDataFromTable(),
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
		add(new ProblemOption(PROBLEM_NOLIKE, "I don't like to exercise", EXERCISE_NOLIKE));
		add(new ProblemOption(PROBLEM_TIME, "I don't have time to exercise", EXERCISE_TIME));
		add(new ProblemOption(PROBLEM_MOTIVATION, "I am not motivated to exercise", null));
		add(new ProblemOption(PROBLEM_INJURY, "I have an injury that prevents me from exercising", EXERCISE_INJURY));
	}};
	
	public static final QuestionModel BASE_PROBLEM = new QuestionModel(
			"problem_base",
			"Problem",
			"What is the biggest problem when exercising?",
			problems,
			QuestionType.MULTIPLE);

}
