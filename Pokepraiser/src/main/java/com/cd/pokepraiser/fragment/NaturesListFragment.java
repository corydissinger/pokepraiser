package com.cd.pokepraiser.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.adapter.NatureInfoArrayAdapter;
import com.cd.pokepraiser.data.NatureInfo;
import com.cd.pokepraiser.db.dao.PokemonDataSource;
import com.cd.pokepraiser.util.ExtrasConstants;

public class NaturesListFragment extends SherlockFragment {

	public static final String TAG = "naturesList";
	
	private PokemonDataSource mPokemonDataSource;
	
	private NatureInfoArrayAdapter mAdapter;

	private ViewGroup mParentView;	
	private EditText mNaturesearch;
	
	private ArrayList<NatureInfo> mNatures;	
	
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(savedInstanceState == null){
        	mPokemonDataSource = new PokemonDataSource(((PokepraiserApplication)getActivity().getApplication()).getPokedbDatabaseReference());
	        
        	mPokemonDataSource.open();
	        mNatures = mPokemonDataSource.getAllNatureInfo();
	        mPokemonDataSource.close();
        }else{
        	mNatures = (ArrayList<NatureInfo>) savedInstanceState.getSerializable(ExtrasConstants.NATURES);
        }
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		mParentView = (ViewGroup)inflater.inflate(R.layout.abilities_list_screen, container, false);
	
		ListView naturesListContent = (ListView)mParentView.findViewById(R.id.abilitiesList);
        
        mAdapter = new NatureInfoArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mNatures);
        naturesListContent.setAdapter(mAdapter);

        //Code to setup searcher
        mNaturesearch = (EditText)mParentView.findViewById(R.id.searchAbilities);
        mNaturesearch.setHint(R.string.search_natures);
        
        mNaturesearch.addTextChangedListener(new TextWatcher() {
            
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
    	savedInstanceState.putSerializable(ExtrasConstants.NATURES, mNatures);
    	
    	super.onSaveInstanceState(savedInstanceState);
    }	
}
