package edu.wpi.smartcoachdb.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import edu.wpi.smartcoach.model.exercise.ExerciseLocation;
import edu.wpi.smartcoachdb.db.column.ExerciseColumns;
import edu.wpi.smartcoachdb.db.column.ExerciseLocationColumns;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;

public class ExerciseLocationDao {

	public void insertOne(ExerciseLocation exerciseLocation) {
		// TODO Auto-generated method stub
		String sql = "insert into "
				+ ExerciseLocationColumns.TABLE_EXERCISE_LOCATION + " ("
				+ ExerciseLocationColumns.FIELD_SPECIFIC_LOCATION + ", "
				+ ExerciseLocationColumns.FIELD_LOCATION_TYPE + ") "
				+ "values ('" + exerciseLocation.getSpecificLocation() + "', '"
				+ exerciseLocation.getLocationType() + "')";
		DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
	}

	public List<ExerciseLocation> getAll() {
		// TODO Auto-generated method stub
		List<ExerciseLocation> exerciseLocationList = new ArrayList<ExerciseLocation>();
		ExerciseLocation exerciseLocation = null;
		String sql = "select * from "
				+ ExerciseLocationColumns.TABLE_EXERCISE_LOCATION;
		Cursor cursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		try {
			cursor.moveToNext();
			while (cursor.getPosition() != cursor.getCount()) {
				exerciseLocation = new ExerciseLocation();
				exerciseLocation.setId(cursor.getInt(0));
				exerciseLocation.setSpecificLocation(cursor.getString(1));
				exerciseLocation.setLocationType(cursor.getString(2));
				exerciseLocationList.add(exerciseLocation);
				cursor.moveToNext();
			}
		} catch (Exception e) {
			cursor.close();
		}
		return exerciseLocationList;
	}

	public int getID(String specificLocation) {
		int exerciseLocationID = 0;
		String sql = "select * from " + ExerciseLocationColumns.TABLE_EXERCISE_LOCATION
				+ " where " + ExerciseLocationColumns.FIELD_SPECIFIC_LOCATION + " = '"
				+ specificLocation + "'";
		Cursor mCursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		try {
			mCursor.moveToNext();
			exerciseLocationID = mCursor.getInt(0);
		} finally {
			mCursor.close();
		}
		return exerciseLocationID;
	}
	
	public ExerciseLocation getLocation(int id) {
		String specificLocation;
		String locationType;
		String sql = "select * from " + ExerciseLocationColumns.TABLE_EXERCISE_LOCATION
				+ " where " + ExerciseLocationColumns.FIELD_ID + " = "
				+ id;
		Cursor cursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		try {
			cursor.moveToNext();
			specificLocation = cursor.getString(1);
			locationType = cursor.getString(2);
		} finally {
			cursor.close();
		}
		return new ExerciseLocation(id, specificLocation, locationType);
	}
}
