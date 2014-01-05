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
import com.cd.pokepraiser.data.TeamInfo;

@SuppressLint("ValidFragment")
public class DeleteTeamDialog extends DialogFragment {

	public interface DeleteTeamDialogListener {
		public void onTeamDelete(DeleteTeamDialog dialog);
	}	
	
	DeleteTeamDialogListener mListener;
	
	private TeamInfo teamInfo;
	
	public DeleteTeamDialog(){}
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        try {
        	mListener = (DeleteTeamDialogListener) activity;
        } catch(ClassCastException ce){
        	throw new ClassCastException(activity.toString() + " must implement DeleteTeamDialogListener");
        }
    }
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Activity theActivity = getActivity();
		
        AlertDialog.Builder builder = new AlertDialog.Builder(theActivity);
        builder.setTitle(R.string.delete_team_title)
    		.setMessage(getString(R.string.delete_team_confirm) + teamInfo.getName())
    		.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mListener.onTeamDelete(DeleteTeamDialog.this);
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

	public TeamInfo getTeamInfo() {
		return teamInfo;
	}

	public void setTeamInfo(TeamInfo teamInfo) {
		this.teamInfo = teamInfo;
	}
	
}
