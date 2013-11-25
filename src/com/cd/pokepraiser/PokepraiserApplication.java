package com.cd.pokepraiser;

import java.io.IOException;

import android.app.Application;
import android.graphics.Typeface;
import android.widget.TextView;

import com.cd.pokepraiser.db.DatabaseHelper;

public class PokepraiserApplication extends Application {

	private DatabaseHelper dbHelper;
	private Typeface datFont = null;
	
	public void loadResources(){
		dbHelper = new DatabaseHelper(this);
		
		if(datFont == null)
			datFont = Typeface.createFromAsset(getAssets(), "pokemon_gb_typeface.ttf");
		
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
	
	public void applyTypeface(TextView someView){
		someView.setTypeface(datFont);
	}

	public void applyTypeface(TextView [] someViews){
		for(TextView someView : someViews)
			someView.setTypeface(datFont);
	}	
	
}
