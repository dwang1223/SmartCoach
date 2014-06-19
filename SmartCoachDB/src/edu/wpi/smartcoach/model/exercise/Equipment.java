package edu.wpi.smartcoach.model.exercise;

import java.util.ArrayList;

import edu.wpi.smartcoach.model.OptionModel;

public class Equipment {
	
	public static final int ID_GYM_MEMBERSHIP = 13;
	
	public static ArrayList<Equipment> equipment;
	
	
	
	static {
		equipment = new ArrayList<Equipment>(){{
			add(new Equipment(0, "Treadmill", new int[]{1,2}));
			add(new Equipment(1, "Elliptical", new int[]{1}));
			add(new Equipment(2, "Stationary Bike", new int[]{3}));
			add(new Equipment(3, "Bike", new int[]{3}));
			add(new Equipment(4, "Stepper", new int[]{1}));
			add(new Equipment(5, "Tennis Racquet", new int[]{}));
			add(new Equipment(6, "Badminton Racquet", new int[]{}));
			add(new Equipment(7, "Roller blades/skates", new int[]{5}));
			add(new Equipment(8, "Ice skates", new int[]{5}));
			add(new Equipment(9, "Running Shoes", new int[]{1,2,4}));
			add(new Equipment(10, "Swimsuit", new int[]{11}));
			add(new Equipment(12, "Exercise Videos", new int[]{7,10}));
			add(new Equipment(ID_GYM_MEMBERSHIP, "Gym membership", new int[]{1,2,3,6,9,12}));
		}};
	}
	
	public static Equipment getEquipmentById(int id){
		for(Equipment e:equipment){
			if(id == e.getId()){
				return e;
			}
		}
		return null;
	}
		
	private int id;
	private String name;
	private int[] exercises;
	
	public Equipment(int id, String name, int[] exercises){
		this.id = id;
		this.name = name;
		this.exercises = exercises;
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
	
	public int[] getExercises(){
		return exercises;
	}
	
	
	
}
