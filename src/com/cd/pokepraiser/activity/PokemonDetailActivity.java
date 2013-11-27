package com.cd.pokepraiser.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.AbilityInfo;
import com.cd.pokepraiser.data.PokemonDetail;
import com.cd.pokepraiser.db.dao.AbilitiesDataSource;
import com.cd.pokepraiser.db.dao.PokemonDataSource;
import com.cd.pokepraiser.util.ExtrasConstants;
import com.cd.pokepraiser.util.TypeUtils;

public class PokemonDetailActivity extends PokepraiserActivity {

	private PokemonDataSource pokemonDataSource;
	private AbilitiesDataSource abilitiesDataSource;	

	private PokemonDetail pokemonDetail;
	private AbilityInfo [] pokemonAbilities;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_detail_screen);
        
        final Intent receivedIntent = getIntent();
        final int pokemonId			= receivedIntent.getIntExtra(ExtrasConstants.POKEMON_ID, 0);        
        
        pokemonDataSource = new PokemonDataSource(((PokepraiserApplication)getApplication()).getDatabaseReference());
        abilitiesDataSource = new AbilitiesDataSource(((PokepraiserApplication)getApplication()).getDatabaseReference());
        
        pokemonDataSource.open();
        pokemonDetail = pokemonDataSource.getPokemonDetail(pokemonId, getResources());
        pokemonDataSource.close();        
        
        abilitiesDataSource.open();
        pokemonAbilities 
        	= abilitiesDataSource.getAbilitiesLearnedBy(new int []{pokemonDetail.getAbOne(),
        														   pokemonDetail.getAbTwo(),
        														   pokemonDetail.getAbHa()});
        abilitiesDataSource.close();        
        
        TextView dexNo		 		= (TextView) findViewById(R.id.dexNo);
        TextView pokemonName		= (TextView) findViewById(R.id.pokemonName);
        
        ImageView pokemonPicture	= (ImageView) findViewById(R.id.pokemonPicture);
        
        ImageView typeOne			= (ImageView) findViewById(R.id.typeOne);
        ImageView typeTwo			= (ImageView) findViewById(R.id.typeTwo);        

        TextView abilityLabel		= (TextView) findViewById(R.id.abilityLabel);        
        TextView abilityOneLabel	= (TextView) findViewById(R.id.abilityOneLabel);        
        TextView abilityTwoLabel	= (TextView) findViewById(R.id.abilityTwoLabel);        
        TextView abilityHiddenLabel	= (TextView) findViewById(R.id.abilityHiddenLabel);
        TextView abilityOne			= (TextView) findViewById(R.id.abilityOne);
        TextView abilityTwo			= (TextView) findViewById(R.id.abilityTwo);        
        TextView abilityHidden		= (TextView) findViewById(R.id.abilityHidden);        
        
        TextView hpLabel			= (TextView) findViewById(R.id.baseHpLabel);
        TextView atkLabel			= (TextView) findViewById(R.id.baseAtkLabel);
        TextView defLabel			= (TextView) findViewById(R.id.baseDefLabel);
        TextView spatkLabel			= (TextView) findViewById(R.id.baseSpatkLabel);
        TextView spdefLabel			= (TextView) findViewById(R.id.baseSpdefLabel);
        TextView speLabel			= (TextView) findViewById(R.id.baseSpeLabel);        
        
        dexNo.setText(Integer.toString(pokemonDetail.getDexNo()));
        pokemonName.setText(pokemonDetail.getName());
        
        pokemonPicture.setImageResource(pokemonDetail.getImgDrawable());
        
        //Make the type images and draw them
        typeOne.setImageResource(TypeUtils.getTypeDrawableId(pokemonDetail.getTypeOne()));
        
        if(pokemonDetail.getTypeTwo() != 0){
            typeTwo.setImageResource(TypeUtils.getTypeDrawableId(pokemonDetail.getTypeTwo()));        	
        }
        
        //Make the ability names
        abilityOne.setText(pokemonAbilities[0].getName());

        if(pokemonAbilities.length == 1){
        	final LinearLayout abilityTwoLayout = (LinearLayout) findViewById(R.id.abilityTwoCell);
        	((LinearLayout)abilityTwoLayout.getParent()).removeView(abilityTwoLayout);
        	
        	final LinearLayout abilityHiddenLayout = (LinearLayout) findViewById(R.id.abilityHiddenCell);
        	((LinearLayout)abilityHiddenLayout.getParent()).removeView(abilityHiddenLayout);
        	
        }else if(pokemonAbilities.length == 2){
        	final LinearLayout abilityTwoLayout = (LinearLayout) findViewById(R.id.abilityTwoCell);
        	((LinearLayout)abilityTwoLayout.getParent()).removeView(abilityTwoLayout);

            abilityHidden.setText(pokemonAbilities[1].getName());
            
        }else{
            abilityTwo.setText(pokemonAbilities[1].getName());        	
            abilityHidden.setText(pokemonAbilities[2].getName());
            
        }
        
        //Apply typeface
		((PokepraiserApplication)getApplication()).applyTypeface(new TextView[]{dexNo,
																				pokemonName,
																				abilityLabel,
																				abilityOne,
																				abilityTwo,
																				abilityHidden,
																				abilityOneLabel,
																				abilityTwoLabel,																			
																				abilityHiddenLabel,
																				hpLabel,
																				atkLabel,
																				defLabel,
																				spatkLabel,
																				spdefLabel,
																				speLabel
																				});
    }
    
    public void openAbilityOne(View v){
		final AbilityInfo abilityItem = pokemonAbilities[0];
		
    	Intent i = new Intent(PokemonDetailActivity.this, AbilityDetailActivity.class);
		i.putExtra(ExtrasConstants.ABILITY_ID, abilityItem.getAbilityDbId());
		startActivity(i);    	
    }
    
    public void openAbilityTwo(View v){
		final AbilityInfo abilityItem = pokemonAbilities[1];
		
    	Intent i = new Intent(PokemonDetailActivity.this, AbilityDetailActivity.class);
		i.putExtra(ExtrasConstants.ABILITY_ID, abilityItem.getAbilityDbId());
		startActivity(i);    	
    }
    
    public void openAbilityHidden(View v){
		final AbilityInfo abilityItem;
		
		if(pokemonAbilities.length == 2){
			abilityItem = pokemonAbilities[1];
		}else{
			abilityItem = pokemonAbilities[2];
		}
		
    	Intent i = new Intent(PokemonDetailActivity.this, AbilityDetailActivity.class);
		i.putExtra(ExtrasConstants.ABILITY_ID, abilityItem.getAbilityDbId());
		startActivity(i);    	
    }        
	
}
