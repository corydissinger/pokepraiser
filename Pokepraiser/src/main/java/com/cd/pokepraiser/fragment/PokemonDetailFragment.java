package com.cd.pokepraiser.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.cd.pokepraiser.PokepraiserActivity;
import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.AbilityInfo;
import com.cd.pokepraiser.data.PokemonAttackInfo;
import com.cd.pokepraiser.data.PokemonAttributes;
import com.cd.pokepraiser.data.PokemonDetail;
import com.cd.pokepraiser.data.PokemonInfo;
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

public class PokemonDetailFragment extends SherlockFragment implements AddTeamMemberDialogListener{

	public static String TAG						= "pokeDetail";
	
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

	private ViewGroup mParentView			= null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(savedInstanceState == null){
        	savedInstanceState 			= getArguments();
        	final int pokemonId			= savedInstanceState.getInt(ExtrasConstants.POKEMON_ID);
        	
            mPokemonDataSource 	= new PokemonDataSource(((PokepraiserApplication)getActivity().getApplication()).getPokedbDatabaseReference());
            mAbilitiesDataSource = new AbilitiesDataSource(((PokepraiserApplication)getActivity().getApplication()).getPokedbDatabaseReference());
            mAttacksDataSource 	= new AttacksDataSource(((PokepraiserApplication)getActivity().getApplication()).getPokedbDatabaseReference());        	
			mTeamDataSource = new TeamDataSource(((PokepraiserApplication)getActivity().getApplication()).getTeamdbDatabaseReference());
            
	        mPokemonDataSource.open();
	        PokemonAttributes pokemonAttributes = mPokemonDataSource.getPokemonAttributes(pokemonId, getResources());
	        mPokemonDataSource.close();        
	        
	        mAbilitiesDataSource.open();
	        AbilityInfo [] pokemonAbilities 
	        	= mAbilitiesDataSource.getAbilitiesLearnedBy(new int []{pokemonAttributes.getAbOne(),
	        														   pokemonAttributes.getAbTwo(),
	        														   pokemonAttributes.getAbHa()});
	        mAbilitiesDataSource.close();
	        
			mAttacksDataSource.open();
			List<PokemonAttackInfo> pokemonAttacks = mAttacksDataSource.getPokemonAttackInfoList(pokemonAttributes.getDexNo(), pokemonAttributes.getAltForm());
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
        }
        
        mTeamAdd = new AddTeamMemberDialog(mTeams);
        
