package com.cd.pokepraiser.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.cd.pokepraiser.PokepraiserActivity;
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

public class TeamBuilderFragment extends SherlockFragment implements PokemonSearchDialogListener, DeleteMemberDialogListener {

	public static final String TAG = "teamBuilder";
	
	private TeamInfo mTeamInfo;
	private ArrayList<TeamMemberAttributes> mTeamMembers;
	private ArrayList<PokemonInfo> mPokemon;
	
	private PokemonSearchDialog mPokemonSearch;
	private DeleteMemberDialog mDeleteMember;
	
	private PokemonDataSource 	mPokemonDataSource;
	private TeamDataSource 	  	mTeamDataSource;	

	private ViewGroup			mParentView;
	private LinearLayout		mTeamMembersList;
	private Button				mAddTeamMember;	
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
		
		if(savedInstanceState == null){
			savedInstanceState 	= getArguments();
			mTeamInfo			= (TeamInfo) savedInstanceState.getSerializable(ExtrasConstants.TEAM_INFO);
			
            mPokemonDataSource 	= new PokemonDataSource(((PokepraiserApplication)getActivity().getApplication()).getPokedbDatabaseReference());
            mTeamDataSource		= new TeamDataSource(((PokepraiserApplication)getActivity().getApplication()).getTeamdbDatabaseReference());
            
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

    	mTeamDataSource = new TeamDataSource(((PokepraiserApplication)getActivity().getApplication()).getTeamdbDatabaseReference());		
		
		mPokemonSearch = new PokemonSearchDialog(mPokemon);
		mDeleteMember  = new DeleteMemberDialog();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		mParentView = (ViewGroup)inflater.inflate(R.layout.team_builder_screen, container, false);

		mAddTeamMember			= (Button) mParentView.findViewById(R.id.addPokemon);
		mTeamMembersList		= (LinearLayout) mParentView.findViewById(R.id.memberList);
		TextView teamName 		= (TextView) mParentView.findViewById(R.id.teamName);
		
		teamName.setText(mTeamInfo.getName());

		mAddTeamMember.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openAddPokemonDialog(v);
			}
		});
		
		refreshMemberList(inflater);		
		
        ((PokepraiserApplication)getActivity().getApplication()).overrideFonts(mParentView);		

		return mParentView;
	}
	
	@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putSerializable(ExtrasConstants.TEAM_INFO, mTeamInfo);    	
    	savedInstanceState.putSerializable(ExtrasConstants.TEAM_MEMBERS, mTeamMembers);
    	savedInstanceState.putSerializable(ExtrasConstants.POKEMON_ID, mPokemon);
    	
    	super.onSaveInstanceState(savedInstanceState);
    }	
	
    private void refreshMemberList(LayoutInflater inflater) {
		mTeamMembersList.removeAllViews();
		
		if(mTeamMembers.isEmpty()){
			View noMembers 			= inflater.inflate(R.layout.members_empty, null);

			((PokepraiserApplication)getActivity().getApplication()).overrideFonts(noMembers);			
			
			mTeamMembersList.addView(noMembers);
		}else{
			if(mTeamMembers.size() == 6){
				mAddTeamMember.setClickable(false);
			}else{
				mAddTeamMember.setClickable(true);
			}
			
			for(TeamMemberAttributes memberAttributes : mTeamMembers){
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
				
				mTeamMembersList.addView(memberRow);
			}
		}
		
		((PokepraiserApplication)getActivity().getApplication()).overrideFonts(mTeamMembersList);		
	}

	protected void handleDeleteMemberClick(View v) {
		final TeamMemberAttributes teamMember = (TeamMemberAttributes) v.getTag();
		
		mDeleteMember.setTeamMemberAttributes(teamMember);
		mDeleteMember.setTargetFragment(this, 0);
		mDeleteMember.show(getChildFragmentManager(), null);
	}

	protected void handleEditMemberClick(View v) {
		final TeamMemberAttributes teamMember = (TeamMemberAttributes) v.getTag();
		
        Fragment frag = new TeamMemberFragment();
        Bundle args = new Bundle();
        args.putInt(ExtrasConstants.MEMBER_ID, teamMember.getId());
        frag.setArguments(args);
        
        ((PokepraiserActivity)getActivity()).changeFragment(frag, TeamMemberFragment.TAG);
	}

	public void openAddPokemonDialog(View v){
		mPokemonSearch.setTargetFragment(this, 0);
		mPokemonSearch.show(getChildFragmentManager(), null);
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
        
        refreshMemberList(getActivity().getLayoutInflater());
        
		dialog.dismiss();
	}

	@Override
	public void onMemberDelete(DeleteMemberDialog dialog) {
		final TeamMemberAttributes teamMember = dialog.getTeamMemberAttributes();
		
		mTeamDataSource.openWrite();
		mTeamDataSource.deleteTeamMember(teamMember.getId());
		mTeamDataSource.close();

		mTeamMembers.remove(teamMember);
		
		refreshMemberList(getActivity().getLayoutInflater());		
		dialog.dismiss();		
	}
}
