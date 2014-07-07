package com.cd.pokepraiser.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.NatureInfo;

public class NatureInfoArrayAdapter extends ArrayAdapter<NatureInfo> {

	private final Activity context;
	
	private final ArrayList<NatureInfo> mNatures;
	private ArrayList<NatureInfo> mFilteredNatures;

	private NatureNameFilter mFilter;
	
	static class ViewHolder{
		protected TextView name;
		protected TextView plus;
		protected TextView minus;
	}
	
	public NatureInfoArrayAdapter(Activity context, int textViewResourceId, List<NatureInfo> theNatures) {
		super(context, textViewResourceId, theNatures);
		this.context = context;
		mNatures = new ArrayList<NatureInfo>(theNatures);
		mFilteredNatures	= new ArrayList<NatureInfo>(theNatures);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		
		if(convertView == null){
			LayoutInflater inflater = context.getLayoutInflater();
			view 					= inflater.inflate(R.layout.nature_info_row, null);
			
			final ViewHolder viewHolder		= new ViewHolder();
			
			viewHolder.name					= (TextView) view.findViewById(R.id.name);
			viewHolder.plus					= (TextView) view.findViewById(R.id.plus);
			viewHolder.minus				= (TextView) view.findViewById(R.id.minus);
			
			((PokepraiserApplication)context.getApplication()).overrideFonts(view);
			
			view.setTag(viewHolder);
			
		}else{
			view = convertView;
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		
		final NatureInfo naturePos 		= mFilteredNatures.get(position);
		
		
		holder.name.setText(naturePos.getName());
		
		if(naturePos.getPlus() == 0){
			holder.plus.setText(context.getString(R.string.neutral));
			
			((ViewGroup)view).removeView(holder.minus);
		}else{
			holder.plus.setText("+" + getStatName(naturePos.getPlus()));
			holder.plus.setTextColor(context.getResources().getColor(R.color.vibrant_green));
			
			holder.minus.setText("-" + getStatName(naturePos.getMinus()));
			holder.minus.setTextColor(context.getResources().getColor(R.color.exciting_red));
		}
		
		return view;
	}

	public Filter getFilter(){
		if(mFilter == null)
			mFilter = new NatureNameFilter();
		
		return mFilter;
	}
	
	private String getStatName(int statCode){
		switch(statCode){
			case 2: return context.getString(R.string.attack);
				
			case 3: return context.getString(R.string.defense);
				
			case 4: return context.getString(R.string.special_attack);
				
			case 5: return context.getString(R.string.special_defense);
				
			case 6: return context.getString(R.string.speed);				
				
			default: return "";
		}
	}
	
	private class NatureNameFilter extends Filter
	{
	        @Override
	        protected FilterResults performFiltering(CharSequence constraint)
	        {   
	            FilterResults results = new FilterResults();
	            String prefix = constraint.toString().toLowerCase();

	            if (prefix == null || prefix.length() == 0)
	            {
	            	ArrayList<NatureInfo> list = new ArrayList<NatureInfo>(mNatures);
	                results.values = list;
	                results.count = list.size();
	            }
	            else
	            {
	            	ArrayList<NatureInfo> list = new ArrayList<NatureInfo>(mNatures);
	                final ArrayList<NatureInfo> nlist = new ArrayList<NatureInfo>();
	                int count = list.size();

	                for (int i=0; i<count; i++)
	                {
	                    final NatureInfo Nature = list.get(i);
	                    final String value = Nature.getName().toLowerCase();

	                    if (value.startsWith(prefix))
	                    {
	                        nlist.add(Nature);
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
	        	mFilteredNatures = (ArrayList<NatureInfo>)results.values;

	            clear();
	            int count = mFilteredNatures.size();
	            for (int i=0; i<count; i++)
	            {
	                NatureInfo pkmn = mFilteredNatures.get(i);
	                add(pkmn);
	            }
	        }

	    }	
	
}
