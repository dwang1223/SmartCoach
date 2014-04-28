package edu.wpi.smartcoachdb.dao;

import edu.wpi.smartcoach.model.ExerciseToLocation;
import edu.wpi.smartcoachdb.db.column.ExerciseToLocationColumns;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;

public class ExerciseToLocationDao {

	public void intertOne(ExerciseToLocation exerciseToLocation) {
		String sql = "insert into "
				+ ExerciseToLocationColumns.TABLE_EXERCISE + " ("
				+ ExerciseToLocationColumns.FIELD_EXERCISE_ID + ", "
				+ ExerciseToLocationColumns.FIELD_EXERCISE_LOCATION_ID + ") "
				+ "values ('" + exerciseToLocation.getExerciseID() + "', '"
				+ exerciseToLocation.getLocationID() + "')";
		DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
	}
}
