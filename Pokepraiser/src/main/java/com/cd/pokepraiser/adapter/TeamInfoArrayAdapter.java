package com.cd.pokepraiser.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.TeamInfo;

public class TeamInfoArrayAdapter extends ArrayAdapter<TeamInfo> {

	private final Activity mContext;
	private final ArrayList<TeamInfo> mTeamInfoArray;
	
	public TeamInfoArrayAdapter(Activity context, int textViewResourceId, List<TeamInfo> objects) {
		super(context, textViewResourceId, objects);
		this.mContext = context;
		mTeamInfoArray 	= new ArrayList<TeamInfo>(objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		LayoutInflater inflater = mContext.getLayoutInflater();
		view 					= inflater.inflate(R.layout.team_info_row, null);

		TeamInfo teamInfo = mTeamInfoArray.get(position);
		
		final TextView teamName 	= (TextView) view.findViewById(R.id.teamName);
		final TextView teamCount 	= (TextView) view.findViewById(R.id.teamCount);		
		final String countString	= teamInfo.getCount() + "/6";
		
		teamName.setText(teamInfo.getName());
		teamCount.setText(countString);
		
		((PokepraiserApplication)mContext.getApplication()).applyTypeface(teamName);
		
		return view;
	}
	
}
