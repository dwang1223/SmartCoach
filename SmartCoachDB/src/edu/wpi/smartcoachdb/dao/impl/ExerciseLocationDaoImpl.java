package edu.wpi.smartcoachdb.dao.impl;

import edu.wpi.smartcoachdb.dao.ExerciseLocationDao;
import edu.wpi.smartcoachdb.db.column.ExerciseLocationColumns;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;
import edu.wpi.smartcoachdb.model.ExerciseLocation;

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

}
