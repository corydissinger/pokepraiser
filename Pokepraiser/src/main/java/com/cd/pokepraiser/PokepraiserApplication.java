package com.cd.pokepraiser;

import java.io.IOException;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
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

    private Boolean mIsDefaultFontPreferred = null;

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
        if(isDefaultFontPreferred()){
            return;
        }

		loadTypeface();
		someView.setTypeface(datFont);
	}

	public void applyTypeface(TextView [] someViews){
        if(isDefaultFontPreferred()){
            return;
        }

		loadTypeface();		
		for(TextView someView : someViews)
			someView.setTypeface(datFont);
	}	

	public void overrideFonts(final View v){
        if(isDefaultFontPreferred()){
            return;
        }

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

    private boolean isDefaultFontPreferred(){
        if(mIsDefaultFontPreferred != null){
            return mIsDefaultFontPreferred;
        }

        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);

        if(prefs.getBoolean(getString(R.string.preference_font), false)){
            mIsDefaultFontPreferred = true;
        }else{
            mIsDefaultFontPreferred = false;
        }

        return mIsDefaultFontPreferred;
    }

    public void resetCachedPrefs() {
        mIsDefaultFontPreferred = null;
    }
}
