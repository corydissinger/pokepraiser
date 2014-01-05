package com.cd.pokepraiser.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.AttackAttributes;
import com.cd.pokepraiser.data.AttackDetail;
import com.cd.pokepraiser.data.PokemonInfo;
import com.cd.pokepraiser.db.dao.AttacksDataSource;
import com.cd.pokepraiser.db.dao.PokemonDataSource;
import com.cd.pokepraiser.util.ExtrasConstants;
import com.cd.pokepraiser.util.TypeUtils;

public class AttacksDetailActivity extends PokepraiserActivity {

	private static final String ATTACK_DETAIL = "attackDetail";
	
	private AttacksDataSource attacksDataSource;
	private PokemonDataSource pokemonDataSource;	
	
	private AttackDetail mAttackDetail;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attack_detail_screen);
        
        final Intent receivedIntent = getIntent();
        final int attackId			= receivedIntent.getIntExtra(ExtrasConstants.ATTACK_ID, 0);
        
        AttackAttributes attackAttributes;
        
        if(savedInstanceState == null){
        	List<PokemonInfo> pokemonLearningAttack;
        	
	        attacksDataSource = new AttacksDataSource(((PokepraiserApplication)getApplication()).getPokedbDatabaseReference());
	        pokemonDataSource = new PokemonDataSource(((PokepraiserApplication)getApplication()).getPokedbDatabaseReference());
	        
	        attacksDataSource.open();
	        attackAttributes = attacksDataSource.getAttackDetail(attackId);
	        attacksDataSource.close();
	
	        pokemonDataSource.open();
	        pokemonLearningAttack = pokemonDataSource.getPokemonLearningAttack(attackAttributes.getAttackDbId(), getResources());
	        pokemonDataSource.close();
	        
	        mAttackDetail = new AttackDetail();
	        mAttackDetail.setAttackAttributes(attackAttributes);
	        mAttackDetail.setPokemonLearningAttack(pokemonLearningAttack);
    	}else{
    		mAttackDetail = savedInstanceState.getParcelable(ATTACK_DETAIL);
    		attackAttributes = mAttackDetail.getAttackAttributes();
    	}
        
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
    	
    	TextView contactLabel		= (TextView) findViewById(R.id.contactLabel);        
    	TextView contact 			= (TextView) findViewById(R.id.contact);
    	
    	TextView soundLabel			= (TextView) findViewById(R.id.soundLabel);        
    	TextView sound 				= (TextView) findViewById(R.id.sound);
    	
    	TextView punchLabel			= (TextView) findViewById(R.id.punchLabel);        
    	TextView punch 				= (TextView) findViewById(R.id.punch);    	
    	
    	TextView snatchLabel			= (TextView) findViewById(R.id.snatchLabel);        
    	TextView snatch 				= (TextView) findViewById(R.id.snatch);    	

    	TextView gravityLabel			= (TextView) findViewById(R.id.gravityLabel);        
    	TextView gravity 				= (TextView) findViewById(R.id.gravity);
    	
    	TextView defrostLabel			= (TextView) findViewById(R.id.defrostLabel);        
    	TextView defrost 				= (TextView) findViewById(R.id.defrost);    
    	
    	TextView triplesLabel			= (TextView) findViewById(R.id.triplesLabel);        
    	TextView triples 				= (TextView) findViewById(R.id.triples);
    	
    	TextView reflectedLabel			= (TextView) findViewById(R.id.reflectedLabel);        
    	TextView reflected 				= (TextView) findViewById(R.id.reflected);
    	
    	TextView blockedLabel			= (TextView) findViewById(R.id.blockedLabel);        
    	TextView blocked 				= (TextView) findViewById(R.id.blocked);
    	
    	TextView mirrorableLabel			= (TextView) findViewById(R.id.mirrorableLabel);        
    	TextView mirrorable 				= (TextView) findViewById(R.id.mirrorable);    	
    	
    	TextView learnedBy			= (TextView) findViewById(R.id.learningLabel);
    	
        attackName.setText(attackAttributes.getName());
        battleDesc.setText(attackAttributes.getBattleEffectDesc());
        secondaryDesc.setText(attackAttributes.getSecondaryEffectDesc());
        
    	power.setText(attackAttributes.getBasePower());
    	accuracy.setText(attackAttributes.getBaseAccuracy());	
        pp.setText(attackAttributes.getBasePp());
        
        if(attackAttributes.getEffectPct() > 0){
        	effectPct.setText(Integer.toString(attackAttributes.getEffectPct()) + "%");
        }else{
        	LinearLayout effectPctRow = (LinearLayout) findViewById(R.id.effectPctLayoutRow);
        	((LinearLayout)effectPctRow.getParent()).removeView(effectPctRow);
        }
        
        contact.setText(attackAttributes.isContacts() ? R.string.yes : R.string.no);
        sound.setText(attackAttributes.isSound() ? R.string.yes : R.string.no);
        punch.setText(attackAttributes.isPunch() ? R.string.yes : R.string.no);
        snatch.setText(attackAttributes.isSnatchable() ? R.string.yes : R.string.no);
        gravity.setText(attackAttributes.isGravity() ? R.string.yes : R.string.no);
        defrost.setText(attackAttributes.isDefrosts() ? R.string.yes : R.string.no);
        triples.setText(attackAttributes.isHitsOpponentTriples() ? R.string.yes : R.string.no);
        reflected.setText(attackAttributes.isReflectable() ? R.string.yes : R.string.no);
        blocked.setText(attackAttributes.isBlockable() ? R.string.yes : R.string.no);
        mirrorable.setText(attackAttributes.isMirrorable() ? R.string.yes : R.string.no);
        
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
        																		effectPct,
        																		learnedBy,
        																		contact,
        																		contactLabel,
        																		sound,
        																		soundLabel,
        																		punch,
        																		punchLabel,
        																		snatch,
        																		snatchLabel,
        																		gravity,
        																		gravityLabel,
        																		defrost,
        																		defrostLabel,
        																		triples,
        																		triplesLabel,
        																		reflected,
        																		reflectedLabel,
        																		blocked,
        																		blockedLabel,
        																		mirrorable,
        																		mirrorableLabel});
        
        buildPokemonList();
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putParcelable(ATTACK_DETAIL, mAttackDetail);
    	
    	super.onSaveInstanceState(savedInstanceState);
    }    
    
	public void buildPokemonList(){
        LinearLayout learningList = (LinearLayout) findViewById(R.id.learningList);
		
		for(int i = 0; i < mAttackDetail.getPokemonLearningAttack().size(); i++){
			final PokemonInfo thePokemon = mAttackDetail.getPokemonLearningAttack().get(i);
			
			LayoutInflater inflater = getLayoutInflater();
			View view 				= inflater.inflate(R.layout.pokemon_info_row, null);
		
			ImageView icon			= (ImageView) view.findViewById(R.id.pokemonIcon);
			TextView dexNo			= (TextView) view.findViewById(R.id.dexNo);		
			TextView pokemonName	= (TextView) view.findViewById(R.id.pokemonName);
			
			ImageView typeOne		= (ImageView) view.findViewById(R.id.typeOne);
			ImageView typeTwo		= (ImageView) view.findViewById(R.id.typeTwo);		
			
			icon.setImageResource(thePokemon.getIconDrawable());
			dexNo.setText(Integer.toString(thePokemon.getDexNo()));
			pokemonName.setText(thePokemon.getName());
			
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
		        	Intent i = new Intent(AttacksDetailActivity.this, PokemonDetailActivity.class);
	        		i.putExtra(ExtrasConstants.POKEMON_ID, thePokemon.getId());
					startActivity(i);
				}
	        });

			final int theColor = getResources().getColor(R.color.clickable_text);
			dexNo.setTextColor(theColor);
			pokemonName.setTextColor(theColor);
			
			learningList.addView(view);
		}
	}    
	
}
