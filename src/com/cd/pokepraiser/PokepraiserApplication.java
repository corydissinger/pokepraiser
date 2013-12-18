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
		if(datFont == null)
			datFont = Typeface.createFromAsset(getAssets(), "pokemon_gb_typeface.ttf");
		
		dbHelper = new DatabaseHelper(this);			
		
		try {
			dbHelper.initializeDataBase();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error("Unable to create database for PokePraiser");
		}			
	}
	
	public void closeResources(){
		if(dbHelper != null){
			dbHelper.close();
		}
	}
	
	public boolean isDbOpen(){
		if(dbHelper == null){
			return false;
		}else{
			if(dbHelper.getReadableDatabase().isOpen()){
				return true;
			}else{
				return false;
			}
		}
	}
	
	public DatabaseHelper getDatabaseReference(){
		//If this works, huzzah for strange practices...
		if(dbHelper == null){
			dbHelper = new DatabaseHelper(this);			
		}
		
		return dbHelper;
	}
	
	public void applyTypeface(TextView someView){
		loadTypeface();
		someView.setTypeface(datFont);
	}

	public void applyTypeface(TextView [] someViews){
		loadTypeface();		
		for(TextView someView : someViews)
			someView.setTypeface(datFont);
	}	

	private void loadTypeface() {
		if(datFont == null)
			datFont = Typeface.createFromAsset(getAssets(), "pokemon_gb_typeface.ttf");		
	}	
}
