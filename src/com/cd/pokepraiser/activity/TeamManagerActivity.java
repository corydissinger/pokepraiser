package com.cd.pokepraiser.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.TeamInfo;
import com.cd.pokepraiser.db.dao.TeamDataSource;
import com.cd.pokepraiser.dialog.AddTeamDialog;
import com.cd.pokepraiser.dialog.AddTeamDialog.AddTeamDialogListener;
import com.cd.pokepraiser.dialog.DeleteTeamDialog;
import com.cd.pokepraiser.dialog.DeleteTeamDialog.DeleteTeamDialogListener;
import com.cd.pokepraiser.util.ExtrasConstants;

public class TeamManagerActivity extends PokepraiserActivity implements AddTeamDialogListener, DeleteTeamDialogListener{

	private static final String TEAMS		 		= "t";	

	private TeamDataSource mTeamDataSource;	

	private ArrayList<TeamInfo> mTeams			= null;

	private AddTeamDialog mAddTeamDialog		= null;
	private DeleteTeamDialog mDeleteTeamDialog	= null;	
	
	private LinearLayout mTeamList				= null;
	
	@SuppressWarnings("unchecked")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_manager_screen);
        
        if(savedInstanceState != null){
        	mTeams = (ArrayList<TeamInfo>) savedInstanceState.getSerializable(TEAMS);
        }
        
    	mTeamDataSource = new TeamDataSource(((PokepraiserApplication)getApplication()).getTeamdbDatabaseReference());        
        
        mAddTeamDialog 		= new AddTeamDialog();
        mDeleteTeamDialog 	= new DeleteTeamDialog();
        mTeamList			= (LinearLayout) findViewById(R.id.teamList);
        
        ((PokepraiserApplication)getApplication()).overrideFonts(findViewById(android.R.id.content));        
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
    	mTeamDataSource.openRead();
    	mTeams = new ArrayList<TeamInfo>(mTeamDataSource.getTeamInfo());
    	mTeamDataSource.close();
    	
        refreshTeamList();    	
	}

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putSerializable(TEAMS, mTeams);
    	
    	super.onSaveInstanceState(savedInstanceState);
    }	
	
    public void openAddTeamDialog(View v){
    	mAddTeamDialog.show(getSupportFragmentManager(), null);
    }

	private void refreshTeamList() {
		mTeamList.removeAllViews();
		
		if(mTeams.isEmpty()){
			LayoutInflater inflater = getLayoutInflater();
			
			View noTeams 			= inflater.inflate(R.layout.team_empty, null);

			((PokepraiserApplication)getApplication()).overrideFonts(noTeams);			
			
			mTeamList.addView(noTeams);
		}else{
			for(TeamInfo teamInfo : mTeams){
				LayoutInflater inflater = getLayoutInflater();
				View teamRow			= inflater.inflate(R.layout.team_info_manage_row, null);
		
				final TextView teamName 	= (TextView) teamRow.findViewById(R.id.teamName);
				final TextView teamCount 	= (TextView) teamRow.findViewById(R.id.teamCount);
				final ImageView teamEdit	= (ImageView) teamRow.findViewById(R.id.teamEdit);			
				final ImageView teamDelete	= (ImageView) teamRow.findViewById(R.id.teamDelete);
				final String countString	= teamInfo.getCount() + "/6";
				
				teamName.setText(teamInfo.getName());
				teamCount.setText(countString);
	
				teamEdit.setTag(teamInfo);
				teamDelete.setTag(teamInfo);
	
				teamEdit.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						handleEditTeamClick(v);
					}
				});			
				
				teamDelete.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						handleTeamDeleteClick(v);
					}
				});			
				
				((PokepraiserApplication)getApplication()).overrideFonts(teamRow);
				
				mTeamList.addView(teamRow);
			}
		}
	}

	public void handleTeamDeleteClick(View v) {
		final TeamInfo teamInfo = (TeamInfo) v.getTag();
		mDeleteTeamDialog.setTeamInfo(teamInfo);
		mDeleteTeamDialog.show(getSupportFragmentManager(), null);
	}

	public void handleEditTeamClick(View v){
		final TeamInfo teamInfo = (TeamInfo) v.getTag();
		
    	final Intent i = new Intent(TeamManagerActivity.this, TeamBuilderActivity.class);
    	i.putExtra(ExtrasConstants.TEAM_INFO, teamInfo);
    	startActivity(i);		
	}
	
	public void onTeamAdd(AddTeamDialog dialog) {
		final String teamName = dialog.getTeamName();
		
		mTeamDataSource.openWrite();
		final TeamInfo newTeam = mTeamDataSource.addTeam(teamName);
		mTeamDataSource.close();

		mTeams.add(newTeam);		
		
		refreshTeamList();
		dialog.dismiss();
	}	
	
	@Override
	public void onTeamDelete(DeleteTeamDialog dialog) {
		final TeamInfo team = dialog.getTeamInfo();
		
		mTeamDataSource.openWrite();
		mTeamDataSource.deleteTeam(team);
		mTeamDataSource.close();

		mTeams.remove(team);
		
		refreshTeamList();		
		dialog.dismiss();
	}	
}
