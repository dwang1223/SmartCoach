package edu.wpi.smartcoach.solver;

import java.util.ArrayList;

import android.content.Context;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.SimpleOption;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;

public class InjuryProblemSolver implements ProblemSolver {

	private Context context;
	
	private OptionQuestionModel qGymMembership;
	private OptionQuestionModel qTherapist;
	private OptionQuestionModel qFollowedTherapist;
	
	
	private boolean hasGymMembership = false;	
	private boolean hasPhysicalTherapist = false;
	private boolean hasConsultedTherapist = false;
	private boolean finished = false;
	
	
	
	QuestionModel nextQuestion;
	
	public InjuryProblemSolver(Context context){
		this.context = context;		
		
		qGymMembership = InjuryQuestionBuilder.getGymMembershipQuestion(context);
		qTherapist = InjuryQuestionBuilder.getPhysicalTherapistQuestion();
		qFollowedTherapist = InjuryQuestionBuilder.getFollowedTherapistQuestion();
		
		nextQuestion = qGymMembership;
		
	}
	
	@Override
	public QuestionModel getNextQuestion() {
		return nextQuestion;
	}

	@Override
	public void submitResponse(QuestionModel responseQuestion) {
		OptionQuestionModel response = (OptionQuestionModel)responseQuestion;
		if(response == qGymMembership){
			hasGymMembership = response.getSelectedResponse().getId() == SimpleOption.YES;
			nextQuestion = qTherapist;
		} else if (response == qTherapist){
			hasPhysicalTherapist = response.getSelectedResponse().getId() == SimpleOption.YES;
			if(hasPhysicalTherapist){
				nextQuestion = qFollowedTherapist;
			} else {
				finished = true;
			}
		} else if (response == qFollowedTherapist){
			hasConsultedTherapist = response.getSelectedResponse().getId() == SimpleOption.YES;
			finished = true;
		}
		
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
		
		ArrayList<SimpleOption> solutions = new ArrayList<SimpleOption>();
		
		if(hasPhysicalTherapist){
			if(hasConsultedTherapist){
				solutions.add(new SimpleOption(solutions.size(), "Make sure to follow any advise given by you physical therapist."));
			} else {

				solutions.add(new SimpleOption(solutions.size(), "Try consulting your physical therapist for advise on how to proceed with your injury."));
			}
		}
		
		if (hasGymMembership){

			solutions.add(new SimpleOption(solutions.size(), "See if a professional trainer at your gym can help you continue exercise safely."));
		
		} else if (!hasPhysicalTherapist) {

			solutions.add(new SimpleOption(solutions.size(), "Consult your primary care provider for further advice, or get a referral to a physical therapist."));
		}
		
		return new OptionQuestionModel("solutions", "Solutions", 
				"Here are some things you can try:", solutions, QuestionType.MULTIPLE);
	}

}
