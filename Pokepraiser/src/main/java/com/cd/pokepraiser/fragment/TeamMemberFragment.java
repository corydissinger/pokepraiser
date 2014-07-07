package com.cd.pokepraiser.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.AbilityInfo;
import com.cd.pokepraiser.data.AttackInfo;
import com.cd.pokepraiser.data.ItemInfo;
import com.cd.pokepraiser.data.NatureInfo;
import com.cd.pokepraiser.data.PokemonAttackInfo;
import com.cd.pokepraiser.data.PokemonAttributes;
import com.cd.pokepraiser.data.PokemonDetail;
import com.cd.pokepraiser.data.TeamMemberAttributes;
import com.cd.pokepraiser.db.dao.AbilitiesDataSource;
import com.cd.pokepraiser.db.dao.AttacksDataSource;
import com.cd.pokepraiser.db.dao.ItemsDataSource;
import com.cd.pokepraiser.db.dao.PokemonDataSource;
import com.cd.pokepraiser.db.dao.TeamDataSource;
import com.cd.pokepraiser.dialog.AbilitySearchDialog;
import com.cd.pokepraiser.dialog.AbilitySearchDialog.AbilitySearchDialogListener;
import com.cd.pokepraiser.dialog.AttackSearchDialog;
import com.cd.pokepraiser.dialog.AttackSearchDialog.AttackSearchDialogListener;
import com.cd.pokepraiser.dialog.ItemSearchDialog;
import com.cd.pokepraiser.dialog.ItemSearchDialog.ItemSearchDialogListener;
import com.cd.pokepraiser.dialog.NatureSearchDialog;
import com.cd.pokepraiser.dialog.NatureSearchDialog.NatureSearchDialogListener;
import com.cd.pokepraiser.filter.EVFilter;
import com.cd.pokepraiser.util.AttackUtils;
import com.cd.pokepraiser.util.ExtrasConstants;
import com.cd.pokepraiser.util.TypeUtils;

