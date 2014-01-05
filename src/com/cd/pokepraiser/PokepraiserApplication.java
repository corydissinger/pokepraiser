package com.cd.pokepraiser;

import java.io.IOException;

import android.app.Application;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cd.pokepraiser.db.PokeDbHelper;
import com.cd.pokepraiser.db.TeamDbHelper;

public class PokepraiserApplication extends Application {

	private PokeDbHelper pokedbHelper;
	private TeamDbHelper teamdbHelper;
	
	private Typeface datFont = null;
	
	public void loadResources(){
		if(datFont == null)
			datFont = Typeface.createFromAsset(getAssets(), "pokemon_gb_typeface.ttf");
		
		pokedbHelper = new PokeDbHelper(this);			
		teamdbHelper = new TeamDbHelper(this);
		
		try {
			pokedbHelper.initializeDataBase();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error("Unable to create database for PokePraiser");
		}			
	}
	
	public void closeResources(){
		if(pokedbHelper != null){
			pokedbHelper.close();
		}
		
		if(teamdbHelper != null){
			teamdbHelper.close();
		}
	}
	
	public PokeDbHelper getPokedbDatabaseReference(){
		//If this works, huzzah for strange practices...
		if(pokedbHelper == null){
			pokedbHelper = new PokeDbHelper(this);			
		}
		
		return pokedbHelper;
	}
	
	public TeamDbHelper getTeamdbDatabaseReference(){
		//If this works, huzzah for strange practices...
		if(teamdbHelper == null){
			teamdbHelper = new TeamDbHelper(this);			
		}
		
		return teamdbHelper;
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

	public void overrideFonts(final View v){
		loadTypeface();
		
		try{
			if(v instanceof ViewGroup){
				
				ViewGroup vg = (ViewGroup) v;
				
				for(int i = 0; i < vg.getChildCount(); i++){
					View child = vg.getChildAt(i);
					overrideFonts(child);
				}
				
			}else if(v instanceof TextView) {
				((TextView) v).setTypeface(datFont);
			}
		}catch(Exception e){
			Log.w("Font Error", e.getMessage());
		}
	}
	
	private void loadTypeface() {
		if(datFont == null)
			datFont = Typeface.createFromAsset(getAssets(), "pokemon_gb_typeface.ttf");		
	}	
}
