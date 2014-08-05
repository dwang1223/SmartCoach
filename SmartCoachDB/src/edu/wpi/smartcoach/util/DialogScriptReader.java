package edu.wpi.smartcoach.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import android.util.Log;
import edu.wpi.smartcoach.model.DialogScriptOption;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.Solution;
import edu.wpi.smartcoach.solver.DialogScriptSolver;
import edu.wpi.smartcoach.view.Option;

public class DialogScriptReader {
	
	private static final String TAG = DialogScriptReader.class.getSimpleName();
	
	public static DialogScriptSolver readScript(InputStream stream){
		Scanner sc = new Scanner(stream);
		
		String script = "";
		
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			if(!line.isEmpty()){
				script += line + "\n";
			}
		}
		
		script = script.replaceAll("\\t", "");
		
//		while(script.contains("\n\n")){
//			script = script.replaceAll("\n\n", "\n");
//		}
		
	//	Log.d(TAG, script);
		String[] items = script.split("\nend\n");
		Log.d(TAG, items.length+" items");
		HashMap<String, OptionQuestionModel> questions = new HashMap<String, OptionQuestionModel>();
		HashMap<String, Solution> solutions = new HashMap<String, Solution>();
		
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
				
				OptionQuestionModel newQ = new OptionQuestionModel(id, "", text, options, QuestionType.SINGLE, false, false);
				questions.put(id, newQ);
				
			} else if (item.startsWith("solution")){
				String[] parts = item.split("\n");
				String message = null;
				String info = null;
				for(String part:parts){
					if(part.startsWith("solution")){
						id = part.split(" ")[1];
					} else if (part.startsWith("info")){
						info = part.split("\\|")[1];
					} else if (!part.startsWith("end")){
						message = part;
					}
				}
				solutions.put(id, new Solution(Solution.TYPE_DEFAULT, message, info));
				Log.d(TAG, String.format("solution %s: %s - %s", id, message, info));
			}
		}		
		return new DialogScriptSolver(questions, solutions);
	}

}
