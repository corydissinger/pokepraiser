package com.cd.pokepraiser.activity;

import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
	
	private static final String POKEMON_SEARCH 		= "P";
	private static final String ALL_ATTACKS 		= "O";
	private static final String ALL_ABILITIES 		= "K";
	private static final String ALL_TYPES	 		= "E";
	
	private static boolean IS_TYPE_ONE				= false;
	private static boolean IS_TYPE_TWO				= false;	
	
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
        
        TextView abilityLabel 		= (TextView) findViewById(R.id.abilityLabel);
        TextView typeOneLabel 		= (TextView) findViewById(R.id.typeOne);
        TextView typeTwoLabel 		= (TextView) findViewById(R.id.typeTwo);
        TextView attackOneLabel 	= (TextView) findViewById(R.id.attackOne);
        TextView attackTwoLabel 	= (TextView) findViewById(R.id.attackTwo);
        TextView attackThreeLabel 	= (TextView) findViewById(R.id.attackThree);
        TextView attackFourLabel 	= (TextView) findViewById(R.id.attackFour);        
        
        ((PokepraiserApplication)getApplication()).applyTypeface(new TextView[]{mAbilitySearchButton,
        																		mTypeOneSearchButton,
        																		mTypeTwoSearchButton,
        																		mAttackOneSearchButton,
        																		mAttackTwoSearchButton,
        																		mAttackThreeSearchButton,
        																		mAttackFourSearchButton,        																		
        																		abilityLabel,
        																		typeOneLabel,
        																		typeTwoLabel,
        																		attackOneLabel,
        																		attackTwoLabel,
        																		attackThreeLabel,
        																		attackFourLabel});
        
        applyUserValues();
    }
    
    private void applyUserValues() {
    	if(!mSearchQuery.isEmpty()){
    		mAbilitySearchButton.setText(mAllAbilities.get(mSearchQuery.getAbilityId()).getName());
    		mTypeOneSearchButton.setText(mAllTypes.get(mSearchQuery.getTypeOne()).getName());
    		mTypeTwoSearchButton.setText(mAllTypes.get(mSearchQuery.getTypeTwo()).getName());    
    		mAttackOneSearchButton.setText(mAllAttacks.get(mSearchQuery.getAttackIdOne()).getName());
    		mAttackTwoSearchButton.setText(mAllAttacks.get(mSearchQuery.getAttackIdTwo()).getName());
    		mAttackThreeSearchButton.setText(mAllAttacks.get(mSearchQuery.getAttackIdThree()).getName());
    		mAttackFourSearchButton.setText(mAllAttacks.get(mSearchQuery.getAttackIdFour()).getName());    		
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
    	mTypeSearch.show(getSupportFragmentManager(), null);
    	
    	if(R.id.typeOneSearch == v.getId()){
    		IS_TYPE_ONE = true;
    	}else if(R.id.typeTwoSearch == v.getId()){
    		IS_TYPE_TWO = true;    		
    	}
    }    

    public void openAttackSearchDialog(View v){
    	final int originButton = Integer.parseInt((String)v.getTag());
    	mAttackSearch.setOriginButton(originButton);
    	mAttackSearch.show(getSupportFragmentManager(), null);
    }    
    
    public void searchPokemon(View v){
    	new SearchTask().execute();
    }
    
	@Override
	public void onAbilityItemClick(AbilitySearchDialog dialog) {
		final AbilityInfo selectedAbility = mAllAbilities.get(dialog.getSelectedItem());
		mSearchQuery.setAbilityId(selectedAbility.getAbilityDbId());
		mAbilitySearchButton.setText(selectedAbility.getName());
		dialog.dismiss();
	}

	@Override
	public void onTypeItemClick(TypeSearchDialog dialog) {
		final TypeInfo selectedType = mAllTypes.get(dialog.getSelectedItem());
		
		if(IS_TYPE_ONE){
			mSearchQuery.setTypeOne(selectedType.getDbId());
			mTypeOneSearchButton.setText(selectedType.getName());
		}else if(IS_TYPE_TWO){
			mSearchQuery.setTypeTwo(selectedType.getDbId());
			mTypeTwoSearchButton.setText(selectedType.getName());			
		}

		IS_TYPE_ONE = false;		
		IS_TYPE_TWO = false;
		dialog.dismiss();
	}

	@Override
	public void onAttackItemClick(AttackSearchDialog dialog) {
		final AttackInfo selectedAttack = mAllAttacks.get(dialog.getSelectedItem());
		final int originButton			= dialog.getOriginButton();
		
		if(0 == originButton){
			mSearchQuery.setAttackIdOne(selectedAttack.getAttackDbId());
			mAttackOneSearchButton.setText(selectedAttack.getName());
		}else if(1 == originButton){
			mSearchQuery.setAttackIdTwo(selectedAttack.getAttackDbId());
			mAttackTwoSearchButton.setText(selectedAttack.getName());			
		}else if(2 == originButton){
			mSearchQuery.setAttackIdThree(selectedAttack.getAttackDbId());
			mAttackThreeSearchButton.setText(selectedAttack.getName());			
		}else if(3 == originButton){
			mSearchQuery.setAttackIdFour(selectedAttack.getAttackDbId());
			mAttackFourSearchButton.setText(selectedAttack.getName());			
		} 
		
		dialog.dismiss();
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
