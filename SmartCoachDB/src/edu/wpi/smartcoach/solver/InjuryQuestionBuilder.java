package edu.wpi.smartcoach.solver;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.exercise.Equipment;
import edu.wpi.smartcoach.view.Option;

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
		
		ArrayList<Option> yesNoOption = new ArrayList<Option>();
		yesNoOption.add(new Option("yes", "Yes"));
		yesNoOption.add(new Option("No", "No"));
		
		return new OptionQuestionModel("gymmember", "Gym Membership", 
				message, 
				null,
				QuestionType.SINGLE, false, false);
	}
	
	public static OptionQuestionModel getPhysicalTherapistQuestion(){
		return new OptionQuestionModel("haspt", "Physical Therapist",
				"Do you have a physical therapist?", null, QuestionType.SINGLE, false, false);
	}
	
	public static OptionQuestionModel getFollowedTherapistQuestion(){
		return new OptionQuestionModel("followpt", "Physical Therapist", 
				"Have you already consulted you physical therapist about your injury?", 
				null, QuestionType.SINGLE, false, false);
		
	}
	
}
