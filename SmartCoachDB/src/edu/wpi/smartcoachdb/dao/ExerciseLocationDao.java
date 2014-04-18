package edu.wpi.smartcoachdb.dao;

import java.util.List;

import edu.wpi.smartcoach.model.ExerciseLocation;

public interface ExerciseLocationDao {

	/**
	 * insert one tuple into the table of t_exercise_location
	 * @param exercise
	 */
	public void insertOne(ExerciseLocation exerciseLocation);
	
	/**
	 * get all data from table of t_exercise_location
	 * @return
	 */
	public List<ExerciseLocation> getAll();
}
