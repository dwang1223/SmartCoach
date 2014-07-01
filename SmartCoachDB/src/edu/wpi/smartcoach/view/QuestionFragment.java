package edu.wpi.smartcoach.view;

import android.support.v4.app.Fragment;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.TimeQuestionModel;

public abstract class QuestionFragment extends Fragment {

	public static QuestionFragment createQuestion(QuestionModel q){
		if (q.getId().equals("exercises_week_grid")){
			return new WeekGridQuestionFragment().setQuestion((OptionQuestionModel)q);			
		} else if(q instanceof OptionQuestionModel){
			return new OptionQuestionFragment()
				.setQuestion((OptionQuestionModel)q);
		} else if (q instanceof TimeQuestionModel){
			return new TimeQuestionFragment().setQuestion((TimeQuestionModel)q);
			
		}
		return null;
	}

	public abstract void setBackEnabled(boolean first);
	public abstract void setLast(boolean isLast);
	public abstract QuestionFragment setNextButtonListener(QuestionResponseListener listener);
	public abstract QuestionFragment setBackButtonListener(QuestionResponseListener listener);

}
