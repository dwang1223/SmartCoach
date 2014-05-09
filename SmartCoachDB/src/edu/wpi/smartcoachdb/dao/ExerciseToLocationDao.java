package edu.wpi.smartcoachdb.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import edu.wpi.smartcoach.model.exercise.ExerciseLocation;
import edu.wpi.smartcoach.model.exercise.ExerciseToLocation;
import edu.wpi.smartcoach.service.ExerciseLocationService;
import edu.wpi.smartcoachdb.db.column.ExerciseToLocationColumns;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;

public class ExerciseToLocationDao {

	public void intertOne(ExerciseToLocation exerciseToLocation) {
		String sql = "insert into " + ExerciseToLocationColumns.TABLE_EXERCISE
				+ " (" + ExerciseToLocationColumns.FIELD_EXERCISE_ID + ", "
				+ ExerciseToLocationColumns.FIELD_EXERCISE_LOCATION_ID + ") "
				+ "values ('" + exerciseToLocation.getExerciseID() + "', '"
				+ exerciseToLocation.getLocationID() + "')";
		DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
	}

	public List<ExerciseLocation> getLocationList(int exerciseID) {
		List<ExerciseLocation> locationList = new ArrayList<ExerciseLocation>();
		int locationID = 0;
		String sql = "select * from "
				+ ExerciseToLocationColumns.TABLE_EXERCISE + " where "
				+ ExerciseToLocationColumns.FIELD_EXERCISE_ID + " = "
				+ exerciseID;
		Cursor mCursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		try {
			mCursor.moveToNext();
			while (mCursor.getPosition() != mCursor.getCount()) {
				locationID = mCursor.getInt(1);
				locationList.add(ExerciseLocationService.getInstance().getLocation(locationID));
				mCursor.moveToNext();
			}
		} finally {
			mCursor.close();
		}
		return locationList;
	}
}