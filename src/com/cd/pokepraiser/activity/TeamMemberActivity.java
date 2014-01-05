package com.cd.pokepraiser.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.AbilityInfo;
import com.cd.pokepraiser.data.AttackInfo;
import com.cd.pokepraiser.data.PokemonAttackInfo;
import com.cd.pokepraiser.data.PokemonAttributes;
import com.cd.pokepraiser.data.PokemonDetail;
import com.cd.pokepraiser.data.TeamMemberAttributes;
import com.cd.pokepraiser.db.dao.AbilitiesDataSource;
import com.cd.pokepraiser.db.dao.AttacksDataSource;
import com.cd.pokepraiser.db.dao.PokemonDataSource;
import com.cd.pokepraiser.db.dao.TeamDataSource;
import com.cd.pokepraiser.dialog.AbilitySearchDialog;
import com.cd.pokepraiser.dialog.AbilitySearchDialog.AbilitySearchDialogListener;
import com.cd.pokepraiser.dialog.AttackSearchDialog;
import com.cd.pokepraiser.dialog.AttackSearchDialog.AttackSearchDialogListener;
import com.cd.pokepraiser.filter.EVFilter;
import com.cd.pokepraiser.fragment.MemberStatsTable;
import com.cd.pokepraiser.util.AttackUtils;
import com.cd.pokepraiser.util.ExtrasConstants;
import com.cd.pokepraiser.util.TypeUtils;

public class TeamMemberActivity extends PokepraiserActivity implements AbilitySearchDialogListener, AttackSearchDialogListener{

	private TeamMemberAttributes 	mMemberAttributes;
	private PokemonDetail			mPokemonDetail;
	private ArrayList<AttackInfo>	mAttackInfoList;
	
	private PokemonDataSource 	mPokemonDataSource;
	private TeamDataSource 	  	mTeamDataSource;	
	private AbilitiesDataSource mAbilitiesDataSource;	
	private AttacksDataSource 	mAttacksDataSource;	

	private AbilitySearchDialog mAbilitySearch;
	private AttackSearchDialog 	mAttackSearch;	
	
	private Button				mAbilityModifyButton;
	private ImageView			mAbilityCancel;
	
	private Button				mAttackOneSearchButton;
	private Button				mAttackTwoSearchButton;
	private Button				mAttackThreeSearchButton;
	private Button				mAttackFourSearchButton;	
	
	private ImageView			mAttackOneCancel;
	private ImageView			mAttackTwoCancel;
	private ImageView			mAttackThreeCancel;
	private ImageView			mAttackFourCancel;	

	private EditText			mHpEvsEdit;
	private EditText			mAtkEvsEdit;
	private EditText			mDefEvsEdit;
	private EditText			mSpatkEvsEdit;
	private EditText			mSpdefEvsEdit;
	private EditText			mSpeEvsEdit;	
	
