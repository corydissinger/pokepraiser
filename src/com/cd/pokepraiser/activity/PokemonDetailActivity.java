package com.cd.pokepraiser.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.AbilityInfo;
import com.cd.pokepraiser.data.PokemonAttackInfo;
import com.cd.pokepraiser.data.PokemonAttributes;
import com.cd.pokepraiser.data.PokemonDetail;
import com.cd.pokepraiser.data.TeamInfo;
import com.cd.pokepraiser.db.dao.AbilitiesDataSource;
import com.cd.pokepraiser.db.dao.AttacksDataSource;
import com.cd.pokepraiser.db.dao.PokemonDataSource;
import com.cd.pokepraiser.db.dao.TeamDataSource;
import com.cd.pokepraiser.dialog.AddTeamMemberDialog;
import com.cd.pokepraiser.dialog.AddTeamMemberDialog.AddTeamMemberDialogListener;
import com.cd.pokepraiser.util.ExtrasConstants;
import com.cd.pokepraiser.util.StatUtils;
import com.cd.pokepraiser.util.TypeUtils;

public class PokemonDetailActivity extends PokepraiserActivity implements AddTeamMemberDialogListener{

	private static final int LEVEL_UP_ATTACKS_LIST = 0;
	private static final int TM_HM_ATTACKS_LIST = 1;
	private static final int EGG_ATTACKS_LIST = 2;
	private static final int GV_ONLY_ATTACKS_LIST = 3;
	private static final int PRE_EVO_ATTACKS_LIST = 4;	
	private static final int MOVE_TUTOR_ATTACKS_LIST = 5;	
	
	private static final String POKEMON_DETAIL 		= "pokeDetail";
	private static final String TEAMS		 		= "t";	
	
	private PokemonDataSource mPokemonDataSource;
	private AbilitiesDataSource mAbilitiesDataSource;	
	private AttacksDataSource mAttacksDataSource;
	private TeamDataSource mTeamDataSource;	

	private PokemonDetail mPokemonDetail		= null;
	private ArrayList<TeamInfo> mTeams			= null;
	
	private AddTeamMemberDialog	mTeamAdd;	
	
