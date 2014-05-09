package edu.wpi.smartcoach.activity;

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
import edu.wpi.smartcoach.model.OptionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.exercise.ExerciseProfile;
import edu.wpi.smartcoach.view.OptionQuestionFragment;
import edu.wpi.smartcoach.view.QuestionResponseListener;

public class ProfileActivity extends FragmentActivity {
	

	QuestionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		mSectionsPagerAdapter = new QuestionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}
	
	private void doFinish(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Editor prefEdit = prefs.edit();
		for(OptionQuestionModel qm:ExerciseProfile.questions){
			List<OptionModel> select = qm.getSelectedResponses();
			String responseStr = "";
			for(int i = 0; i < select.size(); i++){
				responseStr += select.get(i).getId();
				if(i < select.size()-1){
					responseStr += ",";
				}
			}
			prefEdit.putString(qm.getId(), responseStr);
		}
		prefEdit.commit();	
		
		Intent intent = new Intent(this, ExerciseProblemActivity.class);
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
			qf
				.setQuestion(ExerciseProfile.questions[position])
				.setLast(position == getCount()-1)
				.setNextButtonListener( new QuestionResponseListener() {
					
					@Override
					public void responseEntered(QuestionModel question) {
						if(position == getCount()-1){
							doFinish();
						} else {
							mViewPager.setCurrentItem(position+1, true);
						}
						
					}
				});
				
			return qf;
		}

		@Override
		public int getCount() {
			return ExerciseProfile.questions.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return ExerciseProfile.questions[position].getTitle();
		}
	}



}
