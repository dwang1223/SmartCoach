package edu.wpi.smartcoachdb.dao;

import edu.wpi.smartcoachdb.model.PatientInfo;

public interface PatientInfoDao {

	/**
	 * insert one tuple into the table of t_patient_info
	 * @param patientInfo
	 */
	public void insertOne(PatientInfo patientInfo);
}
