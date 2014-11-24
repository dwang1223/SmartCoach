package edu.wpi.smartcoach.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import edu.wpi.smartcoach.R;

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

	/**
	 * Constructor
	 * @param context The context of this application
	 */
	private DatabaseHelper(Context context) {
		// default value of CursorFactory is null
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	/**
	 * Initializes the database by copying from the default DB in res/raw/smartcoachdb.db
	 * @param ctx The context of this application
	 */
	public void initializeFromDefault(Context ctx){
		File dbFile = new File(getWritableDatabase().getPath());
		InputStream dbSource = ctx.getResources().openRawResource(R.raw.smartcoachdb);
		try {
			
			FileOutputStream dbOut = new FileOutputStream(dbFile);
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = dbSource.read(buffer)) > 0){
				dbOut.write(buffer, 0, len);
			}
			
			dbSource.close();
			dbOut.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void copyToStorage(Context ctx){
		File dbFile = new File(getWritableDatabase().getPath());
		File outFile = new File(ctx.getExternalFilesDir(null), "smartcoachdb.db");
		Log.d(DatabaseHelper.class.getSimpleName(), outFile.getAbsolutePath());
		try {

			FileInputStream dbSource = new FileInputStream(dbFile);
			FileOutputStream dbOut = new FileOutputStream(outFile);
			
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = dbSource.read(buffer)) > 0){
				dbOut.write(buffer, 0, len);
			}
			
			dbSource.close();
			dbOut.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}

}
