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
import com.cd.pokepraiser.adapter.PokemonInfoArrayAdapter;
import com.cd.pokepraiser.data.PokemonInfo;
import com.cd.pokepraiser.db.dao.PokemonDataSource;
import com.cd.pokepraiser.util.ExtrasConstants;

public class PokemonListActivity extends PokepraiserActivity {

	private PokemonDataSource pokemonDataSource;
	
	private PokemonInfoArrayAdapter adapter;
	
	private EditText pokemonSearch;
	
	private ArrayList<PokemonInfo> thePokemon;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_list_screen);
        
        pokemonDataSource = new PokemonDataSource(((PokepraiserApplication)getApplication()).getDatabaseReference());
        
        pokemonDataSource.open();
        thePokemon = pokemonDataSource.getPokemonList(getResources());
        pokemonDataSource.close();
        
        ListView pokemonListContent = (ListView)findViewById(R.id.pokemonList);
        pokemonSearch = (EditText)findViewById(R.id.searchPokemon);
        
        adapter = new PokemonInfoArrayAdapter(this, android.R.layout.simple_list_item_1, thePokemon);
        pokemonListContent.setAdapter(adapter);
        
        pokemonListContent.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View clickedView, int position, long arg3) {
				final PokemonInfo pokemonItem = (PokemonInfo) parent.getItemAtPosition(position);
				
	        	Intent i = new Intent(PokemonListActivity.this, PokemonDetailActivity.class);
        		i.putExtra(ExtrasConstants.POKEMON_ID, pokemonItem.getPokemonId());
				startActivity(i);
			}
        });
        
        pokemonSearch.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                PokemonListActivity.this.adapter.getFilter().filter(cs);   
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });        
    }	
    
}
