package edu.wpi.smartcoachdb.dao;

import edu.wpi.smartcoachdb.model.PatientProfile;

public interface PatientProfileDao {

	/**
	 * insert one tuple into the table of t_patient_profile
	 * @param patientProfile
	 */
	public void insertOne(PatientProfile patientProfile);
}
