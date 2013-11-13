package com.cd.pokepraiser.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.AttackInfo;

public class AttackInfoArrayAdapter extends ArrayAdapter<AttackInfo> {

	private final Activity context;
	private final AttackInfo[] attackInfoArray;
	
	static class ViewHolder{
		protected TextView attackName;
		protected ImageView categoryImage;
		protected TextView basePower;
		protected TextView baseAccuracy;
		protected TextView basePp;
	}
	
	public AttackInfoArrayAdapter(Activity context, int textViewResourceId, AttackInfo[] objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		attackInfoArray = objects;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		
		if(convertView == null){
			LayoutInflater inflater = context.getLayoutInflater();
			view 					= inflater.inflate(R.layout.attackinfo_row_layout, null);
			
			final ViewHolder viewHolder		= new ViewHolder();
			
			viewHolder.attackName			= (TextView) view.findViewById(R.id.attackName);
			viewHolder.categoryImage		= (ImageView) view.findViewById(R.id.attackCategory);
			viewHolder.basePower			= (TextView) view.findViewById(R.id.basePower);
			viewHolder.baseAccuracy			= (TextView) view.findViewById(R.id.baseAccuracy);
			viewHolder.basePp				= (TextView) view.findViewById(R.id.basePP);
			
			view.setTag(viewHolder);
		}else{
			view = convertView;
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		
		final AttackInfo attackPos 		= attackInfoArray[position];
		final Drawable drawableCategory	= context.getResources().getDrawable(attackPos.getCategoryDrawableId());
		
		holder.attackName.setText(attackPos.getName());
		holder.categoryImage.setImageDrawable(drawableCategory);
		holder.basePower.setText(attackPos.getBasePower());
		holder.baseAccuracy.setText(attackPos.getBaseAccuracy());
		holder.basePp.setText(attackPos.getBasePp());
		
		return view;
	}
	
}
