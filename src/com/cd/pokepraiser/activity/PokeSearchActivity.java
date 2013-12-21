package com.cd.pokepraiser.activity;

import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.AbilityInfo;
import com.cd.pokepraiser.data.AttackInfo;
import com.cd.pokepraiser.data.PokemonInfo;
import com.cd.pokepraiser.data.PokemonSearchQuery;
import com.cd.pokepraiser.data.TypeInfo;
import com.cd.pokepraiser.db.dao.AbilitiesDataSource;
import com.cd.pokepraiser.db.dao.AttacksDataSource;
import com.cd.pokepraiser.db.dao.PokemonDataSource;
import com.cd.pokepraiser.dialog.AbilitySearchDialog;
import com.cd.pokepraiser.dialog.AbilitySearchDialog.AbilitySearchDialogListener;
import com.cd.pokepraiser.dialog.AttackSearchDialog;
import com.cd.pokepraiser.dialog.AttackSearchDialog.AttackSearchDialogListener;
import com.cd.pokepraiser.dialog.ErrorDialog;
import com.cd.pokepraiser.dialog.PokemonSearchDialog;
import com.cd.pokepraiser.dialog.TypeSearchDialog;
import com.cd.pokepraiser.dialog.TypeSearchDialog.TypeSearchDialogListener;
import com.cd.pokepraiser.util.ExtrasConstants;

