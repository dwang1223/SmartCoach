package edu.wpi.smartcoach.activity;

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
import edu.wpi.smartcoachdb.db.helper.DatabaseHelper;

public class MainActivity extends Activity {

	private DatabaseHelper mDatabaseHelp = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);

		mDatabaseHelp = DatabaseHelper.getInstance(this);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		// let it run just once
		if (!prefs.getBoolean("init", false)) {
			mDatabaseHelp.initializeFromDefault(this);
			prefs.edit().putBoolean("init", true).commit();
			startActivity(new Intent(this, RegistrationActivity.class));
		} else {

			startActivity(new Intent(this, ExerciseProblemActivity.class));
		}
		finish();
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