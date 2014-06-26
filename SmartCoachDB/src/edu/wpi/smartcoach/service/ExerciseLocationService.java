package edu.wpi.smartcoach.service;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import edu.wpi.smartcoach.db.column.ExerciseLocationColumns;
import edu.wpi.smartcoach.model.exercise.ExerciseLocation;
import edu.wpi.smartcoach.util.DatabaseHelper;

public class ExerciseLocationService {

	private static ExerciseLocationService mExerciseLocationService = null;
	
	public static ExerciseLocationService getInstance(){
		if(mExerciseLocationService == null){
			mExerciseLocationService = new ExerciseLocationService();
		}
		return mExerciseLocationService;
	}
	
	public List<ExerciseLocation> getAllDataFromTable() {
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
	
	public ExerciseLocation getLocation(int id){
		String specificLocation;
		String locationType;
		String preposition;
		String sql = "select * from " + ExerciseLocationColumns.TABLE_EXERCISE_LOCATION
				+ " where " + ExerciseLocationColumns.FIELD_ID + " = "
				+ id;
		Cursor cursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		try {
			cursor.moveToNext();
			specificLocation = cursor.getString(1);
			locationType = cursor.getString(2);
			preposition = cursor.getString(3);
		} catch(Exception e){
			e.printStackTrace();
			specificLocation = "<error>";
			locationType = "<error>";
			preposition = "<error>";
		} finally {
			cursor.close();
		}
		return new ExerciseLocation(id, specificLocation, locationType, preposition);
	}
	
	public int getExerciseLocationID(String specificLocation){
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
	
	public List<ExerciseLocation> getLocations(List<Integer> idList){		
		ArrayList<ExerciseLocation> locations = new ArrayList<ExerciseLocation>();
		for(Integer i:idList){
			locations.add(getLocation(i));
		}
		return locations;
	}
}
