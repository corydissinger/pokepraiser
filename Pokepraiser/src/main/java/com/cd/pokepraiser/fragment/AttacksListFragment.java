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
import com.cd.pokepraiser.adapter.AttackInfoArrayAdapter;
import com.cd.pokepraiser.data.AttackInfo;
import com.cd.pokepraiser.db.dao.AttacksDataSource;
import com.cd.pokepraiser.util.ExtrasConstants;

public class AttacksListFragment extends SherlockFragment {

	public static final String TAG = "attacksList";
	
	private AttacksDataSource mAttacksDataSource;
	
	private AttackInfoArrayAdapter mAdapter;
	
	private ViewGroup mParentView;
	private EditText mAttackSearch;
	
	private ArrayList<AttackInfo> mAttacks;	
	
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(savedInstanceState == null){
	        mAttacksDataSource = new AttacksDataSource(((PokepraiserApplication)getActivity().getApplication()).getPokedbDatabaseReference());
	        
	        mAttacksDataSource.open();
	        mAttacks = mAttacksDataSource.getAttackInfoList();
	        mAttacksDataSource.close();
        }else{
        	mAttacks = (ArrayList<AttackInfo>) savedInstanceState.getSerializable(ExtrasConstants.ATTACK_ID);
        }
    }	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		mParentView = (ViewGroup)inflater.inflate(R.layout.attacks_list_screen, container, false);

        ListView attacksListContent = (ListView)mParentView.findViewById(R.id.attacksList);
        
        mAdapter = new AttackInfoArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mAttacks);
        attacksListContent.setAdapter(mAdapter);
        
        attacksListContent.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View clickedView, int position, long arg3) {
				final AttackInfo attackItem = (AttackInfo) parent.getItemAtPosition(position);
				
				AttackDetailFragment newFrag = new AttackDetailFragment();
				Bundle args = new Bundle();
				
				args.putInt(ExtrasConstants.ATTACK_ID, attackItem.getAttackDbId());
				newFrag.setArguments(args);
				
				((PokepraiserActivity)getActivity()).setIsListOrigin(true);
				((PokepraiserActivity)getActivity()).changeFragment(newFrag, newFrag.TAG);				
			}
        });
        
        mAttackSearch = (EditText)mParentView.findViewById(R.id.searchAttacks);
        
        mAttackSearch.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                mAdapter.getFilter().filter(cs);   
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
    	savedInstanceState.putSerializable(ExtrasConstants.ATTACK_ID, mAttacks);
    	
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
