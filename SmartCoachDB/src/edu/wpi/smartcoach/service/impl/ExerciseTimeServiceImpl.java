package edu.wpi.smartcoach.service.impl;

import java.util.List;

import edu.wpi.smartcoach.model.ExerciseTime;
import edu.wpi.smartcoach.service.ExerciseTImeService;
import edu.wpi.smartcoachdb.dao.ExerciseTimeDao;
import edu.wpi.smartcoachdb.dao.impl.ExerciseTimeDaoImpl;

public class ExerciseTimeServiceImpl implements ExerciseTImeService {

	private ExerciseTimeDao  mExerciseTimeDao = new ExerciseTimeDaoImpl();
	private static ExerciseTImeService mExerciseTImeService = null;
	
	public static ExerciseTImeService getInstance (){
		if(mExerciseTImeService == null){
			mExerciseTImeService = new ExerciseTimeServiceImpl();
		}
		return mExerciseTImeService;
	}
	
	@Override
	public List<ExerciseTime> getAllDataFromTable() {
		return mExerciseTimeDao.getAll();
	}

}
