package edu.wpi.smartcoach.solver;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import edu.wpi.smartcoach.model.OptionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.SimpleOption;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.exercise.Equipment;

public class InjuryQuestionBuilder {
	
	
	public static OptionQuestionModel getGymMembershipQuestion(Context ctx){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		boolean hadMembership = false;
		String[] equipment = prefs.getString("profile_exercise_equip", "").split(",");
		
		for(String eq:equipment){
			try{
				if(Integer.parseInt(eq) == Equipment.ID_GYM_MEMBERSHIP){
					hadMembership = true;
				}
			} catch(Exception e){}
		}
		
		String message = String.format("Do you%s have a gym membership?", hadMembership?" still":"");
		
		ArrayList<OptionModel> yesNoOption = new ArrayList<OptionModel>();
		yesNoOption.add(new SimpleOption(SimpleOption.YES, "Yes"));
		yesNoOption.add(new SimpleOption(SimpleOption.NO, "No"));
		
		return new OptionQuestionModel("gymmember", "Gym Membership", 
				message, 
				SimpleOption.getYesNoOptions(),
				QuestionType.SINGLE, 1, 1);
	}
	
	public static OptionQuestionModel getPhysicalTherapistQuestion(){
		return new OptionQuestionModel("haspt", "Physical Therapist",
				"Do you have a physical therapist?", SimpleOption.getYesNoOptions(), QuestionType.SINGLE);
	}
	
	public static OptionQuestionModel getFollowedTherapistQuestion(){
		return new OptionQuestionModel("followpt", "Physical Therapist", 
				"Have you already consulted you physical therapist about your injury?", 
				SimpleOption.getYesNoOptions(), QuestionType.SINGLE);
		
	}
	
}
