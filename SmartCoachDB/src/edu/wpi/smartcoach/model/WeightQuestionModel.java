package edu.wpi.smartcoach.model;

/**
 * A question that prompts the user ot input their weight
 * @author Akshay
 *
 */
public class WeightQuestionModel implements QuestionModel {

	private String id;
	private String prompt;
	
	private float weight;
	
	public WeightQuestionModel(String id, String prompt){
		this.prompt = prompt;
		this.id = id;
	}
	
	@Override
	public String getPrompt() {
		return prompt;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String newID) {
		this.id = newID;		
	}
	
	public void setWeight(float w){
		this.weight = w;
	}
	
	public float getWeight(){
		return weight;
	}	

	@Override
	public QuestionResponseOutline getOutline() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public WeightQuestionModel clone(){
		return new WeightQuestionModel(id, prompt);
	}

}
