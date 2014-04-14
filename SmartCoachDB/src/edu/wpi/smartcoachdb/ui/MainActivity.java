package edu.wpi.smartcoachdb.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import edu.wpi.smartcoachdb.R;
import edu.wpi.smartcoachdb.dao.impl.ExerciseDaoImpl;
import edu.wpi.smartcoachdb.dao.impl.ExerciseLocationDaoImpl;
import edu.wpi.smartcoachdb.dao.impl.ExerciseTimeDaoImpl;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;
import edu.wpi.smartcoachdb.model.Exercise;
import edu.wpi.smartcoachdb.model.ExerciseLocation;
import edu.wpi.smartcoachdb.model.ExerciseTime;

public class MainActivity extends Activity {

	private DatabaseHelper mDatabaseHelp = null;
	private static SQLiteDatabase mSQLiteDatabase = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);

		mDatabaseHelp = DatabaseHelper.getInstance(this);
		mSQLiteDatabase = mDatabaseHelp.getWritableDatabase();
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		//let it run just once
		if(!prefs.getBoolean("init", false)){
			initExercise();
			initExerciseLocation();
			initExerciseTime();
			prefs.edit().putBoolean("init", true).commit();
		}

		startActivity(new Intent(this, RegistrationActivity.class));
	}

	/**
	 * insert data of table of t_exercise
	 */
	public void initExercise() {
		ExerciseDaoImpl mExerciseDaoImpl = new ExerciseDaoImpl();
		ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
		Exercise mExercise1 = new Exercise("Walking", "Cardio", "Any",
				"shoes");
		Exercise mExercise2 = new Exercise("Running", "Cardio", "Any",
				"shoes");
		Exercise mExercise3 = new Exercise("Biking", "Cardio", "Any",
				"bike");
		Exercise mExercise4 = new Exercise("Hiking", "Cardio", "Any", "shoes");
		Exercise mExercise5 = new Exercise("Skating", "Cardio", "Any",
				"skates");
		Exercise mExercise6 = new Exercise("Yoga", "Other", "Any", "mat");
		Exercise mExercise7 = new Exercise("Pilates", "Other", "Any", "mat");
		Exercise mExercise8 = new Exercise("Gymnastics", "Other", "Any", "mat");
		Exercise mExercise9 = new Exercise("weight training", "Strength", "Any",
				"weights");
		Exercise mExercise10 = new Exercise("dancing", "Cardio", "Any", "shoes");
		Exercise mExercise11 = new Exercise("swimming", "Cardio",
				"Any", "swimsuit");
		Exercise mExercise12 = new Exercise("basketball", "Cardio",
				"Group", "ball");
		exerciseList.add(mExercise1);
		exerciseList.add(mExercise2);
		exerciseList.add(mExercise3);
		exerciseList.add(mExercise4);
		exerciseList.add(mExercise5);
		exerciseList.add(mExercise6);
		exerciseList.add(mExercise7);
		exerciseList.add(mExercise8);
		exerciseList.add(mExercise9);
		exerciseList.add(mExercise10);
		exerciseList.add(mExercise11);
		exerciseList.add(mExercise12);
		for (Exercise mExercise : exerciseList) {
			mExerciseDaoImpl.insertOne(mExercise);
		}
	}

	/**
	 * insert data of table of t_exercise_location
	 */
	public void initExerciseLocation() {
		ExerciseLocationDaoImpl mExerciseLocationDaoImpl = new ExerciseLocationDaoImpl();
		ArrayList<ExerciseLocation> exerciseLocationList = new ArrayList<ExerciseLocation>();
		ExerciseLocation meExerciseLocation1 = new ExerciseLocation("Home", "Indoor");
		ExerciseLocation meExerciseLocation2 = new ExerciseLocation("Gym", "Indoor");
		ExerciseLocation meExerciseLocation3 = new ExerciseLocation("Mall", "Indoor");
		ExerciseLocation meExerciseLocation4 = new ExerciseLocation("Swimming pool", "Indoor/Outdoor");
		ExerciseLocation meExerciseLocation5 = new ExerciseLocation("Street", "Outdoor");
		ExerciseLocation meExerciseLocation6 = new ExerciseLocation("Yard", "Outdoor");
		ExerciseLocation meExerciseLocation7 = new ExerciseLocation("Park", "Outdoor");
		ExerciseLocation meExerciseLocation8 = new ExerciseLocation("Beach", "Outdoor");
		exerciseLocationList.add(meExerciseLocation1);
		exerciseLocationList.add(meExerciseLocation2);
		exerciseLocationList.add(meExerciseLocation3);
		exerciseLocationList.add(meExerciseLocation4);
		exerciseLocationList.add(meExerciseLocation5);
		exerciseLocationList.add(meExerciseLocation6);
		exerciseLocationList.add(meExerciseLocation7);
		exerciseLocationList.add(meExerciseLocation8);
		for(ExerciseLocation mExerciseLocation: exerciseLocationList){
			mExerciseLocationDaoImpl.insertOne(mExerciseLocation);
		}
	}
	
	/**
	 * insert data of table of t_exercise_time
	 */
	public void initExerciseTime() {
		ExerciseTimeDaoImpl mExerciseTimeDaoImpl = new ExerciseTimeDaoImpl();
		ArrayList<ExerciseTime> exerciseTimeList = new ArrayList<ExerciseTime>();
		ExerciseTime mExerciseTime1 = new ExerciseTime("Early morning");
		ExerciseTime mExerciseTime2 = new ExerciseTime("Morning");
		ExerciseTime mExerciseTime3 = new ExerciseTime("Lunchtime");
		ExerciseTime mExerciseTime4 = new ExerciseTime("Afternoon");
		ExerciseTime mExerciseTime5 = new ExerciseTime("Evening");
		ExerciseTime mExerciseTime6 = new ExerciseTime("Night");
		exerciseTimeList.add(mExerciseTime1);
		exerciseTimeList.add(mExerciseTime2);
		exerciseTimeList.add(mExerciseTime3);
		exerciseTimeList.add(mExerciseTime4);
		exerciseTimeList.add(mExerciseTime5);
		exerciseTimeList.add(mExerciseTime6);
		for (ExerciseTime mExerciseTime : exerciseTimeList) {
			mExerciseTimeDaoImpl.insertOne(mExerciseTime);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
