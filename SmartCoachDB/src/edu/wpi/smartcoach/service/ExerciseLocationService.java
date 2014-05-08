package edu.wpi.smartcoach.service;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.smartcoach.model.OptionModel;
import edu.wpi.smartcoach.model.SimpleOption;
import edu.wpi.smartcoach.model.exercise.ExerciseLocation;
import edu.wpi.smartcoachdb.dao.ExerciseLocationDao;

public class ExerciseLocationService {

	private ExerciseLocationDao mExerciseLocationDao = new ExerciseLocationDao();
	private static ExerciseLocationService mExerciseLocationService = null;
	
	public static ExerciseLocationService getInstance(){
		if(mExerciseLocationService == null){
			mExerciseLocationService = new ExerciseLocationService();
		}
		return mExerciseLocationService;
	}
	
	public List<ExerciseLocation> getAllDataFromTable() {
		return mExerciseLocationDao.getAll();
	}
	
	public ExerciseLocation getLocation(int id){
		return mExerciseLocationDao.getLocation(id);
	}
	
	public int getExerciseLocationID(String specificLocation){
		return mExerciseLocationDao.getID(specificLocation);
	}
	
	public List<OptionModel> getLocations(List<Integer> idList){		
		ArrayList<OptionModel> locations = new ArrayList<OptionModel>();
		for(Integer i:idList){
			locations.add(new SimpleOption(i, getLocation(i).getSpecificLocation()));
		}
		return locations;
	}
}