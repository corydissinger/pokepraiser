package com.cd.pokepraiser.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.AttackDetail;
import com.cd.pokepraiser.data.PokemonInfo;
import com.cd.pokepraiser.db.dao.AttacksDataSource;
import com.cd.pokepraiser.db.dao.PokemonDataSource;
import com.cd.pokepraiser.util.ExtrasConstants;
import com.cd.pokepraiser.util.TypeUtils;

public class AttacksDetailActivity extends PokepraiserActivity {

	private AttacksDataSource attacksDataSource;
	private PokemonDataSource pokemonDataSource;	
	
	private ArrayList<PokemonInfo> pokemonLearningAttack;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attack_detail_screen);
        
        final Intent receivedIntent = getIntent();
        final int attackId			= receivedIntent.getIntExtra(ExtrasConstants.ATTACK_ID, 0);
        
        attacksDataSource = new AttacksDataSource(((PokepraiserApplication)getApplication()).getDatabaseReference());
        pokemonDataSource = new PokemonDataSource(((PokepraiserApplication)getApplication()).getDatabaseReference());
        
        attacksDataSource.open();
        final AttackDetail attackDetail = attacksDataSource.getAttackDetail(attackId);
        attacksDataSource.close();

        pokemonDataSource.open();

        pokemonLearningAttack = pokemonDataSource.getPokemonLearningAttack(attackDetail.getAttackDbId(), getResources());
        pokemonDataSource.close();        
        
        TextView attackName		= (TextView) findViewById(R.id.attackName);
        TextView battleDesc 	= (TextView) findViewById(R.id.battleDesc);
        TextView secondaryDesc 	= (TextView) findViewById(R.id.secondaryDesc);

        TextView powerLabel		= (TextView) findViewById(R.id.basePowerLabel);
        TextView power 			= (TextView) findViewById(R.id.basePower);
        
        TextView accuracyLabel	= (TextView) findViewById(R.id.baseAccuracyLabel);        
        TextView accuracy		= (TextView) findViewById(R.id.baseAccuracy);

        TextView ppLabel		= (TextView) findViewById(R.id.basePpLabel);        
        TextView pp	 			= (TextView) findViewById(R.id.basePp);
        
    	TextView effectPctLabel	= (TextView) findViewById(R.id.effectPctLabel);        
    	TextView effectPct 		= (TextView) findViewById(R.id.effectPct);        

    	TextView battleDescLabel	= (TextView) findViewById(R.id.battleDescLabel);
    	TextView secondaryDescLabel	= (TextView) findViewById(R.id.secondaryDescLabel);    	
    	
    	
        attackName.setText(attackDetail.getName());
        battleDesc.setText(attackDetail.getBattleEffectDesc());
        secondaryDesc.setText(attackDetail.getSecondaryEffectDesc());
        
    	power.setText(attackDetail.getBasePower());
    	accuracy.setText(attackDetail.getBaseAccuracy());	
        pp.setText(attackDetail.getBasePp());
        
        if(attackDetail.getEffectPct() > 0){
        	effectPct.setText(Integer.toString(attackDetail.getEffectPct()) + "%");
        }else{
        	LinearLayout effectPctRow = (LinearLayout) findViewById(R.id.effectPctLayoutRow);
        	((LinearLayout)effectPctRow.getParent()).removeView(effectPctRow);
        }
        
        ((PokepraiserApplication)getApplication()).applyTypeface(new TextView[]{attackName,
        																		battleDescLabel,
        																		battleDesc,
        																		secondaryDescLabel,        																		
        																		secondaryDesc,
        																		powerLabel,
        																		power,
        																		accuracyLabel,
        																		accuracy,
        																		ppLabel,
        																		pp,
        																		effectPctLabel,
        																		effectPct});
        
        buildPokemonList();
    }
    
	public void buildPokemonList(){
        LinearLayout attackScrollable = (LinearLayout) findViewById(R.id.attackScrollable);
		
		for(int i = 0; i < pokemonLearningAttack.size(); i++){
			final PokemonInfo thePokemon = pokemonLearningAttack.get(i);
			
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
		        	Intent i = new Intent(AttacksDetailActivity.this, PokemonDetailActivity.class);
	        		i.putExtra(ExtrasConstants.POKEMON_ID, thePokemon.getPokemonId());
					startActivity(i);
				}
	        });

			final int theColor = getResources().getColor(R.color.clickable_text);
			dexNo.setTextColor(theColor);
			pokemonName.setTextColor(theColor);
			
			attackScrollable.addView(view);
		}
	}    
    
    @Override
	public void onBackPressed(){
    	finish();
    }
	
}
