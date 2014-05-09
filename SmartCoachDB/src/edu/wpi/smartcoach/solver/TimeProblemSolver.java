package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import edu.wpi.smartcoach.model.OptionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.SimpleOption;
import edu.wpi.smartcoach.model.exercise.ExerciseState;

public class TimeProblemSolver extends MotivationProblemSolver {

	@Override
	public QuestionModel getSolution(Context ctx){
		OptionQuestionModel solutionQuestion = (OptionQuestionModel)super.getSolution(ctx);
		List<OptionModel> solutions = solutionQuestion.getOptions();
		
		for(ExerciseState s:state.values()){
			if(!s.isTimeLiked()){
				solutions.add(0, new SimpleOption(solutions.size(), "Increase intensity of "+s.getExercise()));
			}
		}
		
		solutionQuestion.setOptions(solutions);
		return solutionQuestion;

	}
	
}
