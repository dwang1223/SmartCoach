package edu.wpi.smartcoach.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.util.Log;
import edu.wpi.smartcoach.model.DialogXMLOption;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.exercise.Equipment;
import edu.wpi.smartcoach.model.exercise.Exercise;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.Solution;
import edu.wpi.smartcoach.model.TimeQuestionModel;
import edu.wpi.smartcoach.service.ExerciseService;
import edu.wpi.smartcoach.solver.DialogXMLSolver;
import edu.wpi.smartcoach.view.Option;

public class DialogXMLReader {
	
	private static final String TAG = DialogXMLReader.class.getSimpleName();
	
	public static DialogXMLSolver readXML(int resource, Context context){
		DialogXMLSolver solver = null;
		List<QuestionModel> questions = new ArrayList<QuestionModel>();
		HashMap<String, String> conditions = new HashMap<String, String>();
		HashMap<String, Solution> solutions  = new HashMap<String, Solution>();
		 
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			InputStream qStream = context.getResources().openRawResource(resource);
			Document doc = dBuilder.parse(qStream);
			
			Element script = (Element)doc.getElementsByTagName("script").item(0);
			NodeList questionList = ((Element)script.getElementsByTagName("questions").item(0)).getChildNodes();
			NodeList conditionList =((Element) script.getElementsByTagName("conditions").item(0)).getElementsByTagName("condition");
			NodeList solutionList = ((Element)script.getElementsByTagName("solutions").item(0)).getElementsByTagName("solution");
			Node flow = ((Element)script.getElementsByTagName("questions").item(0)).getElementsByTagName("flow").item(0);
			
				for(int i = 0; i < questionList.getLength(); i++){
				
				if(questionList.item(i) instanceof Element && ((Element)questionList.item(i)).getTagName().equals("question")){
				 
					Element questionElement = (Element)questionList.item(i);
					
					String id = questionElement.getAttribute("id");
					QuestionType type = QuestionType.SINGLE;
					if(questionElement.hasAttribute("type")){
						String qType = questionElement.getAttribute("type");
						if(qType.equals("multiple")){ 
							type = QuestionType.MULTIPLE;
						}
					}
					
					
//					NodeList ll = questionElement.getChildNodes();
//					for(int d = 0; d < ll.getLength(); d++){
//						Log.d(TAG,((Element)ll.item(d)).getTagName());
//					}
//					
//					Log.d(TAG, id+" "+questionElement.getElementsByTagName("prompt").getLength() );
//					
					
					String prompt = questionElement.getElementsByTagName("prompt").item(0).getTextContent();
					
					List<Option> options = new ArrayList<Option>();
					NodeList optionList = questionElement.getElementsByTagName("option");
					
					if(optionList.getLength() == 0){
						if(questionElement.getElementsByTagName("time").getLength() > 0){
							questions.add(new TimeQuestionModel(id, "", prompt));
						} else if(questionElement.getElementsByTagName("options").getLength() > 0){
							
							String source = ((Element)questionElement.getElementsByTagName("options").item(0)).getAttribute("src");
							
							if(source.equals("EXERCISES")){
								List<Exercise> exercises = ExerciseService.getInstance().getAllDataFromTable();
								for(Exercise exercise:exercises){
									options.add(new DialogXMLOption(exercise.getId()+"", exercise.getName(), exercise.getId()+""));
								}
							} else if (source.equals("EQUIPMENT")){
								for(Equipment eq :Equipment.equipment){
									options.add(new DialogXMLOption(Integer.toString(eq.getId()), eq.getName(), eq.getId()+""));
								}
							}
							
							questions.add(new OptionQuestionModel(id, "", prompt, options, type, true, true));
							
						}
						
					} else {
						for(int o = 0; o < optionList.getLength(); o++){
							Element op = (Element)optionList.item(o);
							String oId = op.getAttribute("id");
							String condition = null;
							if(op.hasAttribute("condition")){
								condition = op.getAttribute("condition");
								//Log.d(TAG, condition);
							}
							String text = op.getTextContent();  
							//Log.d(TAG, id+""+oId+" => "+condition);
							options.add(new DialogXMLOption(oId, text, condition));
							
						}
						questions.add(new OptionQuestionModel(id, "", prompt, options, type, false, false));
					}
					
				}
				
			}			
			
			for(int i = 0; i < conditionList.getLength(); i++){
				Element condition = (Element)conditionList.item(i);
				String name = condition.getAttribute("name");
				String text = condition.getTextContent();
				//Log.d(TAG, name + " => "+text);
				conditions.put(name, text);
				
			}			
			
			for(int i = 0; i < solutionList.getLength(); i++){
				Element solution = (Element)solutionList.item(i);
				String id = solution.getAttribute("id");
				String text = solution.getTextContent();
				solutions.put(id, new Solution(text));
				
			}
			
			
			solver = new DialogXMLSolver(questions, conditions, solutions, flow, resource, context);
					
			
			
		} catch(Exception e){
			e.printStackTrace();
		}	
		
		return solver;
	}
	

}
