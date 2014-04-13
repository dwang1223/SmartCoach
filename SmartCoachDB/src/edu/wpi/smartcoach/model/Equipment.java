package edu.wpi.smartcoach.model;

import java.util.ArrayList;

public class Equipment {
	
	public static ArrayList<OptionModel> equipment;
	
	static {
		equipment = new ArrayList<OptionModel>(){{
			add(new OptionModel("treadmill", "Treadmill"));
			add(new OptionModel("eliptical", "Eliptical"));
			add(new OptionModel("st_bike", "Stationary Bike"));
			add(new OptionModel("stepper", "Stepper"));
			add(new OptionModel("tennis", "Tennis Racquet"));
			add(new OptionModel("badminton", "Badminton Racquet"));
			add(new OptionModel("roller", "Roller blades/skates"));
			add(new OptionModel("ice_skate", "Ice skates"));
			add(new OptionModel("running_shoes", "Running Shoes"));
			add(new OptionModel("swimsuit", "Swimsuit"));
			add(new OptionModel("exercise_vid", "Excercise Videos"));
			add(new OptionModel("gym_member", "Gym membership"));
		}};
	}
	
	public static OptionModel getEquipmentById(String id){
		for(OptionModel opm:equipment){
			if(id.equals(opm.getId())){
				return opm;
			}
		}
		return null;
	}

}
