package com.cd.pokepraiser.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;

@SuppressLint("ValidFragment")
public class AddTeamDialog extends DialogFragment {

	public interface AddTeamDialogListener {
		public void onTeamAdd(AddTeamDialog dialog);
	}	
	
	AddTeamDialogListener mListener;
	
	private EditText mTeamName		= null;
	
	public AddTeamDialog(){}
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        try {
        	mListener = (AddTeamDialogListener) activity;
        } catch(ClassCastException ce){
        	throw new ClassCastException(activity.toString() + " must implement TeamAddDialogListener");
        }
    }
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Activity theActivity = getActivity();
		
		View teamAddScreen 		= theActivity.getLayoutInflater().inflate(R.layout.team_add_screen, null);
		mTeamName 				= (EditText) teamAddScreen.findViewById(R.id.teamName);
		
		((PokepraiserApplication)theActivity.getApplication()).overrideFonts(teamAddScreen);
		
        AlertDialog.Builder builder = new AlertDialog.Builder(theActivity);
        builder.setTitle(R.string.create_team_title)
    		.setView(teamAddScreen)
    		.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mListener.onTeamAdd(AddTeamDialog.this);
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
	
	public String getTeamName(){
		return mTeamName.getText().toString();
	}
}
