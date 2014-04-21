package edu.wpi.smartcoach.service.impl;

import java.util.List;

import edu.wpi.smartcoach.model.Exercise;
import edu.wpi.smartcoach.service.ExerciseService;
import edu.wpi.smartcoachdb.dao.ExerciseDao;
import edu.wpi.smartcoachdb.dao.impl.ExerciseDaoImpl;

public class ExerciseServiceImpl implements ExerciseService {

	private ExerciseDao mExerciseDao = new ExerciseDaoImpl();
	private static ExerciseService mExerciseService = null;
	
	public static ExerciseService getInstance(){
		if(mExerciseService == null){
			mExerciseService = new ExerciseServiceImpl();
		}
		return mExerciseService;
	}
	
	@Override
	public List<Exercise> getAllDataFromTable() {
		return mExerciseDao.getAll();
	}

}
