package com.cd.pokepraiser.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cd.pokepraiser.PokepraiserActivity;
import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.AttackAttributes;
import com.cd.pokepraiser.data.AttackDetail;
import com.cd.pokepraiser.data.PokemonInfo;
import com.cd.pokepraiser.db.dao.AttacksDataSource;
import com.cd.pokepraiser.db.dao.PokemonDataSource;
import com.cd.pokepraiser.util.ExtrasConstants;
import com.cd.pokepraiser.util.TypeUtils;

public class AttackDetailFragment extends Fragment {

	public static final String TAG = "attackDetail";
	private static final String ATTACK_DETAIL = "attackDetail";
	
	private AttacksDataSource attacksDataSource;
	private PokemonDataSource pokemonDataSource;	
	
	private AttackDetail mAttackDetail;

	private ViewGroup mParentView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(savedInstanceState == null){
            savedInstanceState = getArguments();
            final int attackId = savedInstanceState.getInt(ExtrasConstants.ATTACK_ID);
            
            AttackAttributes attackAttributes;        	
        	
        	List<PokemonInfo> pokemonLearningAttack;
        	
	        attacksDataSource = new AttacksDataSource(((PokepraiserApplication)getActivity().getApplication()).getPokedbDatabaseReference());
	        pokemonDataSource = new PokemonDataSource(((PokepraiserApplication)getActivity().getApplication()).getPokedbDatabaseReference());
	        
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
    	}
        
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		mParentView = (ViewGroup)inflater.inflate(R.layout.attack_detail_screen, container, false);

        TextView attackName		= (TextView) mParentView.findViewById(R.id.attackName);
        TextView battleDesc 	= (TextView) mParentView.findViewById(R.id.battleDesc);
        TextView secondaryDesc 	= (TextView) mParentView.findViewById(R.id.secondaryDesc);

        TextView power 			= (TextView) mParentView.findViewById(R.id.basePower);
        
        TextView accuracy		= (TextView) mParentView.findViewById(R.id.baseAccuracy);

        TextView pp	 			= (TextView) mParentView.findViewById(R.id.basePp);
        
    	TextView effectPct 		= (TextView) mParentView.findViewById(R.id.effectPct);        

    	TextView priority 			= (TextView) mParentView.findViewById(R.id.priority);    	
    	TextView contact 			= (TextView) mParentView.findViewById(R.id.contact);
    	TextView sound 				= (TextView) mParentView.findViewById(R.id.sound);
    	TextView punch 				= (TextView) mParentView.findViewById(R.id.punch);    	
    	TextView snatch 				= (TextView) mParentView.findViewById(R.id.snatch);    	
    	TextView gravity 				= (TextView) mParentView.findViewById(R.id.gravity);
    	TextView defrost 				= (TextView) mParentView.findViewById(R.id.defrost);    
    	TextView triples 				= (TextView) mParentView.findViewById(R.id.triples);
    	TextView reflected 				= (TextView) mParentView.findViewById(R.id.reflected);
    	TextView blocked 				= (TextView) mParentView.findViewById(R.id.blocked);
    	TextView mirrorable 				= (TextView) mParentView.findViewById(R.id.mirrorable);    	
    	
    	
    	AttackAttributes attackAttributes = mAttackDetail.getAttackAttributes();
    	
        attackName.setText(attackAttributes.getName());
        battleDesc.setText(attackAttributes.getBattleEffectDesc());
        secondaryDesc.setText(attackAttributes.getSecondaryEffectDesc());
        
    	power.setText(attackAttributes.getBasePower());
    	accuracy.setText(attackAttributes.getBaseAccuracy());	
        pp.setText(attackAttributes.getBasePp());
        
        if(attackAttributes.getEffectPct() > 0){
        	effectPct.setText(Integer.toString(attackAttributes.getEffectPct()) + "%");
        }else{
        	LinearLayout effectPctRow = (LinearLayout) mParentView.findViewById(R.id.effectPctLayoutRow);
        	((LinearLayout)effectPctRow.getParent()).removeView(effectPctRow);
        }
        
        priority.setText(Integer.toString(attackAttributes.getPriority()));
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
        
        ((PokepraiserApplication)getActivity().getApplication()).overrideFonts(mParentView);
        
        buildPokemonList(inflater);
        
        return mParentView;
	}
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putParcelable(ATTACK_DETAIL, mAttackDetail);
    	
    	super.onSaveInstanceState(savedInstanceState);
    }    
    
	public void buildPokemonList(LayoutInflater inflater){
        LinearLayout learningList = (LinearLayout) mParentView.findViewById(R.id.learningList);
		
		for(int i = 0; i < mAttackDetail.getPokemonLearningAttack().size(); i++){
			final PokemonInfo thePokemon = mAttackDetail.getPokemonLearningAttack().get(i);
			
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
		    	typeCell.removeView(mParentView.findViewById(R.id.typeSpacer));
				typeCell.removeView(typeTwo);			
			}
			
			((PokepraiserApplication)getActivity().getApplication()).applyTypeface(pokemonName);
			((PokepraiserApplication)getActivity().getApplication()).applyTypeface(dexNo);
			
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					PokemonDetailFragment newFrag = new PokemonDetailFragment();
					Bundle args = new Bundle();
					
					args.putInt(ExtrasConstants.POKEMON_ID, thePokemon.getId());
					newFrag.setArguments(args);
					
					((PokepraiserActivity)getActivity()).setIsListOrigin(false);
					((PokepraiserActivity)getActivity()).changeFragment(newFrag, newFrag.TAG);
				}
	        });
			
			view.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					TextView pokemonName 		= (TextView) v.findViewById(R.id.pokemonName);
					
					final int blueResource		= getResources().getColor(R.color.medium_blue);
					final int blackResource		= getResources().getColor(R.color.black);					
					
	                switch (event.getAction()) {

		                case MotionEvent.ACTION_DOWN:
		                	pokemonName.setTextColor(blueResource);
		                    break;
		                case MotionEvent.ACTION_UP:
		                	pokemonName.setTextColor(blackResource);
		                    break;
	                }
	                
					return false;
				}
			});

			final int theColor = getResources().getColor(R.color.clickable_text);
			dexNo.setTextColor(theColor);
			pokemonName.setTextColor(theColor);
			
			learningList.addView(view);
		}
	}    
	
}
