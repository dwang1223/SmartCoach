package edu.wpi.smartcoach.service;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Manages storage of patient registration information in preferences
 * @author Akshay
 *
 */
public class PatientProfile {
	
	private static final String PREFERENCE_NAME = "patient_info";

	private static final String KEY_INIT = "init";
	private static final String KEY_FIRST_NAME = "firstName";
	private static final String KEY_LAST_NAME = "lastName";
	private static final String KEY_GENDER = "gender";
	private static final String KEY_HEIGHT = "height";
	private static final String KEY_START_WEIGHT = "startWeight";
	private static final String KEY_GOAL_WEIGHT = "goalWeight";
	
	private static final String KEY_PROFILE_CONDITIONS = "conditions";
    private static final String KEY_PROFILE_RESPONSES = "responses";
	
	/**
	 * Marks the profile as initialized, so that the SmartCoach intro screens are only shown once
	 * @param c
	 */
	public static void setInitialized(Context c){		
		getPreferences(c).edit().putBoolean(KEY_INIT, true).commit();
	}
	
	/**
	 * Set first name
	 * @param value first name
	 * @param c Android context
	 */
	public static void setFirstName(String value, Context c) {
		getPreferences(c).edit().putString(KEY_FIRST_NAME, value).commit();
	}

	/**
	 * set last name
	 * @param value last name
	 * @param c Android context
	 */
	public static void setLastName(String value, Context c) {
		getPreferences(c).edit().putString(KEY_LAST_NAME, value).commit();
	}

	/**
	 * set gender
	 * @param value gender
	 * @param c Android context
	 */
	public static void setGender(String value, Context c) {
		getPreferences(c).edit().putString(KEY_GENDER, value).commit();
	}

	/**
	 * set height
	 * @param heightInches height in inches
	 * @param c Android context
	 */
	public static void setHeight(int heightInches, Context c){
		getPreferences(c).edit().putInt(KEY_HEIGHT, heightInches).commit();
	}
	
	/**
	 * Set start weight. Only sets the weight if it has not been set already
	 * @param value start weight
	 * @param c android context
	 */
	public static void setStartWeight(float value, Context c) {
		if(!isStartWeightSet(c)){
			getPreferences(c).edit().putFloat(KEY_START_WEIGHT, value).commit();
		}
	}
	
	/**
	 * Set the profile conditions to the given set of conditions.
	 * @param conditions
	 * @param c
	 */
	public static void setConditions(Set<String> conditions, Context c){
		if(conditions == null){
			conditions = new HashSet<String>();
		}
		getPreferences(c).edit().putStringSet(KEY_PROFILE_CONDITIONS, conditions).commit();
	}

    /**
     * Save the response to a profile question
     * @param questionId id of the question
     * @param optionIds list of selected option ids
     * @param c Android context
     */
    public static void setResponse(String questionId, Set<String> optionIds, Context c){
        if(optionIds == null){
            optionIds = new HashSet<String>();
        }
        getPreferences(c).edit().putStringSet(KEY_PROFILE_RESPONSES+"."+questionId, optionIds).commit();
    }

	/**
	 * Set goal weight
	 * @param value goal weight
	 * @param c Android context
	 */
	public static void setGoalWeight(float value, Context c) {
		getPreferences(c).edit().putFloat(KEY_GOAL_WEIGHT, value).commit();
	}
	
	/**
	 * Returns whether or not the profile has been marked as initialized
	 * @param c Android context
	 * @return is initialized
	 */
	public static boolean isInitialized(Context c){
		return getPreferences(c).getBoolean(KEY_INIT, false);
	}
	
	public static String getFirstName(Context c){
		return getPreferences(c).getString(KEY_FIRST_NAME, "");
	}
	
	public static String getLastName(Context c){
		return getPreferences(c).getString(KEY_LAST_NAME, "");
	}
	
	public static String getGender(Context c){
		return getPreferences(c).getString(KEY_GENDER, "");		
	}
	
	public static int getHeight(Context c){
		return getPreferences(c).getInt(KEY_HEIGHT, -1);			
	}
	
	public static float getStartWeight(Context c){
		return getPreferences(c).getFloat(KEY_START_WEIGHT, -1);
	}
	
	public static float getGoalWeight(Context c){
		return getPreferences(c).getFloat(KEY_GOAL_WEIGHT, -1);
	}
	
	/**
	 * Returns whether the profile has the given condition
	 * @param condition condition to check for
	 * @param c Android context
	 * @return whether the profile has the given condition
	 */
	public static boolean hasCondition(String condition, Context c){
		return getPreferences(c).getStringSet(KEY_PROFILE_CONDITIONS, new HashSet<String>()).contains(condition);
	}

    /**
     * Get the saved responses to a profile question
     * @param questionId question to get the responses to
     * @param c Android context
     * @return set of selected option ids
     */
    public static Set<String> getResponses(String questionId, Context c){
        return getPreferences(c).getStringSet(KEY_PROFILE_RESPONSES+"."+questionId, new HashSet<String>());
    }
	
	/**
	 * Returns whether the start weight has been set before
	 * @param c Context
	 * @return whether the start weight has been set before
	 */
	public static boolean isStartWeightSet(Context c){
		return getPreferences(c).contains(KEY_START_WEIGHT);
	}
	
	private static SharedPreferences getPreferences(Context c){
		return c.getSharedPreferences(PREFERENCE_NAME, 0);
	}

}
