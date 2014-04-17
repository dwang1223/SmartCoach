package edu.wpi.smartcoachdb.dao.impl;

import edu.wpi.smartcoach.model.ExerciseTime;
import edu.wpi.smartcoachdb.dao.ExerciseTimeDao;
import edu.wpi.smartcoachdb.db.column.ExerciseTimeColumns;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;

public class ExerciseTimeDaoImpl implements ExerciseTimeDao {

	@Override
	public void insertOne(ExerciseTime exerciseTime) {
		// TODO Auto-generated method stub
		String sql = "insert into " + ExerciseTimeColumns.TABLE_EXERCISE_TIME
				+ " (" + ExerciseTimeColumns.FIELD_EXERCISE_TIME + ") "
				+ "values ('" + exerciseTime.getExerciseTime() + "')";
		DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
	}

}
