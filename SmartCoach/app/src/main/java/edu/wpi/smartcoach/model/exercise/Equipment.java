package edu.wpi.smartcoach.model.exercise;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import edu.wpi.smartcoach.R;

/**
 * Equipment used for exercise
 * @author Akshay
 */
public class Equipment {
	
	private static final int RESOURCE_EQUIPMENT_LIST = R.raw.equipment_list; // res/raw/equipment_list.xml
	
	private static ArrayList<Equipment> equipment;
	
	/**
	 * Returns a list of all Equipment
	 * @param c Android context
	 * @return list of all equipment
	 */
	public static List<Equipment> getAll(Context c){
		
		if(equipment == null || equipment.isEmpty()){
			readEquipment(c);
		}
		
		
		return new ArrayList<Equipment>(equipment);
		
	}
	
	/**
	 * Reads the list of equipment from the equipment_list xml resource
	 * @param c Android context
	 */
	private static void readEquipment(Context c){
		equipment = new ArrayList<Equipment>();
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			InputStream qStream = c.getResources().openRawResource(RESOURCE_EQUIPMENT_LIST);
			Document doc = dBuilder.parse(qStream);
			
			NodeList exerciseList = ((Element)doc.getElementsByTagName("equipments").item(0)).getElementsByTagName("equipment");
			
			for(int e = 0; e < exerciseList.getLength(); e++){
				Element exerciseElement = (Element)exerciseList.item(e);
				equipment.add(new Equipment(
						Integer.parseInt(exerciseElement.getAttribute("id")),
						exerciseElement.getAttribute("name"),
						null));
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the Equipment with the given id
	 * @param id the id to search for
	 * @return The equipment that has id
	 */
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
