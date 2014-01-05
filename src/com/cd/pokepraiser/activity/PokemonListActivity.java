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
	
	private EditText mPokemonSearch;
	
	private ArrayList<PokemonInfo> mPokemon;
	
    @SuppressWarnings("unchecked")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_list_screen);
        
        final Intent receivedIntent = getIntent();
        mPokemon = (ArrayList<PokemonInfo>) receivedIntent.getSerializableExtra(ExtrasConstants.POKEMON_SEARCH);        
        
        if(mPokemon == null){
            if(savedInstanceState == null){
                pokemonDataSource = new PokemonDataSource(((PokepraiserApplication)getApplication()).getPokedbDatabaseReference());
                
                pokemonDataSource.open();
                mPokemon = pokemonDataSource.getPokemonList(getResources());
                pokemonDataSource.close();        	
            }else{
            	mPokemon = (ArrayList<PokemonInfo>) savedInstanceState.getSerializable(ExtrasConstants.POKEMON_ID);
            }        	
        }
        
        ListView pokemonListContent = (ListView)findViewById(R.id.pokemonList);
        mPokemonSearch = (EditText)findViewById(R.id.searchPokemon);
        
        adapter = new PokemonInfoArrayAdapter(this, android.R.layout.simple_list_item_1, mPokemon);
        pokemonListContent.setAdapter(adapter);
        
        pokemonListContent.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View clickedView, int position, long arg3) {
				final PokemonInfo pokemonItem = (PokemonInfo) parent.getItemAtPosition(position);
				
	        	Intent i = new Intent(PokemonListActivity.this, PokemonDetailActivity.class);
        		i.putExtra(ExtrasConstants.POKEMON_ID, pokemonItem.getId());
				startActivity(i);
			}
        });
        
        mPokemonSearch.addTextChangedListener(new TextWatcher() {
            
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
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putSerializable(ExtrasConstants.POKEMON_ID, mPokemon);
    	
    	super.onSaveInstanceState(savedInstanceState);
    }    
    
}
