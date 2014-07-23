package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import edu.wpi.smartcoach.model.ExerciseQuestions;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.Solution;
import edu.wpi.smartcoach.model.exercise.ExerciseSolution;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.view.Option;

public class WeatherProblemSolver extends BaseProblemSolver{

	private static final String TAG = WeatherProblemSolver.class.getSimpleName();
	
	private static final int WEATHER_HOT = 1;
	private static final int WEATHER_COLD = 2;
	private static final int WEATHER_RAIN = 3;
	
	private int weather = 0;
	
	@Override
	public void init(){
		super.init();
		
		OptionQuestionModel weatherQuestion = ExerciseQuestions.getInstance().getOptionQuestion("weather");
		
		questions.add(new QuestionStateEntry(weatherQuestion, null, false));
		Log.d(TAG, questions.toString());
	}
	
	@Override
	public void submitResponse(QuestionModel response) {
		if(response.getId().equals("weather")){
			String weatherString = ((OptionQuestionModel)response).getSelectedOption().getId();
			if(weatherString.equals("cold")){
				weather = WEATHER_COLD;
			}
		}
		
		super.submitResponse(response);
	}
	
	@Override
	public void addStateQuestions(ExerciseState state, boolean weekend){
		OptionQuestionModel location = ExerciseQuestions.getInstance().getOptionQuestion("exercise_location");
		OptionQuestionModel time = ExerciseQuestions.getInstance().getOptionQuestion("exercise_time");
//		OptionQuestionModel frequency = ExerciseQuestions.getInstance().getOptionQuestion("exercise_frequency");
//		TimeQuestionModel duration = ExerciseQuestions.getInstance().getTimeQuestion("exercise_duration");
//		OptionQuestionModel like = ExerciseQuestions.getInstance().getOptionQuestion("exercise_like");
	
		questions.add(new QuestionStateEntry(location, state, weekend));
		questions.add(new QuestionStateEntry(time, state, weekend));
//		questions.add(new QuestionStateEntry(frequency, state, weekend));
//		questions.add(new QuestionStateEntry(duration, state, weekend));
//		questions.add(new QuestionStateEntry(like, state, weekend));
	}
	
	@Override
	public QuestionModel getSolution(Context ctx) {
		List<ExerciseSolution> solutions = new ArrayList<ExerciseSolution>();

		
		solutions.addAll(Solutions.getNewLocationSolutions(new ArrayList<ExerciseState>(), ctx));
		ArrayList<Option> options = new ArrayList<Option>(); 
		
		if(weather == WEATHER_COLD){
			options.add(new Option("warmup", new Solution(Solution.TYPE_DEFAULT, "Try doing some warm ups before going out")));
		}
		
		for(ExerciseSolution s:solutions){
			options.add(new Option(solutions.size()+"", s));
		}
				
		return new OptionQuestionModel("solutions", "Solutions", 
				"Try some of these to make exercise a little more interesting",
				options, 
				QuestionType.MULTIPLE, false, false);
	}

}
