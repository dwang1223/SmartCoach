package edu.wpi.smartcoach.model;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Map.Entry;

import edu.wpi.smartcoach.model.QuestionModel.QuestionType;

public class ExerciseProfile {

	public static final QuestionModel[] questions;

	public static final int YES = 1;
	public static final int NO = 0;

	public static final int HOME = 2;
	public static final int GYM = 3;
	public static final int OUTDOOR = 4;

	public static final int ALONE = 5;
	public static final int FRIEND = 6;
	public static final int GROUP = 7;

	static {
		questions = new QuestionModel[] {
				
		new QuestionModel(
				"profile_exercise_like", 
				"Preference",
				"Do you like to exercise?", 
				new ArrayList<OptionModel>() {{
					add(new SimpleOption(YES, "Yes"));
					add(new SimpleOption(NO, "No"));
					add(new SimpleOption(QuestionModel.DEFAULT, "Not Sure"));
				}},
				QuestionType.SINGLE),
			
		new QuestionModel(
				"profile_exercise_location",
				"Location",
				"Where would you like to exercise?",
				new ArrayList<OptionModel>() {{
					add(new SimpleOption(HOME, "At Home"));
					add(new SimpleOption(GYM, "In the Gym"));
					add(new SimpleOption(OUTDOOR, "Outdoors"));
					add(new SimpleOption(QuestionModel.DEFAULT, "It doesn't matter"));
				}}, 
				QuestionType.MULTIPLE),
			
			new QuestionModel(
				"profile_exercise_who",
				"Social",
				"With whom do you prefer to exercise?",
				new ArrayList<OptionModel>() {{
					add(new SimpleOption(ALONE, "Alone"));
					add(new SimpleOption(FRIEND, "With a friend"));
					add(new SimpleOption(GROUP, "With a group"));
					add(new SimpleOption(QuestionModel.DEFAULT, "It doesn't matter"));
				}},
				QuestionType.MULTIPLE),
				
//			new QuestionModel(
//					"profile_exercise_enjoy",
//					"Favorites", 
//					"Which exercises do you enjoy the MOST? (up to 5)", 
//					Exercises.getExerciseOptions(),
//					QuestionType.MULTIPLE, 
//					5),	
//					
//			new QuestionModel(
//					"profile_exercise_hate",
//					"Least Favorites", 
//					"Which exercises do you enjoy the LEAST? (up to 5)", 
//					Exercises.getExerciseOptions(),
//					QuestionType.MULTIPLE, 
//					5),
					
			new QuestionModel(
					"profile_exercise_equip",
					"Equipment",
					"Please check all that you own:",
					Equipment.equipment,
					QuestionType.MULTIPLE),
					
		};
	}
	
	public static QuestionModel getQuestionById(String id){
		for(QuestionModel q:questions){
			if(id.equals(q.getId())){
				return q;
			}
		}
		return null;
	}

}
