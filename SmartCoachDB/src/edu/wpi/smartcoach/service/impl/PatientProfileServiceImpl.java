package edu.wpi.smartcoach.service.impl;

import edu.wpi.smartcoach.service.PatientProfileService;
import edu.wpi.smartcoachdb.dao.PatientProfileDao;
import edu.wpi.smartcoachdb.dao.impl.PatientProfileDaoImpl;
import edu.wpi.smartcoachdb.model.PatientProfile;

public class PatientProfileServiceImpl implements PatientProfileService {

	private PatientProfileDao mPatientProfileDao = new PatientProfileDaoImpl();

	private static PatientProfileService mPatientProfileService;

	public static PatientProfileService getInstance() {
		if (mPatientProfileService == null) {
			mPatientProfileService = new PatientProfileServiceImpl();
		}
		return mPatientProfileService;
	}

	@Override
	public void initPatientProfile(PatientProfile mPatientProfile) {
		mPatientProfileDao.insertOne(mPatientProfile);
	}

}
