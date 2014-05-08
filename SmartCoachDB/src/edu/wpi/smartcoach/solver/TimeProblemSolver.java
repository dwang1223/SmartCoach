package edu.wpi.smartcoach.solver;

import java.util.HashMap;
import java.util.Queue;

import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.exercise.Exercise;

public class TimeProblemSolver implements ProblemSolver {
	
	private HashMap<Exercise, Queue<QuestionModel>> questions;

	@Override
	public QuestionModel getNextQuestion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void submitResponse(QuestionModel response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasNextQuestion() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public QuestionModel getSolution() {
		// TODO Auto-generated method stub
		return null;
	}


}
