package com.cd.pokepraiser.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.NavDrawerItem;

public class NavDrawerAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<NavDrawerItem> mNavDrawerItems;
	
	public NavDrawerAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
		mContext = context;
		mNavDrawerItems = navDrawerItems;
	}	
	
	@Override
	public int getCount() {
		return mNavDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mNavDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);		
		
		View view = inflater.inflate(R.layout.navigation_list_item, null);
		
		((PokepraiserApplication)mContext).overrideFonts(view);
		
		final NavDrawerItem currItem = mNavDrawerItems.get(position);
		
		ImageView icon = (ImageView) view.findViewById(R.id.icon);
		TextView title = (TextView) view.findViewById(R.id.title);		
		
		if(currItem.getIcon() != 0){
			icon.setImageResource(currItem.getIcon());
			title.setText(currItem.getTitle());
			title.setTextColor(mContext.getResources().getColor(R.color.exciting_red));
			
			view.setEnabled(false);
			view.setOnClickListener(null);
		}else{
			icon.setVisibility(View.GONE);
			title.setText(currItem.getTitle());
			title.setTextColor(mContext.getResources().getColor(R.color.medium_blue));
		}			
		
		return view;
	}

}
