package edu.wpi.smartcoach.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.DialogXMLOption;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.util.DialogXMLReader;
import edu.wpi.smartcoach.view.Option;
import edu.wpi.smartcoach.view.OptionQuestionFragment;
import edu.wpi.smartcoach.view.QuestionResponseListener;

public class ProfileActivity extends FragmentActivity {
	

	QuestionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;
	
	List<OptionQuestionModel> questions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		setTitle("SmartCoach Profile Information");
		
		questions = new ArrayList<OptionQuestionModel>();
		List<QuestionModel> read = DialogXMLReader.readXML(R.raw.profile, this).getQuestions();//QuestionReader.readQuestions(R.raw.profile_questions, getBaseContext());
		for(QuestionModel qm:read){
			questions.add((OptionQuestionModel)qm);
		}
	
		mSectionsPagerAdapter = new QuestionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}
	
	private void doFinish(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Editor prefEdit = prefs.edit();
		for(OptionQuestionModel qm:questions){
			Set<String> conditions = new HashSet<String>();
			Set<String> optionIds = new HashSet<String>();
			for(Option opt:qm.getSelectedOptions()){
				optionIds.add(opt.getId());
				if(((DialogXMLOption)opt).getCondition() != null){
					conditions.add(String.format("profile.%s.%s",qm.getId(),((DialogXMLOption)opt).getCondition()));
				}
			}
			prefEdit.putStringSet(String.format("responses.profile.%s", qm.getId()), optionIds);
			prefEdit.putStringSet(String.format("conditions.profile.%s", qm.getId()),  conditions);
			
		}
		prefEdit.commit();	
		

		prefs.edit().putBoolean("init", true).commit();
		
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);		
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
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
			qf.setNextButtonListener( new QuestionResponseListener() {
					
					@Override
					public void responseEntered(QuestionModel question) {
						if(position == getCount()-1){
							doFinish();
						} else {
							mViewPager.setCurrentItem(position+1, true);
						}
						
					}
				});
			qf.setBackButtonListener(new QuestionResponseListener() {
				
				@Override
				public void responseEntered(QuestionModel question) {
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
