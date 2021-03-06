package com.cd.pokepraiser.dialog;

import com.cd.pokepraiser.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

@SuppressLint("ValidFragment")
public class ErrorDialog extends DialogFragment {

	private int errorMessage = -1;
	
	public ErrorDialog(){}

	public ErrorDialog(final int anErrorMessage){
		errorMessage = anErrorMessage;
	}	
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Activity theActivity = getActivity();
		
        AlertDialog.Builder builder = new AlertDialog.Builder(theActivity);
        builder.setTitle(R.string.error)
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dismiss();
				}
			});
        
        if(errorMessage != -1)
        	builder.setMessage(getString(errorMessage));
        
        return builder.create();
	}	
	
	@Override
	 public void onDestroyView() {
	     if (getDialog() != null && getRetainInstance())
	         getDialog().setDismissMessage(null);
	     
         super.onDestroyView();
	 }	
}
