package edu.wpi.smartcoach.service;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import edu.wpi.smartcoach.db.column.ExerciseColumns;
import edu.wpi.smartcoach.model.exercise.Exercise;
import edu.wpi.smartcoach.util.DatabaseHelper;

public class ExerciseService {

	private static ExerciseService instance = null;
	
	public static ExerciseService getInstance(){
		if(instance == null){
			instance = new ExerciseService();
		}
		return instance;
	}
	
	public List<Exercise> getAllDataFromTable() {
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
				exercise.setType(cursor.getString(2));
				exercise.setNumberOfPersons(cursor.getString(3));
				exercise.setEquipment(cursor.getString(4));
				exercise.setFormPresent(cursor.getString(5));
				exercise.setFormContinuous(cursor.getString(6));
				exerciseList.add(exercise);
				cursor.moveToNext();
			}
		} catch (Exception e) {
			e.printStackTrace();
			cursor.close();
		}
		return exerciseList;
	}

	
	public int getExerciseID(String exerciseName){
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
	
	public Exercise getExercise(int id){
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
			exercise.setType(cursor.getString(2));
			exercise.setNumberOfPersons(cursor.getString(3));
			exercise.setEquipment(cursor.getString(4));
			exercise.setFormPresent(cursor.getString(5));
			exercise.setFormContinuous(cursor.getString(6));
		} finally {
			cursor.close();
		}
		return exercise;
	}
}
