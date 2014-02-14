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
import com.cd.pokepraiser.data.ItemInfo;

public class ItemInfoArrayAdapter extends ArrayAdapter<ItemInfo> {

	private final Activity mContext;
	private final ArrayList<ItemInfo> mItemInfoArray;

	private ArrayList<ItemInfo> mFilteredItems;
	private Filter mFilter;
	
	public ItemInfoArrayAdapter(Activity context, int textViewResourceId, List<ItemInfo> objects) {
		super(context, textViewResourceId, objects);
		this.mContext = context;
		mItemInfoArray 	= new ArrayList<ItemInfo>(objects);
		mFilteredItems	= new ArrayList<ItemInfo>(objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		LayoutInflater inflater = mContext.getLayoutInflater();
		view 					= inflater.inflate(R.layout.ability_info_row, null);

		ItemInfo itemInfo 		= mFilteredItems.get(position);
		
		TextView itemName	= (TextView) view.findViewById(R.id.abilityName);
		itemName.setText(itemInfo.getName());
		((PokepraiserApplication)mContext.getApplication()).applyTypeface(itemName);
		
		return view;
	}
	
	public Filter getFilter(){
		if(mFilter == null)
			mFilter = new ItemNameFilter();
		
		return mFilter;
	}
	
	private class ItemNameFilter extends Filter
	{
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {   
            FilterResults results = new FilterResults();
            String prefix = constraint.toString().toLowerCase();

            if (prefix == null || prefix.length() == 0)
            {
            	ArrayList<ItemInfo> list = new ArrayList<ItemInfo>(mItemInfoArray);
                results.values = list;
                results.count = list.size();
            }
            else
            {
            	ArrayList<ItemInfo> list = new ArrayList<ItemInfo>(mItemInfoArray);	            	
                final ArrayList<ItemInfo> nlist = new ArrayList<ItemInfo>();
                int count = list.size();

                for (int i=0; i<count; i++)
                {
                    final ItemInfo attack = list.get(i);
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
        	mFilteredItems = (ArrayList<ItemInfo>)results.values;

            clear();
            int count = mFilteredItems.size();
            for (int i=0; i<count; i++)
            {
                ItemInfo ability = mFilteredItems.get(i);
                add(ability);
            }
        }

    }

	public List<ItemInfo> getFilteredItems() {
		return mFilteredItems;
	}	
	
}
