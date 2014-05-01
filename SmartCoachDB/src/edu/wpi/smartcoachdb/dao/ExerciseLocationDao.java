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
		List<ExerciseLocation> mExerciseLocationList = new ArrayList<ExerciseLocation>();
		ExerciseLocation mExerciseLocation = null;
		String sql = "select * from "
				+ ExerciseLocationColumns.TABLE_EXERCISE_LOCATION;
		Cursor mCursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		try {
			mCursor.moveToNext();
			while (mCursor.getPosition() != mCursor.getCount()) {
				mExerciseLocation = new ExerciseLocation();
				mExerciseLocation.setId(mCursor.getInt(0));
				mExerciseLocation.setSpecificLocation(mCursor.getString(1));
				mExerciseLocation.setLocationType(mCursor.getString(2));
				mExerciseLocationList.add(mExerciseLocation);
				mCursor.moveToNext();
			}
		} catch (Exception e) {
			mCursor.close();
		}
		return mExerciseLocationList;
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
	
	public String getLocation(int exerciseLocationID) {
		String specificLocation;
		String sql = "select * from " + ExerciseLocationColumns.TABLE_EXERCISE_LOCATION
				+ " where " + ExerciseLocationColumns.FIELD_ID + " = "
				+ exerciseLocationID;
		Cursor mCursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		try {
			mCursor.moveToNext();
			specificLocation = mCursor.getString(1);
		} finally {
			mCursor.close();
		}
		return specificLocation;
	}
}
