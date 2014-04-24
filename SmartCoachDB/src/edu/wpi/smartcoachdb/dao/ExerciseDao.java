package edu.wpi.smartcoachdb.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import edu.wpi.smartcoach.model.Exercise;
import edu.wpi.smartcoachdb.db.column.ExerciseColumns;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;

public class ExerciseDao {

	public void insertOne(Exercise exercise) {
		// TODO Auto-generated method stub
		String sql = "insert into "
				+ ExerciseColumns.TABLE_EXERCISE + " ("
				+ ExerciseColumns.FIELD_EXERCISE_NAME + ", "
				+ ExerciseColumns.FIELD_EXERCISE_TYPE + ", "
				+ ExerciseColumns.FIELD_EXERCISE_NUMBER_OF_PERSONS + ", "
				+ ExerciseColumns.FIELD_EXERCISE_EQUIPMENT + ") "
				+ "values ('" + exercise.getName() + "', '"
				+ exercise.getExerciseType() + "','"
				+ exercise.getExerciseNumberOfPersons() + "','"
				+ exercise.getExerciseEquipment() + "')";
		DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
	}

	public List<Exercise> getAll() {
		List<Exercise> mExerciseList = new ArrayList<Exercise>();
		Exercise mExercise = null;
		String sql = "select * from "
				+ ExerciseColumns.TABLE_EXERCISE;
		Cursor mCursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		try {
			mCursor.moveToNext();
			while (mCursor.getPosition() != mCursor.getCount()) {
				mExercise = new Exercise();
				mExercise.setId(mCursor.getInt(0));
				mExercise.setName(mCursor.getString(1));
				mExercise.setExerciseType(mCursor.getString(2));
				mExercise.setExerciseNumberOfPersons(mCursor.getString(3));
				mExercise.setExerciseEquipment(mCursor.getString(4));
				mExerciseList.add(mExercise);
				mCursor.moveToNext();
			}
		} catch (Exception e) {
			mCursor.close();
		}
		return mExerciseList;
	}
	
}
