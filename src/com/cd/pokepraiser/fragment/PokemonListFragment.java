package com.cd.pokepraiser.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.cd.pokepraiser.PokepraiserActivity;
import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.adapter.PokemonInfoArrayAdapter;
import com.cd.pokepraiser.data.PokemonInfo;
import com.cd.pokepraiser.db.dao.PokemonDataSource;
import com.cd.pokepraiser.util.ExtrasConstants;

public class PokemonListFragment extends SherlockFragment {

	public static final String TAG = "pokeList";
	
	private PokemonDataSource mPokemonDataSource;
	private ArrayList<PokemonInfo> mPokemon;	
	private PokemonInfoArrayAdapter mPokemonListAdapter;

	private LinearLayout mParentView;	
	private EditText mPokemonSearch;
	
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(savedInstanceState == null){
        	savedInstanceState = getArguments();
        	
        	if(savedInstanceState == null){
	            mPokemonDataSource = new PokemonDataSource(((PokepraiserApplication)getActivity().getApplication()).getPokedbDatabaseReference());
	            
	            mPokemonDataSource.open();
	            mPokemon = mPokemonDataSource.getPokemonList(getResources());
	            mPokemonDataSource.close();        	
        	}else{
        		mPokemon = (ArrayList<PokemonInfo>) savedInstanceState.getSerializable(ExtrasConstants.POKEMON_SEARCH);        		
        	}
        }else{
        	mPokemon = (ArrayList<PokemonInfo>) savedInstanceState.getSerializable(ExtrasConstants.POKEMON_ID);
        }
    }	
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		mParentView = (LinearLayout)inflater.inflate(R.layout.pokemon_list_screen, container, false);
	    
        ListView pokemonListContent = (ListView) mParentView.findViewById(R.id.pokemonList);
        mPokemonSearch = (EditText) mParentView.findViewById(R.id.searchPokemon);
        
        mPokemonListAdapter = new PokemonInfoArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mPokemon);
        pokemonListContent.setAdapter(mPokemonListAdapter);
        
        pokemonListContent.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View clickedView, int position, long arg3) {
				final PokemonInfo pokemonItem = (PokemonInfo) parent.getItemAtPosition(position);
				
				PokemonDetailFragment newFrag = new PokemonDetailFragment();
				Bundle args = new Bundle();
				
				args.putInt(ExtrasConstants.POKEMON_ID, pokemonItem.getId());
				newFrag.setArguments(args);
				
				((PokepraiserActivity)getActivity()).changeFragment(newFrag, newFrag.TAG);
			}
        });
        
        mPokemonSearch.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                mPokemonListAdapter.getFilter().filter(cs);   
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
        
        return mParentView;
	}

    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putSerializable(ExtrasConstants.POKEMON_ID, mPokemon);
    	
    	super.onSaveInstanceState(savedInstanceState);
    }    
    
}
