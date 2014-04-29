package edu.wpi.smartcoachdb.db.helper;

import edu.wpi.smartcoachdb.db.column.ExerciseColumns;
import edu.wpi.smartcoachdb.db.column.ExerciseLocationColumns;
import edu.wpi.smartcoachdb.db.column.ExerciseTimeColumns;
import edu.wpi.smartcoachdb.db.column.ExerciseToLocationColumns;
import edu.wpi.smartcoachdb.db.column.PatientExerciseColumns;
import edu.wpi.smartcoachdb.db.column.PatientInfoColumns;
import edu.wpi.smartcoachdb.db.column.PatientProfileColumns;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static DatabaseHelper instance;

	public static DatabaseHelper getInstance(Context ctx) {
		if (instance == null) {
			instance = new DatabaseHelper(ctx);
		}
		return instance;
	}

	public static DatabaseHelper getInstance() {
		return instance;
	}

	private static final String DATABASE_NAME = "smartcoach.db";
	private static final int DATABASE_VERSION = 1;

	private DatabaseHelper(Context context) {
		// default value of CursorFactory is null
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// create table of t_patient_profile
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ PatientProfileColumns.TABLE_PATIENT_PROFILE + " ("
				+ PatientProfileColumns.FIELD_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ PatientProfileColumns.FIELD_PATIENT_FIRST_NAME + " VARCHAR,"
				+ PatientProfileColumns.FIELD_PATIENT_LAST_NAME + " VARCHAR,"
				+ PatientProfileColumns.FIELD_PATIENT_GENDER + " VARCHAR,"
				+ PatientProfileColumns.FIELD_PATIENT_BIRTHDAY + " DATETIME,"
				+ PatientProfileColumns.FIELD_PATIENT_ADDRESS + " VARCHAR,"
				+ PatientProfileColumns.FIELD_PATIENT_OCCUPATION + " VARCHAR"
				+ ")");
		// create table of t_patient_info
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ PatientInfoColumns.TABLE_PATIENT_INFO + " ("
				+ PatientInfoColumns.FIELD_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ PatientInfoColumns.FIELD_PATIENT_HEIGHT + " FLOAT,"
				+ PatientInfoColumns.FIELD_PATIENT_WEIGHT + " FLOAT,"
				+ PatientInfoColumns.FIELD_PATIENT_AGE + " INTEGER,"
				+ PatientInfoColumns.FIELD_LAST_UPDATE_TIME + " DATETIME,"
				+ PatientInfoColumns.FIELD_GOAL_WEIGHT + " FLOAT,"
				+ "FOREIGN KEY (" + PatientInfoColumns.FIELD_ID
				+ ") REFERENCES " + PatientProfileColumns.TABLE_PATIENT_PROFILE
				+ "(" + PatientProfileColumns.FIELD_ID + ") )");
		// create table of t_exercise
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ ExerciseColumns.TABLE_EXERCISE + " ("
				+ ExerciseColumns.FIELD_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ ExerciseColumns.FIELD_EXERCISE_NAME + " VARCHAR,"
				+ ExerciseColumns.FIELD_EXERCISE_TYPE + " VARCHAR,"
				+ ExerciseColumns.FIELD_EXERCISE_NUMBER_OF_PERSONS
				+ " VARCHAR," + ExerciseColumns.FIELD_EXERCISE_EQUIPMENT
				+ " VARCHAR" + ")");
		// create table of t_exercise_location
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ ExerciseLocationColumns.TABLE_EXERCISE_LOCATION + " ("
				+ ExerciseLocationColumns.FIELD_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ ExerciseLocationColumns.FIELD_SPECIFIC_LOCATION + " VARCHAR,"
				+ ExerciseLocationColumns.FIELD_LOCATION_TYPE + " INTEGER"
				+ ")");
		// create table of t_exercise_to_location
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ ExerciseToLocationColumns.TABLE_EXERCISE + " ("
				+ ExerciseToLocationColumns.FIELD_EXERCISE_ID + " INTEGER,"
				+ ExerciseToLocationColumns.FIELD_EXERCISE_LOCATION_ID
				+ " INTEGER," + "FOREIGN KEY ("
				+ ExerciseToLocationColumns.FIELD_EXERCISE_ID + ") REFERENCES "
				+ ExerciseColumns.TABLE_EXERCISE + "("
				+ ExerciseColumns.FIELD_ID + ")," + "FOREIGN KEY ("
				+ ExerciseToLocationColumns.FIELD_EXERCISE_LOCATION_ID
				+ ") REFERENCES "
				+ ExerciseLocationColumns.TABLE_EXERCISE_LOCATION + "("
				+ ExerciseLocationColumns.FIELD_ID + ") )");
		// create table of t_exercise_time
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ ExerciseTimeColumns.TABLE_EXERCISE_TIME + " ("
				+ ExerciseTimeColumns.FIELD_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ ExerciseTimeColumns.FIELD_EXERCISE_TIME + " VARCHAR" + ")");
		// create table of t_patient_exercise
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ PatientExerciseColumns.TABLE_PATIENT_EXERCISE + " ("
				+ PatientExerciseColumns.FIELD_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ PatientExerciseColumns.FIELD_PATIENT_ID + " INTEGER,"
				+ PatientExerciseColumns.FIELD_EXERCISE_ID + " INTEGER,"
				+ PatientExerciseColumns.FIELD_EXERCISE_LOCATION_ID
				+ " INTEGER," + PatientExerciseColumns.FIELD_EXERCISE_TIME_ID
				+ " INTEGER," + PatientExerciseColumns.FIELD_PATIENT_IS_LIKED
				+ " BOOLEAN," + "FOREIGN KEY ("
				+ PatientExerciseColumns.FIELD_PATIENT_ID + ") REFERENCES "
				+ PatientProfileColumns.TABLE_PATIENT_PROFILE + "("
				+ PatientProfileColumns.FIELD_ID + ")," + "FOREIGN KEY ("
				+ PatientExerciseColumns.FIELD_EXERCISE_ID + ") REFERENCES "
				+ ExerciseColumns.TABLE_EXERCISE + "("
				+ ExerciseColumns.FIELD_ID + ")," + "FOREIGN KEY ("
				+ PatientExerciseColumns.FIELD_EXERCISE_LOCATION_ID
				+ ") REFERENCES "
				+ ExerciseLocationColumns.TABLE_EXERCISE_LOCATION + "("
				+ ExerciseLocationColumns.FIELD_ID + ")," + "FOREIGN KEY ("
				+ PatientExerciseColumns.FIELD_EXERCISE_TIME_ID
				+ ") REFERENCES " + ExerciseTimeColumns.TABLE_EXERCISE_TIME
				+ "(" + ExerciseTimeColumns.FIELD_ID + ") )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
