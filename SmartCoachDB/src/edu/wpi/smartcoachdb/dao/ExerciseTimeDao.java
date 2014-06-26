package edu.wpi.smartcoachdb.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import edu.wpi.smartcoach.model.exercise.ExerciseTime;
import edu.wpi.smartcoachdb.db.column.ExerciseColumns;
import edu.wpi.smartcoachdb.db.column.ExerciseLocationColumns;
import edu.wpi.smartcoachdb.db.column.ExerciseTimeColumns;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;

public class ExerciseTimeDao {

//	public void insertOne(ExerciseTime exerciseTime) {
//		// TODO Auto-generated method stub
//		String sql = "insert into " + ExerciseTimeColumns.TABLE_EXERCISE_TIME
//				+ " (" + ExerciseTimeColumns.FIELD_EXERCISE_TIME + ") "
//				+ "values ('" + exerciseTime.getTime() + "')";
//		DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
//	}

	public List<ExerciseTime> getAll() {
		// TODO Auto-generated method stub
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

	public int getID(String exerciseTime) {
		int exerciseTimeID = 0;
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
	
	public ExerciseTime getTime(int id) {
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
