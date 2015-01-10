package edu.wpi.smartcoach.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.Option;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.service.PatientProfile;
import edu.wpi.smartcoach.util.Callback;
import edu.wpi.smartcoach.util.DialogXMLReader;
import edu.wpi.smartcoach.view.OptionQuestionFragment;

/**
 * Activity where the user answers some basic profile questions
 * @author Akshay
 *
 */
public class ProfileActivity extends FragmentActivity {
	

	QuestionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;
		
	List<OptionQuestionModel> questions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		questions = new ArrayList<OptionQuestionModel>();
		List<QuestionModel> read = DialogXMLReader.readXML(R.raw.profile, this).getQuestions(); //profile doesnt have "solutions", just get the question list
		
		for(QuestionModel qm:read){
			questions.add((OptionQuestionModel)qm);
		}
	
		mSectionsPagerAdapter = new QuestionsPagerAdapter(getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}
	
	/**
	 * Saves the conditions, marks profile as initialized and finishes
	 */
	private void doFinish(){
		Set<String> conditions = new HashSet<String>();
		
		for(OptionQuestionModel question:questions){
			for(Option opt:question.getSelectedOptions()){
				if(opt.isSelected()){
					conditions.add(opt.getCondition());
				}
			}
		}
		
		PatientProfile.setConditions(conditions, this);
		PatientProfile.setInitialized(this);
	
		
		finish();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);	
	}



	public class QuestionsPagerAdapter extends FragmentPagerAdapter {

		public QuestionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(final int position) {
			OptionQuestionFragment qf = new OptionQuestionFragment();
			qf.setQuestion(questions.get(position));
			qf.setBackEnabled(position != 0);
			qf.setLast(position == getCount()-1);
			qf.setNextButtonListener( new Callback<QuestionModel>() {
					
					@Override
					public void callback(QuestionModel question) {
						if(position == getCount()-1){
							doFinish();
						} else {
							mViewPager.setCurrentItem(position+1, true);
						}
						
					}
				});
			qf.setBackButtonListener(new Callback<QuestionModel>() {
				
				@Override
				public void callback(QuestionModel question) {
					mViewPager.setCurrentItem(position-1, true);					
				}
			});
			return qf;
		}

		@Override
		public int getCount() {
			return questions.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return questions.get(position).getTitle();
		}
	}



}
