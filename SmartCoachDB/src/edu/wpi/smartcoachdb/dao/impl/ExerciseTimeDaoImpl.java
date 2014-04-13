package edu.wpi.smartcoachdb.dao.impl;

import android.database.sqlite.SQLiteDatabase;
import edu.wpi.smartcoachdb.dao.ExerciseTimeDao;
import edu.wpi.smartcoachdb.db.column.ExerciseTimeColumns;
import edu.wpi.smartcoachdb.model.ExerciseTime;
import edu.wpi.smartcoachdb.ui.MainActivity;

public class ExerciseTimeDaoImpl implements ExerciseTimeDao {

	private SQLiteDatabase mSQLiteDatabase = MainActivity.mSQLiteDatabase;

	@Override
	public void insertOne(ExerciseTime exerciseTime) {
		// TODO Auto-generated method stub
		String sql = "insert into " + ExerciseTimeColumns.TABLE_EXERCISE_TIME
				+ " (" + ExerciseTimeColumns.FIELD_EXERCISE_TIME + ") "
				+ "values ('" + exerciseTime.getExerciseTime() + "')";
		mSQLiteDatabase.execSQL(sql);
	}

}
