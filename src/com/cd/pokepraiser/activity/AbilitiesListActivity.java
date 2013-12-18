package com.cd.pokepraiser.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.adapter.AbilityInfoArrayAdapter;
import com.cd.pokepraiser.data.AbilityInfo;
import com.cd.pokepraiser.db.dao.AbilitiesDataSource;
import com.cd.pokepraiser.dialog.AbilitySearchDialog;
import com.cd.pokepraiser.dialog.AbilitySearchDialog.AbilitySearchDialogListener;
import com.cd.pokepraiser.util.ExtrasConstants;

public class AbilitiesListActivity extends PokepraiserActivity implements AbilitySearchDialogListener {

	private AbilitiesDataSource abilitiesDataSource;
	
	private AbilityInfoArrayAdapter adapter;
	
	private EditText abilitySearch;
	
	private ArrayList<AbilityInfo> theAbilities;	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abilities_list_screen);
        
        abilitiesDataSource = new AbilitiesDataSource(((PokepraiserApplication)getApplication()).getDatabaseReference());
        
        abilitiesDataSource.open();
        theAbilities = abilitiesDataSource.getAbilityList();
        abilitiesDataSource.close();
        
        ListView abilitiesListContent = (ListView)findViewById(R.id.abilitiesList);
        
        adapter = new AbilityInfoArrayAdapter(this, android.R.layout.simple_list_item_1, theAbilities);
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

        //Code to setup searcher
        abilitySearch = (EditText)findViewById(R.id.searchAbilities);
        
        abilitySearch.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                AbilitiesListActivity.this.adapter.getFilter().filter(cs);   
                
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });         
        
        
    }

	@Override
	public void onAbilityItemClick(AbilitySearchDialog dialog) {
		
	}	
    
}
