package com.cd.pokepraiser.activity;

import com.cd.pokepraiser.PokepraiserApplication;

import android.app.Activity;
import android.os.Bundle;

public class PokepraiserActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);    	
    }

    //protected void onStart();
    
    //protected void onRestart();

    //protected void onResume();

    //protected void onPause();

    //protected void onStop();

    protected void onDestroy(){
    	((PokepraiserApplication)getApplication()).closeResources();
    	super.onDestroy();    	
    }
}
