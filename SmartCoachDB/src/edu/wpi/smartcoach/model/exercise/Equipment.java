package edu.wpi.smartcoach.model.exercise;

import java.util.ArrayList;

import edu.wpi.smartcoach.model.OptionModel;

public class Equipment {
	
	public static ArrayList<Equipment> equipment;
	
	static {
		equipment = new ArrayList<Equipment>(){{
			add(new Equipment(0, "Treadmill"));
			add(new Equipment(1, "Eliptical"));
			add(new Equipment(2, "Stationary Bike"));
			add(new Equipment(3, "Stepper"));
			add(new Equipment(4, "Tennis Racquet"));
			add(new Equipment(5, "Badminton Racquet"));
			add(new Equipment(6, "Roller blades/skates"));
			add(new Equipment(7, "Ice skates"));
			add(new Equipment(8, "Running Shoes"));
			add(new Equipment(9, "Swimsuit"));
			add(new Equipment(10, "Excercise Videos"));
			add(new Equipment(11, "Gym membership"));
		}};
	}
	
	public static Equipment getEquipmentById(String id){
		for(Equipment e:equipment){
			if(id.equals(e.getId())){
				return e;
			}
		}
		return null;
	}
	
	private int id;
	private String name;
	
	public Equipment(int id, String name){
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName(){
		return name;
	}
	
}
