package edu.wpi.smartcoachdb.dao;

import edu.wpi.smartcoach.model.PatientProfile;
import edu.wpi.smartcoachdb.db.column.PatientProfileColumns;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;

public class PatientProfileDao {

	public void insertOne(PatientProfile patientProfile) {
		// TODO Auto-generated method stub
		String sql = "insert into "
				+ PatientProfileColumns.TABLE_PATIENT_PROFILE + " ("
				+ PatientProfileColumns.FIELD_PATIENT_FIRST_NAME + ", "
				+ PatientProfileColumns.FIELD_PATIENT_LAST_NAME + ", "
				+ PatientProfileColumns.FIELD_PATIENT_GENDER + ", "
				+ PatientProfileColumns.FIELD_PATIENT_BIRTHDAY + ", "
				+ PatientProfileColumns.FIELD_PATIENT_ADDRESS + ", "
				+ PatientProfileColumns.FIELD_PATIENT_OCCUPATION + ") "
				+ "values ('" + patientProfile.getPatientFirstName() + "', '"
				+ patientProfile.getPatientLastName() + "','"
				+ patientProfile.getPatientGender() + "','"
				+ patientProfile.getPatientBirthday() + "','"
				+ patientProfile.getPatientAddress() + "','"
				+ patientProfile.getPatientOccupation() + "')";
		DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
	}

}
