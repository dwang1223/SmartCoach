package edu.wpi.smartcoachdb.dao;

import java.util.List;

import edu.wpi.smartcoach.model.ExerciseTime;

public interface ExerciseTimeDao {

	/**
	 * insert one tuple into the table of t_exercise_time
	 * @param exercise
	 */
	public void insertOne(ExerciseTime exerciseTime);
	
	/**
	 * get all data from table of t_exercise_time
	 * @return
	 */
	public List<ExerciseTime> getAll();
}
