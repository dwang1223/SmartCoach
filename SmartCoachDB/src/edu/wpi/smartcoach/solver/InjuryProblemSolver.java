package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.exercise.Equipment;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.util.QuestionReader;
import edu.wpi.smartcoach.view.Option;

public class InjuryProblemSolver implements ProblemSolver {

	private Context context;
	
	private OptionQuestionModel qGymMembership;
	private OptionQuestionModel qTherapist;
	private OptionQuestionModel qFollowedTherapist;
	
	
	private boolean hasGymMembership = false;	
	private boolean hasPhysicalTherapist = false;
	private boolean hasConsultedTherapist = false;
	private boolean finished = false;
	
	private Stack<OptionQuestionModel> backStack;
	
	private HashMap<String, QuestionModel> questions;
	
	QuestionModel nextQuestion;
	
	public InjuryProblemSolver(Context context){
		this.context = context;		
		
		backStack = new Stack<OptionQuestionModel>();
		
		List<QuestionModel> qList = QuestionReader.readQuestions(R.raw.injury_questions, context);
		questions = new HashMap<String, QuestionModel>();
		
		for(QuestionModel q:qList){
			questions.put(q.getId(), q);
		}
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		boolean hadMembership = false;
		String[] equipment = prefs.getString("profile_exercise_equip", "").split(",");
		
		for(String eq:equipment){
			try{
				if(Integer.parseInt(eq) == Equipment.ID_GYM_MEMBERSHIP){
					hadMembership = true;
				}
			} catch(Exception e){}
		}
		
		qGymMembership = (OptionQuestionModel)questions.get(hadMembership?"hasgymstill":"hasgym");
		qTherapist = (OptionQuestionModel)questions.get("haspt");
		qFollowedTherapist = (OptionQuestionModel)questions.get("followpt");
		
		nextQuestion = qGymMembership;
		
	}
	
	@Override
	public QuestionModel getNextQuestion() {
		return nextQuestion;
	}

	@Override
	public void submitResponse(QuestionModel responseQuestion) {
		boolean response = ((OptionQuestionModel)responseQuestion).getSelectedOption().getId().equals("yes");
		backStack.push((OptionQuestionModel)responseQuestion);
		if(responseQuestion == qGymMembership){
			hasGymMembership = response;
			nextQuestion = qTherapist;
		} else if (responseQuestion == qTherapist){
			hasPhysicalTherapist = response;
			if(hasPhysicalTherapist){
				nextQuestion = qFollowedTherapist;
			} else {
				finished = true;
			}
		} else if (responseQuestion == qFollowedTherapist){
			hasConsultedTherapist = response;
			finished = true;
		}
		
	}
	
	@Override
	public void back(){
		nextQuestion = backStack.pop();
	}
	
	@Override
	public boolean isFirstQuestion(){
		return nextQuestion.getId().startsWith("hasgym");
	}
	
	@Override
	public boolean isBackAllowed(){
		return !(nextQuestion == qGymMembership);
	}

	@Override
	public boolean hasNextQuestion() {
		return !finished;
	}

	@Override
	public QuestionModel getSolution(Context ctx) {
		//no gym no therapist -> store, doctor
		//therapist -> got advise?
		//yes -> ok do that
		//no -> get advise
		// gym, no therapist -> gym
		
		ArrayList<Option> solutions = new ArrayList<Option>();
		
		if(hasPhysicalTherapist){
			if(hasConsultedTherapist){
				solutions.add(new Option(solutions.size()+"", "Make sure to follow any advise given by you physical therapist."));
			} else {

				solutions.add(new Option(solutions.size()+"", "Try consulting your physical therapist for advise on how to proceed with your injury."));
			}
		}
		
		if (hasGymMembership){

			solutions.add(new Option(solutions.size()+"", "See if a professional trainer at your gym can help you continue exercise safely."));
		
		} else if (!hasPhysicalTherapist) {

			solutions.add(new Option(solutions.size()+"", "Consult your primary care provider for further advice, or get a referral to a physical therapist."));
		}
		
		return new OptionQuestionModel("solutions", "Solutions", 
				"Here are some things you can try:", solutions, QuestionType.MULTIPLE, false, false);
	}

}
