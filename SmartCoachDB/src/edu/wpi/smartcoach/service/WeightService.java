package edu.wpi.smartcoach.service;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import android.database.Cursor;
import edu.wpi.smartcoach.util.DatabaseHelper;

public class WeightService {
		
		private static WeightService instance = null;
		
		public static WeightService getInstance(){
			if(instance == null){
				instance = new WeightService();
			}
			
			return instance;
		}
		
		public List<Entry<Long, Float>> getAllDataFromTable(){
			List<Entry<Long, Float>> reminders = new ArrayList<Entry<Long, Float>>();
			
			String sql = "select * from t_weight";
			Cursor cursor = DatabaseHelper.getInstance().getReadableDatabase().rawQuery(sql, null);
			
			while(cursor.moveToNext()){
				reminders.add(new SimpleEntry<Long, Float>(
						cursor.getLong(0),
						cursor.getFloat(1)));
			}
			
			return reminders;
		}
		
		public void addWeight(long time, float weight){
			String sql = String.format("insert into t_weight (time, weight) values (%d, %f)", time, weight);
		
			DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
		}

	
}
