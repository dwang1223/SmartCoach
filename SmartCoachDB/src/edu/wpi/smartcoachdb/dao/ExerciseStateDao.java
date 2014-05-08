package edu.wpi.smartcoachdb.dao;

import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoachdb.db.column.ExerciseStateColumns;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;

public class ExerciseStateDao {

	public void insertOne(ExerciseState exerciseState) {
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
				+ exerciseState.getExercise().getId() + "', '"
				+ exerciseState.getLocation().getId() + "','"
				+ exerciseState.getTime().getId() + "',"
				+ exerciseState.getFrequency() + ","
				+ exerciseState.getDuration() + ","
				+ exerciseState.isExerciseLiked() + "','"
				+ exerciseState.isLocationLiked() + "','"
				+ exerciseState.isTimeLiked() + "','"
				+ exerciseState.getRecordTime() + "')";
		DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
	}
}
