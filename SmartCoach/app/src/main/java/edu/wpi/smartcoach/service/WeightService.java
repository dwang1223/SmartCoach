package edu.wpi.smartcoach.service;

import android.content.Context;
import android.database.Cursor;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import edu.wpi.smartcoach.util.DatabaseHelper;

/**
 * Handles storing the user's weight entries into the sqlite database
 * @author Akshay
 */
public class WeightService {
		
		private static WeightService instance = null;

		public static WeightService getInstance(){
			if(instance == null){
				instance = new WeightService();
			}
			
			return instance;
		}

        private WeightService(){}
		
		public List<Entry<Long, Float>> getAllDataFromTable(Context c){
			List<Entry<Long, Float>> reminders = new ArrayList<Entry<Long, Float>>();
			
			String sql = "select * from t_weight";
			Cursor cursor = DatabaseHelper.getInstance(c).getReadableDatabase().rawQuery(sql, null);
			
			while(cursor.moveToNext()){
				reminders.add(new SimpleEntry<Long, Float>(
						cursor.getLong(0),
						cursor.getFloat(1)));
			}
			return reminders;
		}
		
		public void addWeight(long time, float weight, Context c){
			String sql = String.format("insert into t_weight (time, weight) values (%d, %f)", time, weight);
			DatabaseHelper.getInstance(c).getWritableDatabase().execSQL(sql);
		}	
}
