package edu.wpi.smartcoach.service;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import edu.wpi.smartcoach.db.column.ExerciseToLocationColumns;
import edu.wpi.smartcoach.model.exercise.ExerciseLocation;
import edu.wpi.smartcoach.util.DatabaseHelper;

public class ExerciseToLocationService {

	private static ExerciseToLocationService instance = null;
	
	public static ExerciseToLocationService getInstance(){
		if(instance == null){
			instance = new ExerciseToLocationService();
		}
		return instance;
	}
	
	public List<ExerciseLocation> getLocationListByExercise(int exerciseID) {
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
				locationList.add(ExerciseLocationService.getInstance()
						.getLocation(locationID));
				cursor.moveToNext();
			}
		} finally {
			cursor.close();
		}
		return locationList;
	}
}
