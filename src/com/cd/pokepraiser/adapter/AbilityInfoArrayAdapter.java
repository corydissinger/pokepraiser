package com.cd.pokepraiser.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.AbilityInfo;

public class AbilityInfoArrayAdapter extends ArrayAdapter<AbilityInfo> {

	private final Activity context;
	private final AbilityInfo[] abilityInfoArray;
	
	public AbilityInfoArrayAdapter(Activity context, int textViewResourceId, AbilityInfo[] objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		abilityInfoArray = objects;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		
		if(convertView == null){
			LayoutInflater inflater = context.getLayoutInflater();
			view 					= inflater.inflate(R.layout.abilityinfo_row_layout, null);

			AbilityInfo abilityInfo = abilityInfoArray[position];
			
			TextView abilityName	= (TextView) view.findViewById(R.id.abilityName);
			abilityName.setText(abilityInfo.getName());
		}else{
			view = convertView;
		}
		
		return view;
	}
	
}
