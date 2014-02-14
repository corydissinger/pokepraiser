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
import com.cd.pokepraiser.adapter.NatureInfoArrayAdapter;
import com.cd.pokepraiser.data.NatureInfo;

@SuppressLint("ValidFragment")
public class NatureSearchDialog extends DialogFragment {

	public interface NatureSearchDialogListener {
		public void onNatureItemClick(NatureSearchDialog dialog);
	}	
	
	NatureSearchDialogListener mListener;
	
	private NatureInfoArrayAdapter adapter;	
	private List<NatureInfo> mNature;
	private NatureInfo mSelectedItem;
	
	public NatureSearchDialog(){}
	
	public NatureSearchDialog(List<NatureInfo> allNature){
		mNature = allNature;
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
        	mListener = (NatureSearchDialogListener) getTargetFragment();
        } catch(ClassCastException ce){
        	throw new ClassCastException(getTargetFragment().toString() + " must implement NatureSearchDialogListener");
        }		
		
		final Activity theActivity = getActivity();
		
		View natureListView 		= theActivity.getLayoutInflater().inflate(R.layout.abilities_list_screen, null);
		ListView natureList			= (ListView) natureListView.findViewById(R.id.abilitiesList);
		EditText natureSearch		= (EditText) natureListView.findViewById(R.id.searchAbilities);
		
        adapter = new NatureInfoArrayAdapter(theActivity, android.R.layout.simple_list_item_2, mNature);
        
		natureList.setAdapter(adapter);

		natureList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				mSelectedItem = mNature.get(position);
				mListener.onNatureItemClick(NatureSearchDialog.this);
			}
			
		});
		
		natureSearch.setHint(R.string.search_natures);
		natureSearch.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            	NatureSearchDialog.this.adapter.getFilter().filter(cs);   
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });		
		
        AlertDialog.Builder builder = new AlertDialog.Builder(theActivity);
        builder.setTitle(R.string.all_natures)
    		.setView(natureListView)
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
	
	public NatureInfo getSelectedItem() {
		return mSelectedItem;
	}

	public void setSelectedItem(NatureInfo selectedItem) {
		this.mSelectedItem = selectedItem;
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