	private static final String [] mStatTableTags = new String [] {"hp50", "hp100", 
																  "atk50", "atk100", 
															  	  "def50", "def100", 
															  	  "spatk50", "spatk100",
															  	  "spdef50", "spdef100",
															  	  "spe50", "spe100"};
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_member_screen);		
		
        final Intent receivedIntent = getIntent();
        mMemberAttributes = (TeamMemberAttributes) receivedIntent.getSerializableExtra(ExtrasConstants.MEMBER_ID);
		
        if(mMemberAttributes == null)
        	finish();
        
    	PokemonAttributes pokemonAttributes;
    	AbilityInfo []	  pokemonAbilities;
    	List<PokemonAttackInfo> pokemonAttacks;        

		mTeamDataSource = new TeamDataSource(((PokepraiserApplication)getApplication()).getTeamdbDatabaseReference());    	
    	
		if(savedInstanceState == null){
            mPokemonDataSource 	= new PokemonDataSource(((PokepraiserApplication)getApplication()).getPokedbDatabaseReference());
            mAbilitiesDataSource = new AbilitiesDataSource(((PokepraiserApplication)getApplication()).getPokedbDatabaseReference());
            mAttacksDataSource 	= new AttacksDataSource(((PokepraiserApplication)getApplication()).getPokedbDatabaseReference());        	
			
	        mPokemonDataSource.open();
	        pokemonAttributes = mPokemonDataSource.getPokemonAttributes(mMemberAttributes.getPokemonId(), getResources());
	        mPokemonDataSource.close();        
	        
	        mAbilitiesDataSource.open();
	        pokemonAbilities 
	        	= mAbilitiesDataSource.getAbilitiesLearnedBy(new int []{pokemonAttributes.getAbOne(),
	        														   pokemonAttributes.getAbTwo(),
	        														   pokemonAttributes.getAbHa()});
	        mAbilitiesDataSource.close();
	        
			mAttacksDataSource.open();
			pokemonAttacks = mAttacksDataSource.getPokemonAttackInfoList(pokemonAttributes.getDexNo(), pokemonAttributes.getAltForm());
			mAttacksDataSource.close();
            
			mAttackInfoList = new ArrayList<AttackInfo>(AttackUtils.convertPokemonAttackInfoToAttackInfo(pokemonAttacks));
			
        	mPokemonDetail = new PokemonDetail();
        	mPokemonDetail.setPokemonAttributes(pokemonAttributes);
        	mPokemonDetail.setPokemonAbilities(pokemonAbilities);
        	mPokemonDetail.setPokemonAttacks(pokemonAttacks);            
		}else{
			mMemberAttributes = (TeamMemberAttributes) savedInstanceState.getSerializable(ExtrasConstants.MEMBER_ID);
			mPokemonDetail = (PokemonDetail) savedInstanceState.getParcelable(ExtrasConstants.POKEMON_ID);
			mAttackInfoList = (ArrayList<AttackInfo>) savedInstanceState.getSerializable(ExtrasConstants.ATTACK_ID);
			
        	pokemonAttributes 	= mPokemonDetail.getPokemonAttributes();
        	pokemonAbilities  	= mPokemonDetail.getPokemonAbilities();
		}
		
		mAbilitySearch				= new AbilitySearchDialog(Arrays.asList(pokemonAbilities));
		mAttackSearch				= new AttackSearchDialog(mAttackInfoList);
		
        mAbilityCancel				= (ImageView) findViewById(R.id.abilityCancel);		
        mAbilityModifyButton 		= (Button) findViewById(R.id.abilitySearch);		
		
        mAttackOneSearchButton		= (Button) findViewById(R.id.attackOneSearch);
        mAttackTwoSearchButton		= (Button) findViewById(R.id.attackTwoSearch);
        mAttackThreeSearchButton	= (Button) findViewById(R.id.attackThreeSearch);
        mAttackFourSearchButton		= (Button) findViewById(R.id.attackFourSearch);		
		
        mAttackOneCancel			= (ImageView) findViewById(R.id.attackOneCancel);
        mAttackTwoCancel			= (ImageView) findViewById(R.id.attackTwoCancel);
        mAttackThreeCancel			= (ImageView) findViewById(R.id.attackThreeCancel);
        mAttackFourCancel			= (ImageView) findViewById(R.id.attackFourCancel);		
		
        mHpEvsEdit					= (EditText) findViewById(R.id.hpEvs);
        mAtkEvsEdit					= (EditText) findViewById(R.id.atkEvs);
        mDefEvsEdit					= (EditText) findViewById(R.id.defEvs);
        mSpatkEvsEdit				= (EditText) findViewById(R.id.spatkEvs);
        mSpdefEvsEdit				= (EditText) findViewById(R.id.spdefEvs);
        mSpeEvsEdit					= (EditText) findViewById(R.id.speEvs);        
        
        TextView dexNo		 		= (TextView) findViewById(R.id.dexNo);
        TextView pokemonName		= (TextView) findViewById(R.id.pokemonName);
        
        ImageView pokemonPicture	= (ImageView) findViewById(R.id.pokemonPicture);
        
        ImageView typeOne			= (ImageView) findViewById(R.id.typeOne);
        ImageView typeTwo			= (ImageView) findViewById(R.id.typeTwo);
        
        TextView baseHp			= (TextView) findViewById(R.id.baseHp);
        TextView baseAtk		= (TextView) findViewById(R.id.baseAtk);
        TextView baseDef		= (TextView) findViewById(R.id.baseDef);
        TextView baseSpatk		= (TextView) findViewById(R.id.baseSpatk);
        TextView baseSpdef		= (TextView) findViewById(R.id.baseSpdef);
        TextView baseSpe		= (TextView) findViewById(R.id.baseSpe);        
        
        dexNo.setText(Integer.toString(pokemonAttributes.getDexNo()));
        pokemonName.setText(pokemonAttributes.getName());
        
        pokemonPicture.setImageResource(pokemonAttributes.getImgDrawable());
        
        //Make the type images and draw them
        typeOne.setImageResource(TypeUtils.getTypeDrawableId(pokemonAttributes.getTypeOne()));
        
        if(pokemonAttributes.getTypeTwo() != 0){
            typeTwo.setImageResource(TypeUtils.getTypeDrawableId(pokemonAttributes.getTypeTwo()));        	
        }        
		
        final ImageView [] theClickableImages = new ImageView[]{mAbilityCancel, 
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
		
		final EditText [] theEditTexts = new EditText[]{mHpEvsEdit,
														mAtkEvsEdit,
														mDefEvsEdit,
														mSpatkEvsEdit,
														mSpdefEvsEdit,
														mSpeEvsEdit};
		
		for(EditText editableText : theEditTexts){
			editableText.setFilters(new EVFilter[]{new EVFilter()});
			
			editableText.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
				
				@Override
				public void afterTextChanged(Editable s) {
					final EditText editText = (EditText) getCurrentFocus();
					final String newVal = s.toString();
					
					if(newVal.length() > 0){
						final int evs = Integer.parseInt(newVal);
						handleEVChanged(editText.getId(), evs);
					}
				}
			});
		}
        
		baseHp.setText(Integer.toString(pokemonAttributes.getBsHp()));
		baseAtk.setText(Integer.toString(pokemonAttributes.getBsAtk()));
		baseDef.setText(Integer.toString(pokemonAttributes.getBsDef()));
		baseSpatk.setText(Integer.toString(pokemonAttributes.getBsSpatk()));
		baseSpdef.setText(Integer.toString(pokemonAttributes.getBsSpdef()));
		baseSpe.setText(Integer.toString(pokemonAttributes.getBsSpd()));
		
		createStatTableFragments();
		
        ((PokepraiserApplication)getApplication()).overrideFonts(findViewById(android.R.id.content));		
	}

	@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putSerializable(ExtrasConstants.MEMBER_ID, mMemberAttributes);    	
    	savedInstanceState.putParcelable(ExtrasConstants.POKEMON_ID, mPokemonDetail);
    	savedInstanceState.putSerializable(ExtrasConstants.ATTACK_ID, mAttackInfoList);    
    	
    	super.onSaveInstanceState(savedInstanceState);
    }

	@Override
	public void onAttackItemClick(AttackSearchDialog dialog) {
		final AttackInfo selectedAttack = mAttackInfoList.get(dialog.getSelectedItem());      
		final int originButtonId		= dialog.getOriginButton();                       
		                                                                                  
		switch(originButtonId){                                                           
			case R.id.attackOneSearch:         
							mMemberAttributes.setMoveOne(selectedAttack.getAttackDbId());
							updateMemberData();  
							mAttackOneSearchButton.setText(selectedAttack.getName());     
							mAttackOneCancel.setVisibility(View.VISIBLE);                 
							break;                                                        
							                                                              
			case R.id.attackTwoSearch:                                                    
							mMemberAttributes.setMoveTwo(selectedAttack.getAttackDbId());
							updateMemberData();  
							mAttackTwoSearchButton.setText(selectedAttack.getName());     
							mAttackTwoCancel.setVisibility(View.VISIBLE);					
							break;                                                        
							                                                              
			case R.id.attackThreeSearch:                                                  
							mMemberAttributes.setMoveThree(selectedAttack.getAttackDbId());
							updateMemberData();
							mAttackThreeSearchButton.setText(selectedAttack.getName());   
							mAttackThreeCancel.setVisibility(View.VISIBLE);					
							break;                                                        
							                                                              
			case R.id.attackFourSearch:                                                   
							mMemberAttributes.setMoveFour(selectedAttack.getAttackDbId());
							updateMemberData(); 
							mAttackFourSearchButton.setText(selectedAttack.getName());    
							mAttackFourCancel.setVisibility(View.VISIBLE);					
							break;							                              
		}                                                                                 
		                                                                                  
		dialog.dismiss();                                                                 
	}

	@Override
	public void onAbilityItemClick(AbilitySearchDialog dialog) {
		AbilityInfo selectedAbility = dialog.getSelectedItem();
		
		mMemberAttributes.setAbility(selectedAbility.getAbilityDbId());
		mAbilityModifyButton.setText(selectedAbility.getName());
		mAbilityCancel.setVisibility(View.VISIBLE);		
		updateMemberData();
		
		dialog.dismiss();		
	}	
	
    public void openAbilitySearchDialog(View v){
    	mAbilitySearch.show(getSupportFragmentManager(), null);
    }
    
    public void openAttackSearchDialog(View v){
    	mAttackSearch.setOriginButton(v.getId());
    	mAttackSearch.show(getSupportFragmentManager(), null);
    }
    
	public void handleCancelClick(View v){
		final int viewId				= v.getId();
		
		switch(viewId){
			case R.id.abilityCancel:
								v.setVisibility(View.INVISIBLE);
								mAbilityModifyButton.setText(R.string.none);
								mMemberAttributes.setAbility(-1);
								updateMemberData();
								break;
				
			case R.id.attackOneCancel:
								v.setVisibility(View.INVISIBLE);
								mAttackOneSearchButton.setText(R.string.none);
								mMemberAttributes.setMoveOne(-1);
								updateMemberData();
								break;
				
			case R.id.attackTwoCancel:
								v.setVisibility(View.INVISIBLE);
								mAttackTwoSearchButton.setText(R.string.none);
								mMemberAttributes.setMoveTwo(-1);
								updateMemberData();
								break;
				
			case R.id.attackThreeCancel:
								v.setVisibility(View.INVISIBLE);
								mAttackThreeSearchButton.setText(R.string.none);
								mMemberAttributes.setMoveThree(-1);
								updateMemberData();
								break;
				
			case R.id.attackFourCancel:
								v.setVisibility(View.INVISIBLE);
								mAttackFourSearchButton.setText(R.string.none);
								mMemberAttributes.setMoveFour(-1);
								updateMemberData();
								break;				
		}	
	}    
	
	public void toggleStatTable(View v){
		final String state = (String)((TextView)v).getText();
		final boolean show = getString(R.string.show).equals(state);
		final int visibility = show ? View.VISIBLE : View.GONE;
		final int id = v.getId();
		View container = null;		
		
		switch(id){
			case R.id.hpAt50:
				container = findViewById(R.id.hpStatsFragmentContainer50);
				break;
				
			case R.id.hpAt100:
				container = findViewById(R.id.hpStatsFragmentContainer100);				
				break;
				
			case R.id.atkAt50:
				container = findViewById(R.id.atkStatsFragmentContainer50);				
				break;
				
			case R.id.atkAt100:
				container = findViewById(R.id.atkStatsFragmentContainer100);				
				break;
				
			case R.id.defAt50:
				container = findViewById(R.id.defStatsFragmentContainer50);				
				break;
				
			case R.id.defAt100:
				container = findViewById(R.id.defStatsFragmentContainer100);				
				break;	
				
			case R.id.spatkAt50:
				container = findViewById(R.id.spatkStatsFragmentContainer50);				
				break;
				
			case R.id.spatkAt100:
				container = findViewById(R.id.spatkStatsFragmentContainer100);				
				break;
				
			case R.id.spdefAt50:
				container = findViewById(R.id.spdefStatsFragmentContainer50);				
				break;
				
			case R.id.spdefAt100:
				container = findViewById(R.id.spdefStatsFragmentContainer100);				
				break;
				
			case R.id.speAt50:
				container = findViewById(R.id.speStatsFragmentContainer50);				
				break;
				
			case R.id.speAt100:
				container = findViewById(R.id.speStatsFragmentContainer100);				
				break;				
		}

		final String newText = show ? getString(R.string.hide) : getString(R.string.show);
		((TextView)v).setText(newText);
		container.setVisibility(visibility);		
	}

	private void createStatTableFragments(){
		final int [] parentViews = new int[]{R.id.hpStatsFragmentContainer50, R.id.hpStatsFragmentContainer100,
											 R.id.atkStatsFragmentContainer50, R.id.atkStatsFragmentContainer100,
											 R.id.defStatsFragmentContainer50, R.id.defStatsFragmentContainer100,
											 R.id.spatkStatsFragmentContainer50, R.id.spatkStatsFragmentContainer100,
											 R.id.spdefStatsFragmentContainer50, R.id.spdefStatsFragmentContainer100,
											 R.id.speStatsFragmentContainer50, R.id.speStatsFragmentContainer100};
		
		for(int i = 0; i < parentViews.length; i++){
			final Bundle args = new Bundle();
			MemberStatsTable fragment = new MemberStatsTable();
			
			if(i == 0){
				args.putInt(ExtrasConstants.LEVEL, 50);
				args.putBoolean(ExtrasConstants.IS_HP, true);
				args.putInt(ExtrasConstants.EVS, mMemberAttributes.getHp());
				args.putInt(ExtrasConstants.BASE_STAT, mPokemonDetail.getPokemonAttributes().getBsHp());
			}else if(i == 1){
				args.putInt(ExtrasConstants.LEVEL, 100);
				args.putBoolean(ExtrasConstants.IS_HP, true);
				args.putInt(ExtrasConstants.EVS, mMemberAttributes.getHp());
				args.putInt(ExtrasConstants.BASE_STAT, mPokemonDetail.getPokemonAttributes().getBsHp());				
			}else if(i == 2){
				args.putInt(ExtrasConstants.LEVEL, 50);
				args.putBoolean(ExtrasConstants.IS_HP, false);
				args.putInt(ExtrasConstants.EVS, mMemberAttributes.getAtk());
				args.putInt(ExtrasConstants.BASE_STAT, mPokemonDetail.getPokemonAttributes().getBsAtk());				
			}else if(i == 3){
				args.putInt(ExtrasConstants.LEVEL, 100);
				args.putBoolean(ExtrasConstants.IS_HP, false);
				args.putInt(ExtrasConstants.EVS, mMemberAttributes.getAtk());
				args.putInt(ExtrasConstants.BASE_STAT, mPokemonDetail.getPokemonAttributes().getBsAtk());				
			}else if(i == 4){
				args.putInt(ExtrasConstants.LEVEL, 50);
				args.putBoolean(ExtrasConstants.IS_HP, false);
				args.putInt(ExtrasConstants.EVS, mMemberAttributes.getDef());
				args.putInt(ExtrasConstants.BASE_STAT, mPokemonDetail.getPokemonAttributes().getBsDef());				
			}else if(i == 5){
				args.putInt(ExtrasConstants.LEVEL, 100);
				args.putBoolean(ExtrasConstants.IS_HP, false);
				args.putInt(ExtrasConstants.EVS, mMemberAttributes.getDef());
				args.putInt(ExtrasConstants.BASE_STAT, mPokemonDetail.getPokemonAttributes().getBsDef());				
			}else if(i ==6){
				args.putInt(ExtrasConstants.LEVEL, 50);
				args.putBoolean(ExtrasConstants.IS_HP, false);
				args.putInt(ExtrasConstants.EVS, mMemberAttributes.getSpatk());
				args.putInt(ExtrasConstants.BASE_STAT, mPokemonDetail.getPokemonAttributes().getBsSpatk());				
			}else if(i == 7){
				args.putInt(ExtrasConstants.LEVEL, 100);
				args.putBoolean(ExtrasConstants.IS_HP, false);
				args.putInt(ExtrasConstants.EVS, mMemberAttributes.getSpatk());
				args.putInt(ExtrasConstants.BASE_STAT, mPokemonDetail.getPokemonAttributes().getBsSpatk());				
			}else if(i == 8){
				args.putInt(ExtrasConstants.LEVEL, 50);
				args.putBoolean(ExtrasConstants.IS_HP, false);
				args.putInt(ExtrasConstants.EVS, mMemberAttributes.getSpdef());
				args.putInt(ExtrasConstants.BASE_STAT, mPokemonDetail.getPokemonAttributes().getBsSpdef());				
			}else if(i == 9){
				args.putInt(ExtrasConstants.LEVEL, 100);
				args.putBoolean(ExtrasConstants.IS_HP, false);
				args.putInt(ExtrasConstants.EVS, mMemberAttributes.getSpdef());
				args.putInt(ExtrasConstants.BASE_STAT, mPokemonDetail.getPokemonAttributes().getBsSpdef());				
			}else if(i == 10){
				args.putInt(ExtrasConstants.LEVEL, 50);
				args.putBoolean(ExtrasConstants.IS_HP, false);
				args.putInt(ExtrasConstants.EVS, mMemberAttributes.getSpe());
				args.putInt(ExtrasConstants.BASE_STAT, mPokemonDetail.getPokemonAttributes().getBsSpd());				
			}else if(i == 11){
				args.putInt(ExtrasConstants.LEVEL, 100);
				args.putBoolean(ExtrasConstants.IS_HP, false);
				args.putInt(ExtrasConstants.EVS, mMemberAttributes.getSpe());
				args.putInt(ExtrasConstants.BASE_STAT, mPokemonDetail.getPokemonAttributes().getBsSpd());				
			}
			
			fragment.setArguments(args);
			FragmentTransaction txn = getSupportFragmentManager().beginTransaction();
			txn.add(parentViews[i], fragment, mStatTableTags[i]);
			txn.commit();
		}
		
	}

	protected void handleEVChanged(int id, int evs) {
		MemberStatsTable table50 = null;
		MemberStatsTable table100 = null;
		final FragmentManager manager = getSupportFragmentManager();
		
		switch (id) {
			case R.id.hpEvs:
				table50 = (MemberStatsTable) manager.findFragmentByTag(mStatTableTags[0]);
				table100 = (MemberStatsTable) manager.findFragmentByTag(mStatTableTags[1]);				
				break;
				
			case R.id.atkEvs:
				table50 = (MemberStatsTable) manager.findFragmentByTag(mStatTableTags[2]);
				table100 = (MemberStatsTable) manager.findFragmentByTag(mStatTableTags[3]);				
				break;
				
			case R.id.defEvs:
				table50 = (MemberStatsTable) manager.findFragmentByTag(mStatTableTags[4]);
				table100 = (MemberStatsTable) manager.findFragmentByTag(mStatTableTags[5]);				
				break;
				
			case R.id.spatkEvs:
				table50 = (MemberStatsTable) manager.findFragmentByTag(mStatTableTags[6]);
				table100 = (MemberStatsTable) manager.findFragmentByTag(mStatTableTags[7]);				
				break;
				
			case R.id.spdefEvs:
				table50 = (MemberStatsTable) manager.findFragmentByTag(mStatTableTags[8]);
				table100 = (MemberStatsTable) manager.findFragmentByTag(mStatTableTags[9]);				
				break;
				
			case R.id.speEvs:
				table50 = (MemberStatsTable) manager.findFragmentByTag(mStatTableTags[10]);
				table100 = (MemberStatsTable) manager.findFragmentByTag(mStatTableTags[11]);				
				break;				
		}
		
		if(table50 != null){
			table50.setEvs(evs);
			table100.setEvs(evs);
			table50.refreshRanges();
			table100.refreshRanges();
		}
	}	
	
	private void updateMemberData() {
		// TODO Auto-generated method stub
		
	}	
	
}
