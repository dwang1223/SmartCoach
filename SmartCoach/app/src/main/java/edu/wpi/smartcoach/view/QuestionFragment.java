package edu.wpi.smartcoach.view;

import android.support.v4.app.Fragment;

import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.TimeQuestionModel;
import edu.wpi.smartcoach.model.WeightQuestionModel;
import edu.wpi.smartcoach.util.Callback;

/**
 * A generic fragment that displays a QuestionModel
 * @author Akshay
 *
 */
public abstract class QuestionFragment extends Fragment {

	/**
	 * Returns a QuestionFragment for displaying the given QuestionModel
	 * @param q QuestionModel
	 * @return a QuestionFragment
	 */
	public static QuestionFragment createQuestion(QuestionModel q){
		
		if(q instanceof OptionQuestionModel){
			return new OptionQuestionFragment()
				.setQuestion((OptionQuestionModel)q);
		} else if (q instanceof TimeQuestionModel){
			return new TimeQuestionFragment().setQuestion((TimeQuestionModel)q);
			
		} else if(q instanceof WeightQuestionModel){
			return new WeightQuestionFragment().setQuestion((WeightQuestionModel)q);
		}
		return null;
	}

	/**
	 * Set whether the QuestionFragment should display a back button
	 * @param first
	 */
	public abstract void setBackEnabled(boolean first);
	
	/**
	 * Set wheter this QuestionFragment is that last one in a series. Generally used for setting the text on the Next button
	 * @param isLast
	 */
	public abstract void setLast(boolean isLast);
	
	/**
	 * Set the listener that should be notified when the next button is pressed
	 * @param listener
	 * @return
	 */
	public abstract QuestionFragment setNextButtonListener(Callback<QuestionModel> listener);
	
	/**
	 * Set the listener that should be notified when the back button is pressed
	 * @param listener
	 * @return
	 */
	public abstract QuestionFragment setBackButtonListener(Callback<QuestionModel> listener);

}
