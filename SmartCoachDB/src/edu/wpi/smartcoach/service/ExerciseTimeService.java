package edu.wpi.smartcoach.service;

import java.util.List;

import edu.wpi.smartcoach.model.exercise.ExerciseTime;
import edu.wpi.smartcoachdb.dao.ExerciseTimeDao;

public class ExerciseTimeService {

	private ExerciseTimeDao  mExerciseTimeDao = new ExerciseTimeDao();
	private static ExerciseTimeService instance = null;
	
	public static ExerciseTimeService getInstance (){
		if(instance == null){
			instance = new ExerciseTimeService();
		}
		return instance;
	}
	
	public List<ExerciseTime> getAllDataFromTable() {
		return mExerciseTimeDao.getAll();
	}

	public int getExerciseTimeID(String exerciseTime){
		return mExerciseTimeDao.getID(exerciseTime);
	}
	
	public ExerciseTime getExerciseTime(int exerciseTimeID) {
		return mExerciseTimeDao.getTime(exerciseTimeID);
	}
}
