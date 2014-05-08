package edu.wpi.smartcoach.solver;

import java.util.*;

import edu.wpi.smartcoach.model.OptionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.TimeQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.SimpleOption;
import edu.wpi.smartcoach.model.exercise.Exercise;
import edu.wpi.smartcoach.model.exercise.ExerciseLocation;
import edu.wpi.smartcoach.model.exercise.ExerciseTime;
import edu.wpi.smartcoach.service.ExerciseLocationService;
import edu.wpi.smartcoach.service.ExerciseTimeService;
import edu.wpi.smartcoach.service.ExerciseToLocationService;

public class ExerciseQuestionListBuilder {
	
	public static Queue<QuestionModel> buildBasicQuestionList(Exercise e){
		Queue<QuestionModel> questions = new LinkedList<QuestionModel>();
		
		questions.add(getLocationQuestion(e));
		questions.add(getTimeQuestion(e));
		questions.add(getFrequencyQuestion(e));
		questions.add(getDurationQuestion(e));
		
		return questions;
	}
	
	private static QuestionModel getLocationQuestion(Exercise e){

		List<Integer> locationIds = ExerciseToLocationService.getInstance().getLocationListByExercise(e.getId());
		final List<ExerciseLocation> locations =  ExerciseLocationService.getInstance().getLocations(locationIds);
		final String prompt = String.format("Where do you %s?", e.getName());
		return new OptionQuestionModel("location", "Location",
				prompt, 
				new ArrayList<OptionModel>(){{
					for(ExerciseLocation el:locations){
						add(new SimpleOption(el.getId(), el));
					}
				}},
				QuestionType.MULTIPLE,1,OptionQuestionModel.NO_LIMIT);
	}
	
	private static QuestionModel getTimeQuestion(Exercise e){
		String prompt = String.format("When do you %s?", e.getName());
		return new OptionQuestionModel("time", "Time", prompt,
				new ArrayList<OptionModel>(){{
					for(ExerciseTime et:ExerciseTimeService.getInstance().getAllDataFromTable()){
						add(new SimpleOption(et.getId(), et));
					}
				}},
				QuestionType.MULTIPLE,1,OptionQuestionModel.NO_LIMIT);		
	}
	
	private static QuestionModel getFrequencyQuestion(Exercise e){
		final ArrayList<OptionModel> frequencies = new ArrayList<OptionModel>();
		frequencies.add(new SimpleOption(0, "Less than once per week"));
		frequencies.add(new SimpleOption(1, "Once per week"));
		for(int i = 2; i < 7; i++){
			frequencies.add(new SimpleOption(i, i + "times per week"));
		}
		frequencies.add(new SimpleOption(7, "7 times per week (daily)"));
		
		String prompt = String.format("On average, how many days do you %s in a week", e.getName());
		return new OptionQuestionModel("frequency", "Frequency", prompt,
				frequencies,
				QuestionType.SINGLE);
	}
	
	private static QuestionModel getDurationQuestion(Exercise e){
		String prompt = String.format("On average, how much time do you spend %s each day?", e.getName());
		return new TimeQuestionModel("duration", "Duration",
				prompt);
	}

}
