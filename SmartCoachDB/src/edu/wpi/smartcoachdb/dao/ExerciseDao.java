package edu.wpi.smartcoachdb.dao;

import java.util.List;

import edu.wpi.smartcoach.model.Exercise;

public interface ExerciseDao {

	/**
	 * insert one tuple into the table of t_exercise
	 * @param exercise
	 */
	public void insertOne(Exercise exercise);
	
	/**
	 * get all data from table of t_exercise
	 * @return
	 */
	public List<Exercise> getAll();
}
