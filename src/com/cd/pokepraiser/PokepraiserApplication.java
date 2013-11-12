package com.cd.pokepraiser;

import java.io.IOException;

import android.app.Application;

import com.cd.pokepraiser.db.DatabaseHelper;

public class PokepraiserApplication extends Application {

	private DatabaseHelper dbHelper;
	
	public void loadResources(){
		dbHelper = new DatabaseHelper(this);
		
		try {
			dbHelper.initializeDataBase();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error("Unable to create database for PokePraiser");
		}
		
	}
	
	public void closeResources(){
		if(dbHelper != null)
			dbHelper.close();
	}
	
	public DatabaseHelper getDatabaseReference(){
		return dbHelper;
	}
	
}
