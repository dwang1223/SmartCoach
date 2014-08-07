package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import android.content.Context;
import android.util.Log;
import edu.wpi.smartcoach.model.DialogScriptOption;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.Solution;
import edu.wpi.smartcoach.view.Option;

public class DialogScriptSolver implements ProblemSolver {
	
	private static final String TAG = DialogScriptSolver.class.getSimpleName();
			
	private HashMap<String, OptionQuestionModel> questions;
	private HashMap<String, Solution> solutions;
	
	private OptionQuestionModel current;
	
	private Stack<OptionQuestionModel> backStack;
	
	public DialogScriptSolver(HashMap<String, OptionQuestionModel> questions, HashMap<String, Solution> solutions){
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
	public List<Solution> getSolution(Context ctx) {
		HashMap<String, Solution> selectedSolutions = new HashMap<String, Solution>();
		
		OptionQuestionModel q;
		while(backStack.size() > 0){
			q = backStack.pop();
			DialogScriptOption op = (DialogScriptOption)q.getSelectedValue();
			ArrayList<String> opsolns = op.getSolutions();
			for(String s:opsolns){
				if(solutions.get(s) == null){
					Log.d(TAG, s+" is NULL");
				}
				selectedSolutions.put(s, solutions.get(s));
			}
		}
		
		ArrayList<Solution> solutionList = new ArrayList<Solution>(selectedSolutions.values());
		
		return solutionList;
		
		
	}

	@Override
	public boolean isFirstQuestion(){
		return current.getId().equals("START");
		
	}
	
	@Override
	public QuestionModel getNextQuestion() {
		// TODO Auto-generated method stub
		return current;
	}
	
}
