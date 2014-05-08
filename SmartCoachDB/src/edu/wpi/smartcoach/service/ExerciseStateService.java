package edu.wpi.smartcoach.service;

import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoachdb.dao.ExerciseStateDao;

public class ExerciseStateService {

	private ExerciseStateDao mPatientExerciseDao = new ExerciseStateDao();
	private static ExerciseStateService mPatientExerciseService = null;
	
	public static ExerciseStateService getInstance(){
		if(mPatientExerciseService == null){
			mPatientExerciseService = new ExerciseStateService();
		}
		return mPatientExerciseService;
	}
	
	public void addPatientExercise(ExerciseState mPatientExercise) {
		mPatientExerciseDao.insertOne(mPatientExercise);
	}
}
