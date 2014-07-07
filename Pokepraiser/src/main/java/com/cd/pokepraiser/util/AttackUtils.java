package com.cd.pokepraiser.util;

import java.util.ArrayList;
import java.util.List;

import com.cd.pokepraiser.data.AttackInfo;
import com.cd.pokepraiser.data.PokemonAttackInfo;

public class AttackUtils {

	public static List<AttackInfo> convertPokemonAttackInfoToAttackInfo(List<PokemonAttackInfo> thePokemonAttackInfos){
		final List<AttackInfo> theAttackInfos = new ArrayList<AttackInfo>(thePokemonAttackInfos.size());
		
		for(PokemonAttackInfo pokemonAttack : thePokemonAttackInfos){
			final AttackInfo attackInfo = new AttackInfo();
			
			attackInfo.setName(pokemonAttack.getName());
			attackInfo.setAttackDbId(pokemonAttack.getAttackDbId());
			
			theAttackInfos.add(attackInfo);
		}
		
		return theAttackInfos;
	}
	
}
