package edu.wpi.smartcoach.service;

import android.database.Cursor;
import edu.wpi.smartcoach.db.column.PatientMetricsColumns;
import edu.wpi.smartcoach.model.PatientMetrics;
import edu.wpi.smartcoach.util.DatabaseHelper;

public class PatientMetricsService{
	
	private static PatientMetricsService instance; 
	
	public static PatientMetricsService getInstance() {
		if (instance == null) {
			instance = new PatientMetricsService();
		}
		return instance;
	}
	
	public void initPatientMetrics(PatientMetrics patientInfo) {
		String sql = "insert into "
				+ PatientMetricsColumns.TABLE_PATIENT_METRICS + " ("
				+ PatientMetricsColumns.FIELD_PATIENT_HEIGHT + ", "
				+ PatientMetricsColumns.FIELD_PATIENT_WEIGHT + ", "
				+ PatientMetricsColumns.FIELD_LAST_UPDATE_TIME + ", "
				+ PatientMetricsColumns.FIELD_GOAL_WEIGHT + ") "
				+ "values ('" + patientInfo.getHeight() + "', '"
				+ patientInfo.getWeight() + "','"
				+ patientInfo.getLastUpateTimeDate() + "','"
				+ patientInfo.getGoalWeight() + "')";
		DatabaseHelper.getInstance().getWritableDatabase().execSQL("delete from "+PatientMetricsColumns.TABLE_PATIENT_METRICS);
		DatabaseHelper.getInstance().getWritableDatabase().execSQL(sql);
	}
	
	public PatientMetrics getMetrics(){
		
		PatientMetrics metrics = new PatientMetrics(0, 0, null, 0);
		
		String sql = "select * from "+PatientMetricsColumns.TABLE_PATIENT_METRICS;
		
		Cursor cursor = DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(sql, null);
		
		try {
			cursor.moveToNext();
			metrics.setHeight(cursor.getFloat(1));
			metrics.setWeight(cursor.getFloat(2));
			metrics.setGoalWeight(cursor.getFloat(5));
		} finally {
			cursor.close();
		}
		
		return metrics;
		
	}
	
}
