package edu.wpi.smartcoach.service;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import edu.wpi.smartcoach.util.DatabaseHelper;

public class ReminderService {
	
	private static ReminderService instance = null;
	
	public static ReminderService getInstance(){
		if(instance == null){
			instance = new ReminderService();
		}
		
		return instance;
	}
	
	public List<String> getAllDataFromTable(){
		List<String> reminders = new ArrayList<String>();
		
		String sql = "select * from t_reminders";
		Cursor cursor = DatabaseHelper.getInstance().getReadableDatabase().rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			reminders.add(cursor.getString(1));
		}
		
		return reminders;
	}
	
	public void addReminder(String reminder){
		
	}

}
