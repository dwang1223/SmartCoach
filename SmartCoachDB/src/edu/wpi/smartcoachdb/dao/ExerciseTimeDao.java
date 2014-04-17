package edu.wpi.smartcoachdb.dao;

import edu.wpi.smartcoach.model.ExerciseTime;

public interface ExerciseTimeDao {

	/**
	 * insert one tuple into the table of t_exercise_time
	 * @param exercise
	 */
	public void insertOne(ExerciseTime exerciseTime);
}
