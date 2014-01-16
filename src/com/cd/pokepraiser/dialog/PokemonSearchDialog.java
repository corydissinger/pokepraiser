package com.cd.pokepraiser.dialog;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.cd.pokepraiser.R;
import com.cd.pokepraiser.adapter.PokemonNameArrayAdapter;
import com.cd.pokepraiser.data.PokemonInfo;

@SuppressLint("ValidFragment")
public class PokemonSearchDialog extends DialogFragment {

	public interface PokemonSearchDialogListener {
		public void onPokemonItemClick(PokemonSearchDialog dialog);
	}	
	
	PokemonSearchDialogListener mListener;
	
	private PokemonNameArrayAdapter adapter;	
	private List<PokemonInfo> mPokemon;
	private int selectedItem  = 0;
	
	public PokemonSearchDialog(){}
	
	public PokemonSearchDialog(List<PokemonInfo> allPokemon){
		mPokemon = allPokemon;
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
        	mListener = (PokemonSearchDialogListener) getTargetFragment();
        } catch(ClassCastException ce){
        	throw new ClassCastException(getTargetFragment().toString() + " must implement PokemonSearchDialogListener");
        }		
		
		final Activity theActivity = getActivity();
		
		View pokemonListView 		= theActivity.getLayoutInflater().inflate(R.layout.pokemon_list_screen, null);
		ListView attackList			= (ListView) pokemonListView.findViewById(R.id.pokemonList);
		EditText attackSearch		= (EditText) pokemonListView.findViewById(R.id.searchPokemon);
		
        adapter = new PokemonNameArrayAdapter(theActivity, android.R.layout.simple_list_item_2, mPokemon);
        
		attackList.setAdapter(adapter);

		attackList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				selectedItem = position;
				mListener.onPokemonItemClick(PokemonSearchDialog.this);
			}
			
		});
		
		attackSearch.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            	PokemonSearchDialog.this.adapter.getFilter().filter(cs);   
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });		
		
        AlertDialog.Builder builder = new AlertDialog.Builder(theActivity);
        builder.setTitle(R.string.all_pokemon)
    		.setView(pokemonListView)
    		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dismiss();
				}
			});
        
        return builder.create();
	}

	@Override
	 public void onDestroyView() {
	     if (getDialog() != null && getRetainInstance())
	         getDialog().setDismissMessage(null);
	     
        super.onDestroyView();
	 }	
	
	public int getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(int selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	@Override
	public void dismiss(){
		adapter.getFilter().filter("");
		super.dismiss();
	}
	
	@Override
	public void onCancel(DialogInterface dialog){
		adapter.getFilter().filter("");		
		super.onCancel(dialog);
	}	
}
