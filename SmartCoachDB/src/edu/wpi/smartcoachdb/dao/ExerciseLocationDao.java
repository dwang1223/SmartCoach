package edu.wpi.smartcoachdb.dao;

import edu.wpi.smartcoachdb.model.ExerciseLocation;

public interface ExerciseLocationDao {

	/**
	 * insert one tuple into the table of t_exercise_location
	 * @param exercise
	 */
	public void insertOne(ExerciseLocation exerciseLocation);
}
