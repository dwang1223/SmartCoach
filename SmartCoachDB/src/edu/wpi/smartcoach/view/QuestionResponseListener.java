package edu.wpi.smartcoach.view;

import edu.wpi.smartcoach.model.QuestionModel;

public interface QuestionResponseListener {
	public void responseEntered(QuestionModel question);
}