package edu.wpi.smartcoach.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import edu.wpi.smartcoach.reminders.Reminder;
import edu.wpi.smartcoach.util.DatabaseHelper;

public class ReminderService {
	
	private static ReminderService instance = null;
	
	public static ReminderService getInstance(){
		if(instance == null){
			instance = new ReminderService();
		}
		
		return instance;
	}
	
	public Reminder getReminder(long id, Context c){
		List<Reminder> all = getAllDataFromTable(c);
		Reminder reminder = null;
		for(Reminder r:all){
			if(r.getId() == id){
				reminder = r;
				break;
			}
		}
		return reminder;
	}
	
	public List<Reminder> getAllDataFromTable(Context c){
		List<Reminder> reminders = new ArrayList<Reminder>();
		
		String sql = "select * from t_reminders";
		Cursor cursor = DatabaseHelper.getInstance(c).getReadableDatabase().rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			reminders.add(new Reminder(
					(int)cursor.getLong(0),
					cursor.getString(1),
					cursor.getString(2),
					cursor.getInt(3),
					cursor.getInt(4)));
		}
		
		return reminders;
	}
	
	public void addReminder(Reminder rem, Context c){
		String sql = "insert into t_reminders (message, days, hour, minute) values ("+		
			"\""+rem.getMessage()+"\", "+
			"\""+rem.getDays()+"\", "+
			rem.getHour()+", "+
			rem.getMinute()+")";
		DatabaseHelper.getInstance(c).getWritableDatabase().execSQL(sql);
	}

	public int getNewId(Context c) {
		long max = 0;
		for(Reminder r:getAllDataFromTable(c)){
			if(r.getId() > max){
				max = r.getId();
			}
		}
		
		return (int)max;
	}

}
