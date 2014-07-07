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
import com.cd.pokepraiser.adapter.ItemInfoArrayAdapter;
import com.cd.pokepraiser.data.ItemInfo;

@SuppressLint("ValidFragment")
public class ItemSearchDialog extends DialogFragment {

	public interface ItemSearchDialogListener {
		public void onItemItemClick(ItemSearchDialog dialog);
	}	
	
	ItemSearchDialogListener mListener;
	
	private ItemInfoArrayAdapter adapter;	
	private List<ItemInfo> mItem;
	private ItemInfo selectedItem;
	
	public ItemSearchDialog(){}
	
	public ItemSearchDialog(List<ItemInfo> allItem){
		mItem = allItem;
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
        	mListener = (ItemSearchDialogListener) getTargetFragment();
        } catch(ClassCastException ce){
        	throw new ClassCastException(getTargetFragment().toString() + " must implement ItemSearchDialogListener");
        }		
		
		final Activity theActivity = getActivity();
		
		View itemListView 		= theActivity.getLayoutInflater().inflate(R.layout.abilities_list_screen, null);
		ListView itemList			= (ListView) itemListView.findViewById(R.id.abilitiesList);
		EditText itemSearch		= (EditText) itemListView.findViewById(R.id.searchAbilities);
		
        adapter = new ItemInfoArrayAdapter(theActivity, android.R.layout.simple_list_item_2, mItem);
        
		itemList.setAdapter(adapter);

		itemList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				selectedItem = mItem.get(position);
				mListener.onItemItemClick(ItemSearchDialog.this);
			}
			
		});
		
		itemSearch.setHint(R.string.search_items);
		itemSearch.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            	ItemSearchDialog.this.adapter.getFilter().filter(cs);   
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });		
		
        AlertDialog.Builder builder = new AlertDialog.Builder(theActivity);
        builder.setTitle(R.string.all_items)
    		.setView(itemListView)
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
	
	public ItemInfo getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(ItemInfo selectedItem) {
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
