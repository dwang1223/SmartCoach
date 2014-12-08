package edu.wpi.smartcoach.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.wpi.smartcoach.R;
import edu.wpi.smartcoach.SessionActivity;
import edu.wpi.smartcoach.model.Session;
import edu.wpi.smartcoach.service.SessionService;

public class SessionHistoryFragment extends Fragment {
	
	private SessionAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_session_history, null);
		
		TextView none = (TextView)view.findViewById(R.id.none);
		ListView list = (ListView)view.findViewById(R.id.list);
		
		adapter = new SessionAdapter(getActivity());
		
		adapter.addAll(SessionService.getInstance().getAllSessions(getActivity()));
		
		list.setAdapter(adapter);
		
		if(adapter.getCount() > 0){
			none.setVisibility(View.GONE);
		}
		
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String sessionJson = new Gson().toJson(adapter.getItem(position));
				Intent intent = new Intent(getActivity(), SessionActivity.class);
				intent.putExtra("session", sessionJson);
				startActivity(intent);
			}
		});
		
		return view;
	}
	
	class SessionAdapter extends ArrayAdapter<Session>{

				
		public SessionAdapter(Context context) {
			super(context, R.layout.item_session_history);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			View view;
			
			if(convertView == null){
				view = LayoutInflater.from(getContext()).inflate(R.layout.item_session_history, null);
			} else {
				view = convertView;
			}

			TextView type = (TextView)view.findViewById(R.id.type);
			TextView date = (TextView)view.findViewById(R.id.date);
			TextView count = (TextView)view.findViewById(R.id.numSolutions);
			
			Session s = getItem(position);
			
			type.setText(s.getType());
			date.setText(Session.DATE_FORMAT.format(new Date(s.getTime())));
			count.setText(String.format("%d Solutions", s.getSolutions().size()));
			
			return view;
		}
		
	}

}
