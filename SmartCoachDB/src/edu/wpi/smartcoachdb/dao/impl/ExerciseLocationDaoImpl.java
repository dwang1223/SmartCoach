package edu.wpi.smartcoachdb.dao.impl;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import edu.wpi.smartcoach.model.ExerciseLocation;
import edu.wpi.smartcoachdb.dao.ExerciseLocationDao;
import edu.wpi.smartcoachdb.db.column.ExerciseLocationColumns;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;

public class ExerciseLocationDaoImpl implements ExerciseLocationDao {

	@Override
	public void insertOne(ExerciseLocation exerciseLocation) {
		// TODO Auto-generated method stub
		String sql = "insert into "
				+ ExerciseLocationColumns.TABLE_EXERCISE_LOCATION + " ("
				+ ExerciseLocationColumns.FIELD_SPECIFIC_LOCATION + ", "
				+ ExerciseLocationColumns.FIELD_LOCATION_TYPE + ") "
				+ "values ('" + exerciseLocation.getSpecificLocation() + "', '"
				+ exerciseLocation.getLocationType() + "')";
		DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
	}

	@Override
	public List<ExerciseLocation> getAll() {
		// TODO Auto-generated method stub
		List<ExerciseLocation> mExerciseLocationList = new ArrayList<ExerciseLocation>();
		ExerciseLocation mExerciseLocation = null;
		String sql = "select * from "
				+ ExerciseLocationColumns.TABLE_EXERCISE_LOCATION;
		Cursor mCursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		try {
			mCursor.moveToNext();
			while (mCursor.getPosition() != mCursor.getCount()) {
				mExerciseLocation = new ExerciseLocation();
				mExerciseLocation.setId(mCursor.getInt(0));
				mExerciseLocation.setSpecificLocation(mCursor.getString(1));
				mExerciseLocation.setLocationType(mCursor.getString(2));
				mExerciseLocationList.add(mExerciseLocation);
				mCursor.moveToNext();
			}
		} catch (Exception e) {
			mCursor.close();
		}
		return mExerciseLocationList;
	}

}
