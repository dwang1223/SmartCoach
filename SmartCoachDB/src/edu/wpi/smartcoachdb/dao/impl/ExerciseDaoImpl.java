package edu.wpi.smartcoachdb.dao.impl;

import android.database.sqlite.SQLiteDatabase;
import edu.wpi.smartcoachdb.dao.ExerciseDao;
import edu.wpi.smartcoachdb.db.column.ExerciseColumns;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;
import edu.wpi.smartcoachdb.model.Exercise;
import edu.wpi.smartcoachdb.ui.MainActivity;

public class ExerciseDaoImpl implements ExerciseDao{

	@Override
	public void insertOne(Exercise exercise) {
		// TODO Auto-generated method stub
		String sql = "insert into "
				+ ExerciseColumns.TABLE_EXERCISE + " ("
				+ ExerciseColumns.FIELD_EXERCISE_NAME + ", "
				+ ExerciseColumns.FIELD_EXERCISE_TYPE + ", "
				+ ExerciseColumns.FIELD_EXERCISE_NUMBER_OF_PERSONS + ", "
				+ ExerciseColumns.FIELD_EXERCISE_EQUIPMENT + ") "
				+ "values ('" + exercise.getExerciseName() + "', '"
				+ exercise.getExerciseType() + "','"
				+ exercise.getExerciseNumberOfPersons() + "','"
				+ exercise.getExerciseEquipment() + "')";
		DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
	}
	
}
