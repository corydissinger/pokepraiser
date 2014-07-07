package com.cd.pokepraiser.util;

import java.util.List;

import android.content.Context;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.TeamMemberAttributes;
import com.cd.pokepraiser.db.dao.AbilitiesDataSource;
import com.cd.pokepraiser.db.dao.AttacksDataSource;
import com.cd.pokepraiser.db.dao.ItemsDataSource;
import com.cd.pokepraiser.db.dao.PokemonDataSource;

public class TeamExportUtils {

	private Context mContext;
	private PokemonDataSource mPokemonDataSource;
	private AttacksDataSource mAttacksDataSource;
	private AbilitiesDataSource mAbilitiesDataSource;
	
	public TeamExportUtils(Context context){
		mContext = context;
		mPokemonDataSource = new PokemonDataSource(((PokepraiserApplication)context).getPokedbDatabaseReference());
		mAttacksDataSource = new AttacksDataSource(((PokepraiserApplication)context).getPokedbDatabaseReference());
		mAbilitiesDataSource = new AbilitiesDataSource(((PokepraiserApplication)context).getPokedbDatabaseReference());
	}
	
	public String createFormattedTeam(final List<TeamMemberAttributes> theMembers){
		final StringBuilder builder = new StringBuilder();
		
		for(TeamMemberAttributes aMember : theMembers){
			builder.append(formatMember(aMember));
			builder.append("\n");
		}
		
		return builder.toString();
	}

	private String formatMember(final TeamMemberAttributes aMember) {
		final StringBuilder builder = new StringBuilder();		
		
		mPokemonDataSource.open();
		builder.append(mPokemonDataSource.getPokemonName(aMember.getPokemonId()));
		mPokemonDataSource.close();
		
		builder.append(" @ ");
		
		if(aMember.getItem() == null || "".equals(aMember.getItem())){
			builder.append(mContext.getString(R.string.none));
		}else{
			builder.append(aMember.getItem());
		}
		
		builder.append("\n");
		
		builder.append("Ability: ");
		
		if(aMember.getAbility() <= 0){
			builder.append(mContext.getString(R.string.none));			
		}else{
			mAbilitiesDataSource.open();
			builder.append(mAbilitiesDataSource.getAbilityAttributes(aMember.getAbility()).getName());
			mAbilitiesDataSource.close();
		}

		builder.append("\n");
		
		builder.append("EVs: ");
		
		//Awful hack
		boolean needsSlash = false;
		
		if(aMember.getHp() > 0){
			builder.append(aMember.getHp());
			builder.append(" HP");
			needsSlash = true;
		}
		
		if(aMember.getAtk() > 0){
			if(needsSlash)
				builder.append(" / ");
			else
				needsSlash = true;
			
			builder.append(aMember.getAtk());
			builder.append(" Atk");
		}
		
		if(aMember.getDef() > 0){
			if(needsSlash)
				builder.append(" / ");
			else
				needsSlash = true;
			
			builder.append(aMember.getDef());
			builder.append(" Def");
		}
		
		if(aMember.getSpatk() > 0){
			if(needsSlash)
				builder.append(" / ");
			else
				needsSlash = true;
			
			builder.append(aMember.getSpatk());
			builder.append(" SAtk");
		}
		
		if(aMember.getSpdef() > 0){
			if(needsSlash)
				builder.append(" / ");
			else
				needsSlash = true;
			
			builder.append(aMember.getSpdef());
			builder.append(" SDef");
		}
		
		if(aMember.getSpe() > 0){
			if(needsSlash)
				builder.append(" / ");
			else
				needsSlash = true;
			
			builder.append(aMember.getSpe());
			builder.append(" Spd");
		}		

		builder.append("\n");
		
		if(aMember.getNature() == null || "".equals(aMember.getNature())){
			builder.append(mContext.getString(R.string.none));
		}else{
			builder.append(aMember.getNature());
		}
		
		builder.append(" Nature\n");
		
		mAttacksDataSource.open();
		
		builder.append("- ");
		if(aMember.getMoveOne() < 1){
			builder.append(mContext.getString(R.string.none));
		}else{
			builder.append(mAttacksDataSource.getAttackDetail(aMember.getMoveOne()).getName());
		}
		builder.append("\n");

		builder.append("- ");
		if(aMember.getMoveTwo() < 1){
			builder.append(mContext.getString(R.string.none));
		}else{
			builder.append(mAttacksDataSource.getAttackDetail(aMember.getMoveTwo()).getName());
		}
		builder.append("\n");		

		builder.append("- ");
		if(aMember.getMoveThree() < 1){
			builder.append(mContext.getString(R.string.none));
		}else{
			builder.append(mAttacksDataSource.getAttackDetail(aMember.getMoveThree()).getName());
		}
		builder.append("\n");		

		builder.append("- ");
		if(aMember.getMoveFour() < 1){
			builder.append(mContext.getString(R.string.none));
		}else{
			builder.append(mAttacksDataSource.getAttackDetail(aMember.getMoveFour()).getName());
		}
		builder.append("\n");		
		
		mAttacksDataSource.close();			
		// TODO Auto-generated method stub
		return builder.toString();
	}
	
}
