package edu.wpi.smartcoach.service;

import edu.wpi.smartcoach.model.PatientProfile;
import edu.wpi.smartcoachdb.dao.PatientProfileDao;

public class PatientProfileService{

	private PatientProfileDao mPatientProfileDao = new PatientProfileDao();

	private static PatientProfileService mPatientProfileService;

	public static PatientProfileService getInstance() {
		if (mPatientProfileService == null) {
			mPatientProfileService = new PatientProfileService();
		}
		return mPatientProfileService;
	}

	public void initPatientProfile(PatientProfile mPatientProfile) {
		mPatientProfileDao.insertOne(mPatientProfile);
	}

}
