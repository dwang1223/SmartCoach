package edu.wpi.smartcoach.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.model.Session;
import edu.wpi.smartcoach.model.Solution;
import edu.wpi.smartcoach.view.SolutionListAdapter;

public class SessionActivity extends Activity {

	private TextView none;
	private ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_session);
		

		none = (TextView)findViewById(R.id.none);
		
		String json = getIntent().getExtras().getString("session");
		Session session = new Gson().fromJson(json, Session.class);
		
		if(session == null){
			session = new Session();
		} 
		
		setTitle(String.format("%s Session %s", session.getType(), Session.DATE_FORMAT.format(new Date(session.getTime()))));
		
		List<Solution> solutionList = session.getSolutions();
		
		if(solutionList == null){
			solutionList = new ArrayList<Solution>();
		}
		
		if(session.getSolutions().size() > 0){
			none.setVisibility(View.GONE);
		}
		
		list = (ListView)findViewById(R.id.list);
		SolutionListAdapter adapter = new SolutionListAdapter(this, session.getSolutions());
		list.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.session, menu);
		
		
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
}
