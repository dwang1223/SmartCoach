package edu.wpi.smartcoach.model;

import java.util.ArrayList;

public class Exercises {
	
	public static ArrayList<Exercise> exercises;
	
	static {
		exercises = new ArrayList<Exercise>(){{
			add(new Exercise(0, "Walking"));
			add(new Exercise(1, "Running"));
			add(new Exercise(2, "Biking"));
			add(new Exercise(3, "Hiking"));
			add(new Exercise(4, "Skating"));
			add(new Exercise(5, "Yoga"));
			add(new Exercise(6, "Gymnastics"));
			add(new Exercise(7, "Weight Training"));
			add(new Exercise(8, "Karate"));
			add(new Exercise(9, "Dancing"));
			add(new Exercise(10, "Swimming"));
			add(new Exercise(11, "Tennis"));
			add(new Exercise(12, "Badminton"));
			add(new Exercise(13, "Basketball"));
			add(new Exercise(14, "Volleyball"));
			add(new Exercise(15, "Football"));
			add(new Exercise(16, "Soccer"));
			add(new Exercise(17, "Boxing"));
			add(new Exercise(18, "Hockey"));
			add(new Exercise(19, "Ice skating"));
			add(new Exercise(20, "Skiing"));
			add(new Exercise(21, "Golf"));
			add(new Exercise(22, "Horseback Riding"));
		}};
	}
	
	public static ArrayList<OptionModel> getExerciseOptions(){
		ArrayList<OptionModel> newList = new ArrayList<OptionModel>();
		for(Exercise opm:exercises){
			newList.add(opm);
		}
		return newList;
	}
	
	public static Exercise getExerciseById(String id){
		for(Exercise opm:exercises){
			if(id.equals(opm.getId())){
				return opm;
			}
		}
		return null;
	}
}
