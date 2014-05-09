package edu.wpi.smartcoach.service;

import java.util.List;

import edu.wpi.smartcoach.model.exercise.Exercise;
import edu.wpi.smartcoachdb.dao.ExerciseDao;

public class ExerciseService {

	private ExerciseDao mExerciseDao = new ExerciseDao();
	private static ExerciseService mExerciseService = null;
	
	public static ExerciseService getInstance(){
		if(mExerciseService == null){
			mExerciseService = new ExerciseService();
		}
		return mExerciseService;
	}
	
	public List<Exercise> getAllDataFromTable() {
		return mExerciseDao.getAll();
	}

	
	public int getExerciseID(String exerciseName){
		return mExerciseDao.getID(exerciseName);
	}
	
	public Exercise getExercise(int id){
		return mExerciseDao.getExercise(id);
	}
}
