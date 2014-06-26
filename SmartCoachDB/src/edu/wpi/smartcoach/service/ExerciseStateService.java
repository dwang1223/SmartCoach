package edu.wpi.smartcoach.service;

import edu.wpi.smartcoach.db.column.ExerciseStateColumns;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.util.DatabaseHelper;

public class ExerciseStateService {

	private static ExerciseStateService instance = null;
	
	public static ExerciseStateService getInstance(){
		if(instance == null){
			instance = new ExerciseStateService();
		}
		return instance;
	}
	
	public void addPatientExercise(ExerciseState state) {
		// TODO Auto-generated method stub
		String sql = "insert into " + ExerciseStateColumns.TABLE_EXERCISE_STATE
				+ " (" + ExerciseStateColumns.FIELD_EXERCISE_ID + ", "
				+ ExerciseStateColumns.FIELD_EXERCISE_LOCATION_ID + ", "
				+ ExerciseStateColumns.FIELD_EXERCISE_TIME_ID + ", "
				+ ExerciseStateColumns.FIELD_FREQUENCY + ", "
				+ ExerciseStateColumns.FIELD_DURATION + ", "
				+ ExerciseStateColumns.FIELD_EXERCISE_LIKED + ", "
				+ ExerciseStateColumns.FIELD_LOCATION_LIKED + ", "
				+ ExerciseStateColumns.FIELD_TIME_LIKED + ", "
				+ ExerciseStateColumns.FIELD_RECORD_TIME + ") " + "values ('"
				+ state.getExercise().getId() + "', '"
				+ state.getLocation().getId() + "','"
				+ state.getTime().getId() + "',"
				+ state.getFrequency() + ","
				+ state.getDuration() + ","
				+ state.isExerciseLiked() + "','"
				+ state.isLocationLiked() + "','"
				+ state.isTimeLiked() + "','"
				+ state.getRecordTime() + "')";
		DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
	}
}
