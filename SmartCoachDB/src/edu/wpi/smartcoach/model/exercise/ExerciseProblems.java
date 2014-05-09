package edu.wpi.smartcoach.model.exercise;

import java.util.ArrayList;

import edu.wpi.smartcoach.model.OptionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.ProblemOption;
import edu.wpi.smartcoach.model.SimpleOption;
import edu.wpi.smartcoach.service.ExerciseLocationService;
import edu.wpi.smartcoach.service.ExerciseService;
import edu.wpi.smartcoach.service.ExerciseTimeService;
import edu.wpi.smartcoach.solver.MotivationProblemSolver;
import edu.wpi.smartcoach.solver.TimeProblemSolver;


public class ExerciseProblems {

	private static final int PROBLEM_TIME = 0;
	private static final int PROBLEM_MOTIVATION = 1;
	private static final int PROBLEM_BORED = 2;
	private static final int PROBLEM_INJURY = 3;
	
	public static final OptionQuestionModel EXERCISE_TIME = new OptionQuestionModel(
			"profile_exercise_when",
			"Time",
			"When do you prefer to exercise?",
			new ArrayList<OptionModel>(){{
				for(ExerciseTime et:ExerciseTimeService.getInstance().getAllDataFromTable()){
					add(new SimpleOption(et.getId(), et));
				}
			}},
			QuestionType.MULTIPLE);
	

	public static final OptionQuestionModel EXERCISE_MOTIVATION = new OptionQuestionModel(
			"profile_exercises_try",
			"Tried", 
			"Which exercises did oyu try?", 
			new ArrayList<OptionModel>(){
				{
					for(Exercise e:ExerciseService.getInstance().getAllDataFromTable()){
						add(new SimpleOption(e.getId(), e));
					}
				}
			},
			QuestionType.MULTIPLE 
			);
	
	public static final OptionQuestionModel EXERCISE_LOCATION = new OptionQuestionModel(
			"profile_exercise_where",
			"Location", 
			"Where do you prefer to exercise?", 
			new ArrayList<OptionModel>(){
				{
					for(ExerciseLocation e:ExerciseLocationService.getInstance().getAllDataFromTable()){
						add(new SimpleOption(e.getId(), e));
					}
				}
			}
			,
			QuestionType.MULTIPLE 
			);

	
	
	public static final ArrayList<ProblemOption> problems = new  ArrayList<ProblemOption>(){{
		add(new ProblemOption(PROBLEM_TIME, "I don't have time to exercise", new TimeProblemSolver()));
		add(new ProblemOption(PROBLEM_MOTIVATION, "I find it hard to get started", new MotivationProblemSolver()));
		add(new ProblemOption(PROBLEM_BORED, "I get bored while exercising", null));
		add(new ProblemOption(PROBLEM_INJURY, "I have an injury that prevents me from exercising", null));
	}};
	
	public static final OptionQuestionModel BASE_PROBLEM = new OptionQuestionModel(
			"problem_base",
			"Problem",
			"What is the biggest problem when exercising?",
			problems,
			QuestionType.SINGLE);

}
