package edu.wpi.smartcoach.service;

import java.util.List;

import edu.wpi.smartcoach.model.ExerciseLocation;
import edu.wpi.smartcoachdb.dao.ExerciseLocationDao;

public class ExerciseLocationService {

	private ExerciseLocationDao mExerciseLocationDao = new ExerciseLocationDao();
	private static ExerciseLocationService mExerciseLocationService = null;
	
	public static ExerciseLocationService getInstance(){
		if(mExerciseLocationService == null){
			mExerciseLocationService = new ExerciseLocationService();
		}
		return mExerciseLocationService;
	}
	
	public List<ExerciseLocation> getAllDataFromTable() {
		return mExerciseLocationDao.getAll();
	}

}
