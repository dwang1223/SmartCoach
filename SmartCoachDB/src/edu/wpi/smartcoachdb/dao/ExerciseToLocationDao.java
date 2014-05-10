package edu.wpi.smartcoachdb.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import edu.wpi.smartcoach.model.exercise.ExerciseLocation;
import edu.wpi.smartcoach.service.ExerciseLocationService;
import edu.wpi.smartcoachdb.db.column.ExerciseToLocationColumns;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;

public class ExerciseToLocationDao {

	public List<ExerciseLocation> getLocationList(int exerciseID) {
		List<ExerciseLocation> locationList = new ArrayList<ExerciseLocation>();
		int locationID = 0;
		String sql = "select * from "
				+ ExerciseToLocationColumns.TABLE_EXERCISE + " where "
				+ ExerciseToLocationColumns.FIELD_EXERCISE_ID + " = "
				+ exerciseID;
		Cursor cursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		try {
			cursor.moveToNext();
			while (cursor.getPosition() != cursor.getCount()) {
				locationID = cursor.getInt(1);
				locationList.add(ExerciseLocationService.getInstance().getLocation(locationID));
				cursor.moveToNext();
			}
		} finally {
			cursor.close();
		}
		return locationList;
	}
}