package edu.wpi.smartcoach.service;

import java.util.List;

import edu.wpi.smartcoach.model.ExerciseTime;
import edu.wpi.smartcoachdb.dao.ExerciseTimeDao;

public class ExerciseTimeService {

	private ExerciseTimeDao  mExerciseTimeDao = new ExerciseTimeDao();
	private static ExerciseTimeService mExerciseTImeService = null;
	
	public static ExerciseTimeService getInstance (){
		if(mExerciseTImeService == null){
			mExerciseTImeService = new ExerciseTimeService();
		}
		return mExerciseTImeService;
	}
	
	public List<ExerciseTime> getAllDataFromTable() {
		return mExerciseTimeDao.getAll();
	}

	public int getExerciseTimeID(String exerciseTime){
		return mExerciseTimeDao.getID(exerciseTime);
	}
	
	public String getExerciseTime(int exerciseTimeID) {
		return mExerciseTimeDao.getTime(exerciseTimeID);
	}
}
