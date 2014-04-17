package edu.wpi.smartcoach.model;

import java.util.ArrayList;

public class Equipment implements OptionModel{
	
	public static ArrayList<OptionModel> equipment;
	
	static {
		equipment = new ArrayList<OptionModel>(){{
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
		for(OptionModel opm:equipment){
			if(id.equals(opm.getId())){
				return (Equipment)opm;
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

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

}
