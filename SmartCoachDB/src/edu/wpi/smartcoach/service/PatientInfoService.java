package edu.wpi.smartcoach.service;

import edu.wpi.smartcoachdb.model.PatientInfo;

public interface PatientInfoService {

	/**
	 * initiate the data of patient info
	 * @param mPatientInfo
	 */
	public void initPatientInfo(PatientInfo mPatientInfo);
}