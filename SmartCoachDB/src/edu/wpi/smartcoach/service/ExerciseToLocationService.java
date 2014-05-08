package edu.wpi.smartcoach.service;

import java.util.List;

import edu.wpi.smartcoachdb.dao.ExerciseToLocationDao;

public class ExerciseToLocationService {

	private ExerciseToLocationDao mExerciseToLocationDao = new ExerciseToLocationDao();
	private static ExerciseToLocationService mExerciseToLocationService = null;
	
	public static ExerciseToLocationService getInstance(){
		if(mExerciseToLocationService == null){
			mExerciseToLocationService = new ExerciseToLocationService();
		}
		return mExerciseToLocationService;
	}
	
	public List<Integer> getLocationListByExercise(int exerciseID){
		return mExerciseToLocationDao.getLocationList(exerciseID);
	}
}