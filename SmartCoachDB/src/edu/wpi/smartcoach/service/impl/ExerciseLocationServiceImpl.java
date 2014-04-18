package edu.wpi.smartcoach.service.impl;

import java.util.List;

import edu.wpi.smartcoach.model.ExerciseLocation;
import edu.wpi.smartcoach.service.ExerciseLocationService;
import edu.wpi.smartcoachdb.dao.ExerciseLocationDao;
import edu.wpi.smartcoachdb.dao.impl.ExerciseLocationDaoImpl;

public class ExerciseLocationServiceImpl implements ExerciseLocationService {

	private ExerciseLocationDao mExerciseLocationDao = new ExerciseLocationDaoImpl();
	private static ExerciseLocationService mExerciseLocationService = null;
	
	public static ExerciseLocationService getInstance(){
		if(mExerciseLocationService == null){
			mExerciseLocationService = new ExerciseLocationServiceImpl();
		}
		return mExerciseLocationService;
	}
	
	@Override
	public List<ExerciseLocation> getAllDataFromTable() {
		return mExerciseLocationDao.getAll();
	}

}
