package edu.wpi.smartcoach.solver;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import edu.wpi.smartcoach.model.Option;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.Solution;
import edu.wpi.smartcoach.service.PatientProfile;
import edu.wpi.smartcoach.util.DialogXMLReader;

public class DialogXMLSolver implements ProblemSolver {
	
	private static final String TAG = DialogXMLSolver.class.getSimpleName();
	
	private int resource;
	private Context context;
	
	private List<QuestionModel> questions;
	private HashMap<String, String> conditionMap;
	private HashMap<String, Solution> solutionMap;

	private List<String> setConditions;
	
	private ArrayList<DialogXMLSolver> screens;
	private int screenIndex = -1;
	
	private Node flow;
	private Node currentNode;
	private Stack<Element> pathStack;
	
	private boolean flowFinished = false;
	private boolean screensFinished = false;
	
	
	public DialogXMLSolver(List<QuestionModel> questions, HashMap<String, String> conditionMap, HashMap<String, Solution> solutionMap, Node flow, int resource, Context context){
		this.questions = questions;
		this.conditionMap = conditionMap;
		this.solutionMap = solutionMap;
		this.flow = flow;
		this.resource = resource;
		this.context = context;
		
		for(QuestionModel oom:questions){
			Log.d(TAG, oom.toString());
		}
		
		NodeList emptyTextNodes;
		try {
            //remove whitespace
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
		pathStack = new Stack<Element>();
	}

	@Override
	public QuestionModel getNextQuestion() {
		Element currentElement = (Element)currentNode;
		if(flowFinished){
			if(screenIndex >= 0 && screens.get(screenIndex) != null){
				return screens.get(screenIndex).getNextQuestion();
			}
		} else {
			//Log.d(TAG, currentElement.getAttribute("id")); 
			return getQuestionById(currentElement.getAttribute("id"));
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
					String id = ((Element)currentNode).getAttribute("name");
					setConditions.addAll(evaluateCondition(id));
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
        Element cElement;
		if(currentNode instanceof Element) {
            cElement = (Element) currentNode;

            if(cElement.getTagName().equals("question")){
                Log.d(TAG,"pushing " + cElement.getAttribute("id"));
                pathStack.push(cElement);
            }
        }
		

		
		if(currentNode.getNextSibling() != null){
			//Log.d(TAG, "next sibling");
			currentNode = currentNode.getNextSibling();
            if(currentNode instanceof  Element) {
                cElement = (Element) currentNode;
                if (cElement.getTagName().equals("if")) {
                    String ifId = cElement.getAttribute("id");
                    String[] ifResponse = cElement.getAttribute("response").split(",");
                    if (ifId.startsWith("profile")) {
                        Set<String> responseSet = PatientProfile.getResponses(ifId, context);
                        for (String ifRes : ifResponse) {
                            if (responseSet.contains(ifRes)) {
                                currentNode = cElement.getFirstChild();
                                return;
                            }
                        }
                    } else {
                        OptionQuestionModel testQuestion = (OptionQuestionModel) getQuestionById(ifId);
                        for (Option op : testQuestion.getSelectedOptions()) {
                            for (String ifRes : ifResponse) {
                                if (op.getId().equals(ifRes)) {
                                    currentNode = cElement.getFirstChild();
                                    return;
                                }
                            }
                        }

                    }

                }
            } else {
                advanceFlow();
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

	public QuestionModel getQuestionById(String id){
		QuestionModel question = null;
		for(QuestionModel oom:questions){
			if(oom.getId().equals(id)){
				question = oom;
				break;
			}
		}
		return question;
	}
	
	public Solution getSolutionById(String id){
		return solutionMap.get(id);
	}
	
	public boolean hasCondition(String condition){
		return getConditions().contains(condition);
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
		
		if(!flowFinished){
			return !pathStack.isEmpty();
		} else if (!screensFinished){
			return screens.get(screenIndex).isBackAllowed();
		}
		
		return false;
		
	}

	@Override
	public void back() {
		
		if(!flowFinished){
			Element previous = pathStack.pop();
			currentNode = previous;
			//questions.
			//Log.d(TAG, String.format("back to question %s", previous.getAttribute("id")));
		} else if (!screensFinished){
			if(screens.get(screenIndex).isFirstQuestion()){
				if(screenIndex > 0){
					screenIndex--;
				} else {
					flowFinished = false;
					back();
				}
			} else {
				screens.get(screenIndex).back();
			}
		}		
	}
	
	public int getResource(){
		return resource;
	}
	
	public List<String> getConditions(){
        List<String> conditions = new ArrayList<String>();
        conditions.addAll(setConditions);

        for(QuestionModel questionModel:questions){
            if(questionModel instanceof OptionQuestionModel){
                OptionQuestionModel question = (OptionQuestionModel)questionModel;
                for(Option selectedOption:question.getSelectedOptions()){
                    String condition = selectedOption.getCondition();
                    conditions.addAll(evaluateCondition(condition));

                }
            }
        }

        return conditions;
    }

    @Override
    public List<String> getConditionsRecursive() {
        List<String> allConditions = getConditions();

        for(DialogXMLSolver screen:screens){
            allConditions.addAll(screen.getConditionsRecursive());
        }

        return allConditions;
    }

    @Override
	public List<Solution> getSolution(Context ctx) {
		List<Solution> solutionList = new ArrayList<Solution>();

        List<String> allConditions = getConditions();

		for(String c:allConditions){
			Log.d(TAG, "Solutions For "+c);
			if(this.conditionMap.containsKey(c)){
				String[] solutionIds = conditionMap.get(c).split(",");
				for(String s:solutionIds){
					if(solutionMap.containsKey(s)){
						Solution soln = solutionMap.get(s);
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
            if(condition.contains("[")){
                String prefixId = condition.substring(condition.indexOf('[')+1, condition.indexOf(']'));
                List<String> subConditions = new ArrayList<String>();

                if(prefixId.contains("profile")){
                    Set<String> profileConditions = PatientProfile.getConditions(context);
                    for(String pCond:profileConditions){
                        if(pCond.startsWith(prefixId)){
                            subConditions.add(pCond);
                        }
                    }
                } else {
                    OptionQuestionModel prefixQuestion = null;
                    if(getQuestionById(prefixId) instanceof OptionQuestionModel){
                        prefixQuestion = (OptionQuestionModel)getQuestionById(prefixId);
                    }

                    if(prefixQuestion != null){
                        for(Option prefixOption:prefixQuestion.getSelectedOptions()){
                            String prefixCondition = prefixOption.getCondition();
                            subConditions.addAll(evaluateCondition(prefixCondition));
                        }
                    }
                }

                for(String sub:subConditions){
                    results.addAll(evaluateCondition(condition.replace("["+prefixId+"]", sub)));
                }

            } else {
                results.add(condition);
            }
        }

        Log.d(TAG, "EVAL "+condition+" -> "+results.toString());

		return results;
	}
	
	public List<QuestionModel> getQuestions(){
		return questions;
	}

}
