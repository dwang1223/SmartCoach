package edu.wpi.smartcoachdb.dao.impl;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
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
				+ "values ('" + exerciseTime.getTime() + "')";
		DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
	}

	@Override
	public List<ExerciseTime> getAll() {
		// TODO Auto-generated method stub
		List<ExerciseTime> mExerciseTimeList = new ArrayList<ExerciseTime>();
		ExerciseTime mExerciseTime = null;
		String sql = "select * from " + ExerciseTimeColumns.TABLE_EXERCISE_TIME;
		Cursor mCursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		try {
			mCursor.moveToNext();
			while (mCursor.getPosition() != mCursor.getCount()) {
				mExerciseTime = new ExerciseTime();
				mExerciseTime.setId(mCursor.getInt(0));
				mExerciseTime.setTime(mCursor.getString(1));
				mExerciseTimeList.add(mExerciseTime);
				mCursor.moveToNext();
			}
		} catch (Exception e) {
			mCursor.close();
		}
		return mExerciseTimeList;
	}

}
