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
import com.cd.pokepraiser.adapter.TeamInfoArrayAdapter;
import com.cd.pokepraiser.data.TeamInfo;

@SuppressLint("ValidFragment")
public class AddTeamMemberDialog extends DialogFragment {

	public interface AddTeamMemberDialogListener {
		public void onTeamItemClick(AddTeamMemberDialog dialog);
	}	
	
	AddTeamMemberDialogListener mListener;
	
	private List<TeamInfo> mTeams;

	private int selectedItem;

	private TeamInfoArrayAdapter adapter;
	
	public AddTeamMemberDialog(){}
	
	public AddTeamMemberDialog(List<TeamInfo> theTeams){
		mTeams = theTeams;
	}	
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
        	mListener = (AddTeamMemberDialogListener) getTargetFragment();
        } catch(ClassCastException ce){
        	throw new ClassCastException(getTargetFragment().toString() + " must implement TeamAddDialogListener");
        }		
		
		final Activity theActivity = getActivity();
		
		View teamListScreen 		= theActivity.getLayoutInflater().inflate(R.layout.types_list_screen, null);
		ListView teamList			= (ListView) teamListScreen.findViewById(R.id.typesList);
		
        adapter = new TeamInfoArrayAdapter(theActivity, android.R.layout.simple_list_item_1, mTeams);		
		
        teamList.setAdapter(adapter);
        
        teamList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				selectedItem = position;
				mListener.onTeamItemClick(AddTeamMemberDialog.this);
			}        	
		});
		
        AlertDialog.Builder builder = new AlertDialog.Builder(theActivity);
        builder.setTitle(R.string.all_teams)
    		.setView(teamListScreen)
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
	
	public int getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(int selectedItem) {
		this.selectedItem = selectedItem;
	}
}
