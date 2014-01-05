package com.cd.pokepraiser.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.PokemonInfo;
import com.cd.pokepraiser.data.TeamInfo;
import com.cd.pokepraiser.data.TeamMemberAttributes;
import com.cd.pokepraiser.db.dao.PokemonDataSource;
import com.cd.pokepraiser.db.dao.TeamDataSource;
import com.cd.pokepraiser.dialog.DeleteMemberDialog;
import com.cd.pokepraiser.dialog.DeleteMemberDialog.DeleteMemberDialogListener;
import com.cd.pokepraiser.dialog.PokemonSearchDialog;
import com.cd.pokepraiser.dialog.PokemonSearchDialog.PokemonSearchDialogListener;
import com.cd.pokepraiser.util.ExtrasConstants;

public class TeamBuilderActivity extends PokepraiserActivity implements PokemonSearchDialogListener, DeleteMemberDialogListener {

	private TeamInfo mTeamInfo;
	private ArrayList<TeamMemberAttributes> mTeamMembers;
	private ArrayList<PokemonInfo> mPokemon;
	
	private PokemonSearchDialog mPokemonSearch;
	private DeleteMemberDialog mDeleteMember;
	
	private PokemonDataSource 	mPokemonDataSource;
	private TeamDataSource 	  	mTeamDataSource;	
	
	private LinearLayout		mTeamMembersList;
	private Button				mAddTeamMember;	
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_builder_screen);		
		
        final Intent receivedIntent = getIntent();
        mTeamInfo			= (TeamInfo) receivedIntent.getSerializableExtra(ExtrasConstants.TEAM_INFO);
		
		if(savedInstanceState == null){
            mPokemonDataSource 	= new PokemonDataSource(((PokepraiserApplication)getApplication()).getPokedbDatabaseReference());
            mTeamDataSource		= new TeamDataSource(((PokepraiserApplication)getApplication()).getTeamdbDatabaseReference());
            
            mTeamDataSource.openRead();
            mTeamMembers = mTeamDataSource.getTeamMembers(mTeamInfo.getDbId());
            mTeamDataSource.close();
            
            mPokemonDataSource.open();
            mPokemon = mPokemonDataSource.getPokemonList(getResources());
            mPokemonDataSource.addPokemonNames(mTeamMembers);
            mPokemonDataSource.close();		
		}else{
			mTeamInfo = (TeamInfo) savedInstanceState.getSerializable(ExtrasConstants.TEAM_INFO);
			mTeamMembers = (ArrayList<TeamMemberAttributes>) savedInstanceState.getSerializable(ExtrasConstants.TEAM_MEMBERS);
			mPokemon = (ArrayList<PokemonInfo>) savedInstanceState.getSerializable(ExtrasConstants.POKEMON_ID);
		}

    	mTeamDataSource = new TeamDataSource(((PokepraiserApplication)getApplication()).getTeamdbDatabaseReference());		
		
		mPokemonSearch = new PokemonSearchDialog(mPokemon);
		mDeleteMember  = new DeleteMemberDialog();
		
		mAddTeamMember			= (Button) findViewById(R.id.addPokemon);
		mTeamMembersList		= (LinearLayout) findViewById(R.id.memberList);
		TextView teamName 	= (TextView) findViewById(R.id.teamName);
		
		teamName.setText(mTeamInfo.getName());
		
        ((PokepraiserApplication)getApplication()).overrideFonts(findViewById(android.R.id.content));		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
    	mTeamDataSource.openRead();
    	final boolean teamExists = mTeamDataSource.checkTeamExists(mTeamInfo.getDbId());
    	mTeamDataSource.close();
    	
    	if(!teamExists)
    		finish();
    	
        refreshMemberList();    	
	}	
	
	@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putSerializable(ExtrasConstants.TEAM_INFO, mTeamInfo);    	
    	savedInstanceState.putSerializable(ExtrasConstants.TEAM_MEMBERS, mTeamMembers);
    	savedInstanceState.putSerializable(ExtrasConstants.POKEMON_ID, mPokemon);
    	
    	super.onSaveInstanceState(savedInstanceState);
    }	
	
    private void refreshMemberList() {
		mTeamMembersList.removeAllViews();
		
		if(mTeamMembers.isEmpty()){
			LayoutInflater inflater = getLayoutInflater();
			
			View noMembers 			= inflater.inflate(R.layout.members_empty, null);

			((PokepraiserApplication)getApplication()).overrideFonts(noMembers);			
			
			mTeamMembersList.addView(noMembers);
		}else{
			if(mTeamMembers.size() == 6){
				mAddTeamMember.setClickable(false);
			}else{
				mAddTeamMember.setClickable(true);
			}
			
			for(TeamMemberAttributes memberAttributes : mTeamMembers){
				LayoutInflater inflater = getLayoutInflater();
				View memberRow			= inflater.inflate(R.layout.team_member_manage_row, null);
		
				final TextView memberName 	= (TextView) memberRow.findViewById(R.id.memberName);
				final ImageView memberEdit	= (ImageView) memberRow.findViewById(R.id.memberEdit);			
				final ImageView memberDelete= (ImageView) memberRow.findViewById(R.id.memberDelete);
				
				memberName.setText(memberAttributes.getPokemonName());
	
				memberEdit.setTag(memberAttributes);
				memberDelete.setTag(memberAttributes);
	
				memberEdit.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						handleEditMemberClick(v);
					}
				});			
				
				memberDelete.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						handleDeleteMemberClick(v);
					}
				});			
				
				((PokepraiserApplication)getApplication()).overrideFonts(memberRow);
				
				mTeamMembersList.addView(memberRow);
			}
		}		
	}

	protected void handleDeleteMemberClick(View v) {
		final TeamMemberAttributes teamMember = (TeamMemberAttributes) v.getTag();
		
		mDeleteMember.setTeamMemberAttributes(teamMember);
		mDeleteMember.show(getSupportFragmentManager(), null);
	}

	protected void handleEditMemberClick(View v) {
		final TeamMemberAttributes teamMember = (TeamMemberAttributes) v.getTag();
		
    	final Intent i = new Intent(TeamBuilderActivity.this, TeamMemberActivity.class);
    	i.putExtra(ExtrasConstants.MEMBER_ID, teamMember);
    	startActivity(i);
	}

	public void openAddPokemonDialog(View v){
		mPokemonSearch.show(getSupportFragmentManager(), null);
	}

	@Override
	public void onPokemonItemClick(PokemonSearchDialog dialog) {
		final int selectedPoke = dialog.getSelectedItem();
		final int selectedPokeId = mPokemon.get(selectedPoke).getId();
		
        mTeamDataSource.openWrite();
        final int memberId = mTeamDataSource.addTeamMember(selectedPokeId, mTeamInfo.getDbId());
        mTeamDataSource.close();
        
        final TeamMemberAttributes newMember = new TeamMemberAttributes();
        newMember.setId(memberId);
        
        mPokemonDataSource.open();
        newMember.setPokemonName(mPokemonDataSource.getPokemonName(selectedPokeId));
        mPokemonDataSource.close();        
		
        mTeamMembers.add(newMember);
        
        refreshMemberList();
        
		dialog.dismiss();
	}

	@Override
	public void onMemberDelete(DeleteMemberDialog dialog) {
		final TeamMemberAttributes teamMember = dialog.getTeamMemberAttributes();
		
		mTeamDataSource.openWrite();
		mTeamDataSource.deleteTeamMember(teamMember.getId());
		mTeamDataSource.close();

		mTeamMembers.remove(teamMember);
		
		refreshMemberList();		
		dialog.dismiss();		
	}
}