public class TeamMemberFragment extends SherlockFragment implements AbilitySearchDialogListener, 
																	AttackSearchDialogListener,
																	ItemSearchDialogListener,
																	NatureSearchDialogListener{

	public static final String TAG = "memberFragment";
	
	private TeamMemberAttributes 	mMemberAttributes;
	private PokemonDetail			mPokemonDetail;
	private ArrayList<AttackInfo>	mAttackInfoList;
	private ArrayList<ItemInfo> 	mItems;
	private ArrayList<NatureInfo> mNatures;	
	
	private PokemonDataSource 	mPokemonDataSource;
	private TeamDataSource 	  	mTeamDataSource;	
	private AbilitiesDataSource mAbilitiesDataSource;	
	private AttacksDataSource 	mAttacksDataSource;	
	private ItemsDataSource 	mItemsDataSource;
	
	private AbilitySearchDialog mAbilitySearch;
	private AttackSearchDialog 	mAttackSearch;
	private NatureSearchDialog 	mNatureSearch;
	private ItemSearchDialog 	mItemSearch;	
	
	private ViewGroup			mParentContainer;

	private Button				mNatureModifyButton;
	private ImageView			mNatureCancel;
	
	private Button				mItemModifyButton;
	private ImageView			mItemCancel;	
	
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
		Activity parent = getActivity();
        
		mTeamDataSource = new TeamDataSource(((PokepraiserApplication)parent.getApplication()).getTeamdbDatabaseReference());    	
    	
		if(savedInstanceState != null){
			mMemberAttributes = (TeamMemberAttributes) savedInstanceState.getSerializable(ExtrasConstants.MEMBER_ID);
			mPokemonDetail = (PokemonDetail) savedInstanceState.getParcelable(ExtrasConstants.POKEMON_ID);
			mAttackInfoList = (ArrayList<AttackInfo>) savedInstanceState.getSerializable(ExtrasConstants.ATTACK_ID);
		}else{
			Bundle bundle = getArguments();
			final int memberId = bundle.getInt(ExtrasConstants.MEMBER_ID);
			
            mPokemonDataSource 	= new PokemonDataSource(((PokepraiserApplication)parent.getApplication()).getPokedbDatabaseReference());
            mAbilitiesDataSource = new AbilitiesDataSource(((PokepraiserApplication)parent.getApplication()).getPokedbDatabaseReference());
            mAttacksDataSource 	= new AttacksDataSource(((PokepraiserApplication)parent.getApplication()).getPokedbDatabaseReference());        	
	        mItemsDataSource = new ItemsDataSource(((PokepraiserApplication)getActivity().getApplication()).getPokedbDatabaseReference());
	        
	        mItemsDataSource.open();
	        mItems = mItemsDataSource.getAllItemInfo();
	        mItemsDataSource.close();			
            
            mTeamDataSource.openRead();
            mMemberAttributes = mTeamDataSource.getTeamMember(memberId);
            mTeamDataSource.close();
            
	        mPokemonDataSource.open();
	        PokemonAttributes pokemonAttributes = mPokemonDataSource.getPokemonAttributes(mMemberAttributes.getPokemonId(), getResources());
	        mNatures							= mPokemonDataSource.getAllNatureInfo();
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
            
			mAttackInfoList = new ArrayList<AttackInfo>(AttackUtils.convertPokemonAttackInfoToAttackInfo(pokemonAttacks));
			
        	mPokemonDetail = new PokemonDetail();
        	mPokemonDetail.setPokemonAttributes(pokemonAttributes);
        	mPokemonDetail.setPokemonAbilities(pokemonAbilities);
        	mPokemonDetail.setPokemonAttacks(pokemonAttacks);			
		}		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		mParentContainer = (ViewGroup) inflater.inflate(R.layout.team_member_screen, container, false);	

		PokemonAttributes pokemonAttributes 	= mPokemonDetail.getPokemonAttributes();
		AbilityInfo [] pokemonAbilities			= mPokemonDetail.getPokemonAbilities();
		
		mAbilitySearch				= new AbilitySearchDialog(Arrays.asList(pokemonAbilities));
		mAttackSearch				= new AttackSearchDialog(mAttackInfoList);
		mItemSearch					= new ItemSearchDialog(mItems);
		mNatureSearch				= new NatureSearchDialog(mNatures);

        mNatureCancel				= (ImageView) mParentContainer.findViewById(R.id.natureCancel);		
        mNatureModifyButton 		= (Button) mParentContainer.findViewById(R.id.natureSearch);		
        
        mItemCancel					= (ImageView) mParentContainer.findViewById(R.id.itemCancel);		
        mItemModifyButton 			= (Button) mParentContainer.findViewById(R.id.itemSearch);        
		
        mAbilityCancel				= (ImageView) mParentContainer.findViewById(R.id.abilityCancel);		
        mAbilityModifyButton 		= (Button) mParentContainer.findViewById(R.id.abilitySearch);		
		
        mAttackOneSearchButton		= (Button) mParentContainer.findViewById(R.id.attackOneSearch);
        mAttackTwoSearchButton		= (Button) mParentContainer.findViewById(R.id.attackTwoSearch);
        mAttackThreeSearchButton	= (Button) mParentContainer.findViewById(R.id.attackThreeSearch);
        mAttackFourSearchButton		= (Button) mParentContainer.findViewById(R.id.attackFourSearch);		
		
        mAttackOneCancel			= (ImageView) mParentContainer.findViewById(R.id.attackOneCancel);
        mAttackTwoCancel			= (ImageView) mParentContainer.findViewById(R.id.attackTwoCancel);
        mAttackThreeCancel			= (ImageView) mParentContainer.findViewById(R.id.attackThreeCancel);
        mAttackFourCancel			= (ImageView) mParentContainer.findViewById(R.id.attackFourCancel);		
		
        mHpEvsEdit					= (EditText) mParentContainer.findViewById(R.id.hpEvs);
        mAtkEvsEdit					= (EditText) mParentContainer.findViewById(R.id.atkEvs);
        mDefEvsEdit					= (EditText) mParentContainer.findViewById(R.id.defEvs);
        mSpatkEvsEdit				= (EditText) mParentContainer.findViewById(R.id.spatkEvs);
        mSpdefEvsEdit				= (EditText) mParentContainer.findViewById(R.id.spdefEvs);
        mSpeEvsEdit					= (EditText) mParentContainer.findViewById(R.id.speEvs);        
        
        TextView dexNo		 		= (TextView) mParentContainer.findViewById(R.id.dexNo);
        TextView pokemonName		= (TextView) mParentContainer.findViewById(R.id.pokemonName);
        
        ImageView pokemonPicture	= (ImageView) mParentContainer.findViewById(R.id.pokemonPicture);
        
        ImageView typeOne			= (ImageView) mParentContainer.findViewById(R.id.typeOne);
        ImageView typeTwo			= (ImageView) mParentContainer.findViewById(R.id.typeTwo);
        
        TextView baseHp			= (TextView) mParentContainer.findViewById(R.id.baseHp);
        TextView baseAtk		= (TextView) mParentContainer.findViewById(R.id.baseAtk);
        TextView baseDef		= (TextView) mParentContainer.findViewById(R.id.baseDef);
        TextView baseSpatk		= (TextView) mParentContainer.findViewById(R.id.baseSpatk);
        TextView baseSpdef		= (TextView) mParentContainer.findViewById(R.id.baseSpdef);
        TextView baseSpe		= (TextView) mParentContainer.findViewById(R.id.baseSpe);        
        
        dexNo.setText(Integer.toString(pokemonAttributes.getDexNo()));
        pokemonName.setText(pokemonAttributes.getName());
        
        pokemonPicture.setImageResource(pokemonAttributes.getImgDrawable());
        
        //Make the type images and draw them
        typeOne.setImageResource(TypeUtils.getTypeDrawableId(pokemonAttributes.getTypeOne()));
        
        if(pokemonAttributes.getTypeTwo() != 0){
            typeTwo.setImageResource(TypeUtils.getTypeDrawableId(pokemonAttributes.getTypeTwo()));        	
        }        
		
        final ImageView [] theClickableImages = new ImageView[]{mNatureCancel,
        														mItemCancel,
        														mAbilityCancel, 
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
					final Object temp = getActivity().getCurrentFocus();
					
					//If you set the text of an edit text before it is in the view, this will fire and have null because nothing is in focus!
					if(temp != null && !(temp instanceof EditText))
						return;
					
					EditText editText = (EditText) temp;
					
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
		
		//NPE Right here???
		mHpEvsEdit.setText(Integer.toString(mMemberAttributes.getHp()));
		mAtkEvsEdit.setText(Integer.toString(mMemberAttributes.getAtk()));
		mDefEvsEdit.setText(Integer.toString(mMemberAttributes.getDef()));
		mSpatkEvsEdit.setText(Integer.toString(mMemberAttributes.getSpatk()));
		mSpdefEvsEdit.setText(Integer.toString(mMemberAttributes.getSpdef()));
		mSpeEvsEdit.setText(Integer.toString(mMemberAttributes.getSpe()));		

		if(mMemberAttributes.getNature() != null && mMemberAttributes.getNature() != getString(R.string.none)){
			mNatureCancel.setVisibility(View.VISIBLE);
			mNatureModifyButton.setText(mMemberAttributes.getNature());
		}
		
		mNatureModifyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openNatureSearchDialog(v);
			}
		});		
		
		if(mMemberAttributes.getItem() != null && mMemberAttributes.getItem() != getString(R.string.none)){
			mItemModifyButton.setText(mMemberAttributes.getItem());
			mItemCancel.setVisibility(View.VISIBLE);
		}
		
		mItemModifyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openItemSearchDialog(v);
			}
		});		
		
		mAbilityModifyButton.setText(getChosenAbility());

		if(mAbilityModifyButton.getText() != getString(R.string.none))
			mAbilityCancel.setVisibility(View.VISIBLE);
		
		mAbilityModifyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openAbilitySearchDialog(v);
			}
		});
		
		mAttackOneSearchButton.setText(getChosenAttack(1));
		mAttackTwoSearchButton.setText(getChosenAttack(2));
		mAttackThreeSearchButton.setText(getChosenAttack(3));
		mAttackFourSearchButton.setText(getChosenAttack(4));		
		
		if(mAttackOneSearchButton.getText() != getString(R.string.none))
			mAttackOneCancel.setVisibility(View.VISIBLE);
		
		if(mAttackTwoSearchButton.getText() != getString(R.string.none))
			mAttackTwoCancel.setVisibility(View.VISIBLE);
		
		if(mAttackThreeSearchButton.getText() != getString(R.string.none))
			mAttackThreeCancel.setVisibility(View.VISIBLE);

		if(mAttackFourSearchButton.getText() != getString(R.string.none))
			mAttackFourCancel.setVisibility(View.VISIBLE);		
		
		final Button [] attackSearchButtons = new Button [] {mAttackOneSearchButton, mAttackTwoSearchButton, mAttackThreeSearchButton, mAttackFourSearchButton};
		
		for(Button attackButton : attackSearchButtons){
			attackButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					openAttackSearchDialog(v);
				}
			});
		}
		
		Button hp50 = (Button) mParentContainer.findViewById(R.id.hpAt50);
		Button hp100 = (Button) mParentContainer.findViewById(R.id.hpAt100);		

		Button atk50 = (Button) mParentContainer.findViewById(R.id.atkAt50);
		Button atk100 = (Button) mParentContainer.findViewById(R.id.atkAt100);
		
		Button def50 = (Button) mParentContainer.findViewById(R.id.defAt50);
		Button def100 = (Button) mParentContainer.findViewById(R.id.defAt100);
		
		Button spatk50 = (Button) mParentContainer.findViewById(R.id.spatkAt50);
		Button spatk100 = (Button) mParentContainer.findViewById(R.id.spatkAt100);
		
		Button spdef50 = (Button) mParentContainer.findViewById(R.id.spdefAt50);
		Button spdef100 = (Button) mParentContainer.findViewById(R.id.spdefAt100);
		
		Button spe50 = (Button) mParentContainer.findViewById(R.id.speAt50);
		Button spe100 = (Button) mParentContainer.findViewById(R.id.speAt100);
		
		final Button [] statTableButtons = new Button [] {hp50, hp100, atk50, atk100, def50, def100, spatk50, spatk100, spdef50, spdef100, spe50, spe100};
		
		for(Button toggleButton : statTableButtons){
			toggleButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					toggleStatTable(v);
				}
			});
		}
		
		createStatTableFragments();
		
        ((PokepraiserApplication)getActivity().getApplication()).overrideFonts(mParentContainer);		
		
		return mParentContainer;
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
							mAttackOneSearchButton.setText(selectedAttack.getName());     
							mAttackOneCancel.setVisibility(View.VISIBLE);             
							new UpdateTeamMemberDataTask().execute();
							break;                                                        
							                                                              
			case R.id.attackTwoSearch:                                                    
							mMemberAttributes.setMoveTwo(selectedAttack.getAttackDbId());
							mAttackTwoSearchButton.setText(selectedAttack.getName());     
							mAttackTwoCancel.setVisibility(View.VISIBLE);
							new UpdateTeamMemberDataTask().execute();
							break;                                                        
							                                                              
			case R.id.attackThreeSearch:                                                  
							mMemberAttributes.setMoveThree(selectedAttack.getAttackDbId());
							mAttackThreeSearchButton.setText(selectedAttack.getName());   
							mAttackThreeCancel.setVisibility(View.VISIBLE);
							new UpdateTeamMemberDataTask().execute();
							break;                                                        
							                                                              
			case R.id.attackFourSearch:                                                   
							mMemberAttributes.setMoveFour(selectedAttack.getAttackDbId());
							mAttackFourSearchButton.setText(selectedAttack.getName());    
							mAttackFourCancel.setVisibility(View.VISIBLE);
							new UpdateTeamMemberDataTask().execute();
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
		new UpdateTeamMemberDataTask().execute();
		
		dialog.dismiss();		
	}	
	
	@Override
	public void onNatureItemClick(NatureSearchDialog dialog) {
		NatureInfo selectedNature = dialog.getSelectedItem();
		
		mMemberAttributes.setNature(selectedNature.getName());
		mNatureModifyButton.setText(selectedNature.getName());
		mNatureCancel.setVisibility(View.VISIBLE);		
		new UpdateTeamMemberDataTask().execute();
		
		dialog.dismiss();
	}

	@Override
	public void onItemItemClick(ItemSearchDialog dialog) {
		ItemInfo selectedItem = dialog.getSelectedItem();
		
		mMemberAttributes.setItem(selectedItem.getName());
		mItemModifyButton.setText(selectedItem.getName());
		mItemCancel.setVisibility(View.VISIBLE);		
		new UpdateTeamMemberDataTask().execute();
		
		dialog.dismiss();
	}	

    public void openNatureSearchDialog(View v){
    	mNatureSearch.setTargetFragment(this, 0);
    	mNatureSearch.show(getActivity().getSupportFragmentManager(), null);
    }
    
    public void openItemSearchDialog(View v){
    	mItemSearch.setTargetFragment(this, 0);
    	mItemSearch.show(getActivity().getSupportFragmentManager(), null);
    }    
	
    public void openAbilitySearchDialog(View v){
    	mAbilitySearch.setTargetFragment(this, 0);
    	mAbilitySearch.show(getActivity().getSupportFragmentManager(), null);
    }
    
    public void openAttackSearchDialog(View v){
    	mAttackSearch.setTargetFragment(this, 0);
    	mAttackSearch.setOriginButton(v.getId());
    	mAttackSearch.show(getActivity().getSupportFragmentManager(), null);
    }
    
	public void handleCancelClick(View v){
		final int viewId				= v.getId();
		
		switch(viewId){
			case R.id.itemCancel:
								v.setVisibility(View.INVISIBLE);
								mItemModifyButton.setText(R.string.none);
								mMemberAttributes.setItem(getString(R.string.none));
								new UpdateTeamMemberDataTask().execute();
								break;
								
			case R.id.natureCancel:
								v.setVisibility(View.INVISIBLE);
								mNatureModifyButton.setText(R.string.none);
								mMemberAttributes.setNature(getString(R.string.none));
								new UpdateTeamMemberDataTask().execute();
								break;								
		
			case R.id.abilityCancel:
								v.setVisibility(View.INVISIBLE);
								mAbilityModifyButton.setText(R.string.none);
								mMemberAttributes.setAbility(-1);
								new UpdateTeamMemberDataTask().execute();
								break;
				
			case R.id.attackOneCancel:
								v.setVisibility(View.INVISIBLE);
								mAttackOneSearchButton.setText(R.string.none);
								mMemberAttributes.setMoveOne(-1);
								new UpdateTeamMemberDataTask().execute();
								break;
				
			case R.id.attackTwoCancel:
								v.setVisibility(View.INVISIBLE);
								mAttackTwoSearchButton.setText(R.string.none);
								mMemberAttributes.setMoveTwo(-1);
								new UpdateTeamMemberDataTask().execute();
								break;
				
			case R.id.attackThreeCancel:
								v.setVisibility(View.INVISIBLE);
								mAttackThreeSearchButton.setText(R.string.none);
								mMemberAttributes.setMoveThree(-1);
								new UpdateTeamMemberDataTask().execute();
								break;
				
			case R.id.attackFourCancel:
								v.setVisibility(View.INVISIBLE);
								mAttackFourSearchButton.setText(R.string.none);
								mMemberAttributes.setMoveFour(-1);
								new UpdateTeamMemberDataTask().execute();
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
				container = mParentContainer.findViewById(R.id.hpStatsFragmentContainer50);
				break;
				
			case R.id.hpAt100:
				container = mParentContainer.findViewById(R.id.hpStatsFragmentContainer100);				
				break;
				
			case R.id.atkAt50:
				container = mParentContainer.findViewById(R.id.atkStatsFragmentContainer50);				
				break;
				
			case R.id.atkAt100:
				container = mParentContainer.findViewById(R.id.atkStatsFragmentContainer100);				
				break;
				
			case R.id.defAt50:
				container = mParentContainer.findViewById(R.id.defStatsFragmentContainer50);				
				break;
				
			case R.id.defAt100:
				container = mParentContainer.findViewById(R.id.defStatsFragmentContainer100);				
				break;	
				
			case R.id.spatkAt50:
				container = mParentContainer.findViewById(R.id.spatkStatsFragmentContainer50);				
				break;
				
			case R.id.spatkAt100:
				container = mParentContainer.findViewById(R.id.spatkStatsFragmentContainer100);				
				break;
				
			case R.id.spdefAt50:
				container = mParentContainer.findViewById(R.id.spdefStatsFragmentContainer50);				
				break;
				
			case R.id.spdefAt100:
				container = mParentContainer.findViewById(R.id.spdefStatsFragmentContainer100);				
				break;
				
			case R.id.speAt50:
				container = mParentContainer.findViewById(R.id.speStatsFragmentContainer50);				
				break;
				
			case R.id.speAt100:
				container = mParentContainer.findViewById(R.id.speStatsFragmentContainer100);				
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
			MemberStatsTableFragment fragment = new MemberStatsTableFragment();
			
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
			FragmentTransaction txn = getChildFragmentManager().beginTransaction();
			txn.add(parentViews[i], fragment, mStatTableTags[i]);
			txn.commit();
		}
		
	}

	protected void handleEVChanged(int id, int evs) {
		MemberStatsTableFragment table50 = null;
		MemberStatsTableFragment table100 = null;
		final FragmentManager manager = getChildFragmentManager();
		
		switch (id) {
			case R.id.hpEvs:
				mMemberAttributes.setHp(evs);
				table50 = (MemberStatsTableFragment) manager.findFragmentByTag(mStatTableTags[0]);
				table100 = (MemberStatsTableFragment) manager.findFragmentByTag(mStatTableTags[1]);
				new UpdateTeamMemberDataTask().execute();
				break;
				
			case R.id.atkEvs:
				mMemberAttributes.setAtk(evs);				
				table50 = (MemberStatsTableFragment) manager.findFragmentByTag(mStatTableTags[2]);
				table100 = (MemberStatsTableFragment) manager.findFragmentByTag(mStatTableTags[3]);
				new UpdateTeamMemberDataTask().execute();
				break;
				
			case R.id.defEvs:
				mMemberAttributes.setDef(evs);				
				table50 = (MemberStatsTableFragment) manager.findFragmentByTag(mStatTableTags[4]);
				table100 = (MemberStatsTableFragment) manager.findFragmentByTag(mStatTableTags[5]);
				new UpdateTeamMemberDataTask().execute();
				break;
				
			case R.id.spatkEvs:
				mMemberAttributes.setSpatk(evs);				
				table50 = (MemberStatsTableFragment) manager.findFragmentByTag(mStatTableTags[6]);
				table100 = (MemberStatsTableFragment) manager.findFragmentByTag(mStatTableTags[7]);
				new UpdateTeamMemberDataTask().execute();
				break;
				
			case R.id.spdefEvs:
				mMemberAttributes.setSpdef(evs);				
				table50 = (MemberStatsTableFragment) manager.findFragmentByTag(mStatTableTags[8]);
				table100 = (MemberStatsTableFragment) manager.findFragmentByTag(mStatTableTags[9]);
				new UpdateTeamMemberDataTask().execute();
				break;
				
			case R.id.speEvs:
				mMemberAttributes.setSpe(evs);				
				table50 = (MemberStatsTableFragment) manager.findFragmentByTag(mStatTableTags[10]);
				table100 = (MemberStatsTableFragment) manager.findFragmentByTag(mStatTableTags[11]);	
				new UpdateTeamMemberDataTask().execute();
				break;				
		}
		
		if(table50 != null){
			table50.setEvs(evs);
			table100.setEvs(evs);
			table50.refreshRanges();
			table100.refreshRanges();
		}
	}	
	
	private String getChosenAttack(int i) {
		switch(i){
			case 1:
				return findAttack(mMemberAttributes.getMoveOne());
				
			case 2:
				return findAttack(mMemberAttributes.getMoveTwo());
				
			case 3:
				return findAttack(mMemberAttributes.getMoveThree());
				
			case 4:
				return findAttack(mMemberAttributes.getMoveFour());
				
			default:
				return getString(R.string.none);				
		}
	}
	
	private String findAttack(int attackId){
		for(PokemonAttackInfo attack : mPokemonDetail.getPokemonAttacks()){
			if(attack.getAttackDbId() == attackId){
				return attack.getName();
			}
		}
		
		return getString(R.string.none);
	}

	private String getChosenAbility() {
		for(AbilityInfo ability : mPokemonDetail.getPokemonAbilities()){
			if(ability.getAbilityDbId() == mMemberAttributes.getAbility()){
				return ability.getName();
			}
		}
		
		return getString(R.string.none);
	}	
	
    private class UpdateTeamMemberDataTask extends AsyncTask<Void, Void, Void> {
    	
		@Override
		protected Void doInBackground(Void... params) {
			mTeamDataSource.openWrite();
			mTeamDataSource.updateTeamMember(mMemberAttributes);
			mTeamDataSource.close();
			
			return null;
		}    	
    	
    }
}
