package edu.wpi.smartcoach.service;

import edu.wpi.smartcoach.model.PatientMetrics;
import edu.wpi.smartcoachdb.dao.PatientMetricsDao;

public class PatientMetricsService{

	private PatientMetricsDao mPatientMetricsDao = new PatientMetricsDao();
	
	private static PatientMetricsService instance; 
	
	public static PatientMetricsService getInstance() {
		if (instance == null) {
			instance = new PatientMetricsService();
		}
		return instance;
	}
	
	public void initPatientMetrics(PatientMetrics mPatientInfo) {
		// TODO Auto-generated method stub
		mPatientMetricsDao.insertOne(mPatientInfo);
	}

	public PatientMetrics getMetrics(){
		return mPatientMetricsDao.getMetrics();
	}
	
}
