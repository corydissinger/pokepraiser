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
import com.cd.pokepraiser.data.TypeInfo;

public class TypeInfoArrayAdapter extends ArrayAdapter<TypeInfo> {

	private final Activity mContext;
	private final ArrayList<TypeInfo> mTypeInfoArray;
	
	public TypeInfoArrayAdapter(Activity context, int textViewResourceId, List<TypeInfo> objects) {
		super(context, textViewResourceId, objects);
		this.mContext = context;
		mTypeInfoArray 	= new ArrayList<TypeInfo>(objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		LayoutInflater inflater = mContext.getLayoutInflater();
		view 					= inflater.inflate(R.layout.ability_info_row, null);

		final TypeInfo typeInfo = mTypeInfoArray.get(position);
		TextView typeName	= (TextView) view.findViewById(R.id.abilityName);
		typeName.setText(typeInfo.getName());
		((PokepraiserApplication)mContext.getApplication()).applyTypeface(typeName);
		
		return view;
	}
	
}
