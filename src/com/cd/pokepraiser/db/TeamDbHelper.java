package com.cd.pokepraiser.db;

import com.cd.pokepraiser.db.queries.TeamQueries;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TeamDbHelper extends SQLiteOpenHelper {
	// The Android's default system path of your application database.
	private static String DB_NAME 			= "teamdb.sqlite";

	private static final int DB_VERSION		= 2;
	
	private final Context myContext;

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public TeamDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);

		this.myContext = context;
	}
	
    @Override
    public void onCreate(SQLiteDatabase db) {
    	db.execSQL(TeamQueries.CREATE_TABLE_TEAMS);
    	db.execSQL(TeamQueries.CREATE_TABLE_TEAMS_MEMBERS);
    	db.execSQL(TeamQueries.CREATE_TABLE_MEMBERS);    	
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	Log.w(TeamDbHelper.class.getName(),
    	        "Upgrading database from version " + oldVersion + " to "
    	            + newVersion + ", which will destroy all old data");
    	db.execSQL("DROP TABLE IF EXISTS TEAMS");
    	db.execSQL("DROP TABLE IF EXISTS TEAMS_MEMBERS");
    	db.execSQL("DROP TABLE IF EXISTS MEMBERS");    	
    	onCreate(db);
    }

}
