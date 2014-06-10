package edu.wpi.smartcoach.model.exercise;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import edu.wpi.smartcoach.model.OptionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.SimpleOption;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.service.ExerciseService;
import edu.wpi.smartcoach.service.ExerciseTimeService;

public class ExerciseProfile {

	private static final String TAG = ExerciseProfile.class.getSimpleName();
	
	public static final OptionQuestionModel[] questions;

	public static final int YES = 1;
	public static final int NO = 0;

	public static final int HOME = 2;
	public static final int GYM = 3;
	public static final int OUTDOOR = 4;

	public static final int ALONE = 5;
	public static final int FRIEND = 6;
	public static final int GROUP = 7;

	static {		
		
		final List<Exercise> exercises = ExerciseService.getInstance().getAllDataFromTable();		
		final List<ExerciseTime> times = ExerciseTimeService.getInstance().getAllDataFromTable();

		Log.d(TAG, exercises.toString());
		Log.d(TAG, times.toString());
		
		questions = new OptionQuestionModel[] {
								
//		new QuestionModel(
//				"profile_exercise_like", 
//				"Preference",
//				"Do you like to exercise?", 
//				new ArrayList<OptionModel>() {{
//					add(new SimpleOption(YES, "Yes"));
//					add(new SimpleOption(NO, "No"));
//					add(new SimpleOption(QuestionModel.DEFAULT, "Not Sure"));
//				}},
//				QuestionType.SINGLE),
			
		new OptionQuestionModel(
				"profile_exercise_location",
				"Location",
				"Where would you prefer to exercise?",
				new ArrayList<OptionModel>() {{
					add(new SimpleOption(HOME, "At Home"));
					add(new SimpleOption(GYM, "In the Gym"));
					add(new SimpleOption(OUTDOOR, "Outdoors"));
					add(new SimpleOption(OptionQuestionModel.DEFAULT, "It doesn't matter"));
				}}, 
				QuestionType.MULTIPLE, true),
			
			new OptionQuestionModel(
				"profile_exercise_who",
				"Social",
				"With whom do you prefer to exercise?",
				new ArrayList<OptionModel>() {{
					add(new SimpleOption(ALONE, "Alone"));
					add(new SimpleOption(FRIEND, "With a friend"));
					add(new SimpleOption(GROUP, "With a group"));
					add(new SimpleOption(OptionQuestionModel.DEFAULT, "It doesn't matter"));
				}},
				QuestionType.SINGLE, 1, OptionQuestionModel.NO_LIMIT, true),
				
			new OptionQuestionModel(
					"profile_exercise_when",
					"Time",
					"When do you prefer to exercise?",
					new ArrayList<OptionModel>(){{
						for(ExerciseTime t:times){
							add(new SimpleOption(t.getId(), t));
						}
					}},
					QuestionType.MULTIPLE, false),
				
			new OptionQuestionModel(
					"profile_exercise_enjoy",
					"Favorites", 
					"Which exercises have you tried and liked?", 
					new ArrayList<OptionModel>(){{
						for(Exercise e:exercises){
							add(new SimpleOption(e.getId(), e));
						}
					}},
					QuestionType.MULTIPLE, true
					),	
					
			new OptionQuestionModel(
					"profile_exercise_hate",
					"Least Favorites", 
					"Which exercises have tried and not liked?", 
					new ArrayList<OptionModel>(){{
						for(Exercise e:exercises){
							add(new SimpleOption(e.getId(), e));
						}
					}},
					QuestionType.MULTIPLE , true
					),
					
			new OptionQuestionModel(
					"profile_exercise_equip",
					"Equipment",
					"Please check all that you own:",
					new ArrayList<OptionModel>(){{
						for(Equipment e:Equipment.equipment){
							add(new SimpleOption(e.getId(), e));
						}
					}},
					QuestionType.MULTIPLE, true),
					
			new OptionQuestionModel("complete", "Profile Complete", 
					"You have created your profile successfully, now you are ready to do some problem solving.", new ArrayList<OptionModel>(), QuestionType.SINGLE, false),

					
		};
		
		for(OptionQuestionModel q:questions){
			Log.d(TAG, q.toString());
		}
	}
	
	public static OptionQuestionModel getQuestionById(String id){
		for(OptionQuestionModel q:questions){
			if(id.equals(q.getId())){
				return q;
			}
		}
		return null;
	}

}
