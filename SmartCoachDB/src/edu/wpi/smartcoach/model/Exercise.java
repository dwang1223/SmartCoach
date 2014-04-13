package edu.wpi.smartcoach.model;

import java.util.ArrayList;

public class Exercise {
	
	public static ArrayList<OptionModel> exercises;
	
	static {
		exercises = new ArrayList<OptionModel>(){{
			add(new OptionModel("walk", "Walking"));
			add(new OptionModel("run", "Running"));
			add(new OptionModel("bike", "Biking"));
			add(new OptionModel("hike", "Hiking"));
			add(new OptionModel("skate", "Skating"));
			add(new OptionModel("yoga", "Yoga"));
			add(new OptionModel("gymnastics", "Gymnastics"));
			add(new OptionModel("weight", "Weight Training"));
			add(new OptionModel("karate", "Karate"));
			add(new OptionModel("dance", "Dancing"));
			add(new OptionModel("swim", "Swimming"));
			add(new OptionModel("tennis", "Tennis"));
			add(new OptionModel("badminton", "Badminton"));
			add(new OptionModel("basket", "Basketball"));
			add(new OptionModel("volley", "Volleyball"));
			add(new OptionModel("handegg", "Football"));
			add(new OptionModel("football", "Soccer"));
			add(new OptionModel("box", "Boxing"));
			add(new OptionModel("hockey", "Hockey"));
			add(new OptionModel("ice", "Ice skating"));
			add(new OptionModel("ski", "Skiing"));
			add(new OptionModel("golf", "Golf"));
			add(new OptionModel("horse", "Horseback Riding"));
		}};
	}
	
	public static ArrayList<OptionModel> getExercises(){
		ArrayList<OptionModel> newList = new ArrayList<OptionModel>();
		for(OptionModel opm:exercises){
			newList.add(new OptionModel(opm.getId(), opm.getText()));
		}
		return newList;
	}
	
	public static OptionModel getExerciseById(String id){
		for(OptionModel opm:exercises){
			if(id.equals(opm.getId())){
				return opm;
			}
		}
		return null;
	}
}
