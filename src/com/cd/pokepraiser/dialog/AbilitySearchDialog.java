package com.cd.pokepraiser.dialog;

import java.util.ArrayList;
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
import com.cd.pokepraiser.adapter.AbilityInfoArrayAdapter;
import com.cd.pokepraiser.data.AbilityInfo;

@SuppressLint("ValidFragment")
public class AbilitySearchDialog extends DialogFragment {

	public interface AbilitySearchDialogListener {
		public void onAbilityItemClick(AbilitySearchDialog dialog);
	}	
	
	AbilitySearchDialogListener mListener;
	
	private AbilityInfoArrayAdapter adapter;	
	private List<AbilityInfo> mAbilities;
	private AbilityInfo selectedItem;
	
	public AbilitySearchDialog(){}
	
	public AbilitySearchDialog(List<AbilityInfo> allAbilities){
		mAbilities = allAbilities;
	}
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        try {
        	mListener = (AbilitySearchDialogListener) activity;
        } catch(ClassCastException ce){
        	throw new ClassCastException(activity.toString() + " must implement AbilitySearchDialogListener");
        }
    }
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Activity theActivity = getActivity();
		
		View abilityListView 		= theActivity.getLayoutInflater().inflate(R.layout.abilities_list_screen, null);
		ListView abilityList		= (ListView) abilityListView.findViewById(R.id.abilitiesList);
		EditText abilitySearch		= (EditText) abilityListView.findViewById(R.id.searchAbilities);
		
        adapter = new AbilityInfoArrayAdapter(theActivity, android.R.layout.simple_list_item_1, mAbilities);
        
		abilityList.setAdapter(adapter);

		abilityList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				selectedItem = adapter.getFilteredAbilities().get(position);
				mListener.onAbilityItemClick(AbilitySearchDialog.this);
			}
			
		});
		
		abilitySearch.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            	AbilitySearchDialog.this.adapter.getFilter().filter(cs);   
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });		
		
        AlertDialog.Builder builder = new AlertDialog.Builder(theActivity);
        builder.setTitle(R.string.all_abilities)
    		.setView(abilityListView)
    		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dismiss();
				}
			});
        
        return builder.create();
	}

	public AbilityInfo getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(AbilityInfo selectedItem) {
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
