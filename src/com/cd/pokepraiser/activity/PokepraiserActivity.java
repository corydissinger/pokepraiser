package com.cd.pokepraiser.activity;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;

public class PokepraiserActivity extends SherlockActivity {
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
    
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main, menu);
        
        return super.onCreateOptionsMenu(menu);
    }
}
