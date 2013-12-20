package com.cd.pokepraiser.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.AbilityInfo;

public class AbilityInfoArrayAdapter extends ArrayAdapter<AbilityInfo> {

	private final Activity mContext;
	private final ArrayList<AbilityInfo> mAbilityInfoArray;

	private ArrayList<AbilityInfo> mFilteredAbilities;
	private Filter mFilter;
	
	public AbilityInfoArrayAdapter(Activity context, int textViewResourceId, List<AbilityInfo> objects) {
		super(context, textViewResourceId, objects);
		this.mContext = context;
		mAbilityInfoArray 	= new ArrayList<AbilityInfo>(objects);
		mFilteredAbilities	= new ArrayList<AbilityInfo>(objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		LayoutInflater inflater = mContext.getLayoutInflater();
		view 					= inflater.inflate(R.layout.ability_info_row, null);

		AbilityInfo abilityInfo = mFilteredAbilities.get(position);
		
		TextView abilityName	= (TextView) view.findViewById(R.id.abilityName);
		abilityName.setText(abilityInfo.getName());
		((PokepraiserApplication)mContext.getApplication()).applyTypeface(abilityName);
		
		return view;
	}
	
	public Filter getFilter(){
		if(mFilter == null)
			mFilter = new AbilityNameFilter();
		
		return mFilter;
	}
	
	private class AbilityNameFilter extends Filter
	{
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {   
            FilterResults results = new FilterResults();
            String prefix = constraint.toString().toLowerCase();

            if (prefix == null || prefix.length() == 0)
            {
            	ArrayList<AbilityInfo> list = new ArrayList<AbilityInfo>(mAbilityInfoArray);
                results.values = list;
                results.count = list.size();
            }
            else
            {
            	ArrayList<AbilityInfo> list = new ArrayList<AbilityInfo>(mAbilityInfoArray);	            	
                final ArrayList<AbilityInfo> nlist = new ArrayList<AbilityInfo>();
                int count = list.size();

                for (int i=0; i<count; i++)
                {
                    final AbilityInfo attack = list.get(i);
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
        	mFilteredAbilities = (ArrayList<AbilityInfo>)results.values;

            clear();
            int count = mFilteredAbilities.size();
            for (int i=0; i<count; i++)
            {
                AbilityInfo ability = mFilteredAbilities.get(i);
                add(ability);
            }
        }

    }

	public List<AbilityInfo> getFilteredAbilities() {
		return mFilteredAbilities;
	}	
	
}
