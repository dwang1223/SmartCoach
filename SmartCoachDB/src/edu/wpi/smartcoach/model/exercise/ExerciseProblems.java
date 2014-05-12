package edu.wpi.smartcoach.model.exercise;

import java.util.ArrayList;

import android.content.Context;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.SimpleOption;
import edu.wpi.smartcoach.solver.BoredomProblemSolver;
import edu.wpi.smartcoach.solver.InjuryProblemSolver;
import edu.wpi.smartcoach.solver.MotivationProblemSolver;
import edu.wpi.smartcoach.solver.ProblemSolver;
import edu.wpi.smartcoach.solver.TimeProblemSolver;


public class ExerciseProblems {

	private static final int PROBLEM_TIME = 0;
	private static final int PROBLEM_MOTIVATION = 1;
	private static final int PROBLEM_BORED = 2;
	private static final int PROBLEM_INJURY = 3;
	

	
	public static final ArrayList<SimpleOption> problems = new  ArrayList<SimpleOption>(){{
		add(new SimpleOption(PROBLEM_TIME, "I don't have time to exercise"));
		add(new SimpleOption(PROBLEM_MOTIVATION, "I find it hard to get started"));
		add(new SimpleOption(PROBLEM_BORED, "I get bored while exercising"));
		add(new SimpleOption(PROBLEM_INJURY, "I have an injury that prevents me from exercising"));
	}};
	
	public static final ProblemSolver getSolver(int problem, Context ctx){
		
		ProblemSolver solver = null;
		
		switch(problem){
			case PROBLEM_TIME:
				solver = new TimeProblemSolver();
				break;
			case PROBLEM_MOTIVATION:
				solver = new MotivationProblemSolver();
				break;
			case PROBLEM_BORED:
				solver = new BoredomProblemSolver();
				break;
			case PROBLEM_INJURY:
				solver = new InjuryProblemSolver(ctx);
				break;
			default:
				solver =null;
				break;
		}
		
		return solver;
	}
	
	public static final OptionQuestionModel BASE_PROBLEM = new OptionQuestionModel(
			"problem_base",
			"Problem",
			"What is the biggest problem when exercising?",
			problems,
			QuestionType.SINGLE, 1, OptionQuestionModel.NO_LIMIT);

}
