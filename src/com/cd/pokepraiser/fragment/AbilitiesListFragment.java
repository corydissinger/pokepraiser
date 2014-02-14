package com.cd.pokepraiser.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.cd.pokepraiser.PokepraiserActivity;
import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.adapter.AbilityInfoArrayAdapter;
import com.cd.pokepraiser.data.AbilityInfo;
import com.cd.pokepraiser.db.dao.AbilitiesDataSource;
import com.cd.pokepraiser.dialog.AbilitySearchDialog;
import com.cd.pokepraiser.dialog.AbilitySearchDialog.AbilitySearchDialogListener;
import com.cd.pokepraiser.util.ExtrasConstants;

public class AbilitiesListFragment extends SherlockFragment {

	public static final String TAG = "abilitiesList";
	
	private AbilitiesDataSource mAbilitiesDataSource;
	
	private AbilityInfoArrayAdapter mAdapter;

	private ViewGroup mParentView;	
	private EditText mAbilitySearch;
	
	private ArrayList<AbilityInfo> mAbilities;	
	
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(savedInstanceState == null){
	        mAbilitiesDataSource = new AbilitiesDataSource(((PokepraiserApplication)getActivity().getApplication()).getPokedbDatabaseReference());
	        
	        mAbilitiesDataSource.open();
	        mAbilities = mAbilitiesDataSource.getAbilityList();
	        mAbilitiesDataSource.close();
        }else{
        	mAbilities = (ArrayList<AbilityInfo>) savedInstanceState.getSerializable(ExtrasConstants.ABILITY_ID);
        }
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		mParentView = (ViewGroup)inflater.inflate(R.layout.abilities_list_screen, container, false);
	
		ListView abilitiesListContent = (ListView)mParentView.findViewById(R.id.abilitiesList);
        
        mAdapter = new AbilityInfoArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mAbilities);
        abilitiesListContent.setAdapter(mAdapter);
        
        abilitiesListContent.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View clickedView, int position, long arg3) {
				final AbilityInfo abilityItem = (AbilityInfo) parent.getItemAtPosition(position);
				
				AbilityDetailFragment newFrag = new AbilityDetailFragment();
				Bundle args = new Bundle();
				
				args.putInt(ExtrasConstants.ABILITY_ID, abilityItem.getAbilityDbId());
				newFrag.setArguments(args);
				
				((PokepraiserActivity)getActivity()).setIsListOrigin(true);
				((PokepraiserActivity)getActivity()).changeFragment(newFrag, newFrag.TAG);
			}
        });

        //Code to setup searcher
        mAbilitySearch = (EditText)mParentView.findViewById(R.id.searchAbilities);
        
        mAbilitySearch.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                mAdapter.getFilter().filter(cs);   
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });		
		
		return mParentView;
	}

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putSerializable(ExtrasConstants.ABILITY_ID, mAbilities);
    	
    	super.onSaveInstanceState(savedInstanceState);
    }
    
    @Override
    public void onStop(){
		mAdapter.getFilter().filter("");    	
    	super.onStop();
    }    
    
    @Override
    public void onDestroyView(){
		mAdapter.getFilter().filter("");    	
    	super.onDestroyView();
    }    
}
