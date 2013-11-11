package com.cd.pokepraiser.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;

public class LoadingScreenActivity extends PokepraiserActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        
        new PrefetchData().execute();
    }

    /**
     * Async Task to make http call
     */
    private class PrefetchData extends AsyncTask<Void, Void, Void> {
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before making http calls         
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
        	((PokepraiserApplication)getApplication()).loadResources();
        	
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // After completing http call
            // will close this activity and lauch main activity
            Intent i = new Intent(LoadingScreenActivity.this, MainMenuScreenActiviy.class);
            startActivity(i);
            finish();
        }
 
    }    
}
