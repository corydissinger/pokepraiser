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
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.cd.pokepraiser.PokepraiserActivity;
import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.adapter.ItemInfoArrayAdapter;
import com.cd.pokepraiser.data.ItemInfo;
import com.cd.pokepraiser.db.dao.ItemsDataSource;
import com.cd.pokepraiser.util.ExtrasConstants;

public class ItemsListFragment extends SherlockFragment {

	public static final String TAG = "itemsList";
	
	private ItemsDataSource mItemsDataSource;
	
	private ItemInfoArrayAdapter mAdapter;

	private ViewGroup mParentView;	
	private EditText mItemSearch;
	
	private ArrayList<ItemInfo> mItems;	
	
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(savedInstanceState == null){
	        mItemsDataSource = new ItemsDataSource(((PokepraiserApplication)getActivity().getApplication()).getPokedbDatabaseReference());
	        
	        mItemsDataSource.open();
	        mItems = mItemsDataSource.getAllItemInfo();
	        mItemsDataSource.close();
        }else{
        	mItems = (ArrayList<ItemInfo>) savedInstanceState.getSerializable(ExtrasConstants.ITEM_ID);
        }
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		mParentView = (ViewGroup)inflater.inflate(R.layout.abilities_list_screen, container, false);
	
		ListView itemsListContent = (ListView)mParentView.findViewById(R.id.abilitiesList);
        
        mAdapter = new ItemInfoArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mItems);
        itemsListContent.setAdapter(mAdapter);
        
        itemsListContent.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View clickedView, int position, long arg3) {
				final ItemInfo item = (ItemInfo) parent.getItemAtPosition(position);
				
				ItemDetailFragment newFrag = new ItemDetailFragment();
				Bundle args = new Bundle();
				
				args.putInt(ExtrasConstants.ITEM_ID, item.getId());
				newFrag.setArguments(args);
				
				((PokepraiserActivity)getActivity()).setIsListOrigin(true);
				((PokepraiserActivity)getActivity()).changeFragment(newFrag, ItemDetailFragment.TAG);
			}
        });

        //Code to setup searcher
        mItemSearch = (EditText)mParentView.findViewById(R.id.searchAbilities);
        mItemSearch.setHint(R.string.search_items);
        
        mItemSearch.addTextChangedListener(new TextWatcher() {
            
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
    	savedInstanceState.putSerializable(ExtrasConstants.ITEM_ID, mItems);
    	
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
