package com.cd.pokepraiser.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.adapter.AbilityInfoArrayAdapter;
import com.cd.pokepraiser.data.AbilityInfo;
import com.cd.pokepraiser.db.abilities.AbilitiesDataSource;
import com.cd.pokepraiser.util.ExtrasConstants;

public class AbilitiesListActivity extends PokepraiserActivity {

	private AbilitiesDataSource abilitiesDataSource;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abilitieslist_screen);
        
        abilitiesDataSource = new AbilitiesDataSource(((PokepraiserApplication)getApplication()).getDatabaseReference());
        
        abilitiesDataSource.open();
        AbilityInfo [] theAbilities = abilitiesDataSource.getAbilityList();
        abilitiesDataSource.close();
        
        ListView abilitiesListContent = (ListView)findViewById(R.id.abilitiesList);
        
        AbilityInfoArrayAdapter adapter = new AbilityInfoArrayAdapter(this, android.R.layout.simple_list_item_1, theAbilities);
        abilitiesListContent.setAdapter(adapter);
        
        abilitiesListContent.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View clickedView, int position, long arg3) {
				final AbilityInfo abilitykItem = (AbilityInfo) parent.getItemAtPosition(position);
				
	        	Intent i = new Intent(AbilitiesListActivity.this, AbilityDetailActivity.class);
        		i.putExtra(ExtrasConstants.ABILITY_ID, abilitykItem.getAbilityDbId());
				startActivity(i);
			}
        });
    }	
	
}
