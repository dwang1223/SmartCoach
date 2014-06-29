package edu.wpi.smartcoach.model.exercise;

import java.util.ArrayList;

import android.content.Context;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.solver.BoredomProblemSolver;
import edu.wpi.smartcoach.solver.InjuryProblemSolver;
import edu.wpi.smartcoach.solver.MotivationProblemSolver;
import edu.wpi.smartcoach.solver.ProblemSolver;
import edu.wpi.smartcoach.solver.TimeProblemSolver;
import edu.wpi.smartcoach.view.Option;


public class ExerciseProblems {

	private static final int PROBLEM_TIME = 0;
	private static final int PROBLEM_MOTIVATION = 1;
	private static final int PROBLEM_BORED = 2;
	private static final int PROBLEM_INJURY = 3;
	

	
	public static final ArrayList<Option> problems = new  ArrayList<Option>(){{
		add(new Option(PROBLEM_TIME+"", "I don't have time to exercise"));
		add(new Option(PROBLEM_MOTIVATION+"", "I find it hard to get started"));
		add(new Option(PROBLEM_BORED+"", "I get bored while exercising"));
		add(new Option(PROBLEM_INJURY+"", "I have an injury that prevents me from exercising"));
	}};
	

	
	public static final OptionQuestionModel BASE_PROBLEM = new OptionQuestionModel(
			"problem_base",
			"Problem",
			"What is the biggest problem when exercising?",
			problems,
			QuestionType.SINGLE, false);

}
