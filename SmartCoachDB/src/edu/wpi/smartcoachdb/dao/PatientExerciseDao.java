package edu.wpi.smartcoachdb.dao;

import edu.wpi.smartcoach.model.exercise.PatientExercise;
import edu.wpi.smartcoachdb.db.column.PatientExerciseColumns;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;

public class PatientExerciseDao {
	public void insertOne(PatientExercise patientExercise) {
		String sql = "insert into "
				+ PatientExerciseColumns.TABLE_PATIENT_EXERCISE + " ("
				+ PatientExerciseColumns.FIELD_PATIENT_ID + ", "
				+ PatientExerciseColumns.FIELD_EXERCISE_ID + ", "
				+ PatientExerciseColumns.FIELD_EXERCISE_LOCATION_ID + ", "
				+ PatientExerciseColumns.FIELD_EXERCISE_TIME_ID + ", "
				+ PatientExerciseColumns.FIELD_PATIENT_IS_LIKED + ") "
				+ "values ('" + patientExercise.getPatientID() + "', '"
				+ patientExercise.getExerciseID() + "','"
				+ patientExercise.getExerciseLocationID() + "','"
				+ patientExercise.getExerciseTimeID() + "','"
				+ patientExercise.isLiked() + "')";
		DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
	}
}
