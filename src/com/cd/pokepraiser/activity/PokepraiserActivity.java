package com.cd.pokepraiser.activity;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;

public class PokepraiserActivity extends SherlockActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);   
    }

    //protected void onStart();
    
    protected void onDestroy(){
    	((PokepraiserApplication)getApplication()).closeResources();
    	super.onDestroy();    	
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main, menu);
        
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        
        switch (item.getItemId()) {
            case R.id.search_pokemon:
                i = new Intent(PokepraiserActivity.this, PokemonListActivity.class);
                startActivity(i);       
                return true;
            case R.id.search_attacks:
                i = new Intent(PokepraiserActivity.this, AttacksListActivity.class);
                startActivity(i);
                return true;
            case R.id.search_abilities:
                i = new Intent(PokepraiserActivity.this, AbilitiesListActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }    
}
