package edu.wpi.smartcoachdb.dao.impl;

import android.database.sqlite.SQLiteDatabase;
import edu.wpi.smartcoachdb.dao.ExerciseLocationDao;
import edu.wpi.smartcoachdb.db.column.ExerciseLocationColumns;
import edu.wpi.smartcoachdb.model.ExerciseLocation;
import edu.wpi.smartcoachdb.ui.MainActivity;

public class ExerciseLocationDaoImpl implements ExerciseLocationDao {

	private SQLiteDatabase mSQLiteDatabase = MainActivity.mSQLiteDatabase;

	@Override
	public void insertOne(ExerciseLocation exerciseLocation) {
		// TODO Auto-generated method stub
		String sql = "insert into "
				+ ExerciseLocationColumns.TABLE_EXERCISE_LOCATION + " ("
				+ ExerciseLocationColumns.FIELD_SPECIFIC_LOCATION + ", "
				+ ExerciseLocationColumns.FIELD_LOCATION_TYPE + ") "
				+ "values ('" + exerciseLocation.getSpecificLocation() + "', '"
				+ exerciseLocation.getLocationType() + "')";
		mSQLiteDatabase.execSQL(sql);
	}

}