        setHasOptionsMenu(true);
    }
  
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		mParentView = (ViewGroup)inflater.inflate(R.layout.pokemon_detail_screen, container, false);

    	PokemonAttributes pokemonAttributes		= mPokemonDetail.getPokemonAttributes();
    	AbilityInfo []	  pokemonAbilities		= mPokemonDetail.getPokemonAbilities();
    	List<PokemonAttackInfo> pokemonAttacks	= mPokemonDetail.getPokemonAttacks();		
		
        TextView dexNo		 		= (TextView) mParentView.findViewById(R.id.dexNo);
        TextView pokemonName		= (TextView) mParentView.findViewById(R.id.pokemonName);
        
        ImageView pokemonPicture	= (ImageView) mParentView.findViewById(R.id.pokemonPicture);
        
        ImageView typeOne			= (ImageView) mParentView.findViewById(R.id.typeOne);
        ImageView typeTwo			= (ImageView) mParentView.findViewById(R.id.typeTwo);        

        TextView abilityLabel		= (TextView) mParentView.findViewById(R.id.abilityLabel);        
        TextView abilityOneLabel	= (TextView) mParentView.findViewById(R.id.abilityOneLabel);        
        TextView abilityTwoLabel	= (TextView) mParentView.findViewById(R.id.abilityTwoLabel);        
        TextView abilityHiddenLabel	= (TextView) mParentView.findViewById(R.id.abilityHiddenLabel);
        TextView abilityOne			= (TextView) mParentView.findViewById(R.id.abilityOne);
        TextView abilityTwo			= (TextView) mParentView.findViewById(R.id.abilityTwo);        
        TextView abilityHidden		= (TextView) mParentView.findViewById(R.id.abilityHidden);        

        TextView eggOne				= (TextView) mParentView.findViewById(R.id.eggOne);
        TextView eggTwo				= (TextView) mParentView.findViewById(R.id.eggTwo);        
        
        TextView hpLabel			= (TextView) mParentView.findViewById(R.id.baseHpLabel);
        TextView atkLabel			= (TextView) mParentView.findViewById(R.id.baseAtkLabel);
        TextView defLabel			= (TextView) mParentView.findViewById(R.id.baseDefLabel);
        TextView spatkLabel			= (TextView) mParentView.findViewById(R.id.baseSpatkLabel);
        TextView spdefLabel			= (TextView) mParentView.findViewById(R.id.baseSpdefLabel);
        TextView speLabel			= (TextView) mParentView.findViewById(R.id.baseSpeLabel);        

        TextView hp					= (TextView) mParentView.findViewById(R.id.baseHp);
        TextView atk				= (TextView) mParentView.findViewById(R.id.baseAtk);
        TextView def				= (TextView) mParentView.findViewById(R.id.baseDef);        
        TextView spatk				= (TextView) mParentView.findViewById(R.id.baseSpatk);
        TextView spdef				= (TextView) mParentView.findViewById(R.id.baseSpdef);        
        TextView spe				= (TextView) mParentView.findViewById(R.id.baseSpe);        
        
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
        abilityOne.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				openAbilityOne(v);
			}
        });
        
        if(pokemonAbilities.length == 1){
        	final LinearLayout abilityTwoLayout = (LinearLayout) mParentView.findViewById(R.id.abilityTwoCell);
        	((LinearLayout)abilityTwoLayout.getParent()).removeView(abilityTwoLayout);
        	
        	final LinearLayout abilityHiddenLayout = (LinearLayout) mParentView.findViewById(R.id.abilityHiddenCell);
        	((LinearLayout)abilityHiddenLayout.getParent()).removeView(abilityHiddenLayout);
        	
        }else if(pokemonAbilities.length == 2){
        	final LinearLayout abilityTwoLayout = (LinearLayout) mParentView.findViewById(R.id.abilityTwoCell);
        	((LinearLayout)abilityTwoLayout.getParent()).removeView(abilityTwoLayout);

            abilityHidden.setText(pokemonAbilities[1].getName());
            abilityHidden.setOnClickListener(new View.OnClickListener(){
    			@Override
    			public void onClick(View v) {
    				openAbilityHidden(v);
    			}            	
            });
        }else{
            abilityTwo.setText(pokemonAbilities[1].getName());        	
            abilityHidden.setText(pokemonAbilities[2].getName());
            
            abilityTwo.setOnClickListener(new View.OnClickListener(){
    			@Override
    			public void onClick(View v) {
    				openAbilityTwo(v);
    			}            	
            });
            
            abilityHidden.setOnClickListener(new View.OnClickListener(){
    			@Override
    			public void onClick(View v) {
    				openAbilityHidden(v);
    			}            	
            });
        }
        
        if(pokemonAttributes.getEggOne() == null){
        	final LinearLayout eggOneLayout = (LinearLayout) mParentView.findViewById(R.id.eggOneCell);
        	((LinearLayout)eggOneLayout.getParent()).removeView(eggOneLayout);        	
        }else{
            eggOne.setText(pokemonAttributes.getEggOne());
            eggOne.setOnClickListener(new View.OnClickListener(){
    			@Override
    			public void onClick(View v) {
    				openEggGroupList(v);
    			}
            });        	
        }
        
        if(pokemonAttributes.getEggTwo() == null){
        	final LinearLayout eggTwoLayout = (LinearLayout) mParentView.findViewById(R.id.eggTwoCell);
        	((LinearLayout)eggTwoLayout.getParent()).removeView(eggTwoLayout);        	
        }else{
        	eggTwo.setText(pokemonAttributes.getEggTwo());
        	eggTwo.setOnClickListener(new View.OnClickListener(){
    			@Override
    			public void onClick(View v) {
    				openEggGroupList(v);
    			}            	
            });        	
        }
        
        if(pokemonAttributes.getEggOne() == null && pokemonAttributes.getEggTwo() == null){
        	final LinearLayout eggNoneLayout = (LinearLayout) mParentView.findViewById(R.id.eggNoneCell);
        	eggNoneLayout.setVisibility(View.VISIBLE);
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
		((PokepraiserApplication)getActivity().getApplication()).overrideFonts(mParentView);
		
		//Most expensive operation by far, most likely.
		inflatePokemonAttackLists();		
		
		return mParentView;
	}
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putParcelable(POKEMON_DETAIL, mPokemonDetail);
    	savedInstanceState.putSerializable(TEAMS, mTeams);
    	
    	super.onSaveInstanceState(savedInstanceState);
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	inflater.inflate(R.menu.main, menu);
    }
    
    public void openAbilityOne(View v){
		final AbilityInfo abilityItem = mPokemonDetail.getPokemonAbilities()[0];
		
		AbilityDetailFragment newFrag = new AbilityDetailFragment();
		Bundle args = new Bundle();
		
		args.putInt(ExtrasConstants.ABILITY_ID, abilityItem.getAbilityDbId());
		newFrag.setArguments(args);
		
		((PokepraiserActivity)getActivity()).setIsListOrigin(false);
		((PokepraiserActivity)getActivity()).changeFragment(newFrag, newFrag.TAG);
    }
    
    public void openAbilityTwo(View v){
		final AbilityInfo abilityItem = mPokemonDetail.getPokemonAbilities()[1];
		
		AbilityDetailFragment newFrag = new AbilityDetailFragment();
		Bundle args = new Bundle();
		
		args.putInt(ExtrasConstants.ABILITY_ID, abilityItem.getAbilityDbId());
		newFrag.setArguments(args);
		
		((PokepraiserActivity)getActivity()).setIsListOrigin(false);
		((PokepraiserActivity)getActivity()).changeFragment(newFrag, newFrag.TAG);    	
    }
    
    public void openAbilityHidden(View v){
		final AbilityInfo abilityItem;
		
		if(mPokemonDetail.getPokemonAbilities().length == 2){
			abilityItem = mPokemonDetail.getPokemonAbilities()[1];
		}else{
			abilityItem = mPokemonDetail.getPokemonAbilities()[2];
		}
		
		AbilityDetailFragment newFrag = new AbilityDetailFragment();
		Bundle args = new Bundle();
		
		args.putInt(ExtrasConstants.ABILITY_ID, abilityItem.getAbilityDbId());
		newFrag.setArguments(args);
		
		((PokepraiserActivity)getActivity()).setIsListOrigin(false);
		((PokepraiserActivity)getActivity()).changeFragment(newFrag, newFrag.TAG);    	
    }        
    
	private void openEggGroupList(View v) {
		final String eggGroup = ((Button)v).getText().toString(); 
		
		mPokemonDataSource.open();
		final ArrayList<PokemonInfo> pokemonInEggGroup = mPokemonDataSource.getAllPokemonInEggGroup(eggGroup, getResources());
		mPokemonDataSource.close();
		
		PokemonListFragment newFrag = new PokemonListFragment();
		Bundle args = new Bundle();
		
		args.putSerializable(ExtrasConstants.POKEMON_SEARCH, pokemonInEggGroup);
		newFrag.setArguments(args);
		
		((PokepraiserActivity)getActivity()).setIsListOrigin(false);
		((PokepraiserActivity)getActivity()).changeFragment(newFrag, newFrag.TAG);		
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
    	
    	ProgressBar hpBar			= (ProgressBar) mParentView.findViewById(R.id.baseHpBar);
    	ProgressBar atkBar			= (ProgressBar) mParentView.findViewById(R.id.baseAtkBar);    	
    	ProgressBar defBar			= (ProgressBar) mParentView.findViewById(R.id.baseDefBar);
    	ProgressBar spatkBar		= (ProgressBar) mParentView.findViewById(R.id.baseSpatkBar);
    	ProgressBar spdefBar		= (ProgressBar) mParentView.findViewById(R.id.baseSpdefBar);
    	ProgressBar speBar			= (ProgressBar) mParentView.findViewById(R.id.baseSpeBar);

    	final int hpStatPctl		= StatUtils.getRoughPercentile(pokemonAttributes.getBsHp());
    	final int atkStatPctl		= StatUtils.getRoughPercentile(pokemonAttributes.getBsAtk());
    	final int defStatPctl		= StatUtils.getRoughPercentile(pokemonAttributes.getBsDef());
    	final int spatkStatPctl		= StatUtils.getRoughPercentile(pokemonAttributes.getBsSpatk());    	
    	final int spdefStatPctl		= StatUtils.getRoughPercentile(pokemonAttributes.getBsSpdef());
    	final int speStatPctl		= StatUtils.getRoughPercentile(pokemonAttributes.getBsSpd());    	
    	
    	hpBar.setProgressDrawable(getActivity().getApplication().getResources().getDrawable(StatUtils.getProgressColor(hpStatPctl)));
    	atkBar.setProgressDrawable(getActivity().getApplication().getResources().getDrawable(StatUtils.getProgressColor(atkStatPctl)));
    	defBar.setProgressDrawable(getActivity().getApplication().getResources().getDrawable(StatUtils.getProgressColor(defStatPctl)));
    	spatkBar.setProgressDrawable(getActivity().getApplication().getResources().getDrawable(StatUtils.getProgressColor(spatkStatPctl)));
    	spdefBar.setProgressDrawable(getActivity().getApplication().getResources().getDrawable(StatUtils.getProgressColor(spdefStatPctl)));
    	speBar.setProgressDrawable(getActivity().getApplication().getResources().getDrawable(StatUtils.getProgressColor(speStatPctl)));
    	
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
    	
    	LinearLayout levelUpList		= (LinearLayout) mParentView.findViewById(R.id.levelUpAttacksList);
    	LinearLayout tmHmList			= (LinearLayout) mParentView.findViewById(R.id.tmHmAttacksList);
    	LinearLayout eggList			= (LinearLayout) mParentView.findViewById(R.id.eggAttacksList);
    	LinearLayout gvOnlyList			= (LinearLayout) mParentView.findViewById(R.id.gvOnlyAttacksList);
    	LinearLayout preEvoList			= (LinearLayout) mParentView.findViewById(R.id.preEvoAttacksList);
    	LinearLayout moveTutorList		= (LinearLayout) mParentView.findViewById(R.id.moveTutorAttacksList);    	
    	
    	LinearLayout attacksList		= (LinearLayout) levelUpList.getParent();
    	LinearLayout scrollableParent	= (LinearLayout) attacksList.getParent();    	
    	
    	for(PokemonAttackInfo pokemonAttack : mPokemonDetail.getPokemonAttacks()){
    		switch(pokemonAttack.getLearnedType()){
    		
	    		case LEVEL_UP_ATTACKS_LIST: 
	    			inflatePokemonAttackInfo(levelUpList, pokemonAttack, true, getString(R.string.level), getActivity().getLayoutInflater());
	    			showLevelUpList = true;
	    			break;

	    		case TM_HM_ATTACKS_LIST: 
	    			inflatePokemonAttackInfo(tmHmList, pokemonAttack, true, getString(R.string.tm_hm), getActivity().getLayoutInflater());
	    			showTmHmList = true;
	    			break;
	    			
	    		case EGG_ATTACKS_LIST: 
	    			inflatePokemonAttackInfo(eggList, pokemonAttack, false, "", getActivity().getLayoutInflater());
	    			showEggList = true;
	    			break;
	    			
	    		case GV_ONLY_ATTACKS_LIST: 
	    			inflatePokemonAttackInfo(gvOnlyList, pokemonAttack, false, "", getActivity().getLayoutInflater());
	    			showGvOnlyList = true;
	    			break;
	    			
	    		case PRE_EVO_ATTACKS_LIST: 
	    			inflatePokemonAttackInfo(preEvoList, pokemonAttack, false, "", getActivity().getLayoutInflater());
	    			showPreEvoList = true;
	    			break;
	    			
	    		case MOVE_TUTOR_ATTACKS_LIST: 
	    			inflatePokemonAttackInfo(moveTutorList, pokemonAttack, false, "", getActivity().getLayoutInflater());
	    			showMoveTutorList = true;
	    			break;	    			
	    			
				default:
					break;
    		}
    	}
    	
    	if(showLevelUpList){
        	levelUpList.addView(generateHideButton());
        	
        	Button levelUp = (Button) mParentView.findViewById(R.id.levelUpAttacksButton);
        	
        	levelUp.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					toggleAttackList(v);
				}
        	});
        	
    	}else{
    		LinearLayout levelUpAttacksLayout 	= (LinearLayout) levelUpList.getParent();
    		
    		scrollableParent.removeView(levelUpAttacksLayout);
    	}
    	
    	if(showTmHmList){
    		tmHmList.addView(generateHideButton());
    		
        	Button tmHm = (Button) mParentView.findViewById(R.id.tmHmAttacksButton);
        	
        	tmHm.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					toggleAttackList(v);
				}
        	});    		
    	}else{
    		LinearLayout tmHmAttacksLayout 	= (LinearLayout) tmHmList.getParent();
    		
    		scrollableParent.removeView(tmHmAttacksLayout);
    	}
    	
    	if(showEggList){
    		eggList.addView(generateHideButton());
    		
        	Button eggs = (Button) mParentView.findViewById(R.id.eggAttacksButton);
        	
        	eggs.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					toggleAttackList(v);
				}
        	});    		
    		
    	}else{
    		LinearLayout eggAttacksLayout 	= (LinearLayout) eggList.getParent();
    		
    		scrollableParent.removeView(eggAttacksLayout);
    	}
    	
    	if(showGvOnlyList){
    		gvOnlyList.addView(generateHideButton());
    		
        	Button gvOnly = (Button) mParentView.findViewById(R.id.gvOnlyAttacksButton);
        	
        	gvOnly.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					toggleAttackList(v);
				}
        	});    		
    		
    	}else{
    		LinearLayout gvOnlyAttacksLayout 	= (LinearLayout) gvOnlyList.getParent();
    		
    		scrollableParent.removeView(gvOnlyAttacksLayout);
    	}    	
    	
    	if(showPreEvoList){
    		preEvoList.addView(generateHideButton());
    		
        	Button preEvo = (Button) mParentView.findViewById(R.id.preEvoAttacksButton);
        	
        	preEvo.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					toggleAttackList(v);
				}
        	});    		
    		
    	}else{
    		LinearLayout preEvoAttacksLayout 	= (LinearLayout) preEvoList.getParent();
    		
    		scrollableParent.removeView(preEvoAttacksLayout);
    	}    	
    	
    	if(showMoveTutorList){
    		moveTutorList.addView(generateHideButton());
    		
        	Button moveTutor = (Button) mParentView.findViewById(R.id.moveTutorAttacksButton);
        	
        	moveTutor.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					toggleAttackList(v);
				}
        	});    		
    		
    	}else{
    		LinearLayout moveTutorAttacksLayout 	= (LinearLayout) moveTutorList.getParent();
    		
    		scrollableParent.removeView(moveTutorAttacksLayout);
    	}    	
    }
    
    private void inflatePokemonAttackInfo(LinearLayout theParent, 	
    									  final PokemonAttackInfo thePokemonAttackInfo, 
    									  final boolean isLevelShown,
    									  final String levelTmLabelText,
    									  LayoutInflater inflater){
    	
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
		
		((PokepraiserApplication)getActivity().getApplication()).applyTypeface(new TextView[]{levelTm,
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
				AttackDetailFragment newFrag = new AttackDetailFragment();
				Bundle args = new Bundle();
				
				args.putInt(ExtrasConstants.ATTACK_ID, thePokemonAttackInfo.getAttackDbId());
				newFrag.setArguments(args);
				
				((PokepraiserActivity)getActivity()).setIsListOrigin(false);
				((PokepraiserActivity)getActivity()).changeFragment(newFrag, newFrag.TAG);				
			}
        });
		
		rowView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				TextView attackName 		= (TextView) v.findViewById(R.id.attackName);
				
				final int blueResource		= getResources().getColor(R.color.medium_blue);
				final int blackResource		= getResources().getColor(R.color.black);					
				
                switch (event.getAction()) {

	                case MotionEvent.ACTION_DOWN:
	                	attackName.setTextColor(blueResource);
	                    break;
	                case MotionEvent.ACTION_UP:
	                	attackName.setTextColor(blackResource);
	                    break;
                }
                
				return false;
			}
		});
		
		theParent.addView(rowView);
    }
    
    private Button generateHideButton(){
    	Button hideButton = new Button(getActivity());
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
	    
	    ((PokepraiserApplication)getActivity().getApplication()).applyTypeface(hideButton);
	    
    	return hideButton;
    }

	public void openTeamAddDialog() {
		mTeamAdd.setTargetFragment(this, 0);
		mTeamAdd.show(getChildFragmentManager(), null);
	}

	@Override
	public void onTeamItemClick(AddTeamMemberDialog dialog) {
		final TeamInfo selectedTeam = mTeams.get(dialog.getSelectedItem());

		mTeamDataSource.openWrite();
		mTeamDataSource.addTeamMember(mPokemonDetail.getPokemonAttributes().getPokemonId(), selectedTeam.getDbId());
		mTeamDataSource.close();
		
		dialog.dismiss();
	}
}
