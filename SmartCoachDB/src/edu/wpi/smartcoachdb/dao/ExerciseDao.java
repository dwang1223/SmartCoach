package edu.wpi.smartcoachdb.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import edu.wpi.smartcoach.model.exercise.Exercise;
import edu.wpi.smartcoachdb.db.column.ExerciseColumns;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;

public class ExerciseDao {

	public void insertOne(Exercise exercise) {
		// TODO Auto-generated method stub
		String sql = "insert into " + ExerciseColumns.TABLE_EXERCISE + " ("
				+ ExerciseColumns.FIELD_EXERCISE_NAME + ", "
				+ ExerciseColumns.FIELD_EXERCISE_TYPE + ", "
				+ ExerciseColumns.FIELD_EXERCISE_NUMBER_OF_PERSONS + ", "
				+ ExerciseColumns.FIELD_EXERCISE_EQUIPMENT + ") " + "values ('"
				+ exercise.getName() + "', '" + exercise.getExerciseType()
				+ "','" + exercise.getExerciseNumberOfPersons() + "','"
				+ exercise.getExerciseEquipment() + "')";
		DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
	}

	public List<Exercise> getAll() {
		List<Exercise> exerciseList = new ArrayList<Exercise>();
		Exercise exercise = null;
		String sql = "select * from " + ExerciseColumns.TABLE_EXERCISE;
		Cursor cursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		try {
			cursor.moveToNext();
			while (cursor.getPosition() != cursor.getCount()) {
				exercise = new Exercise();
				exercise.setId(cursor.getInt(0));
				exercise.setName(cursor.getString(1));
				exercise.setExerciseType(cursor.getString(2));
				exercise.setExerciseNumberOfPersons(cursor.getString(3));
				exercise.setExerciseEquipment(cursor.getString(4));
				exerciseList.add(exercise);
				cursor.moveToNext();
			}
		} catch (Exception e) {
			cursor.close();
		}
		return exerciseList;
	}

	public int getID(String exerciseName) {
		int id = 0;
		String sql = "select * from " + ExerciseColumns.TABLE_EXERCISE
				+ " where " + ExerciseColumns.FIELD_EXERCISE_NAME + " = '"
				+ exerciseName + "'";
		Cursor cursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		try {
			cursor.moveToNext();
			id = cursor.getInt(0);
		} finally {
			cursor.close();
		}
		return id;
	}
	
	public Exercise getExercise(int id) {
		Exercise exercise;
		
		String sql = "select * from " + ExerciseColumns.TABLE_EXERCISE
				+ " where " + ExerciseColumns.FIELD_ID + " = "
				+ id;
		Cursor cursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		try {
			cursor.moveToNext();				
			exercise = new Exercise();
			exercise.setId(cursor.getInt(0));
			exercise.setName(cursor.getString(1));
			exercise.setExerciseType(cursor.getString(2));
			exercise.setExerciseNumberOfPersons(cursor.getString(3));
			exercise.setExerciseEquipment(cursor.getString(4));
		} finally {
			cursor.close();
		}
		return exercise;
	}
}
