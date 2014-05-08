package edu.wpi.smartcoachdb.dao;

import static edu.wpi.smartcoachdb.db.column.ExerciseStateColumns.FIELD_DURATION;
import static edu.wpi.smartcoachdb.db.column.ExerciseStateColumns.FIELD_EXERCISE_ID;
import static edu.wpi.smartcoachdb.db.column.ExerciseStateColumns.FIELD_EXERCISE_LIKED;
import static edu.wpi.smartcoachdb.db.column.ExerciseStateColumns.FIELD_EXERCISE_LOCATION_ID;
import static edu.wpi.smartcoachdb.db.column.ExerciseStateColumns.FIELD_EXERCISE_TIME_ID;
import static edu.wpi.smartcoachdb.db.column.ExerciseStateColumns.FIELD_FREQUENCY;
import static edu.wpi.smartcoachdb.db.column.ExerciseStateColumns.FIELD_ID;
import static edu.wpi.smartcoachdb.db.column.ExerciseStateColumns.FIELD_LOCATION_LIKE;
import static edu.wpi.smartcoachdb.db.column.ExerciseStateColumns.FIELD_RECORD_TIME;
import static edu.wpi.smartcoachdb.db.column.ExerciseStateColumns.FIELD_TIME_LIKED;
import static edu.wpi.smartcoachdb.db.column.ExerciseStateColumns.TABLE_PATIENT_EXERCISE;
import android.util.Log;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;

public class ExerciseStateDao {

	private static final String TAG = ExerciseStateDao.class.getSimpleName();
	
	private static final String INSERT_COMMAND = "insert into %s " 
			+ "(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) "
			+ "values ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')";
	
	public void insertOne(ExerciseState state) {
		
		String sql = String.format(INSERT_COMMAND, 
				TABLE_PATIENT_EXERCISE,
				
				FIELD_ID,
				FIELD_RECORD_TIME,
				FIELD_EXERCISE_ID,
				FIELD_EXERCISE_LOCATION_ID,
				FIELD_EXERCISE_TIME_ID,
				FIELD_FREQUENCY,
				FIELD_DURATION,
				FIELD_EXERCISE_LIKED,
				FIELD_TIME_LIKED,
				FIELD_LOCATION_LIKE,
				
				state.getId(),
				state.getRecordTime(),
				state.getExercise().getId(),
				state.getLocation().getId(),
				state.getTime().getId(),
				state.getFrequency(),
				state.getDuration(),
				state.isExerciseLiked(),
				state.isTimeLiked(),
				state.isLocationLiked()
			);
		
		Log.d(TAG, sql);
		
		DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
	}
}
