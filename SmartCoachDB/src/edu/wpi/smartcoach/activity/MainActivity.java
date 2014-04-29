package edu.wpi.smartcoach.activity;

import java.util.ArrayList;
import java.util.Iterator;

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
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.Exercise;
import edu.wpi.smartcoach.model.ExerciseLocation;
import edu.wpi.smartcoach.model.ExerciseTime;
import edu.wpi.smartcoach.model.ExerciseToLocation;
import edu.wpi.smartcoachdb.dao.ExerciseDao;
import edu.wpi.smartcoachdb.dao.ExerciseLocationDao;
import edu.wpi.smartcoachdb.dao.ExerciseTimeDao;
import edu.wpi.smartcoachdb.dao.ExerciseToLocationDao;
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;

public class MainActivity extends Activity {

	private DatabaseHelper mDatabaseHelp = null;
	private static SQLiteDatabase mSQLiteDatabase = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);

		mDatabaseHelp = DatabaseHelper.getInstance(this);
		mSQLiteDatabase = mDatabaseHelp.getWritableDatabase();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		// let it run just once
		if (!prefs.getBoolean("init", false)) {
			initExercise();
			initExerciseLocation();
			initExerciseToLocation();
			initExerciseTime();
			prefs.edit().putBoolean("init", true).commit();
			startActivity(new Intent(this, RegistrationActivity.class));
		} else {

			startActivity(new Intent(this, ExerciseProblemActivity.class));
		}
		finish();
	}

	/**
	 * insert data of table of t_exercise
	 */
	public void initExercise() {
		ExerciseDao mExerciseDaoImpl = new ExerciseDao();
		ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
		Exercise mExercise1 = new Exercise("Walking", "Cardio", "Any", "shoes");
		Exercise mExercise2 = new Exercise("Running", "Cardio", "Any", "shoes");
		Exercise mExercise3 = new Exercise("Biking", "Cardio", "Any", "bike");
		Exercise mExercise4 = new Exercise("Hiking", "Cardio", "Any", "shoes");
		Exercise mExercise5 = new Exercise("Skating", "Cardio", "Any", "skates");
		Exercise mExercise6 = new Exercise("Yoga", "Other", "Any", "mat");
		Exercise mExercise7 = new Exercise("Pilates", "Other", "Any", "mat");
		Exercise mExercise8 = new Exercise("Gymnastics", "Other", "Any", "mat");
		Exercise mExercise9 = new Exercise("weight training", "Strength",
				"Any", "weights");
		Exercise mExercise10 = new Exercise("dancing", "Cardio", "Any", "shoes");
		Exercise mExercise11 = new Exercise("swimming", "Cardio", "Any",
				"swimsuit");
		Exercise mExercise12 = new Exercise("basketball", "Cardio", "Group",
				"ball");
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
		ExerciseLocationDao mExerciseLocationDaoImpl = new ExerciseLocationDao();
		ArrayList<ExerciseLocation> exerciseLocationList = new ArrayList<ExerciseLocation>();
		ExerciseLocation mExerciseLocation1 = new ExerciseLocation("Home",
				"Indoor");
		ExerciseLocation mExerciseLocation2 = new ExerciseLocation("Gym",
				"Indoor");
		ExerciseLocation mExerciseLocation3 = new ExerciseLocation("Mall",
				"Indoor");
		ExerciseLocation mExerciseLocation4 = new ExerciseLocation(
				"Swimming pool", "Indoor/Outdoor");
		ExerciseLocation mExerciseLocation5 = new ExerciseLocation("Street",
				"Outdoor");
		ExerciseLocation mExerciseLocation6 = new ExerciseLocation("Yard",
				"Outdoor");
		ExerciseLocation mExerciseLocation7 = new ExerciseLocation("Park",
				"Outdoor");
		ExerciseLocation mExerciseLocation8 = new ExerciseLocation("Beach",
				"Outdoor");
		ExerciseLocation mExerciseLocation9 = new ExerciseLocation("Mountain",
				"Outdoor");
		ExerciseLocation mExerciseLocation10 = new ExerciseLocation("Skating Rink",
				"Outdoor");
		ExerciseLocation mExerciseLocation11 = new ExerciseLocation("Field",
				"Outdoor");
		exerciseLocationList.add(mExerciseLocation1);
		exerciseLocationList.add(mExerciseLocation2);
		exerciseLocationList.add(mExerciseLocation3);
		exerciseLocationList.add(mExerciseLocation4);
		exerciseLocationList.add(mExerciseLocation5);
		exerciseLocationList.add(mExerciseLocation6);
		exerciseLocationList.add(mExerciseLocation7);
		exerciseLocationList.add(mExerciseLocation8);
		exerciseLocationList.add(mExerciseLocation9);
		exerciseLocationList.add(mExerciseLocation10);
		exerciseLocationList.add(mExerciseLocation11);
		for (ExerciseLocation mExerciseLocation : exerciseLocationList) {
			mExerciseLocationDaoImpl.insertOne(mExerciseLocation);
		}
	}

	/**
	 * insert data of table of t_exercise_to_location
	 */
	public void initExerciseToLocation() {
		ExerciseToLocationDao mExerciseToLocationDaoImp = new ExerciseToLocationDao();
		ArrayList<ExerciseToLocation> exerciseToLocationList = new ArrayList<ExerciseToLocation>();
		exerciseToLocationList.add(new ExerciseToLocation(1, 1));
		exerciseToLocationList.add(new ExerciseToLocation(1, 2));
		exerciseToLocationList.add(new ExerciseToLocation(1, 3));
		exerciseToLocationList.add(new ExerciseToLocation(1, 5));
		exerciseToLocationList.add(new ExerciseToLocation(1, 6));
		exerciseToLocationList.add(new ExerciseToLocation(1, 7));
		exerciseToLocationList.add(new ExerciseToLocation(1, 8));

		exerciseToLocationList.add(new ExerciseToLocation(2, 2));
		exerciseToLocationList.add(new ExerciseToLocation(2, 3));
		exerciseToLocationList.add(new ExerciseToLocation(2, 5));
		exerciseToLocationList.add(new ExerciseToLocation(2, 6));
		exerciseToLocationList.add(new ExerciseToLocation(2, 7));
		exerciseToLocationList.add(new ExerciseToLocation(2, 8));
		
		exerciseToLocationList.add(new ExerciseToLocation(3, 5));
		exerciseToLocationList.add(new ExerciseToLocation(3, 6));
		exerciseToLocationList.add(new ExerciseToLocation(3, 7));

		exerciseToLocationList.add(new ExerciseToLocation(4, 9));

		exerciseToLocationList.add(new ExerciseToLocation(5, 5));
		exerciseToLocationList.add(new ExerciseToLocation(5, 10));
		
		exerciseToLocationList.add(new ExerciseToLocation(6, 1));
		exerciseToLocationList.add(new ExerciseToLocation(6, 2));
		exerciseToLocationList.add(new ExerciseToLocation(6, 7));
		exerciseToLocationList.add(new ExerciseToLocation(6, 8));
		
		exerciseToLocationList.add(new ExerciseToLocation(7, 1));
		exerciseToLocationList.add(new ExerciseToLocation(7, 2));
		exerciseToLocationList.add(new ExerciseToLocation(7, 7));
		exerciseToLocationList.add(new ExerciseToLocation(7, 8));
		
		exerciseToLocationList.add(new ExerciseToLocation(8, 1));
		exerciseToLocationList.add(new ExerciseToLocation(8, 2));
		exerciseToLocationList.add(new ExerciseToLocation(8, 7));
		exerciseToLocationList.add(new ExerciseToLocation(8, 8));
		
		exerciseToLocationList.add(new ExerciseToLocation(9, 1));
		exerciseToLocationList.add(new ExerciseToLocation(9, 2));
		
		exerciseToLocationList.add(new ExerciseToLocation(10, 1));
		exerciseToLocationList.add(new ExerciseToLocation(10, 2));
		
		exerciseToLocationList.add(new ExerciseToLocation(11, 4));
		
		exerciseToLocationList.add(new ExerciseToLocation(12, 2));
		exerciseToLocationList.add(new ExerciseToLocation(12, 11));
		
		for(ExerciseToLocation mExerciseToLocation : exerciseToLocationList){
			mExerciseToLocationDaoImp.intertOne(mExerciseToLocation);
		}
	}

	/**
	 * insert data of table of t_exercise_time
	 */
	public void initExerciseTime() {
		ExerciseTimeDao mExerciseTimeDaoImpl = new ExerciseTimeDao();
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
