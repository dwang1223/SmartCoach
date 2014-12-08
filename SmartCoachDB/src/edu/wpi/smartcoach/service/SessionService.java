package edu.wpi.smartcoach.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import edu.wpi.smartcoach.model.Session;

public class SessionService {
	
	private static final String TAG= SessionService.class.getSimpleName();
	
	private static final String PREF_SESSION = "sessions";
	private static final String PREF_SOLUTION = "solutions";
	
	private static SessionService instance;
	
	public static SessionService getInstance(){
		if(instance == null){
			instance = new SessionService();
			
		}
		return instance;
	}
	
	public List<Session> getAllSessions(Context context){
		List<Session> sessionList = new ArrayList<Session>();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		String sessionJSON = prefs.getString(PREF_SESSION, "");
		Log.d(TAG, sessionJSON);
		Session[] sessions = new Gson().fromJson(sessionJSON, new Session[]{}.getClass());
		
		if(sessions == null){
			sessions = new Session[]{};
		}
		
		for(Session s:sessions){
			sessionList.add(s);
		}
		
		Collections.sort(sessionList, new Comparator<Session>(){

			@Override
			public int compare(Session lhs, Session rhs) {
				return (int)(rhs.getTime()-lhs.getTime());
			}});
		
		return sessionList;		
	}
	
	public void addSession(Session session, Context context){
		List<Session> sessions = getAllSessions(context);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		sessions.add(session);		
		String json = new Gson().toJson(sessions.toArray(new Session[]{}));		
		prefs.edit().putString(PREF_SESSION, json).commit();
	}

}
