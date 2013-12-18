package com.cd.pokepraiser.db.util;

import com.cd.pokepraiser.data.PokemonSearchQuery;
import com.cd.pokepraiser.db.queries.PokemonQueries;

public class PokemonSearchQueryBuilder {

	private static final String WHERE 	= "WHERE ";
	private static final String AND	 	= "AND ";
	private static final String OR	 	= "OR ";	
	
	private final PokemonSearchQuery mQuery;
	
	public PokemonSearchQueryBuilder(PokemonSearchQuery query){
		mQuery = query;
	}
	
	public String buildQueryString(){
		final int selectedAbility = mQuery.getAbilityId();
		final int [] selectedTypes = getSelectedTypes();
		final int [] selectedAttacks = getSelectedAttacks();
		
		
		final StringBuilder builder = new StringBuilder();

		//
		// "SELECT PKMN._id,"
		//		  PKMN.DEX_NO,"
		//		  PKMN.NAME,"
		//		  PKMN.TYPE_ONE,"
		//		  PKMN.TYPE_TWO,"
		//		  PKMN.ICON_PATH"
		//		  FROM POKEMON AS PKMN";
		
		builder.append(PokemonQueries.POKEMON_SEARCH_QUERY_PT1);
		
		if(selectedAttacks.length > 0){
			// Only join tables when needed
			//
			// JOIN POKEMON_ATTACKS AS PKMN_ATKS 
			// ON PKMN.DEX_NO = PKMN_ATKS.DEX_NO 
			// AND PKMN.ALT_FORM = PKMN_ATKS.ALT_FORM
			builder.append(PokemonQueries.ATTACK_JOIN);
		}
		
		builder.append(WHERE);
		boolean singleWhere = true;
		
		if(selectedAbility != -1){
			builder.append(PokemonQueries.ABILITY_FILTER);
			singleWhere = false;
		}
		
		if(selectedTypes.length == 1){
			
			if(!singleWhere)
				builder.append(AND);
			//Observe evil hack noted below
			//
			// (PKMN.TYPE_ONE = ? OR PKMN.TYPE_TWO = ?)
			builder.append(PokemonQueries.MONO_TYPE_FILTER);
			singleWhere = false;			
		}else if(selectedTypes.length == 2){
			
			if(!singleWhere)			
				builder.append(AND);			
			// (PKMN.TYPE_ONE = ? AND PKMN.TYPE_TWO = ?)
			builder.append(PokemonQueries.DUAL_TYPE_FILTER);
			singleWhere = false;			
		}
		
		if(selectedAttacks.length > 0){
			
			if(!singleWhere)				
				builder.append(AND);
			builder.append("(");
			
			// PKMN_ATKS.ATTACK_NO = ?
			for(int i = 0; i < selectedAttacks.length; i++){
				if(i != 0){
					builder.append(AND);
				}
				
				builder.append(PokemonQueries.ATTACK_WHERE);
			}
			
			builder.append(")");
		}
		
		return builder.toString();
	}
	
	public String [] buildParamsArray(){
		final int selectedAbility = mQuery.getAbilityId();
		int [] selectedTypes = getSelectedTypes();
		final int [] selectedAttacks = getSelectedAttacks();
		
		final String [] paramsArray = new String [getParamsCount(selectedAbility, selectedTypes, selectedAttacks)];

		int nextEle = 0;		
		
		if(selectedAbility != -1){
			//Evil hack
			//Ability param needs to be repeated three times if it exists
			paramsArray[nextEle++] = Integer.toString(selectedAbility);
			paramsArray[nextEle++] = Integer.toString(selectedAbility);
			paramsArray[nextEle++] = Integer.toString(selectedAbility);			
		}
			
		//Evil hack
		//double up types array because the query requires OR and thus additional query slots
		if(selectedTypes.length == 1){
			selectedTypes = new int []{ selectedTypes[0], selectedTypes[0] };
		}
		
		for(int type : selectedTypes){
			paramsArray[nextEle++] = Integer.toString(type);
		}
		
		for(int type : selectedAttacks){
			paramsArray[nextEle++] = Integer.toString(type);
		}		
		
		return paramsArray;
	}

	private int getParamsCount(final int selectedAbility,
								 final int[] selectedTypes, 
								 final int[] selectedAttacks) {
		int paramsCount = 0;
		
		if(selectedAbility != -1)
			paramsCount += 3;
		
		if(selectedTypes.length > 0){
			paramsCount += 2;
		}
		
		paramsCount += selectedAttacks.length;		

		return paramsCount;
	}
	
	private int [] getSelectedTypes(){
		//No types selected
		if(mQuery.getTypeOne() == -1
			&& mQuery.getTypeTwo() == -1){
			return new int[0];
		}else{
			
			//If the user only selected one type
			if(mQuery.getTypeOne() == -1){
				return new int []{ mQuery.getTypeTwo() };
			}else if(mQuery.getTypeTwo() == -1){
				return new int []{ mQuery.getTypeOne() };				
			}
			
			//If the user's second selected type is normal, secretly make it the first
			if(mQuery.getTypeTwo() == 0){
				return new int []{ mQuery.getTypeTwo(), mQuery.getTypeOne() };
			}
			
			//Otherwise, both types were selected
			return new int []{ mQuery.getTypeOne(), mQuery.getTypeTwo() };
		}
	}
	
	private int [] getSelectedAttacks(){
		final int [] theAttacks = new int [4];
		int attackCount = 0;
		
		theAttacks[0] = mQuery.getAttackIdOne();
		theAttacks[1] = mQuery.getAttackIdTwo();
		theAttacks[2] = mQuery.getAttackIdThree();
		theAttacks[3] = mQuery.getAttackIdFour();
		
		//Figure out the number of actual attacks selected
		for(int i = 0; i < theAttacks.length; i++){
			if(theAttacks[i] != -1)
				attackCount++;
		}
		
		final int [] returnableAttacks = new int [attackCount];
		
		int nextAttackPos = 0;
		
		for(int i = 0; i < theAttacks.length; i++){
			if(theAttacks[i] != -1)
				returnableAttacks[nextAttackPos++] = theAttacks[i];
		}
		
		return returnableAttacks;
	}
}
