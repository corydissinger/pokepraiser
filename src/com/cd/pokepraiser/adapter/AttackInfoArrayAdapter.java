package com.cd.pokepraiser.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.cd.pokepraiser.data.AttackInfo;

public class AttackInfoArrayAdapter extends ArrayAdapter<AttackInfo> {

	private Context context;
	
	public AttackInfoArrayAdapter(Context context, int textViewResourceId, AttackInfo[] objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		return view;
	}
	
}
