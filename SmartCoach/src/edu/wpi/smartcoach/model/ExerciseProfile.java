package edu.wpi.smartcoach.model;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Map.Entry;

import edu.wpi.smartcoach.model.QuestionModel.QuestionType;


public class ExerciseProfile {

	public static final QuestionModel[] questions;

	static {
		questions = new QuestionModel[] {
				
		new QuestionModel(
				"profile_exercise_like", 
				"Preference",
				"Do you like to exercise?", 
				new ArrayList<OptionModel>() {{
					add(new OptionModel("yes", "Yes"));
					add(new OptionModel("no", "No"));
					add(new OptionModel(QuestionModel.DEFAULT, "Not Sure"));
				}},
				QuestionType.SINGLE),
			
		new QuestionModel(
				"profile_exercise_location",
				"Location",
				"Where would you like to exercise?",
				new ArrayList<OptionModel>() {{
					add(new OptionModel("home", "At Home"));
					add(new OptionModel("gym", "In the Gym"));
					add(new OptionModel("outdoors", "Outdoors"));
					add(new OptionModel(QuestionModel.DEFAULT, "It doesn't matter"));
				}}, 
				QuestionType.MULTIPLE),
			
			new QuestionModel(
				"profile_exercise_who",
				"Social",
				"With whom do you prefer to exercise?",
				new ArrayList<OptionModel>() {{
					add(new OptionModel("alone", "Alone"));
					add(new OptionModel("friend", "With a friend"));
					add(new OptionModel("group", "With a group"));
					add(new OptionModel(QuestionModel.DEFAULT, "It doesn't matter"));
				}},
				QuestionType.MULTIPLE)
		};
	}

}
