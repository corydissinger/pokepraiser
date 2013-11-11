package com.cd.pokepraiser;

import java.io.IOException;

import com.cd.pokepraiser.db.DataBaseHelper;
import com.cd.pokepraiser.db.attacks.AttacksDataSource;

import android.app.Application;

public class PokepraiserApplication extends Application {

	private DataBaseHelper dbHelper;
	
	public void loadResources(){
		dbHelper = new DataBaseHelper(this);
		
		try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error("Unable to create database for PokePraiser");
		}
		
		dbHelper.openDataBase();
	}
	
	public void closeResources(){
		if(dbHelper != null)
			dbHelper.close();
	}
	
}
