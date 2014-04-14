package edu.wpi.smartcoachdb.dao.impl;

import edu.wpi.smartcoachdb.dao.PatientInfoDao;
import edu.wpi.smartcoachdb.db.column.PatientInfoColumns;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;
import edu.wpi.smartcoachdb.model.PatientInfo;

public class PatientInfoDaoImpl implements PatientInfoDao {

	@Override
	public void insertOne(PatientInfo patientInfo) {
		// TODO Auto-generated method stub
		String sql = "insert into "
				+ PatientInfoColumns.TABLE_PATIENT_INFO + " ("
				+ PatientInfoColumns.FIELD_PATIENT_HEIGHT + ", "
				+ PatientInfoColumns.FIELD_PATIENT_WEIGHT + ", "
				+ PatientInfoColumns.FIELD_PATIENT_AGE + ", "
				+ PatientInfoColumns.FIELD_LAST_UPDATE_TIME + ", "
				+ PatientInfoColumns.FIELD_GOAL_WEIGHT + ") "
				+ "values ('" + patientInfo.getPatientHeight() + "', '"
				+ patientInfo.getPatientWeight() + "','"
				+ patientInfo.getPatientAge() + "','"
				+ patientInfo.getLastUpateTimeDate() + "','"
				+ patientInfo.getPatientGoalWeight() + "')";
		DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
	}

}
