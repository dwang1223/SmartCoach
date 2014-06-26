package edu.wpi.smartcoach.service;

import java.util.Date;

import android.database.Cursor;
import edu.wpi.smartcoach.db.column.PatientProfileColumns;
import edu.wpi.smartcoach.model.PatientProfile;
import edu.wpi.smartcoach.util.DatabaseHelper;

public class PatientProfileService{

	private static PatientProfileService instance;

	public static PatientProfileService getInstance() {
		if (instance == null) {
			instance = new PatientProfileService();
		}
		return instance;
	}

	public void initPatientProfile(PatientProfile profile) {
		// TODO Auto-generated method stub	
		
		String sql = "insert into "
				+ PatientProfileColumns.TABLE_PATIENT_PROFILE + " ("
				+ PatientProfileColumns.FIELD_PATIENT_FIRST_NAME + ", "
				+ PatientProfileColumns.FIELD_PATIENT_LAST_NAME + ", "
				+ PatientProfileColumns.FIELD_PATIENT_GENDER + ", "
				+ PatientProfileColumns.FIELD_PATIENT_BIRTHDAY + ", "
				+ PatientProfileColumns.FIELD_PATIENT_ADDRESS + ", "
				+ PatientProfileColumns.FIELD_PATIENT_OCCUPATION + ") "
				+ "values ('" + profile.getFirstName() + "', '"
				+ profile.getLastName() + "','"
				+ profile.getGender() + "','"
				+ profile.getPatientBirthday() + "','"
				+ profile.getPatientAddress() + "','"
				+ profile.getPatientOccupation() + "')";
		DatabaseHelper.getInstance().getWritableDatabase().execSQL("delete from "+PatientProfileColumns.TABLE_PATIENT_PROFILE);
		DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
	}
	
	public PatientProfile getProfile(){
		PatientProfile profile = new PatientProfile("", "", "", null, "", "");
		
		String sql = "select * from " +PatientProfileColumns.TABLE_PATIENT_PROFILE;
		
		Cursor mCursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		
		try {
			mCursor.moveToNext();
			profile.setPatientFirstName(mCursor.getString(1));
			profile.setPatientLastName(mCursor.getString(2));
			profile.setPatientGender(mCursor.getString(3));
			profile.setPatientBirthday(new Date(Date.parse(mCursor.getString(4))));
		} finally {
			mCursor.close();
		}
		
		return profile;
	}

}
