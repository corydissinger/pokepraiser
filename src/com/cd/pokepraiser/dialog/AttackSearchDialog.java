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
import com.cd.pokepraiser.adapter.AttackInfoArrayAdapter;
import com.cd.pokepraiser.data.AttackInfo;

@SuppressLint("ValidFragment")
public class AttackSearchDialog extends DialogFragment {

	public interface AttackSearchDialogListener {
		public void onAttackItemClick(AttackSearchDialog dialog);
	}	
	
	AttackSearchDialogListener mListener;
	
	private AttackInfoArrayAdapter adapter;	
	private List<AttackInfo> mAttacks;
	private int selectedItem  = 0;
	private int originButton = 0;
	
	public AttackSearchDialog(){}
	
	public AttackSearchDialog(List<AttackInfo> allAttacks){
		mAttacks = allAttacks;
	}
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        try {
        	mListener = (AttackSearchDialogListener) activity;
        } catch(ClassCastException ce){
        	throw new ClassCastException(activity.toString() + " must implement AttackSearchDialogListener");
        }
    }
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Activity theActivity = getActivity();
		
		View attackListView 		= theActivity.getLayoutInflater().inflate(R.layout.attacks_list_screen, null);
		ListView attackList		= (ListView) attackListView.findViewById(R.id.attacksList);
		EditText attackSearch		= (EditText) attackListView.findViewById(R.id.searchAttacks);
		
        adapter = new AttackInfoArrayAdapter(theActivity, android.R.layout.simple_list_item_1, mAttacks);
        
		attackList.setAdapter(adapter);

		attackList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				selectedItem = position;
				mListener.onAttackItemClick(AttackSearchDialog.this);
			}
			
		});
		
		attackSearch.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            	AttackSearchDialog.this.adapter.getFilter().filter(cs);   
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });		
		
        AlertDialog.Builder builder = new AlertDialog.Builder(theActivity);
        builder.setTitle(R.string.all_attacks)
    		.setView(attackListView)
    		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dismiss();
				}
			});
        
        return builder.create();
	}

	public int getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(int selectedItem) {
		this.selectedItem = selectedItem;
	}

	public int getOriginButton() {
		return originButton;
	}

	public void setOriginButton(int originButton) {
		this.originButton = originButton;
	}
}
