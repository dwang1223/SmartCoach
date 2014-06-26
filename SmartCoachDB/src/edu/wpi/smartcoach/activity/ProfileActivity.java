package edu.wpi.smartcoach.activity;

import java.util.ArrayList;
import java.util.List;

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
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.view.Option;
import edu.wpi.smartcoach.view.OptionQuestionFragment;
import edu.wpi.smartcoach.view.QuestionResponseListener;
import edu.wpi.smartcoachdb.db.helper.QuestionReader;

public class ProfileActivity extends FragmentActivity {
	

	QuestionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;
	
	List<OptionQuestionModel> questions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		setTitle("Profile Information");
		
		questions = new ArrayList<OptionQuestionModel>();
		List<QuestionModel> read = QuestionReader.readQuestions(R.raw.profile_questions, getBaseContext());
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
			List<Option> select = qm.getSelectedOptions();
			String responseStr = "";
			for(int i = 0; i < select.size()-1; i++){
				responseStr += select.get(i).getId();
				if(i < select.size()-1){
					responseStr += ",";
				}
			}
			prefEdit.putString(qm.getId(), responseStr);
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
