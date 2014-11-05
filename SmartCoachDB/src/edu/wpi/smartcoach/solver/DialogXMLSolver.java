package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import edu.wpi.smartcoach.model.DialogXMLOption;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.QuestionResponseOutline;
import edu.wpi.smartcoach.model.Solution;
import edu.wpi.smartcoach.util.DialogXMLReader;
import edu.wpi.smartcoach.view.Option;

public class DialogXMLSolver implements ProblemSolver {
	
	private static final String TAG = DialogXMLSolver.class.getSimpleName();
	
	private int resource;
	private Context context;
	
	private List<QuestionModel> questions;
	private HashMap<String, String> conditions;
	private HashMap<String, Solution> solutions;
	
	private List<String> setConditions;
	
	private ArrayList<DialogXMLSolver> screens;
	private int screenIndex = -1;
	
	private Node flow;
	private Node currentNode;
	
	private boolean flowFinished = false;
	private boolean screensFinished = false;
	
	
	public DialogXMLSolver(List<QuestionModel> questions, HashMap<String, String> conditions, HashMap<String, Solution> solutions, Node flow, int resource, Context context){
		this.questions = questions;
		this.conditions = conditions;
		this.solutions = solutions;
		this.flow = flow;
		this.resource = resource;
		this.context = context;
		
		for(QuestionModel oom:questions){
			Log.d(TAG, oom.toString());
		}
		
		NodeList emptyTextNodes;
		try {
			XPathFactory xpathFactory = XPathFactory.newInstance();
			// XPath to find empty text nodes.
			XPathExpression xpathExp = xpathFactory.newXPath().compile("//text()[normalize-space(.) = '']");  
			emptyTextNodes = (NodeList)xpathExp.evaluate(flow, XPathConstants.NODESET);
			// Remove each empty text node from document.
			for (int i = 0; i < emptyTextNodes.getLength(); i++) {
			    Node emptyTextNode = emptyTextNodes.item(i);
			    emptyTextNode.getParentNode().removeChild(emptyTextNode);
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		init();
		
	}
		
	private void init(){
		screens = new ArrayList<DialogXMLSolver>();
		currentNode = flow.getFirstChild();
		setConditions = new ArrayList<String>();
		

	}

	@Override
	public QuestionModel getNextQuestion() {
		Element currentElement = (Element)currentNode;
		if(flowFinished){
			if(screenIndex >= 0 && screens.get(screenIndex) != null){
				return screens.get(screenIndex).getNextQuestion();
			}
		} else {
			Log.d(TAG, currentElement.getAttribute("id")); 
			return getQuestion(currentElement.getAttribute("id"));
		}
		
		return null;
		
	} 

	@Override
	public void submitResponse(QuestionModel response) {
		//OptionQuestionModel q = (OptionQuestionModel)response;
		
		
		if(!flowFinished){
			do {
				advanceFlow();
				if(((Element)currentNode).getTagName().equals("screen")){
					String screenId = ((Element)currentNode).getAttribute("id");
					if(!screens.contains(screenId)){
						int res = context.getResources().getIdentifier(screenId, "raw", context.getPackageName());
						screens.add(DialogXMLReader.readXML(res, context));
					}
					
				} else if(((Element)currentNode).getTagName().equals("condition")){
					String id = ((Element)currentNode).getAttribute("id");
					List<String> firstList = evaluateCondition(id);
					for(String s:firstList){
						String storedId = s.substring(s.indexOf('[')+1, s.indexOf(']'));
						SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
						Set<String> storedConds = prefs.getStringSet("conditions."+storedId, new HashSet<String>());
						
						for(String ss:storedConds){
							setConditions.add(s.replace("["+storedId+"]", ss));
						}
						
					}
				}
			} while(!((Element)currentNode).getTagName().equals("question") && !flowFinished);
		} else {
			if(screenIndex >= 0 && screens.get(screenIndex) != null){
				screens.get(screenIndex).submitResponse(response);
				if(!screens.get(screenIndex).hasNextQuestion()){
					if(screenIndex < screens.size()-1){
						screenIndex++;
					} else {
						screensFinished = true;
					}
				}
			}
		}
		
		
	}
	
	private void advanceFlow(){
				
		Element cElement = (Element)currentNode;

		if(currentNode.getNextSibling() != null){
			//Log.d(TAG, "next sibling");
			currentNode = currentNode.getNextSibling();
			cElement = (Element)currentNode;
			if(cElement.getTagName().equals("if")){
				String ifId = cElement.getAttribute("id");
				String ifResponse = cElement.getAttribute("response");
				OptionQuestionModel testQuestion = (OptionQuestionModel)getQuestion(ifId);
				for(Option op:testQuestion.getSelectedOptions()){
					if(op.getId().equals(ifResponse)){
						currentNode = cElement.getFirstChild();
						return;
					}
				}	
			}
			
		} else {
			currentNode = currentNode.getParentNode();
			cElement = (Element)currentNode;

			//Log.d(TAG,"go up to "+cElement.getTagName());
			if(cElement.getTagName().equals("flow")){
			//	Log.d(TAG, "flow finsihed");
				flowFinished = true;
				if(screens.size() > 0){
					screenIndex = 0;
				} else {
					screensFinished = true;
				}
				//now switch to showing Screeners
			} else {
				advanceFlow();
			}
		}
	
	}


	private QuestionModel getQuestion(String id){
		QuestionModel question = null;
		for(QuestionModel oom:questions){
			if(oom.getId().equals(id)){
				question = oom;
				break;
			}
		}
		return question;
	}
	
	@Override
	public boolean isFirstQuestion() {
		if(currentNode == flow.getFirstChild()){
			return true;
		}
		return false;
	}

	@Override
	public boolean hasNextQuestion() {
		// TODO Auto-generated method stub
		return !flowFinished || !screensFinished;
	}

	@Override
	public boolean isBackAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void back() {
		// TODO Auto-generated method stub
		
	}
	
	public int getResource(){
		return resource;
	}

	@Override
	public List<Solution> getSolution(Context ctx) {
		List<Solution> solutionList = new ArrayList<Solution>();
		for(QuestionModel oom:questions){
			if(oom instanceof OptionQuestionModel){
				OptionQuestionModel opq = (OptionQuestionModel)oom;
				for(Option op:opq.getSelectedOptions()){									
					if(op instanceof DialogXMLOption){						
						String condition = ((DialogXMLOption)op).getCondition();
						setConditions.addAll(evaluateCondition(condition)); 
					}				
				}
			}
		}
		
		for(String c:setConditions){
			Log.d(TAG, "Solutions For "+c);
			if(this.conditions.containsKey(c)){
				String[] solutionIds = this.conditions.get(c).split(",");
				for(String s:solutionIds){
					if(solutions.containsKey(s)){
						Solution soln = solutions.get(s);
						if(soln != null && !solutionList.contains(soln)){
							solutionList.add(soln); 
						}
					}
				}
				
			}
		}
		
		for(DialogXMLSolver screenSolver:screens){
			List<Solution> screenSolutions = screenSolver.getSolution(ctx);
			for(Solution ss:screenSolutions){
				if(ss != null && !solutionList.contains(ss)){
					solutionList.add(0, ss);
				}
			}
		}
		
		return solutionList;
	}
	
	private List<String> evaluateCondition(String condition){
		List<String> results = new ArrayList<String>();
		
		if(condition != null){
			if( condition.startsWith("[")){
				String prefixId = condition.substring(condition.indexOf('[')+1, condition.indexOf(']'));
				OptionQuestionModel prefixQuestion = null;
				if(getQuestion(prefixId) instanceof OptionQuestionModel){
					prefixQuestion = (OptionQuestionModel)getQuestion(prefixId);
				}
				
				if(prefixQuestion != null){
					for(Option prefixOption:prefixQuestion.getSelectedOptions()){
						if(prefixOption instanceof DialogXMLOption){
							String prefixCondition = ((DialogXMLOption)prefixOption).getCondition();
							List<String> prefixes = evaluateCondition(prefixCondition);
							for(String p:prefixes){
								results.add(p + condition.substring(condition.indexOf(']')+1));
							}
						}
					}
				}
			} else {
				results.add(condition);
				
			}
		}
		
		return results;
	}
	
	public List<QuestionModel> getQuestions(){
		return questions;
	}

	@Override
	public QuestionResponseOutline[] getOutline() {
		// TODO Auto-generated method stub
		return null;
	}

}