public class PokeSearchActivity extends PokepraiserActivity implements AbilitySearchDialogListener, 
																	   TypeSearchDialogListener, 
																	   AttackSearchDialogListener{

	private AbilitiesDataSource mAbilitiesDataSource;	
	private AttacksDataSource mAttacksDataSource;	
	private PokemonDataSource mPokemonDataSource;
	
	private PokemonSearchQuery 	mSearchQuery;
	private List<AbilityInfo> 	mAllAbilities;
	private List<AttackInfo>	mAllAttacks;
	private List<TypeInfo>		mAllTypes;
	
	private AbilitySearchDialog mAbilitySearch;
	private TypeSearchDialog 	mTypeSearch;
	private AttackSearchDialog 	mAttackSearch;
	private PokemonSearchDialog	mPokemonSearch;	
	
	private Button				mAbilitySearchButton;
	
	private Button				mTypeOneSearchButton;
	private Button				mTypeTwoSearchButton;
	
	private Button				mAttackOneSearchButton;
	private Button				mAttackTwoSearchButton;
	private Button				mAttackThreeSearchButton;
	private Button				mAttackFourSearchButton;
	
	private ImageView			mAbilityCancel;
	
	private ImageView			mTypeOneCancel;
	private ImageView			mTypeTwoCancel;
	
	private ImageView			mAttackOneCancel;
	private ImageView			mAttackTwoCancel;
	private ImageView			mAttackThreeCancel;
	private ImageView			mAttackFourCancel;	
	
	private static final String POKEMON_SEARCH 		= "P";
	private static final String ALL_ATTACKS 		= "O";
	private static final String ALL_ABILITIES 		= "K";
	private static final String ALL_TYPES	 		= "E";
	
    @SuppressWarnings("unchecked")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_search_screen);

        mPokemonDataSource 	= new PokemonDataSource(((PokepraiserApplication)getApplication()).getDatabaseReference());        
        
        if(savedInstanceState == null){
        	mSearchQuery = new PokemonSearchQuery();
        	
            mAbilitiesDataSource = new AbilitiesDataSource(((PokepraiserApplication)getApplication()).getDatabaseReference());
            mAttacksDataSource 	= new AttacksDataSource(((PokepraiserApplication)getApplication()).getDatabaseReference());
            
            mAbilitiesDataSource.open();
            mAllAbilities = mAbilitiesDataSource.getAbilityList();
            mAbilitiesDataSource.close();
            
            mAttacksDataSource.open();
            mAllAttacks = mAttacksDataSource.getAttackInfoList();
            mAllTypes	= mAttacksDataSource.getAllTypeInfo();
            mAttacksDataSource.close();            
        }else{
        	mSearchQuery = savedInstanceState.getParcelable(POKEMON_SEARCH);
        	mAllAbilities = (List<AbilityInfo>) savedInstanceState.getSerializable(ALL_ABILITIES);
        	mAllAttacks = (List<AttackInfo>) savedInstanceState.getSerializable(ALL_ATTACKS);
        	mAllTypes	= (List<TypeInfo>) savedInstanceState.getSerializable(ALL_TYPES);
        }
        
        mAttackSearch		= new AttackSearchDialog(mAllAttacks);
        mAbilitySearch 		= new AbilitySearchDialog(mAllAbilities);
        mTypeSearch			= new TypeSearchDialog(mAllTypes);
        mPokemonSearch		= new PokemonSearchDialog();
        
        mAbilitySearchButton 		= (Button) findViewById(R.id.abilitySearch);
        mTypeOneSearchButton 		= (Button) findViewById(R.id.typeOneSearch);
        mTypeTwoSearchButton 		= (Button) findViewById(R.id.typeTwoSearch);
        mAttackOneSearchButton		= (Button) findViewById(R.id.attackOneSearch);
        mAttackTwoSearchButton		= (Button) findViewById(R.id.attackTwoSearch);
        mAttackThreeSearchButton	= (Button) findViewById(R.id.attackThreeSearch);
        mAttackFourSearchButton		= (Button) findViewById(R.id.attackFourSearch);        
        
        mAbilityCancel				= (ImageView) findViewById(R.id.abilityCancel);
        
        mTypeOneCancel				= (ImageView) findViewById(R.id.typeOneCancel);
        mTypeTwoCancel				= (ImageView) findViewById(R.id.typeTwoCancel);
        
        mAttackOneCancel			= (ImageView) findViewById(R.id.attackOneCancel);
        mAttackTwoCancel			= (ImageView) findViewById(R.id.attackTwoCancel);
        mAttackThreeCancel			= (ImageView) findViewById(R.id.attackThreeCancel);
        mAttackFourCancel			= (ImageView) findViewById(R.id.attackFourCancel);        
        
        final ImageView [] theClickableImages = new ImageView[]{mAbilityCancel, 
        														mTypeOneCancel, 
        														mTypeTwoCancel, 
        														mAttackOneCancel, 
        														mAttackTwoCancel, 
        														mAttackThreeCancel, 
        														mAttackFourCancel};
        
        for(ImageView clickableImage : theClickableImages){
        	clickableImage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					handleCancelClick(v);
				}
			});
        }
        
        ((PokepraiserApplication)getApplication()).overrideFonts(findViewById(R.id.searchParent));
        
        applyUserValues();
    }
    
    private void applyUserValues() {
    	if(!mSearchQuery.isEmpty()){
    		
    		if(mSearchQuery.getAbilityId() != -1){
    			mAbilitySearchButton.setText(mAllAbilities.get(mSearchQuery.getAbilityId() - 1).getName());
				mAbilityCancel.setVisibility(View.VISIBLE);
    		}
    		
    		if(mSearchQuery.getTypeOne() != -1){
    			mTypeOneSearchButton.setText(mAllTypes.get(mSearchQuery.getTypeOne()).getName());
				mTypeOneCancel.setVisibility(View.VISIBLE);    			
    		}
    			
    		if(mSearchQuery.getTypeTwo() != -1){
    			mTypeTwoSearchButton.setText(mAllTypes.get(mSearchQuery.getTypeTwo()).getName());
				mTypeTwoCancel.setVisibility(View.VISIBLE);    			
    		}
    			
    		//The reason we do attack_id - 1 is because attack ids start at 1, not 0
    		if(mSearchQuery.getAttackIdOne() != -1){
    			mAttackOneSearchButton.setText(mAllAttacks.get(mSearchQuery.getAttackIdOne() - 1).getName());
				mAttackOneCancel.setVisibility(View.VISIBLE);    			
    		}
    			
			if(mSearchQuery.getAttackIdTwo() != -1){
				mAttackTwoSearchButton.setText(mAllAttacks.get(mSearchQuery.getAttackIdTwo() - 1).getName());
				mAttackTwoCancel.setVisibility(View.VISIBLE);				
			}
				
			if(mSearchQuery.getAttackIdThree() != -1){
				mAttackThreeSearchButton.setText(mAllAttacks.get(mSearchQuery.getAttackIdThree() - 1).getName());
				mAttackThreeCancel.setVisibility(View.VISIBLE);				
			}
				
			if(mSearchQuery.getAttackIdFour() != -1){
				mAttackFourSearchButton.setText(mAllAttacks.get(mSearchQuery.getAttackIdFour() - 1).getName());
				mAttackFourCancel.setVisibility(View.VISIBLE);
			}
    	}
	}

	@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putParcelable(POKEMON_SEARCH, mSearchQuery);
    	savedInstanceState.putSerializable(ALL_ABILITIES, (Serializable)mAllAbilities);
    	savedInstanceState.putSerializable(ALL_ATTACKS, (Serializable)mAllAttacks);
    	savedInstanceState.putSerializable(ALL_TYPES, (Serializable)mAllTypes);    	
    	
    	super.onSaveInstanceState(savedInstanceState);
    }    
    
    public void openAbilitySearchDialog(View v){
    	mAbilitySearch.show(getSupportFragmentManager(), null);
    }
    
    public void openTypeSearchDialog(View v){
    	mTypeSearch.setOriginButton(v.getId());
    	mTypeSearch.show(getSupportFragmentManager(), null);
    }    

    public void openAttackSearchDialog(View v){
    	mAttackSearch.setOriginButton(v.getId());
    	mAttackSearch.show(getSupportFragmentManager(), null);
    }    
    
    public void searchPokemon(View v){
    	if(mSearchQuery.isEmpty())
    		new ErrorDialog(R.string.search_not_found).show(getSupportFragmentManager(), null);    		
    	else
    		new SearchTask().execute();
    }
    
	@Override
	public void onAbilityItemClick(AbilitySearchDialog dialog) {
		final AbilityInfo selectedAbility = dialog.getSelectedItem();
		mSearchQuery.setAbilityId(selectedAbility.getAbilityDbId());
		mAbilitySearchButton.setText(selectedAbility.getName());
		mAbilityCancel.setVisibility(View.VISIBLE);
		dialog.dismiss();
	}

	@Override
	public void onTypeItemClick(TypeSearchDialog dialog) {
		final TypeInfo selectedType = mAllTypes.get(dialog.getSelectedItem());
		final int originButtonId	= dialog.getOriginButton();
		
		switch(originButtonId){
			case R.id.typeOneSearch:
								mSearchQuery.setTypeOne(selectedType.getDbId());
								mTypeOneSearchButton.setText(selectedType.getName());	
								mTypeOneCancel.setVisibility(View.VISIBLE);
								break;
									
			case R.id.typeTwoSearch:
								mSearchQuery.setTypeTwo(selectedType.getDbId());
								mTypeTwoSearchButton.setText(selectedType.getName());
								mTypeTwoCancel.setVisibility(View.VISIBLE);
								break;									
		}
		
		dialog.dismiss();
	}

	@Override
	public void onAttackItemClick(AttackSearchDialog dialog) {
		final AttackInfo selectedAttack = mAllAttacks.get(dialog.getSelectedItem());
		final int originButtonId		= dialog.getOriginButton();

		switch(originButtonId){
			case R.id.attackOneSearch:
							mSearchQuery.setAttackIdOne(selectedAttack.getAttackDbId());
							mAttackOneSearchButton.setText(selectedAttack.getName());
							mAttackOneCancel.setVisibility(View.VISIBLE);
							break;
							
			case R.id.attackTwoSearch:
							mSearchQuery.setAttackIdTwo(selectedAttack.getAttackDbId());
							mAttackTwoSearchButton.setText(selectedAttack.getName());
							mAttackTwoCancel.setVisibility(View.VISIBLE);							
							break;
							
			case R.id.attackThreeSearch:
							mSearchQuery.setAttackIdThree(selectedAttack.getAttackDbId());
							mAttackThreeSearchButton.setText(selectedAttack.getName());
							mAttackThreeCancel.setVisibility(View.VISIBLE);							
							break;
							
			case R.id.attackFourSearch:
							mSearchQuery.setAttackIdFour(selectedAttack.getAttackDbId());
							mAttackFourSearchButton.setText(selectedAttack.getName());
							mAttackFourCancel.setVisibility(View.VISIBLE);							
							break;							
		}
		
		dialog.dismiss();
	}
	
	public void handleCancelClick(View v){
		final int viewId				= v.getId();
		
		switch(viewId){
			case R.id.abilityCancel:
								v.setVisibility(View.INVISIBLE);
								mAbilitySearchButton.setText(R.string.none);
								mSearchQuery.setAbilityId(-1);
								break;
								
			case R.id.typeOneCancel:
								v.setVisibility(View.INVISIBLE);
								mTypeOneSearchButton.setText(R.string.none);
								mSearchQuery.setTypeOne(-1);				
								break;
				
			case R.id.typeTwoCancel:
								v.setVisibility(View.INVISIBLE);
								mTypeTwoSearchButton.setText(R.string.none);
								mSearchQuery.setTypeTwo(-1);				
								break;
				
			case R.id.attackOneCancel:
								v.setVisibility(View.INVISIBLE);
								mAttackOneSearchButton.setText(R.string.none);
								mSearchQuery.setAttackIdOne(-1);				
								break;
				
			case R.id.attackTwoCancel:
								v.setVisibility(View.INVISIBLE);
								mAttackTwoSearchButton.setText(R.string.none);
								mSearchQuery.setAttackIdTwo(-1);				
								break;
				
			case R.id.attackThreeCancel:
								v.setVisibility(View.INVISIBLE);
								mAttackThreeSearchButton.setText(R.string.none);
								mSearchQuery.setAttackIdThree(-1);				
								break;
				
			case R.id.attackFourCancel:
								v.setVisibility(View.INVISIBLE);
								mAttackFourSearchButton.setText(R.string.none);
								mSearchQuery.setAttackIdFour(-1);				
								break;				
		}
	}
	
	private class SearchTask extends AsyncTask<Void, Void, List<PokemonInfo>> {

		@Override
		protected void onPreExecute() {
			mPokemonSearch.show(getSupportFragmentManager(), null);
		}
		
		@Override
		protected List<PokemonInfo> doInBackground(Void... params) {
			mPokemonDataSource.open();
			final List<PokemonInfo> searchResults = mPokemonDataSource.getPokemonSearch(mSearchQuery, getResources());
			mPokemonDataSource.close();
			return searchResults;
		}
		
		protected void onPostExecute(final List<PokemonInfo> pokemonInfo) {
			mPokemonSearch.dismiss();
			
			if(pokemonInfo.size() > 0){
		    	Intent i = new Intent(PokeSearchActivity.this, PokemonListActivity.class);
				i.putExtra(ExtrasConstants.POKEMON_SEARCH, (Serializable)pokemonInfo);
				startActivity(i);
			}else{
				new ErrorDialog(R.string.search_not_found).show(getSupportFragmentManager(), null);
			}
		}
	}
}
