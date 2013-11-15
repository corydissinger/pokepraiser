package com.cd.pokepraiser.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cd.pokepraiser.R;

public class MainMenuScreenActiviy extends PokepraiserActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu_screen);
    }	
	
    public void openAttacksList(View v){
        Intent i = new Intent(MainMenuScreenActiviy.this, AttacksListActivity.class);
        startActivity(i);
    }
    
    public void openAbilitiesList(View v){
        Intent i = new Intent(MainMenuScreenActiviy.this, AbilitiesListActivity.class);
        startActivity(i);
    }    
}
