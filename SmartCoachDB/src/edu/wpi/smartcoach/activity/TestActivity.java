package edu.wpi.smartcoach.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.Exercise;
import edu.wpi.smartcoach.model.ExerciseLocation;
import edu.wpi.smartcoach.model.ExerciseTime;
import edu.wpi.smartcoach.service.ExerciseLocationService;
import edu.wpi.smartcoach.service.ExerciseService;
import edu.wpi.smartcoach.service.ExerciseTimeService;

public class TestActivity extends Activity {

	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		mListView = (ListView) findViewById(R.id.test_listview);
		mListView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData_ExerciseLocation()));
	}

	private List<String> getData_Exercise(){
		List<Exercise> mExercises = ExerciseService.getInstance().getAllDataFromTable();
		List<String> data = new ArrayList<String>();
		for(Exercise mExercise : mExercises){
			data.add(mExercise.getName());
		}
		return data;
	}
	private List<String> getData_ExerciseTime() {
		List<ExerciseTime> mExerciseTimes = ExerciseTimeService
				.getInstance().getAllDataFromTable();
		List<String> data = new ArrayList<String>();
		for (ExerciseTime mExerciseTime : mExerciseTimes) {
			data.add(mExerciseTime.getTime());
		}
		return data;
	}
	private List<String> getData_ExerciseLocation() {
		List<ExerciseLocation> mExerciseLocations = ExerciseLocationService
				.getInstance().getAllDataFromTable();
		List<String> data = new ArrayList<String>();
		for (ExerciseLocation mExerciseLocation : mExerciseLocations) {
			data.add(mExerciseLocation.getSpecificLocation());
		}
		return data;
	}
}
