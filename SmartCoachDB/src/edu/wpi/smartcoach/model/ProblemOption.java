package edu.wpi.smartcoach.model;

import edu.wpi.smartcoach.solver.ProblemSolver;

public class ProblemOption extends SimpleOption{

	private static final String TAG = ProblemOption.class.getSimpleName();
	
	private ProblemSolver solver;
	
	public ProblemOption(int id, String text, ProblemSolver solver){
		super(id, text);
		this.solver = solver;
		
	}
	
	public ProblemSolver getSolver() {
		return solver;
	}

}
