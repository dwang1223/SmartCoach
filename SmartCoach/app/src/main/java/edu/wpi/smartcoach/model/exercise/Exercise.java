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
 * An exercise
 * @author Akshay
 */
public class Exercise {
	
	
	private static final int RESOURCE_EXERCISE_LIST = R.raw.exercise_list; // res/raw/exercise_list.xml
	
	private static List<Exercise> exercises;
	
	/**
	 * Returns a list of all exercises
	 * @param c Android context
	 * @return list of all exercises
	 */
	public static List<Exercise> getAll(Context c){
		
		if(exercises == null || exercises.isEmpty()){
			readExercises(c);
		}
		
		return new ArrayList<Exercise>(exercises);
	}
	
	/**
	 * Reads the list of exercises from the exercise_list xml resource
	 * @param c Android context
	 */
	private static void readExercises(Context c){
		exercises = new ArrayList<Exercise>();
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			InputStream qStream = c.getResources().openRawResource(RESOURCE_EXERCISE_LIST);
			Document doc = dBuilder.parse(qStream);
			
			NodeList exerciseList = ((Element)doc.getElementsByTagName("exercises").item(0)).getElementsByTagName("exercise");
			
			for(int e = 0; e < exerciseList.getLength(); e++){
				Element exerciseElement = (Element)exerciseList.item(e);
				exercises.add(new Exercise(
						Integer.parseInt(exerciseElement.getAttribute("id")),
						exerciseElement.getAttribute("name"),
						exerciseElement.getAttribute("present"),
						exerciseElement.getAttribute("continuous")));
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private int id;
	private String name;	
	private String formPresent;
	private String formContinuous;
	
	/**
	 * Constructor
	 * @param id
	 * @param name
	 * @param formPresent
	 * @param formContinuous
	 */
	public Exercise(int id, String name, String formPresent, String formContinuous) {
		super();
		this.id = id;
		this.name = name;
		this.formPresent = formPresent;
		this.formContinuous = formContinuous;
	}
		
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the formPresent
	 */
	public String getFormPresent() {
		return formPresent;
	}

	/**
	 * @return the formContinuous
	 */
	public String getFormContinuous() {
		return formContinuous;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param formPresent the formPresent to set
	 */
	public void setFormPresent(String formPresent) {
		this.formPresent = formPresent;
	}

	/**
	 * @param formContinuous the formContinuous to set
	 */
	public void setFormContinuous(String formContinuous) {
		this.formContinuous = formContinuous;
	}

	@Override
	public boolean equals(Object e){
		if(e == null){
			return false;
		}
		if(e instanceof Exercise){
			return id == ((Exercise)e).getId();
		} else {
			return false;
		}
		
	}


}
