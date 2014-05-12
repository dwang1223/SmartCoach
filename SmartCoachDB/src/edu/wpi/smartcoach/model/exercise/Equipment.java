package edu.wpi.smartcoach.model.exercise;

import java.util.ArrayList;

import edu.wpi.smartcoach.model.OptionModel;

public class Equipment {
	
	public static final int ID_GYM_MEMBERSHIP = 13;
	
	public static ArrayList<Equipment> equipment;
	
	
	
	static {
		equipment = new ArrayList<Equipment>(){{
			add(new Equipment(0, "Treadmill"));
			add(new Equipment(1, "Eliptical"));
			add(new Equipment(2, "Stationary Bike"));
			add(new Equipment(3, "Bike"));
			add(new Equipment(4, "Stepper"));
			add(new Equipment(5, "Tennis Racquet"));
			add(new Equipment(6, "Badminton Racquet"));
			add(new Equipment(7, "Roller blades/skates"));
			add(new Equipment(8, "Ice skates"));
			add(new Equipment(9, "Running Shoes"));
			add(new Equipment(10, "Swimsuit"));
			add(new Equipment(12, "Excercise Videos"));
			add(new Equipment(ID_GYM_MEMBERSHIP, "Gym membership"));
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
	
	public String toString(){
		return name;
	}
	
}
