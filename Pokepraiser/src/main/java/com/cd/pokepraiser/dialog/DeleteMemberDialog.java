package com.cd.pokepraiser.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.TeamMemberAttributes;

@SuppressLint("ValidFragment")
public class DeleteMemberDialog extends DialogFragment {

	public interface DeleteMemberDialogListener {
		public void onMemberDelete(DeleteMemberDialog dialog);
	}	
	
	DeleteMemberDialogListener mListener;
	
	private TeamMemberAttributes memberAttributes;
	
	public DeleteMemberDialog(){}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
        	mListener = (DeleteMemberDialogListener) getTargetFragment();
        } catch(ClassCastException ce){
        	throw new ClassCastException(getTargetFragment().toString() + " must implement DeleteMemberDialogListener");
        }		
		
		final Activity theActivity = getActivity();
		
        AlertDialog.Builder builder = new AlertDialog.Builder(theActivity);
        builder.setTitle(R.string.delete_member_title)
    		.setMessage(getString(R.string.delete_member_confirm) + memberAttributes.getPokemonName())
    		.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mListener.onMemberDelete(DeleteMemberDialog.this);
				}
			})
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
	
	public TeamMemberAttributes getTeamMemberAttributes() {
		return memberAttributes;
	}

	public void setTeamMemberAttributes(TeamMemberAttributes memberAttributes) {
		this.memberAttributes = memberAttributes;
	}
	
}
