package edu.wpi.smartcoach.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

import android.content.Context;
import android.util.Log;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.solver.ProblemSolver;
import edu.wpi.smartcoach.view.Option;

public class DialogScriptSolver implements ProblemSolver {
	
	private static final String TAG = DialogScriptSolver.class.getSimpleName();
	
	public static DialogScriptSolver readScript(InputStream stream){
		Scanner sc = new Scanner(stream);
		
		String script = "";
		
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			if(!line.isEmpty()){
				script += line + "\n";
			}
		}
		
		script = script.replaceAll("\t", "");
		
		while(script.contains("\n\n")){
			script = script.replaceAll("\n\n", "\n");
		}
		
	//	Log.d(TAG, script);
		String[] items = script.split("end");
		Log.d(TAG, items.length+" items");
		HashMap<String, OptionQuestionModel> questions = new HashMap<String, OptionQuestionModel>();
		HashMap<String, String> solutions = new HashMap<String, String>();
		
		for(String item:items){
			Log.d(TAG,".\n"+item);
			String id = "";
			String text = "";
			String defaultNext = null;
			while(item.startsWith("\n")){
				item = item.substring(1);
			}
			ArrayList<Option> options = new ArrayList<Option>();
			if(item.startsWith("question")){
				
				String[] q = item.split("\n");
				
				for(String s:q){
										
					if(s.startsWith("question")){
						
						id = s.split(" ")[1];
						
						Log.d(TAG, "Question, "+id);
								
					} else if (s.startsWith("-")){
						
						DialogScriptOption dso = new DialogScriptOption();
						dso.setNext(defaultNext);
						
						String[] tokens = s.substring(1).split("#");
						
						dso.setText(tokens[0]);
						
						ArrayList<String> solutionList = new ArrayList<String>();
						
						if(tokens.length > 1){
							for(int i = 1; i < tokens.length; i++){
								if(tokens[i].split(" ")[0].equals("question")){
									dso.setNext(tokens[i].split(" ")[1]);									
								} else if (tokens[i].split(" ")[0].equals("solution")){
									String[] solutionParts = tokens[i].split(" ")[1].split(",");
									for(String sl:solutionParts){
										solutionList.add(sl);
									}
								}
							}
						}
						
						Log.d(TAG, "option:"+dso.getText()+" -> "+dso.getNext());
						dso.setSolutions(solutionList); 
						options.add(dso);
						
					} else {
						String[] tokens = s.split("#");
						if(tokens.length > 1){
							defaultNext = tokens[1];
							Log.d(TAG, "defaultNext="+defaultNext);
						}
						text = tokens[0];
						Log.d(TAG,"text="+text);
					}
				}
				
				OptionQuestionModel newQ = new OptionQuestionModel(id, "", text, options, QuestionType.SINGLE, true);
				questions.put(id, newQ);
				
			} else if (item.startsWith("solution")){
				String[] parts = item.split("\n");
				id = parts[0].split(" ")[1];
				text = parts[1];
				Log.d(TAG, "solution="+id+", \""+text+"\"");
				solutions.put(id, text);
			}
		}		
		return new DialogScriptSolver(questions, solutions);
	}
	
	private HashMap<String, OptionQuestionModel> questions;
	private HashMap<String, String> solutions;
	
	private OptionQuestionModel current;
	
	private Stack<OptionQuestionModel> backStack;
	
	public DialogScriptSolver(HashMap<String, OptionQuestionModel> questions, HashMap<String, String> solutions){
		this.questions = questions;
		this.solutions = solutions;
		
		current = questions.get("START");
		backStack = new Stack<OptionQuestionModel>();
	}

	@Override
	public void submitResponse(QuestionModel response) {
		OptionQuestionModel r = (OptionQuestionModel)response;
		
		DialogScriptOption option = (DialogScriptOption)r.getSelectedValue();
		backStack.push(r);
		current = questions.get(option.getNext());
		//Log.d(TAG, current.getId());
		
	}

	@Override
	public boolean hasNextQuestion() {
		return current != null;
	}

	@Override
	public boolean isBackAllowed() {
		return backStack.size() > 0 && current != null;
	}

	@Override
	public void back() {
		current = backStack.pop();		
	}

	@Override
	public QuestionModel getSolution(Context ctx) {
		ArrayList<String> solutions = new ArrayList<String>();
		
		OptionQuestionModel q;
		while(backStack.size() > 0){
			q = backStack.pop();
			DialogScriptOption op = (DialogScriptOption)q.getSelectedValue();
			ArrayList<String> opsolns = op.getSolutions();
			for(String s:opsolns){
				if(!solutions.contains(s)){
					solutions.add(s);
				}
			}
		}
		
		ArrayList<Option> sOptions = new ArrayList<Option>();
		for(String s:solutions){
			sOptions.add(new Option(sOptions.size()+"", this.solutions.get(s)));
		}
				
		return new OptionQuestionModel("solution", "Solutions", "Here are some things you can try:", sOptions, QuestionType.MULTIPLE, true);
		
	}

	@Override
	public QuestionModel getNextQuestion() {
		// TODO Auto-generated method stub
		return current;
	}
	
}
