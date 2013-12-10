package com.cd.pokepraiser.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.PokemonInfo;
import com.cd.pokepraiser.util.TypeUtils;

public class PokemonInfoArrayAdapter extends ArrayAdapter<PokemonInfo> {

	private final Activity context;
	private final ArrayList<PokemonInfo> pokemonInfoArray;
	
	private Filter filter;
	private ArrayList<PokemonInfo> filteredPokemon;	
	
	public PokemonInfoArrayAdapter(Activity context, int textViewResourceId, ArrayList<PokemonInfo> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		pokemonInfoArray 	= new ArrayList<PokemonInfo>(objects);
		filteredPokemon		= new ArrayList<PokemonInfo>(objects);
	}

	static class ViewHolder{
		protected ImageView icon;		
		protected TextView dexNo;
		protected TextView pokemonName;		
		protected ImageView typeOne;
		protected ImageView typeTwo;
	}	
	
	//Make a tag
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		LayoutInflater inflater = context.getLayoutInflater();
		view 					= inflater.inflate(R.layout.pokemon_info_row, null);

		PokemonInfo pokemonInfo = filteredPokemon.get(position);
		
		ImageView icon			= (ImageView) view.findViewById(R.id.pokemonIcon);
		TextView dexNo			= (TextView) view.findViewById(R.id.dexNo);		
		TextView pokemonName	= (TextView) view.findViewById(R.id.pokemonName);
		
		ImageView typeOne		= (ImageView) view.findViewById(R.id.typeOne);
		ImageView typeTwo		= (ImageView) view.findViewById(R.id.typeTwo);		
		
		icon.setImageResource(pokemonInfo.getIconDrawable());
		dexNo.setText(Integer.toString(pokemonInfo.getDexNo()));
		pokemonName.setText(pokemonInfo.getPokemonName());
		
		typeOne.setImageResource(TypeUtils.getTypeDrawableId(pokemonInfo.getTypeOne()));
		
		if(pokemonInfo.getTypeTwo() != 0){
			typeTwo.setImageResource(TypeUtils.getTypeDrawableId(pokemonInfo.getTypeTwo()));
		}else{
        	LinearLayout typeCell = (LinearLayout) view.findViewById(R.id.typeCell);
        	typeCell.removeView(context.findViewById(R.id.typeSpacer));
			typeCell.removeView(typeTwo);			
		}

		((PokepraiserApplication)context.getApplication()).applyTypeface(pokemonName);
		((PokepraiserApplication)context.getApplication()).applyTypeface(dexNo);		
		
		return view;
	}

	public Filter getFilter(){
		if(filter == null)
			filter = new PokemonNameFilter();
		
		return filter;
	}
	
	private class PokemonNameFilter extends Filter
	{
	        @Override
	        protected FilterResults performFiltering(CharSequence constraint)
	        {   
	            FilterResults results = new FilterResults();
	            String prefix = constraint.toString().toLowerCase();

	            if (prefix == null || prefix.length() == 0)
	            {
	            	final ArrayList<PokemonInfo> original = new ArrayList<PokemonInfo>(pokemonInfoArray);
	                results.values = original;
	                results.count = pokemonInfoArray.size();
	            }
	            else
	            {
	            	final ArrayList<PokemonInfo> original = new ArrayList<PokemonInfo>(pokemonInfoArray);
	                final ArrayList<PokemonInfo> nlist = new ArrayList<PokemonInfo>();
	                int count = pokemonInfoArray.size();

	                for (int i=0; i<count; i++)
	                {
	                    final PokemonInfo pkmn = original.get(i);
	                    final String value = pkmn.getPokemonName().toLowerCase();

	                    if (value.startsWith(prefix))
	                    {
	                        nlist.add(pkmn);
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
	        	filteredPokemon = (ArrayList<PokemonInfo>)results.values;

	            clear();
	            int count = filteredPokemon.size();
	            for (int i=0; i<count; i++)
	            {
	                PokemonInfo pkmn = filteredPokemon.get(i);
	                add(pkmn);
	            }
	        }

	    }	
	
}
