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
import edu.wpi.smartcoach.service.ExerciseService;
import edu.wpi.smartcoach.service.ExerciseTimeService;
import edu.wpi.smartcoach.service.ExerciseToLocationService;

public class ExerciseQuestionBuilder {
	
	public static ArrayList<QuestionModel> buildBasicQuestionList(Exercise e){
		ArrayList<QuestionModel> questions = new ArrayList<QuestionModel>();
		
		questions.add(getLocationQuestion(e, false));
		questions.add(getTimeQuestion(e, false));
		questions.add(getFrequencyQuestion(e));
		questions.add(getDurationQuestion(e, false));
		questions.add(getCheckWeekendQuestion(e));
		return questions;
	}
	
	public static QuestionModel getExerciseListQuestion(){
		ArrayList<SimpleOption> exerciseOptions = new ArrayList<SimpleOption>();
		List<Exercise> exerciseList = ExerciseService.getInstance().getAllDataFromTable();
		for(Exercise e:exerciseList){
			exerciseOptions.add(new SimpleOption(e.getId(), e));
		}
		return new OptionQuestionModel("exercises", "Exercises",
				"Which exercises did you try to do?", exerciseOptions,
				QuestionType.MULTIPLE, 1, OptionQuestionModel.NO_LIMIT, true);
	}
	
	public static QuestionModel getLocationQuestion(Exercise e, boolean weekend){
		final List<ExerciseLocation> locations =  ExerciseToLocationService.getInstance().getLocationListByExercise(e.getId());
		final String prompt = String.format("Where do you %s%s?", e.getFormInfinitive(), weekend?" on weekends":"");
		String id = "location"+(weekend?"_we":"");
		return new OptionQuestionModel(id, "Location",
				prompt, 
				new ArrayList<OptionModel>(){{
					for(ExerciseLocation el:locations){
						add(new SimpleOption(el.getId(), el));
					}
				}},
				QuestionType.MULTIPLE, 1, OptionQuestionModel.NO_LIMIT, true);
	}
	
	public static QuestionModel getTimeQuestion(Exercise e, boolean weekend){
		String prompt = String.format("When do you %s%s?", e.getFormInfinitive(), weekend?" on weekends":"");
		String id = "time"+(weekend?"_we":"");
		return new OptionQuestionModel(id, "Time", prompt,
				new ArrayList<OptionModel>(){{
					for(ExerciseTime et:ExerciseTimeService.getInstance().getAllDataFromTable()){
						add(new SimpleOption(et.getId(), et));
					}
				}},
				QuestionType.MULTIPLE,1,OptionQuestionModel.NO_LIMIT, false);		
	}
	
	private static QuestionModel getFrequencyQuestion(Exercise e){
		final ArrayList<OptionModel> frequencies = new ArrayList<OptionModel>();
		frequencies.add(new SimpleOption(0, "Less than once per week"));
		frequencies.add(new SimpleOption(1, "Once per week"));
		for(int i = 2; i < 7; i++){
			frequencies.add(new SimpleOption(i, i + " times per week"));
		}
		frequencies.add(new SimpleOption(7, "7 times per week (daily)"));
		
		String prompt = String.format("On average, how many days do you %s in a week?", e.getFormInfinitive());
		return new OptionQuestionModel("frequency", "Frequency", prompt,
				frequencies,
				QuestionType.SINGLE, 1, OptionQuestionModel.NO_LIMIT, false);
	}
	
	private static QuestionModel getCheckWeekendQuestion(Exercise e){
		String prompt = String.format("Is your %s schedule different on weekends?", e.getName().toLowerCase());
		return new OptionQuestionModel("checkweekend", "Weekends", prompt, SimpleOption.getYesNoOptions(), QuestionType.SINGLE,1, OptionQuestionModel.NO_LIMIT, false);
	}
	
	public static QuestionModel getDurationQuestion(Exercise e, boolean weekend){
		String prompt = String.format("On average, how much time do you spend %s each day%s?", e.getName().toLowerCase(), weekend?" on weekends":"");
		String id = "duration"+(weekend?"_we":"");
		return new TimeQuestionModel(id, "Duration",
				prompt);
	}

}
