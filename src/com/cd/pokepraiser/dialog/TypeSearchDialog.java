package com.cd.pokepraiser.dialog;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cd.pokepraiser.R;
import com.cd.pokepraiser.adapter.TypeInfoArrayAdapter;
import com.cd.pokepraiser.data.TypeInfo;

@SuppressLint("ValidFragment")
public class TypeSearchDialog extends DialogFragment {

	public interface TypeSearchDialogListener {
		public void onTypeItemClick(TypeSearchDialog dialog);
	}	
	
	TypeSearchDialogListener mListener;

	private TypeInfoArrayAdapter adapter;	
	private List<TypeInfo> mTypes;
	
	private int selectedItem  	= 0;
	private int originButton	= 0;
	
	public TypeSearchDialog(){}
	
	public TypeSearchDialog(List<TypeInfo> allTypes){
		mTypes = allTypes;
	}
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        try {
        	mListener = (TypeSearchDialogListener) activity;
        } catch(ClassCastException ce){
        	throw new ClassCastException(activity.toString() + " must implement TypeSearchDialogListener");
        }
    }
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Activity theActivity = getActivity();
		
		View typesListView 		= theActivity.getLayoutInflater().inflate(R.layout.types_list_screen, null);		
		ListView typesList		= (ListView) typesListView.findViewById(R.id.typesList);
		
        adapter = new TypeInfoArrayAdapter(theActivity, android.R.layout.simple_list_item_1, mTypes);		
		
        typesList.setAdapter(adapter);
        
        typesList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				selectedItem = position;
				mListener.onTypeItemClick(TypeSearchDialog.this);
			}        	
		});
        
        AlertDialog.Builder builder = new AlertDialog.Builder(theActivity);
        builder.setTitle(R.string.all_types)
    		.setView(typesListView)
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
