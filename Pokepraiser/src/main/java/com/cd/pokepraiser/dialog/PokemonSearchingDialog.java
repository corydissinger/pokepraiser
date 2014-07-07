package com.cd.pokepraiser.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.View;

import com.cd.pokepraiser.R;

@SuppressLint("ValidFragment")
public class PokemonSearchingDialog extends DialogFragment {
	
	public PokemonSearchingDialog(){}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Activity theActivity = getActivity();
		
		final ProgressDialog dialog = new ProgressDialog(getActivity());
		dialog.setTitle(R.string.searching);
		dialog.setMessage(getString(R.string.searching));
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
 
		// Disable the back button
		OnKeyListener keyListener = new OnKeyListener() {
 
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				
				if( keyCode == KeyEvent.KEYCODE_BACK){					
					return true;
				}
				return false;
			}
 
		
		};
		dialog.setOnKeyListener(keyListener);        
        
        return dialog;
	}
	
	@Override
	 public void onDestroyView() {
	     if (getDialog() != null && getRetainInstance())
	         getDialog().setDismissMessage(null);
	     
        super.onDestroyView();
	 }	
}
