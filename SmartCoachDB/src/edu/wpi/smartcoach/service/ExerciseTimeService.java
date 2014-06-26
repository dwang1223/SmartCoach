package edu.wpi.smartcoach.service;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import edu.wpi.smartcoach.db.column.ExerciseTimeColumns;
import edu.wpi.smartcoach.model.exercise.ExerciseTime;
import edu.wpi.smartcoach.util.DatabaseHelper;

public class ExerciseTimeService {

	private static ExerciseTimeService instance = null;
	
	public static ExerciseTimeService getInstance (){
		if(instance == null){
			instance = new ExerciseTimeService();
		}
		return instance;
	}
	
	public List<ExerciseTime> getAllDataFromTable() {		// TODO Auto-generated method stub
		List<ExerciseTime> exerciseTimeList = new ArrayList<ExerciseTime>();
		ExerciseTime exerciseTime = null;
		String sql = "select * from " + ExerciseTimeColumns.TABLE_EXERCISE_TIME;
		Cursor cursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		try {
			cursor.moveToNext();
			while (cursor.getPosition() != cursor.getCount()) {
				exerciseTime = new ExerciseTime();
				exerciseTime.setId(cursor.getInt(0));
				exerciseTime.setTime(cursor.getString(1));
				exerciseTime.setPrepositionIn(cursor.getString(2));
				exerciseTimeList.add(exerciseTime);
				cursor.moveToNext();
			}
		} catch (Exception e) {
			cursor.close();
		}
		return exerciseTimeList;
	}

	public int getExerciseTimeID(String exerciseTime){		int exerciseTimeID = 0;
		String sql = "select * from " +ExerciseTimeColumns.TABLE_EXERCISE_TIME
				+ " where " + ExerciseTimeColumns.FIELD_EXERCISE_TIME + " = '"
				+ exerciseTime + "'";
		Cursor mCursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		try {
			mCursor.moveToNext();
			exerciseTimeID = mCursor.getInt(0);
		} finally {
			mCursor.close();
		}
		return exerciseTimeID;
	}
	
	public ExerciseTime getExerciseTime(int id) {
		String exerciseTime;
		String prepositionIn;
		String sql = "select * from " + ExerciseTimeColumns.TABLE_EXERCISE_TIME
				+ " where " + ExerciseTimeColumns.FIELD_ID + " = "
				+ id;
		Cursor cursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		try {
			cursor.moveToNext();
			exerciseTime = cursor.getString(1);
			prepositionIn = cursor.getString(2);
		} finally {
			cursor.close();
		}
		return new ExerciseTime(id, exerciseTime, prepositionIn);
	}
}
