package edu.wpi.smartcoach.service;

import edu.wpi.smartcoachdb.model.PatientProfile;

public interface PatientProfileService {
	/**
	 * initiate the data of patient profile
	 * @param mPatientProfile
	 */
	public void initPatientProfile(PatientProfile mPatientProfile);
}