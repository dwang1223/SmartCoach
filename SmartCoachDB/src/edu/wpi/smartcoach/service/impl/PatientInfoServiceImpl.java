package edu.wpi.smartcoach.service.impl;

import edu.wpi.smartcoach.service.PatientInfoService;
import edu.wpi.smartcoach.service.PatientProfileService;
import edu.wpi.smartcoachdb.dao.PatientInfoDao;
import edu.wpi.smartcoachdb.dao.impl.PatientInfoDaoImpl;
import edu.wpi.smartcoachdb.dao.impl.PatientProfileDaoImpl;
import edu.wpi.smartcoachdb.model.PatientInfo;

public class PatientInfoServiceImpl implements PatientInfoService {

	private PatientInfoDao mPatientInfoDao = new PatientInfoDaoImpl();
	
	private static PatientInfoService mPatientInfoService; 
	
	public static PatientInfoService getInstance() {
		if (mPatientInfoService == null) {
			mPatientInfoService = new PatientInfoServiceImpl();
		}
		return mPatientInfoService;
	}
	@Override
	public void initPatientInfo(PatientInfo mPatientInfo) {
		// TODO Auto-generated method stub
		mPatientInfoDao.insertOne(mPatientInfo);
	}

}
