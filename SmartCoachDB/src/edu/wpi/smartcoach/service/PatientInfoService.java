package edu.wpi.smartcoach.service;

import edu.wpi.smartcoach.model.PatientInfo;
import edu.wpi.smartcoachdb.dao.PatientInfoDao;

public class PatientInfoService{

	private PatientInfoDao mPatientInfoDao = new PatientInfoDao();
	
	private static PatientInfoService mPatientInfoService; 
	
	public static PatientInfoService getInstance() {
		if (mPatientInfoService == null) {
			mPatientInfoService = new PatientInfoService();
		}
		return mPatientInfoService;
	}
	
	public void initPatientInfo(PatientInfo mPatientInfo) {
		// TODO Auto-generated method stub
		mPatientInfoDao.insertOne(mPatientInfo);
	}

}
