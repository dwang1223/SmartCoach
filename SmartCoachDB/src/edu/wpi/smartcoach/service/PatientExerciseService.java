package edu.wpi.smartcoach.service;

import edu.wpi.smartcoach.model.PatientExercise;
import edu.wpi.smartcoachdb.dao.PatientExerciseDao;

public class PatientExerciseService {

	private PatientExerciseDao mPatientExerciseDao = new PatientExerciseDao();
	private static PatientExerciseService mPatientExerciseService = null;
	
	public static PatientExerciseService getInstance(){
		if(mPatientExerciseService == null){
			mPatientExerciseService = new PatientExerciseService();
		}
		return mPatientExerciseService;
	}
	
	public void addPatientExercise(PatientExercise mPatientExercise) {
		mPatientExerciseDao.insertOne(mPatientExercise);
	}
}
