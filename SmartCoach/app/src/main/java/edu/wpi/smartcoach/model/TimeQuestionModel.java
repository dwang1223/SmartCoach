package edu.wpi.smartcoach.model;

/**
 * A question that prompts the user to input a time of day
 * @author Akshay
 *
 */
public class TimeQuestionModel implements QuestionModel {

    private static final String TAG = TimeQuestionModel.class.getSimpleName();

	private String id;
	private String title;
	private String prompt;
	
	private int response = 0;
	
	public static String formatTime(int time){
		time = time % (60*24);
        int hour = (time/60)%12;
        if(hour == 0){
            hour = 12;
        }
        int minute = time%60;
        String formattedTime = String.format("%d:%02d %s", hour, minute, time < 12*60 ? "AM":"PM");
        //Log.d(TAG, String.format("formatted \'%d\' to \'%s\'", time, formattedTime));
		return formattedTime;
	}
	
	public TimeQuestionModel(String id, String title, String prompt) {
		super();
		this.id = id;
		this.title = title;
		this.prompt = prompt;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @return the prompt
	 */
	public String getPrompt() {
		return prompt;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @param prompt the prompt to set
	 */
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	
	public void setResponse(int hours, int minutes){
		response = hours*60 + minutes;
	}
	
	public int getResponse(){
		return response;
	}
	
	@Override
	public TimeQuestionModel clone(){
		return new TimeQuestionModel(id, title, prompt);
	}

}
