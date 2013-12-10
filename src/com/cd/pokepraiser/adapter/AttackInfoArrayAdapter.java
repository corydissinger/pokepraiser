package com.cd.pokepraiser.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.AttackInfo;

public class AttackInfoArrayAdapter extends ArrayAdapter<AttackInfo> {

	private final Activity context;
	
	private final ArrayList<AttackInfo> attackInfoArray;
	private ArrayList<AttackInfo> filteredAttacks;

	private AttackNameFilter filter;
	
	static class ViewHolder{
		protected TextView attackName;
		protected ImageView typeImage;		
		protected ImageView categoryImage;
		protected TextView basePower;
		protected TextView baseAccuracy;
		protected TextView basePp;
	}
	
	public AttackInfoArrayAdapter(Activity context, int textViewResourceId, ArrayList<AttackInfo> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		attackInfoArray = new ArrayList<AttackInfo>(objects);
		filteredAttacks	= new ArrayList<AttackInfo>(objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		
		if(convertView == null){
			LayoutInflater inflater = context.getLayoutInflater();
			view 					= inflater.inflate(R.layout.attack_info_row, null);
			
			final ViewHolder viewHolder		= new ViewHolder();
			
			viewHolder.attackName			= (TextView) view.findViewById(R.id.attackName);
			viewHolder.typeImage			= (ImageView) view.findViewById(R.id.attackType);
			viewHolder.categoryImage		= (ImageView) view.findViewById(R.id.attackCategory);
			viewHolder.basePower			= (TextView) view.findViewById(R.id.basePower);
			viewHolder.baseAccuracy			= (TextView) view.findViewById(R.id.baseAccuracy);
			viewHolder.basePp				= (TextView) view.findViewById(R.id.basePP);

			TextView basePowerLabel			= (TextView) view.findViewById(R.id.basePowerLabel);
			TextView baseAccuracyLabel		= (TextView) view.findViewById(R.id.baseAccuracyLabel);
			TextView basePPLabel			= (TextView) view.findViewById(R.id.basePPLabel);			
			
			((PokepraiserApplication)context.getApplication()).applyTypeface(new TextView[]{basePowerLabel,
																			 baseAccuracyLabel,
																			 basePPLabel,
																			 viewHolder.attackName,
																			 viewHolder.basePower,
																			 viewHolder.baseAccuracy,
																			 viewHolder.basePp});
			
			view.setTag(viewHolder);
			
		}else{
			view = convertView;
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		
		final AttackInfo attackPos 		= filteredAttacks.get(position);
		
		final Drawable drawableType		=  context.getResources().getDrawable(attackPos.getTypeDrawableId());
		final Drawable drawableCategory	= context.getResources().getDrawable(attackPos.getCategoryDrawableId());		
		
		holder.attackName.setText(attackPos.getName());
		holder.typeImage.setImageDrawable(drawableType);
		holder.categoryImage.setImageDrawable(drawableCategory);
		holder.basePower.setText(attackPos.getBasePower());
		holder.baseAccuracy.setText(attackPos.getBaseAccuracy());
		holder.basePp.setText(attackPos.getBasePp());
		
		return view;
	}

	public Filter getFilter(){
		if(filter == null)
			filter = new AttackNameFilter();
		
		return filter;
	}
	
	private class AttackNameFilter extends Filter
	{
	        @Override
	        protected FilterResults performFiltering(CharSequence constraint)
	        {   
	            FilterResults results = new FilterResults();
	            String prefix = constraint.toString().toLowerCase();

	            if (prefix == null || prefix.length() == 0)
	            {
	            	ArrayList<AttackInfo> list = new ArrayList<AttackInfo>(attackInfoArray);
	                results.values = list;
	                results.count = list.size();
	            }
	            else
	            {
	            	ArrayList<AttackInfo> list = new ArrayList<AttackInfo>(attackInfoArray);
	                final ArrayList<AttackInfo> nlist = new ArrayList<AttackInfo>();
	                int count = list.size();

	                for (int i=0; i<count; i++)
	                {
	                    final AttackInfo attack = list.get(i);
	                    final String value = attack.getName().toLowerCase();

	                    if (value.startsWith(prefix))
	                    {
	                        nlist.add(attack);
	                    }
	                }
	                results.values = nlist;
	                results.count = nlist.size();
	            }
	            return results;
	        }

	        @SuppressWarnings("unchecked")
	        @Override
	        protected void publishResults(CharSequence constraint, FilterResults results) {
	        	filteredAttacks = (ArrayList<AttackInfo>)results.values;

	            clear();
	            int count = filteredAttacks.size();
	            for (int i=0; i<count; i++)
	            {
	                AttackInfo pkmn = filteredAttacks.get(i);
	                add(pkmn);
	            }
	        }

	    }	
	
}
