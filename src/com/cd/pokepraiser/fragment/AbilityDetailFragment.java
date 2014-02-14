package com.cd.pokepraiser.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.cd.pokepraiser.PokepraiserActivity;
import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.AbilityAttributes;
import com.cd.pokepraiser.data.AbilityDetail;
import com.cd.pokepraiser.data.PokemonInfo;
import com.cd.pokepraiser.db.dao.AbilitiesDataSource;
import com.cd.pokepraiser.db.dao.PokemonDataSource;
import com.cd.pokepraiser.util.ExtrasConstants;
import com.cd.pokepraiser.util.TypeUtils;

public class AbilityDetailFragment extends SherlockFragment {

	public static final String TAG				= "abilityDetail";
	private static final String ABILITY_DETAIL	= "z";
	
	private AbilitiesDataSource mAbilitiesDataSource;
	private PokemonDataSource mPokemonDataSource;

	private AbilityDetail mAbilityDetail;
	
	private ViewGroup mParentView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(savedInstanceState == null){
        	savedInstanceState = getArguments();
            final int abilityId			= savedInstanceState.getInt(ExtrasConstants.ABILITY_ID);        	
            AbilityAttributes abilityAttributes;        
            
        	List<PokemonInfo> pokemonLearningAbility;        	
	        mAbilitiesDataSource = new AbilitiesDataSource(((PokepraiserApplication)getActivity().getApplication()).getPokedbDatabaseReference());
	        mPokemonDataSource	= new PokemonDataSource(((PokepraiserApplication)getActivity().getApplication()).getPokedbDatabaseReference());
	        
	        mAbilitiesDataSource.open();
	        abilityAttributes
	        	= mAbilitiesDataSource.getAbilityAttributes(abilityId);
	        mAbilitiesDataSource.close();        
	        
	        mPokemonDataSource.open();
	        pokemonLearningAbility	
	        	= mPokemonDataSource.getPokemonLearningAbility(abilityId, getResources());
	        mPokemonDataSource.close();
	        
	        mAbilityDetail = new AbilityDetail();
	        mAbilityDetail.setAbilityAttributes(abilityAttributes);
	        mAbilityDetail.setPokemonLearningAbility(pokemonLearningAbility);
        }else{
        	mAbilityDetail = savedInstanceState.getParcelable(ABILITY_DETAIL);
        }
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		mParentView = (ViewGroup)inflater.inflate(R.layout.ability_detail_screen, container, false);		
		AbilityAttributes abilityAttributes = mAbilityDetail.getAbilityAttributes();
		
        TextView abilityName 		= (TextView) mParentView.findViewById(R.id.abilityName);
        TextView battleEffectLabel 	= (TextView) mParentView.findViewById(R.id.battleEffectLabel);        
        TextView battleEffect		= (TextView) mParentView.findViewById(R.id.battleEffect);

        TextView worldEffectLabel	= (TextView) mParentView.findViewById(R.id.worldEffectLabel);
        TextView worldEffect		= (TextView) mParentView.findViewById(R.id.worldEffect);
        
        TextView learningLabel		= (TextView) mParentView.findViewById(R.id.learningLabel);
        
        //Apply data to ability detail views
        
        abilityName.setText(abilityAttributes.getName());
        battleEffect.setText("\t" + abilityAttributes.getBattleEffect());
        
        if(abilityAttributes.getWorldEffect() != null && !("".equals(abilityAttributes.getWorldEffect()))){
	        worldEffect.setText("\t" + abilityAttributes.getWorldEffect());
        }else{
        	((ViewManager)worldEffectLabel.getParent()).removeView(worldEffectLabel);        	
        	((ViewManager)worldEffect.getParent()).removeView(worldEffect);
        	
        	((PokepraiserApplication)getActivity().getApplication()).applyTypeface(worldEffectLabel);
        	((PokepraiserApplication)getActivity().getApplication()).applyTypeface(worldEffect);
        }
        
        //Apply detail to pokemon learning ability

        buildPokemonList(inflater);
        
        //Apply typeface
        ((PokepraiserApplication)getActivity().getApplication()).applyTypeface(new TextView[]{abilityName,
        																		battleEffectLabel,
        																		battleEffect,
        																		worldEffectLabel,
        																		worldEffect,
        																		learningLabel});		
		
		return mParentView;
	}
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putParcelable(ABILITY_DETAIL, mAbilityDetail);
    	
    	super.onSaveInstanceState(savedInstanceState);
    }    
    
	public void buildPokemonList(LayoutInflater inflater){
        LinearLayout learningList = (LinearLayout) mParentView.findViewById(R.id.learningList);
		
		for(int i = 0; i < mAbilityDetail.getPokemonLearningAbility().size(); i++){
			final PokemonInfo thePokemon = mAbilityDetail.getPokemonLearningAbility().get(i);
			
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
