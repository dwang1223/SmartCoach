package edu.wpi.smartcoach.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.util.Log;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.TimeQuestionModel;
import edu.wpi.smartcoach.model.exercise.Equipment;
import edu.wpi.smartcoach.model.exercise.Exercise;
import edu.wpi.smartcoach.model.exercise.ExerciseLocation;
import edu.wpi.smartcoach.model.exercise.ExerciseTime;
import edu.wpi.smartcoach.service.ExerciseLocationService;
import edu.wpi.smartcoach.service.ExerciseService;
import edu.wpi.smartcoach.service.ExerciseTimeService;
import edu.wpi.smartcoach.view.Option;

public class QuestionReader {
	
	private static final String TAG = QuestionReader.class.getSimpleName();
	
	private static final String XML_TAG_QUESTION = "question";
	private static final String XML_TAG_PROMPT = "q";
	private static final String XML_TAG_OPTION = "option";
	
	private static final String XML_ATTR_ID = "id";
	private static final String XML_ATTR_TYPE = "type";
	private static final String XML_ATTR_TITLE = "title";
	private static final String XML_ATTR_DEFAULT = "default";
	private static final String XML_ATTR_OPTIONS = "options";
	private static final String XML_ATTR_SORT = "sort";
	
	private static final String TYPE_TIME = "time";
	
	private static final String OPTIONS_EXERCISES = "EXERCISES";
	private static final String OPTIONS_TIMES = "TIMES";
	private static final String OPTIONS_LOCATIONS = "LOCATIONS";
	private static final String OPTION_EQUIPMENT = "EQUIPMENT";
	
	public static List<QuestionModel> readQuestions(int resource, Context context){
		ArrayList<QuestionModel> questions = new ArrayList<QuestionModel>();
		
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			InputStream qStream = context.getResources().openRawResource(resource);
			Document doc = dBuilder.parse(qStream);
			
			NodeList questionList = doc.getElementsByTagName(XML_TAG_QUESTION);
			
			for(int i = 0; i < questionList.getLength(); i++){
				Element e = (Element)questionList.item(i);
				if(e.getAttribute(XML_ATTR_TYPE).equalsIgnoreCase(TYPE_TIME)){
					TimeQuestionModel tm = readTimeQuestion(e);
					questions.add(tm);
				} else {
					OptionQuestionModel om = readOptionQuestion(e);
					questions.add(om);
				}
			}
			
			
		} catch(Exception e){
			e.printStackTrace();
		}
	
		return questions;
	}
	
	private static TimeQuestionModel readTimeQuestion(Element e){
		String id = e.getAttribute(XML_ATTR_ID);
		String prompt = e.getElementsByTagName(XML_TAG_PROMPT).item(0).getTextContent();
		
		return new TimeQuestionModel(id, "", prompt);
		
	}
	
	private static OptionQuestionModel readOptionQuestion(Element e){
		String id = e.getAttribute(XML_ATTR_ID);
		String title = e.getAttribute(XML_ATTR_TITLE);
		String prompt = e.getElementsByTagName(XML_TAG_PROMPT).item(0).getTextContent();
		String typeStr = e.getAttribute(XML_ATTR_TYPE);
		boolean search = false;
		boolean sort = e.getAttribute(XML_ATTR_SORT).equals("true");
		
		QuestionType type = QuestionType.AT_LEAST_ONE;
		
		if(typeStr.equalsIgnoreCase("single")){
			type = QuestionType.SINGLE;
		} else if(typeStr.equalsIgnoreCase("multiple")){
			type = QuestionType.MULTIPLE;
		} else if(typeStr.equalsIgnoreCase("at_least_one")) {
			type = QuestionType.AT_LEAST_ONE;
		}
		
		List<Option> options = new ArrayList<Option>();
		
		String optionAttribute = e.getAttribute(XML_ATTR_OPTIONS);
		
		if(optionAttribute.isEmpty()){
			
			NodeList nList = e.getElementsByTagName(XML_TAG_OPTION);
			
			for(int i = 0; i < nList.getLength(); i++){
				Element optionElement = (Element)nList.item(i);
				
				String optionId = optionElement.getAttribute(XML_ATTR_ID);
				String optionStr = optionElement.getTextContent();
				
				Option option = new Option(optionId, optionStr);
				options.add(option);
			}
			
		} else {
			
			if(optionAttribute.equalsIgnoreCase(OPTIONS_EXERCISES)){
				search = true;
				List<Exercise> exercises = ExerciseService.getInstance().getAllDataFromTable();
				for(Exercise exercise:exercises){
					options.add(new Option(Integer.toString(exercise.getId()), exercise));
				}
				
			} else if(optionAttribute.equalsIgnoreCase(OPTIONS_TIMES)){
				
				List<ExerciseTime> times = ExerciseTimeService.getInstance().getAllDataFromTable();
				for(ExerciseTime time:times){
					options.add(new Option(Integer.toString(time.getId()), time));
				}
				
			} else if (optionAttribute.equalsIgnoreCase(OPTIONS_LOCATIONS)){
				
				List<ExerciseLocation> locations = ExerciseLocationService.getInstance().getAllDataFromTable();
				for(ExerciseLocation location:locations){
					options.add(new Option(Integer.toString(location.getId()), location));
				}
				
			} else if(optionAttribute.equalsIgnoreCase(OPTION_EQUIPMENT)){
				search = true;
				for(Equipment eq :Equipment.equipment){
					options.add(new Option(Integer.toString(eq.getId()), eq));
				}
				
			}
		}
		Log.d(TAG, id+" search > "+search);
		return new OptionQuestionModel(id, title, prompt, options, type, sort, search);
	}

}
