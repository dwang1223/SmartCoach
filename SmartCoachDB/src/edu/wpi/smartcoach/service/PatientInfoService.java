package edu.wpi.smartcoach.service;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manages storage of patient registration information in preferences
 * @author Akshay
 *
 */
public class PatientInfoService {
	
	private static final String PREFERENCE_NAME = "patient_info";

	private static final String KEY_FIRST_NAME = "firstName";
	private static final String KEY_LAST_NAME = "lastName";
	private static final String KEY_GENDER = "gender";
	private static final String KEY_HEIGHT = "height";
	private static final String KEY_START_WEIGHT = "startWeight";
	private static final String KEY_GOAL_WEIGHT = "goalWeight";
	
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
		getPreferences(c).edit().putInt(KEY_HEIGHT, heightInches);
	}
	
	/**
	 * Set start weight. Only sets the weight if it has not been set already
	 * @param value start weight
	 * @param Android context
	 */
	public static void setStartWeight(float value, Context c) {
		if(!isStartWeightSet(c)){
			getPreferences(c).edit().putFloat(KEY_START_WEIGHT, value).commit();
		}
	}

	/**
	 * Set goal weight
	 * @param value goal weight
	 * @param c Android context
	 */
	public static void setGoalWeight(float value, Context c) {
		getPreferences(c).edit().putFloat(KEY_GOAL_WEIGHT, value).commit();
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
		return getPreferences(c).getInt(KEY_HEIGHT, 65);			
	}
	
	public static float getStartWeight(Context c){
		return getPreferences(c).getFloat(KEY_START_WEIGHT, 0);
	}
	
	public static float getGoalWeight(Context c){
		return getPreferences(c).getFloat(KEY_GOAL_WEIGHT, 0);
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
