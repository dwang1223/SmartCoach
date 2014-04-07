package edu.wpi.smartcoach;

import java.util.Locale;

import edu.wpi.smartcoach.model.ExerciseProfile;
import edu.wpi.smartcoach.view.QuestionFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
			QuestionFragment qf = new QuestionFragment();
			qf
				.setQuestion(ExerciseProfile.questions[position])
				.setLast(position == getCount()-1)
				.setNextButtonListener( new View.OnClickListener() {				
					@Override
					public void onClick(View v) {
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