	private boolean isLevelUpListShown 		= false;
	private boolean isTmHmListShown 		= false;
	private boolean isEggListShown			= false;
	private boolean isGvOnlyListShown		= false;
	private boolean isPreEvoListShown		= false;
	private boolean isMoveTutorListShown	= false;	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_detail_screen);
        
        final Intent receivedIntent = getIntent();
        final int pokemonId			= receivedIntent.getIntExtra(ExtrasConstants.POKEMON_ID, 0);        
        
    	PokemonAttributes pokemonAttributes;
    	AbilityInfo []	  pokemonAbilities;
    	List<PokemonAttackInfo> pokemonAttacks;        
        
        if(savedInstanceState == null){
            mPokemonDataSource 	= new PokemonDataSource(((PokepraiserApplication)getApplication()).getPokedbDatabaseReference());
            mAbilitiesDataSource = new AbilitiesDataSource(((PokepraiserApplication)getApplication()).getPokedbDatabaseReference());
            mAttacksDataSource 	= new AttacksDataSource(((PokepraiserApplication)getApplication()).getPokedbDatabaseReference());        	
			mTeamDataSource = new TeamDataSource(((PokepraiserApplication)getApplication()).getTeamdbDatabaseReference());
            
	        mPokemonDataSource.open();
	        pokemonAttributes = mPokemonDataSource.getPokemonAttributes(pokemonId, getResources());
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
			
			mTeamDataSource.openRead();
			mTeams = (ArrayList<TeamInfo>) mTeamDataSource.getTeamInfo();
			mTeamDataSource.close();			
			
        	mPokemonDetail = new PokemonDetail();
        	mPokemonDetail.setPokemonAttributes(pokemonAttributes);
        	mPokemonDetail.setPokemonAbilities(pokemonAbilities);
        	mPokemonDetail.setPokemonAttacks(pokemonAttacks);
        }else{
        	mPokemonDetail		= savedInstanceState.getParcelable(POKEMON_DETAIL);
        	mTeams				= (ArrayList<TeamInfo>) savedInstanceState.getSerializable(TEAMS);
        	
        	pokemonAttributes 	= mPokemonDetail.getPokemonAttributes();
        	pokemonAbilities  	= mPokemonDetail.getPokemonAbilities();
        	pokemonAttacks		= mPokemonDetail.getPokemonAttacks();
        }
        
        mTeamAdd = new AddTeamMemberDialog(mTeams);
        
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

        TextView hp					= (TextView) findViewById(R.id.baseHp);
        TextView atk				= (TextView) findViewById(R.id.baseAtk);
        TextView def				= (TextView) findViewById(R.id.baseDef);        
        TextView spatk				= (TextView) findViewById(R.id.baseSpatk);
        TextView spdef				= (TextView) findViewById(R.id.baseSpdef);        
        TextView spe				= (TextView) findViewById(R.id.baseSpe);        
        
        dexNo.setText(Integer.toString(pokemonAttributes.getDexNo()));
        pokemonName.setText(pokemonAttributes.getName());
        
        pokemonPicture.setImageResource(pokemonAttributes.getImgDrawable());
        
        //Make the type images and draw them
        typeOne.setImageResource(TypeUtils.getTypeDrawableId(pokemonAttributes.getTypeOne()));
        
        if(pokemonAttributes.getTypeTwo() != 0){
            typeTwo.setImageResource(TypeUtils.getTypeDrawableId(pokemonAttributes.getTypeTwo()));        	
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
        
        //Set the base stat values

        applyProgressAndColorToBars();
        hp.setText(Integer.toString(pokemonAttributes.getBsHp()));        
        def.setText(Integer.toString(pokemonAttributes.getBsDef()));        
        atk.setText(Integer.toString(pokemonAttributes.getBsAtk()));        
        spatk.setText(Integer.toString(pokemonAttributes.getBsSpatk()));        
        spdef.setText(Integer.toString(pokemonAttributes.getBsSpdef()));
        spe.setText(Integer.toString(pokemonAttributes.getBsSpd()));        
        
        //Apply typeface
		((PokepraiserApplication)getApplication()).overrideFonts(findViewById(android.R.id.content));
		
		//Most expensive operation by far, most likely.
		inflatePokemonAttackLists();
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putParcelable(POKEMON_DETAIL, mPokemonDetail);
    	savedInstanceState.putSerializable(TEAMS, mTeams);
    	
    	super.onSaveInstanceState(savedInstanceState);
    }
    
    public void openAbilityOne(View v){
		final AbilityInfo abilityItem = mPokemonDetail.getPokemonAbilities()[0];
		
    	Intent i = new Intent(PokemonDetailActivity.this, AbilityDetailActivity.class);
		i.putExtra(ExtrasConstants.ABILITY_ID, abilityItem.getAbilityDbId());
		startActivity(i);    	
    }
    
    public void openAbilityTwo(View v){
		final AbilityInfo abilityItem = mPokemonDetail.getPokemonAbilities()[1];
		
    	Intent i = new Intent(PokemonDetailActivity.this, AbilityDetailActivity.class);
		i.putExtra(ExtrasConstants.ABILITY_ID, abilityItem.getAbilityDbId());
		startActivity(i);    	
    }
    
    public void openAbilityHidden(View v){
		final AbilityInfo abilityItem;
		
		if(mPokemonDetail.getPokemonAbilities().length == 2){
			abilityItem = mPokemonDetail.getPokemonAbilities()[1];
		}else{
			abilityItem = mPokemonDetail.getPokemonAbilities()[2];
		}
		
    	Intent i = new Intent(PokemonDetailActivity.this, AbilityDetailActivity.class);
		i.putExtra(ExtrasConstants.ABILITY_ID, abilityItem.getAbilityDbId());
		startActivity(i);    	
    }        
    
    public void toggleAttackList(View v){
    	LinearLayout labelButtonContainer = (LinearLayout) v.getParent();
    	final LinearLayout attacksContainer = (LinearLayout) labelButtonContainer.getParent();
    	final int listType = Integer.parseInt((String)attacksContainer.getTag());
    	
    	labelButtonContainer					= (LinearLayout) attacksContainer.getChildAt(0);
    	final LinearLayout attacksListContainer = (LinearLayout) attacksContainer.getChildAt(1);
    	final Button headerToggleButton			= (Button) labelButtonContainer.getChildAt(1);
    	
    	switch(listType){
    		case LEVEL_UP_ATTACKS_LIST:
    	    	if(isLevelUpListShown){
    	    		attacksListContainer.setVisibility(View.GONE);
    	    		headerToggleButton.setText(getString(R.string.show));
    	    		
    	    		isLevelUpListShown = false;    		
    	    	}else{
    	    		attacksListContainer.setVisibility(View.VISIBLE);
    	    		headerToggleButton.setText(getString(R.string.hide));    		
    	    		
    	    		isLevelUpListShown = true;
    	    	}
    	    	
    	    	break;
    	    	
    		case TM_HM_ATTACKS_LIST:
    	    	if(isTmHmListShown){
    	    		attacksListContainer.setVisibility(View.GONE);
    	    		headerToggleButton.setText(getString(R.string.show));
    	    		
    	    		isTmHmListShown = false;    		
    	    	}else{
    	    		attacksListContainer.setVisibility(View.VISIBLE);
    	    		headerToggleButton.setText(getString(R.string.hide));    		
    	    		
    	    		isTmHmListShown = true;
    	    	}    	    	
    	    	
    	    	break;
    	    	
    		case EGG_ATTACKS_LIST:
    	    	if(isEggListShown){
    	    		attacksListContainer.setVisibility(View.GONE);
    	    		headerToggleButton.setText(getString(R.string.show));
    	    		
    	    		isEggListShown = false;    		
    	    	}else{
    	    		attacksListContainer.setVisibility(View.VISIBLE);
    	    		headerToggleButton.setText(getString(R.string.hide));    		
    	    		
    	    		isEggListShown = true;
    	    	}    	    	
    	    	
    	    	break;
    	    	
    		case GV_ONLY_ATTACKS_LIST:
    	    	if(isGvOnlyListShown){
    	    		attacksListContainer.setVisibility(View.GONE);
    	    		headerToggleButton.setText(getString(R.string.show));
    	    		
    	    		isGvOnlyListShown = false;    		
    	    	}else{
    	    		attacksListContainer.setVisibility(View.VISIBLE);
    	    		headerToggleButton.setText(getString(R.string.hide));    		
    	    		
    	    		isGvOnlyListShown = true;
    	    	}    	    	
    	    	
    	    	break;    	    	
    	    	
    		case PRE_EVO_ATTACKS_LIST:
    	    	if(isPreEvoListShown){
    	    		attacksListContainer.setVisibility(View.GONE);
    	    		headerToggleButton.setText(getString(R.string.show));
    	    		
    	    		isPreEvoListShown = false;    		
    	    	}else{
    	    		attacksListContainer.setVisibility(View.VISIBLE);
    	    		headerToggleButton.setText(getString(R.string.hide));    		
    	    		
    	    		isPreEvoListShown = true;
    	    	}    	    	
    	    	
    	    	break;  
    	    	
    		case MOVE_TUTOR_ATTACKS_LIST:
    	    	if(isMoveTutorListShown){
    	    		attacksListContainer.setVisibility(View.GONE);
    	    		headerToggleButton.setText(getString(R.string.show));
    	    		
    	    		isMoveTutorListShown = false;    		
    	    	}else{
    	    		attacksListContainer.setVisibility(View.VISIBLE);
    	    		headerToggleButton.setText(getString(R.string.hide));    		
    	    		
    	    		isMoveTutorListShown = true;
    	    	}    	    	
    	    	
    	    	break;    	    	
    			
    	}

    }
	
    private void applyProgressAndColorToBars(){
    	final PokemonAttributes pokemonAttributes 	= mPokemonDetail.getPokemonAttributes();
    	
    	ProgressBar hpBar			= (ProgressBar)findViewById(R.id.baseHpBar);
    	ProgressBar atkBar			= (ProgressBar)findViewById(R.id.baseAtkBar);    	
    	ProgressBar defBar			= (ProgressBar)findViewById(R.id.baseDefBar);
    	ProgressBar spatkBar		= (ProgressBar)findViewById(R.id.baseSpatkBar);
    	ProgressBar spdefBar		= (ProgressBar)findViewById(R.id.baseSpdefBar);
    	ProgressBar speBar			= (ProgressBar)findViewById(R.id.baseSpeBar);

    	final int hpStatPctl		= StatUtils.getRoughPercentile(pokemonAttributes.getBsHp());
    	final int atkStatPctl		= StatUtils.getRoughPercentile(pokemonAttributes.getBsAtk());
    	final int defStatPctl		= StatUtils.getRoughPercentile(pokemonAttributes.getBsDef());
    	final int spatkStatPctl		= StatUtils.getRoughPercentile(pokemonAttributes.getBsSpatk());    	
    	final int spdefStatPctl		= StatUtils.getRoughPercentile(pokemonAttributes.getBsSpdef());
    	final int speStatPctl		= StatUtils.getRoughPercentile(pokemonAttributes.getBsSpd());    	
    	
    	hpBar.setProgressDrawable(getApplication().getResources().getDrawable(StatUtils.getProgressColor(hpStatPctl)));
    	atkBar.setProgressDrawable(getApplication().getResources().getDrawable(StatUtils.getProgressColor(atkStatPctl)));
    	defBar.setProgressDrawable(getApplication().getResources().getDrawable(StatUtils.getProgressColor(defStatPctl)));
    	spatkBar.setProgressDrawable(getApplication().getResources().getDrawable(StatUtils.getProgressColor(spatkStatPctl)));
    	spdefBar.setProgressDrawable(getApplication().getResources().getDrawable(StatUtils.getProgressColor(spdefStatPctl)));
    	speBar.setProgressDrawable(getApplication().getResources().getDrawable(StatUtils.getProgressColor(speStatPctl)));
    	
    	hpBar.setProgress(hpStatPctl);
    	atkBar.setProgress(atkStatPctl);
    	defBar.setProgress(defStatPctl);
    	spatkBar.setProgress(spatkStatPctl);
    	spdefBar.setProgress(spdefStatPctl);
    	speBar.setProgress(speStatPctl);    	
    }
    
    private void inflatePokemonAttackLists(){
    	boolean showLevelUpList			= false;
    	boolean showTmHmList			= false;
    	boolean showEggList				= false;
    	boolean showGvOnlyList			= false;
    	boolean showPreEvoList			= false;
    	boolean showMoveTutorList		= false;    	
    	
    	LinearLayout levelUpList		= (LinearLayout) findViewById(R.id.levelUpAttacksList);
    	LinearLayout tmHmList			= (LinearLayout) findViewById(R.id.tmHmAttacksList);
    	LinearLayout eggList			= (LinearLayout) findViewById(R.id.eggAttacksList);
    	LinearLayout gvOnlyList			= (LinearLayout) findViewById(R.id.gvOnlyAttacksList);
    	LinearLayout preEvoList			= (LinearLayout) findViewById(R.id.preEvoAttacksList);
    	LinearLayout moveTutorList		= (LinearLayout) findViewById(R.id.moveTutorAttacksList);    	
    	
    	LinearLayout attacksList		= (LinearLayout) levelUpList.getParent();
    	LinearLayout scrollableParent	= (LinearLayout) attacksList.getParent();    	
    	
    	for(PokemonAttackInfo pokemonAttack : mPokemonDetail.getPokemonAttacks()){
    		switch(pokemonAttack.getLearnedType()){
    		
	    		case LEVEL_UP_ATTACKS_LIST: 
	    			inflatePokemonAttackInfo(levelUpList, pokemonAttack, true, getString(R.string.level));
	    			showLevelUpList = true;
	    			break;

	    		case TM_HM_ATTACKS_LIST: 
	    			inflatePokemonAttackInfo(tmHmList, pokemonAttack, true, getString(R.string.tm_hm));
	    			showTmHmList = true;
	    			break;
	    			
	    		case EGG_ATTACKS_LIST: 
	    			inflatePokemonAttackInfo(eggList, pokemonAttack, false, "");
	    			showEggList = true;
	    			break;
	    			
	    		case GV_ONLY_ATTACKS_LIST: 
	    			inflatePokemonAttackInfo(gvOnlyList, pokemonAttack, false, "");
	    			showGvOnlyList = true;
	    			break;
	    			
	    		case PRE_EVO_ATTACKS_LIST: 
	    			inflatePokemonAttackInfo(preEvoList, pokemonAttack, false, "");
	    			showPreEvoList = true;
	    			break;
	    			
	    		case MOVE_TUTOR_ATTACKS_LIST: 
	    			inflatePokemonAttackInfo(moveTutorList, pokemonAttack, false, "");
	    			showMoveTutorList = true;
	    			break;	    			
	    			
				default:
					break;
    		}
    	}
    	
    	if(showLevelUpList){
        	levelUpList.addView(generateHideButton());
    	}else{
    		LinearLayout levelUpAttacksLayout 	= (LinearLayout) levelUpList.getParent();
    		
    		scrollableParent.removeView(levelUpAttacksLayout);
    	}
    	
    	if(showTmHmList){
    		tmHmList.addView(generateHideButton());
    	}else{
    		LinearLayout tmHmAttacksLayout 	= (LinearLayout) tmHmList.getParent();
    		
    		scrollableParent.removeView(tmHmAttacksLayout);
    	}
    	
    	if(showEggList){
    		eggList.addView(generateHideButton());
    	}else{
    		LinearLayout eggAttacksLayout 	= (LinearLayout) eggList.getParent();
    		
    		scrollableParent.removeView(eggAttacksLayout);
    	}
    	
    	if(showGvOnlyList){
    		gvOnlyList.addView(generateHideButton());
    	}else{
    		LinearLayout gvOnlyAttacksLayout 	= (LinearLayout) gvOnlyList.getParent();
    		
    		scrollableParent.removeView(gvOnlyAttacksLayout);
    	}    	
    	
    	if(showPreEvoList){
    		preEvoList.addView(generateHideButton());
    	}else{
    		LinearLayout preEvoAttacksLayout 	= (LinearLayout) preEvoList.getParent();
    		
    		scrollableParent.removeView(preEvoAttacksLayout);
    	}    	
    	
    	if(showMoveTutorList){
    		moveTutorList.addView(generateHideButton());
    	}else{
    		LinearLayout moveTutorAttacksLayout 	= (LinearLayout) moveTutorList.getParent();
    		
    		scrollableParent.removeView(moveTutorAttacksLayout);
    	}    	
    }
    
    private void inflatePokemonAttackInfo(LinearLayout theParent, 	
    									  final PokemonAttackInfo thePokemonAttackInfo, 
    									  final boolean isLevelShown,
    									  final String levelTmLabelText){
    	
		LayoutInflater inflater				= getLayoutInflater();
		LinearLayout rowView 				= (LinearLayout)inflater.inflate(R.layout.pokemon_attack_info_row, null);
		
		final Drawable drawableType		=  getResources().getDrawable(thePokemonAttackInfo.getTypeDrawableId());
		final Drawable drawableCategory	= getResources().getDrawable(thePokemonAttackInfo.getCategoryDrawableId());		
		
		TextView levelTmLabel		= (TextView) rowView.findViewById(R.id.levelTmLabel);		
		TextView levelTm			= (TextView) rowView.findViewById(R.id.levelTm);		
		TextView attackName			= (TextView) rowView.findViewById(R.id.attackName);
		ImageView typeImage			= (ImageView) rowView.findViewById(R.id.attackType);
		ImageView categoryImage		= (ImageView) rowView.findViewById(R.id.attackCategory);
		TextView basePower			= (TextView) rowView.findViewById(R.id.basePower);
		TextView baseAccuracy		= (TextView) rowView.findViewById(R.id.baseAccuracy);
		TextView basePp				= (TextView) rowView.findViewById(R.id.basePP);

		TextView basePowerLabel			= (TextView) rowView.findViewById(R.id.basePowerLabel);
		TextView baseAccuracyLabel		= (TextView) rowView.findViewById(R.id.baseAccuracyLabel);
		TextView basePPLabel			= (TextView) rowView.findViewById(R.id.basePPLabel);			
		
		((PokepraiserApplication)getApplication()).applyTypeface(new TextView[]{levelTm,
																				levelTmLabel,
																				basePowerLabel,
																				baseAccuracyLabel,
																				basePPLabel,
																				attackName,
																				basePower,
																				baseAccuracy,
																				basePp});
		
		if(isLevelShown){
			levelTm.setText(Integer.toString(thePokemonAttackInfo.getLvlOrTm()));
			levelTmLabel.setText(levelTmLabelText);
		}else{
			//Hacky but works
			rowView.removeView((View)levelTm.getParent());
		}
		
		attackName.setText(thePokemonAttackInfo.getName());
		typeImage.setImageDrawable(drawableType);
		categoryImage.setImageDrawable(drawableCategory);
		basePower.setText(thePokemonAttackInfo.getBasePower());
		baseAccuracy.setText(thePokemonAttackInfo.getBaseAccuracy());
		basePp.setText(thePokemonAttackInfo.getBasePp());		
		
		rowView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
	        	Intent i = new Intent(PokemonDetailActivity.this, AttacksDetailActivity.class);
        		i.putExtra(ExtrasConstants.ATTACK_ID, thePokemonAttackInfo.getAttackDbId());
				startActivity(i);
			}
        });
		
		theParent.addView(rowView);
    }
    
    private Button generateHideButton(){
    	Button hideButton = new Button(this);
	    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, //W H 
	    																 LinearLayout.LayoutParams.WRAP_CONTENT);
	    
	    params.setMargins(25, 0, 25, 0);
	    hideButton.setLayoutParams(params);
	    hideButton.setText(R.string.hide);
	    hideButton.setTextSize(14);
	    
	    hideButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toggleAttackList(v);
			}
		});
	    
	    ((PokepraiserApplication)getApplication()).applyTypeface(hideButton);
	    
    	return hideButton;
    }

	public void openTeamAddDialog() {
		mTeamAdd.show(getSupportFragmentManager(), null);
	}

	@Override
	public void onTeamItemClick(AddTeamMemberDialog dialog) {
		final TeamInfo selectedTeam = mTeams.get(dialog.getSelectedItem());

		dialog.dismiss();
	}
}
