package com.cd.pokepraiser.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.ItemDetail;
import com.cd.pokepraiser.db.dao.ItemsDataSource;
import com.cd.pokepraiser.util.ExtrasConstants;

public class ItemDetailFragment extends SherlockFragment {

	public static final String TAG				= "itemDetail";
	
	private ItemsDataSource mItemsDataSource;

	private ItemDetail mItemDetail;
	
	private ViewGroup mParentView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(savedInstanceState == null){
        	savedInstanceState = getArguments();
            final int itemId			= savedInstanceState.getInt(ExtrasConstants.ITEM_ID);        	
            mItemsDataSource = new ItemsDataSource(((PokepraiserApplication)getActivity().getApplication()).getPokedbDatabaseReference());	        
            
	        mItemsDataSource.open();
	        mItemDetail
	        	= mItemsDataSource.getItemDetail(itemId);
	        mItemsDataSource.close();        
        }else{
        	mItemDetail = (ItemDetail) savedInstanceState.getSerializable(ExtrasConstants.ITEM_ID);
        }
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		mParentView = (ViewGroup)inflater.inflate(R.layout.item_detail_screen, container, false);		
		
        TextView itemName 		= (TextView) mParentView.findViewById(R.id.itemName);
        TextView effect			= (TextView) mParentView.findViewById(R.id.effect);
        
        itemName.setText(mItemDetail.getName());
        effect.setText("\t" + mItemDetail.getDesc());
        
        //Apply detail to pokemon learning ability

        //Apply typeface
        ((PokepraiserApplication)getActivity().getApplication()).overrideFonts(mParentView);		
		
		return mParentView;
	}
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putSerializable(ExtrasConstants.ITEM_ID, mItemDetail);
    	
    	super.onSaveInstanceState(savedInstanceState);
    }    

}
