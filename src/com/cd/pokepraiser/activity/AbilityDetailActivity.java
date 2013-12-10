package com.cd.pokepraiser.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.AbilityAttributes;
import com.cd.pokepraiser.data.AbilityDetail;
import com.cd.pokepraiser.data.PokemonInfo;
import com.cd.pokepraiser.db.dao.AbilitiesDataSource;
import com.cd.pokepraiser.db.dao.PokemonDataSource;
import com.cd.pokepraiser.util.ExtrasConstants;
import com.cd.pokepraiser.util.TypeUtils;

public class AbilityDetailActivity extends PokepraiserActivity {

	private static final String ABILITY_DETAIL	= "abilityDetail";
	
	private AbilitiesDataSource abilitiesDataSource;
	private PokemonDataSource pokemonDataSource;

	private AbilityDetail mAbilityDetail;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ability_detail_screen);
        
        final Intent receivedIntent = getIntent();
        final int abilityId			= receivedIntent.getIntExtra(ExtrasConstants.ABILITY_ID, 0);        

    	AbilityAttributes abilityAttributes;
        
        if(savedInstanceState == null){
        	List<PokemonInfo> pokemonLearningAbility;        	
	        abilitiesDataSource = new AbilitiesDataSource(((PokepraiserApplication)getApplication()).getDatabaseReference());
	        pokemonDataSource	= new PokemonDataSource(((PokepraiserApplication)getApplication()).getDatabaseReference());
	        
	        abilitiesDataSource.open();
	        abilityAttributes
	        	= abilitiesDataSource.getAbilityAttributes(abilityId);
	        abilitiesDataSource.close();        
	        
	        pokemonDataSource.open();
	        pokemonLearningAbility	
	        	= pokemonDataSource.getPokemonLearningAbility(abilityId, getResources());
	        pokemonDataSource.close();
	        
	        mAbilityDetail = new AbilityDetail();
	        mAbilityDetail.setAbilityAttributes(abilityAttributes);
	        mAbilityDetail.setPokemonLearningAbility(pokemonLearningAbility);
        }else{
        	mAbilityDetail = savedInstanceState.getParcelable(ABILITY_DETAIL);
        	abilityAttributes = mAbilityDetail.getAbilityAttributes();
        }

        TextView abilityName 		= (TextView) findViewById(R.id.abilityName);
        TextView battleEffectLabel 	= (TextView) findViewById(R.id.battleEffectLabel);        
        TextView battleEffect		= (TextView) findViewById(R.id.battleEffect);

        TextView worldEffectLabel	= (TextView) findViewById(R.id.worldEffectLabel);
        TextView worldEffect		= (TextView) findViewById(R.id.worldEffect);
        
        TextView learningLabel		= (TextView) findViewById(R.id.learningLabel);
        
        //Apply data to ability detail views
        
        abilityName.setText(abilityAttributes.getName());
        battleEffect.setText("\t" + abilityAttributes.getBattleEffect());
        
        if(abilityAttributes.getWorldEffect() != null && !("".equals(abilityAttributes.getWorldEffect()))){
	        worldEffect.setText("\t" + abilityAttributes.getWorldEffect());
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
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putParcelable(ABILITY_DETAIL, mAbilityDetail);
    	
    	super.onSaveInstanceState(savedInstanceState);
    }    
    
	public void buildPokemonList(){
        LinearLayout learningList = (LinearLayout) findViewById(R.id.learningList);
		
		for(int i = 0; i < mAbilityDetail.getPokemonLearningAbility().size(); i++){
			final PokemonInfo thePokemon = mAbilityDetail.getPokemonLearningAbility().get(i);
			
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

			view.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					TextView dexNo 		= (TextView) v.findViewById(R.id.dexNo);
					TextView pokeName 	= (TextView) v.findViewById(R.id.pokemonName);					
					
					final int blueResource		= getResources().getColor(R.color.medium_blue);
					final int blackResource		= getResources().getColor(R.color.black);					
					
	                switch (event.getAction()) {

		                case MotionEvent.ACTION_DOWN:
		                	dexNo.setTextColor(blueResource);
		                	pokeName.setTextColor(blueResource);
		                    break;
		                case MotionEvent.ACTION_UP:
		                	dexNo.setTextColor(blackResource);
		                	pokeName.setTextColor(blackResource);
		                    break;
	                }
	                
					return false;
				}
			});
			
			learningList.addView(view);
		}
	}    

}
