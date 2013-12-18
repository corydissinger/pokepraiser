package com.cd.pokepraiser.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.cd.pokepraiser.R;

@SuppressLint("ValidFragment")
public class PokemonSearchDialog extends DialogFragment {
	
	public PokemonSearchDialog(){}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Activity theActivity = getActivity();
		
		View searchDialogView 		= theActivity.getLayoutInflater().inflate(R.layout.searching_dialog, null);
		
        AlertDialog.Builder builder = new AlertDialog.Builder(theActivity);
        builder.setTitle(R.string.searching)
    		.setView(searchDialogView);
        
        return builder.create();
	}
}
