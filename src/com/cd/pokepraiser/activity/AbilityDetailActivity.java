package com.cd.pokepraiser.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.AbilityDetail;
import com.cd.pokepraiser.data.PokemonInfo;
import com.cd.pokepraiser.db.dao.AbilitiesDataSource;
import com.cd.pokepraiser.db.dao.PokemonDataSource;
import com.cd.pokepraiser.util.ExtrasConstants;
import com.cd.pokepraiser.util.TypeUtils;

public class AbilityDetailActivity extends PokepraiserActivity {

	private AbilitiesDataSource abilitiesDataSource;
	private PokemonDataSource pokemonDataSource;

    private ArrayList<PokemonInfo> pokemonLearningAbility;	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ability_detail_screen);
        
        final Intent receivedIntent = getIntent();
        final int abilityId			= receivedIntent.getIntExtra(ExtrasConstants.ABILITY_ID, 0);        
        
        abilitiesDataSource = new AbilitiesDataSource(((PokepraiserApplication)getApplication()).getDatabaseReference());
        pokemonDataSource	= new PokemonDataSource(((PokepraiserApplication)getApplication()).getDatabaseReference());
        
        abilitiesDataSource.open();
        final AbilityDetail abilityDetail = abilitiesDataSource.getAbilityDetail(abilityId);
        abilitiesDataSource.close();        
        
        pokemonDataSource.open();
        pokemonLearningAbility	
        	= pokemonDataSource.getPokemonLearningAbility(abilityId, getResources());
        pokemonDataSource.close();

        TextView abilityName 		= (TextView) findViewById(R.id.abilityName);
        TextView battleEffectLabel 	= (TextView) findViewById(R.id.battleEffectLabel);        
        TextView battleEffect		= (TextView) findViewById(R.id.battleEffect);

        TextView worldEffectLabel	= (TextView) findViewById(R.id.worldEffectLabel);
        TextView worldEffect		= (TextView) findViewById(R.id.worldEffect);
        
        TextView learningLabel		= (TextView) findViewById(R.id.learningLabel);
        
        //Apply data to ability detail views
        
        abilityName.setText(abilityDetail.getName());
        battleEffect.setText(abilityDetail.getBattleEffect());
        
        if(abilityDetail.getWorldEffect() != null && !("".equals(abilityDetail.getWorldEffect()))){
	        worldEffect.setText(abilityDetail.getWorldEffect());
        }else{
        	((ViewManager)worldEffectLabel.getParent()).removeView(worldEffectLabel);        	
        	((ViewManager)worldEffect.getParent()).removeView(worldEffect);
        	
        	((PokepraiserApplication)getApplication()).applyTypeface(worldEffectLabel);
        	((PokepraiserApplication)getApplication()).applyTypeface(worldEffect);
        }
        
        //Apply detail to pokemon learning ability

        buildPokemonList();
        
        //Apply typeface
        ((PokepraiserApplication)getApplication()).applyTypeface(new TextView[]{abilityName,
        																		battleEffectLabel,
        																		battleEffect,
        																		worldEffectLabel,
        																		worldEffect,
        																		learningLabel});        
    }
    
	public void buildPokemonList(){
        LinearLayout abilityScrollable = (LinearLayout) findViewById(R.id.abilityScrollable);
		
		for(int i = 0; i < pokemonLearningAbility.size(); i++){
			final PokemonInfo thePokemon = pokemonLearningAbility.get(i);
			
			LayoutInflater inflater = getLayoutInflater();
			View view 				= inflater.inflate(R.layout.pokemon_info_row, null);
		
			ImageView icon			= (ImageView) view.findViewById(R.id.pokemonIcon);
			TextView dexNo			= (TextView) view.findViewById(R.id.dexNo);		
			TextView pokemonName	= (TextView) view.findViewById(R.id.pokemonName);
			
			ImageView typeOne		= (ImageView) view.findViewById(R.id.typeOne);
			ImageView typeTwo		= (ImageView) view.findViewById(R.id.typeTwo);		
			
			icon.setImageResource(thePokemon.getIconDrawable());
			dexNo.setText(Integer.toString(thePokemon.getDexNo()));
			pokemonName.setText(thePokemon.getPokemonName());
			
			typeOne.setImageResource(TypeUtils.getTypeDrawableId(thePokemon.getTypeOne()));
			
			if(thePokemon.getTypeTwo() != 0){
				typeTwo.setImageResource(TypeUtils.getTypeDrawableId(thePokemon.getTypeTwo()));
			}else{
		    	LinearLayout typeCell = (LinearLayout) view.findViewById(R.id.typeCell);
		    	typeCell.removeView(findViewById(R.id.typeSpacer));
				typeCell.removeView(typeTwo);			
			}
		
			if(i % 2 == 0){
				view.setBackgroundResource(R.drawable.dark_gray_background);
			}else{
				view.setBackgroundResource(R.drawable.gray_background);			
			}
			
			((PokepraiserApplication)getApplication()).applyTypeface(pokemonName);
			((PokepraiserApplication)getApplication()).applyTypeface(dexNo);
			
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
		        	Intent i = new Intent(AbilityDetailActivity.this, PokemonDetailActivity.class);
	        		i.putExtra(ExtrasConstants.POKEMON_ID, thePokemon.getPokemonId());
					startActivity(i);
				}
	        });

			final int theColor = getResources().getColor(R.color.clickable_text);
			dexNo.setTextColor(theColor);
			pokemonName.setTextColor(theColor);
			
			abilityScrollable.addView(view);
		}
	}    

}
