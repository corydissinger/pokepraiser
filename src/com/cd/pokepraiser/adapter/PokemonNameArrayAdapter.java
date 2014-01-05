package com.cd.pokepraiser.adapter;

import java.util.ArrayList;
import java.util.List;

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
import com.cd.pokepraiser.data.PokemonInfo;

public class PokemonNameArrayAdapter extends ArrayAdapter<PokemonInfo> {

	private final Activity context;
	
	private final ArrayList<PokemonInfo> pokemonInfoArray;
	private ArrayList<PokemonInfo> filteredPokemon;

	private PokemonNameFilter filter;
	
	static class ViewHolder{
		protected TextView pokemonName;
		protected TextView dexNo;		
	}
	
	public PokemonNameArrayAdapter(Activity context, int textViewResourceId, List<PokemonInfo> mPokemon) {
		super(context, textViewResourceId, mPokemon);
		this.context = context;
		pokemonInfoArray = new ArrayList<PokemonInfo>(mPokemon);
		filteredPokemon	= new ArrayList<PokemonInfo>(mPokemon);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		
		if(convertView == null){
			LayoutInflater inflater = context.getLayoutInflater();
			view 					= inflater.inflate(R.layout.pokemon_name_row, null);
			
			final ViewHolder viewHolder		= new ViewHolder();
			
			viewHolder.pokemonName			= (TextView) view.findViewById(R.id.pokemonName);
			viewHolder.dexNo				= (TextView) view.findViewById(R.id.dexNo);
			
			((PokepraiserApplication)context.getApplication()).applyTypeface(new TextView[]{viewHolder.pokemonName, viewHolder.dexNo});
			
			view.setTag(viewHolder);
			
		}else{
			view = convertView;
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		
		final PokemonInfo pokemonPos 		= filteredPokemon.get(position);
		
		holder.pokemonName.setText(pokemonPos.getName());
		holder.dexNo.setText(Integer.toString(pokemonPos.getDexNo()));		
		
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
	            	ArrayList<PokemonInfo> list = new ArrayList<PokemonInfo>(pokemonInfoArray);
	                results.values = list;
	                results.count = list.size();
	            }
	            else
	            {
	            	ArrayList<PokemonInfo> list = new ArrayList<PokemonInfo>(pokemonInfoArray);
	                final ArrayList<PokemonInfo> nlist = new ArrayList<PokemonInfo>();
	                int count = list.size();

	                for (int i=0; i<count; i++)
	                {
	                    final PokemonInfo pokemon = list.get(i);
	                    final String value = pokemon.getName().toLowerCase();

	                    if (value.startsWith(prefix))
	                    {
	                        nlist.add(pokemon);
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
