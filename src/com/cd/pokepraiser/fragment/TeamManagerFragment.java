package com.cd.pokepraiser.fragment;

import java.util.ArrayList;
import java.util.List;

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
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.cd.pokepraiser.PokepraiserActivity;
import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.TeamInfo;
import com.cd.pokepraiser.data.TeamMemberAttributes;
import com.cd.pokepraiser.db.dao.TeamDataSource;
import com.cd.pokepraiser.dialog.AddTeamDialog;
import com.cd.pokepraiser.dialog.AddTeamDialog.AddTeamDialogListener;
import com.cd.pokepraiser.dialog.DeleteTeamDialog;
import com.cd.pokepraiser.dialog.DeleteTeamDialog.DeleteTeamDialogListener;
import com.cd.pokepraiser.util.ExtrasConstants;

public class TeamManagerFragment extends SherlockFragment implements AddTeamDialogListener, DeleteTeamDialogListener{

	public static final String TAG					= "teamManager";
	
	private static final String TEAMS		 		= "t";	

	private TeamDataSource mTeamDataSource;	

	private ArrayList<TeamInfo> mTeams			= null;

	private AddTeamDialog mAddTeamDialog		= null;
	private DeleteTeamDialog mDeleteTeamDialog	= null;	
	
	private ViewGroup mParentView				= null;
	private LinearLayout mTeamList				= null;
	
	@SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(savedInstanceState != null){
        	mTeams = (ArrayList<TeamInfo>) savedInstanceState.getSerializable(TEAMS);
        }else{
        	mTeamDataSource = new TeamDataSource(((PokepraiserApplication)getActivity().getApplication()).getTeamdbDatabaseReference());        

        	mTeamDataSource.openRead();
        	mTeams = new ArrayList<TeamInfo>(mTeamDataSource.getTeamInfo());
        	mTeamDataSource.close();        	
        }
    	
        mAddTeamDialog 		= new AddTeamDialog();
        mDeleteTeamDialog 	= new DeleteTeamDialog();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		mParentView = (ViewGroup)inflater.inflate(R.layout.team_manager_screen, container, false);
		
        mTeamList			= (LinearLayout) mParentView.findViewById(R.id.teamList);
        Button addTeam		= (Button) mParentView.findViewById(R.id.addTeam);
        
        addTeam.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				openAddTeamDialog(v);
			}
        });
        
        refreshTeamList(inflater);

        ((PokepraiserApplication)getActivity().getApplication()).overrideFonts(mParentView);        
        
        return mParentView;
	}

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putSerializable(TEAMS, mTeams);
    	
    	super.onSaveInstanceState(savedInstanceState);
    }	
	
    public void openAddTeamDialog(View v){
    	mAddTeamDialog.setTargetFragment(this, 0);
    	mAddTeamDialog.show(getChildFragmentManager(), null);
    }

	private void refreshTeamList(LayoutInflater inflater) {
		mTeamList.removeAllViews();
		
		if(mTeams.isEmpty()){
			View noTeams 			= inflater.inflate(R.layout.team_empty, null);

			mTeamList.addView(noTeams);
		}else{
			for(TeamInfo teamInfo : mTeams){
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
				
				mTeamList.addView(teamRow);
			}
		}
		
		((PokepraiserApplication)getActivity().getApplication()).overrideFonts(mTeamList);
	}

	public void handleTeamDeleteClick(View v) {
		final TeamInfo teamInfo = (TeamInfo) v.getTag();
		mDeleteTeamDialog.setTeamInfo(teamInfo);
		mDeleteTeamDialog.setTargetFragment(this, 0);
		mDeleteTeamDialog.show(getChildFragmentManager(), null);
	}

	public void handleEditTeamClick(View v){
		final TeamInfo teamInfo = (TeamInfo) v.getTag();
		
        Fragment frag = new TeamBuilderFragment();
        Bundle args = new Bundle();
        args.putSerializable(ExtrasConstants.TEAM_INFO, teamInfo);
        frag.setArguments(args);
        
        ((PokepraiserActivity)getActivity()).setIsListOrigin(true);        
        ((PokepraiserActivity)getActivity()).changeFragment(frag, TeamBuilderFragment.TAG);		
	}
	
	public void onTeamAdd(AddTeamDialog dialog) {
		final String teamName = dialog.getTeamName();
		
		mTeamDataSource.openWrite();
		final TeamInfo newTeam = mTeamDataSource.addTeam(teamName);
		mTeamDataSource.close();

		mTeams.add(newTeam);		
		
		refreshTeamList(getActivity().getLayoutInflater());
		dialog.dismiss();
	}	
	
	@Override
	public void onTeamDelete(DeleteTeamDialog dialog) {
		final TeamInfo team = dialog.getTeamInfo();
		
		mTeamDataSource.openWrite();
		mTeamDataSource.deleteTeam(team);
		mTeamDataSource.close();

		mTeams.remove(team);
		
		refreshTeamList(getActivity().getLayoutInflater());		
		dialog.dismiss();
	}
}
