package com.cd.pokepraiser.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cd.pokepraiser.data.ItemInfo;
import com.cd.pokepraiser.data.NatureInfo;
import com.cd.pokepraiser.data.PokemonAttributes;
import com.cd.pokepraiser.data.PokemonInfo;
import com.cd.pokepraiser.data.PokemonSearchQuery;
import com.cd.pokepraiser.data.TeamMemberAttributes;
import com.cd.pokepraiser.db.PokeDbHelper;
import com.cd.pokepraiser.db.queries.ItemQueries;
import com.cd.pokepraiser.db.queries.PokemonQueries;
import com.cd.pokepraiser.db.util.PokemonSearchQueryBuilder;

public class PokemonDataSource {
	
	private SQLiteDatabase db;
	private PokeDbHelper dbHelper;
	
	public PokemonDataSource(PokeDbHelper dbHelper){
		this.dbHelper = dbHelper;
	}
	
	public void open() throws SQLException {
		db = dbHelper.getReadableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}	
	
	public ArrayList<PokemonInfo> getPokemonList(Resources resources){
		Cursor cursor = db.rawQuery(PokemonQueries.GET_ALL_POKEMON_INFO, null);

		final ArrayList<PokemonInfo> pokemonInfoList = new ArrayList<PokemonInfo>(cursor.getCount());
		
		while(cursor.moveToNext()){
			final PokemonInfo info = new PokemonInfo();
			
			//TODO: Never name Android resources with hyphens, use underscores!
			final int drawableId 			= resources.getIdentifier(cursor.getString(5), "drawable", "com.cd.pokepraiser");
			
			info.setId(cursor.getInt(0));
			info.setDexNo(cursor.getInt(1));
			info.setName(cursor.getString(2));
			info.setTypeOne(cursor.getInt(3));
			info.setTypeTwo(cursor.getInt(4));
			info.setIconDrawable(drawableId);
			
			pokemonInfoList.add(info);
		}
		
		cursor.close();
		return pokemonInfoList;
	}
	
	public PokemonAttributes getPokemonAttributes(int pokemonId, Resources resources){
		Cursor cursor = db.rawQuery(PokemonQueries.GET_POKEMON_DETAIL, new String [] { Integer.toString(pokemonId) });

		final PokemonAttributes pokemonDetail = new PokemonAttributes();
		cursor.moveToFirst();
		
		pokemonDetail.setPokemonId(cursor.getInt(0));
		pokemonDetail.setDexNo(cursor.getInt(1));
		pokemonDetail.setName(cursor.getString(2));
		pokemonDetail.setTypeOne(cursor.getInt(3));
		pokemonDetail.setTypeTwo(cursor.getInt(4));
		pokemonDetail.setBsHp(cursor.getInt(5));
		pokemonDetail.setBsAtk(cursor.getInt(6));
		pokemonDetail.setBsDef(cursor.getInt(7));
		pokemonDetail.setBsSpatk(cursor.getInt(8));
		pokemonDetail.setBsSpdef(cursor.getInt(9));
		pokemonDetail.setBsSpd(cursor.getInt(10));
		pokemonDetail.setAbOne(cursor.getInt(11));
		pokemonDetail.setAbTwo(cursor.getInt(12));
		pokemonDetail.setAbHa(cursor.getInt(13));
		pokemonDetail.setEggOne(cursor.getString(14));
		pokemonDetail.setEggTwo(cursor.getString(15));
		pokemonDetail.setAltForm(cursor.getInt(18));

		final int imgDrawableId 		= resources.getIdentifier(cursor.getString(16), "drawable", "com.cd.pokepraiser");
		final int icnDrawableId 		= resources.getIdentifier(cursor.getString(17), "drawable", "com.cd.pokepraiser");		
		
		pokemonDetail.setImgDrawable(imgDrawableId);
		pokemonDetail.setIconDrawable(icnDrawableId);		
		
		cursor.close();
		return pokemonDetail;
	}

	public ArrayList<PokemonInfo> getPokemonLearningAbility(final int theAbilityId, Resources resources){
		Cursor cursor = db.rawQuery(PokemonQueries.GET_POKEMON_LEARNING_ABILITY, 
										new String []{ Integer.toString(theAbilityId), 
													   Integer.toString(theAbilityId),
													   Integer.toString(theAbilityId)});

		final ArrayList<PokemonInfo> pokemonInfoList = new ArrayList<PokemonInfo>(cursor.getCount());
		
		while(cursor.moveToNext()){
			final PokemonInfo info = new PokemonInfo();
			
			//TODO: Never name Android resources with hyphens, use underscores!
			final int drawableId 			= resources.getIdentifier(cursor.getString(5), "drawable", "com.cd.pokepraiser");
			
			info.setId(cursor.getInt(0));
			info.setDexNo(cursor.getInt(1));
			info.setName(cursor.getString(2));
			info.setTypeOne(cursor.getInt(3));
			info.setTypeTwo(cursor.getInt(4));
			info.setIconDrawable(drawableId);
			
			pokemonInfoList.add(info);
		}
		
		cursor.close();
		return pokemonInfoList;
	}
	
	public List<PokemonInfo> getPokemonLearningAttack(final int theAttackId, Resources resources){
		Cursor cursor = db.rawQuery(PokemonQueries.GET_POKEMON_LEARNING_ATTACK, 
										new String []{ Integer.toString(theAttackId)});

		final ArrayList<PokemonInfo> pokemonInfoList = new ArrayList<PokemonInfo>(cursor.getCount());
		
		while(cursor.moveToNext()){
			final PokemonInfo info = new PokemonInfo();
			
			//TODO: Never name Android resources with hyphens, use underscores!
			final int drawableId 			= resources.getIdentifier(cursor.getString(5), "drawable", "com.cd.pokepraiser");
			
			info.setId(cursor.getInt(0));
			info.setDexNo(cursor.getInt(1));
			info.setName(cursor.getString(2));
			info.setTypeOne(cursor.getInt(3));
			info.setTypeTwo(cursor.getInt(4));
			info.setIconDrawable(drawableId);
			
			pokemonInfoList.add(info);
		}
		
		cursor.close();
		return pokemonInfoList;
	}	
	
	public List<PokemonInfo> getPokemonSearch(final PokemonSearchQuery searchQuery, Resources resources){
		final PokemonSearchQueryBuilder queryBuilder = new PokemonSearchQueryBuilder(searchQuery);
		
		Cursor cursor = db.rawQuery(queryBuilder.buildQueryString(), queryBuilder.buildParamsArray());
		final ArrayList<PokemonInfo> pokemonInfoList = new ArrayList<PokemonInfo>(cursor.getCount());		

		while(cursor.moveToNext()){
			final PokemonInfo info = new PokemonInfo();
			
			//TODO: Never name Android resources with hyphens, use underscores!
			final int drawableId 			= resources.getIdentifier(cursor.getString(5), "drawable", "com.cd.pokepraiser");
			
			info.setId(cursor.getInt(0));
			info.setDexNo(cursor.getInt(1));
			info.setName(cursor.getString(2));
			info.setTypeOne(cursor.getInt(3));
			info.setTypeTwo(cursor.getInt(4));
			info.setIconDrawable(drawableId);
			
			pokemonInfoList.add(info);
		}		
		
		cursor.close();
		return pokemonInfoList;
	}
	
	public void addPokemonNames(final List<TeamMemberAttributes> teamMembers){
		for(TeamMemberAttributes member : teamMembers){
			Cursor cursor = db.rawQuery(PokemonQueries.GET_POKEMON_NAME, new String [] { Integer.toString(member.getPokemonId()) });
			
			if(!cursor.moveToFirst()){
				cursor.close();
				continue;
			}
			
			final String pokemonName = cursor.getString(0);
			cursor.close();
			
			member.setPokemonName(pokemonName);
		}
	}

	public String getPokemonName(int selectedPokeId) {
		Cursor cursor = db.rawQuery(PokemonQueries.GET_POKEMON_NAME, new String [] { Integer.toString(selectedPokeId) });
		
		if(!cursor.moveToFirst()){
			cursor.close();
			return "";
		}
		
		final String pokemonName = cursor.getString(0);
		cursor.close();
		
		return pokemonName;
	}

	public ArrayList<NatureInfo> getAllNatureInfo() {
		Cursor cursor = db.rawQuery(PokemonQueries.GET_ALL_NATURE_INFO, null);

		final ArrayList<NatureInfo> natureInfoList = new ArrayList<NatureInfo>(cursor.getCount());
		
		while(cursor.moveToNext()){
			final NatureInfo nature = new NatureInfo();
			
			nature.setId(cursor.getInt(0));
			nature.setName(cursor.getString(1));
			nature.setPlus(cursor.getInt(2));
			nature.setMinus(cursor.getInt(3));
			
			natureInfoList.add(nature);
		}
		
		cursor.close();
		return natureInfoList;
	}

	public ArrayList<PokemonInfo> getAllPokemonInEggGroup(String eggGroup, Resources resources) {
		Cursor cursor = db.rawQuery(PokemonQueries.GET_EGG_GROUP_ID, new String [] { eggGroup });		
		int eggGroupId = 0;
		
		while(cursor.moveToNext()){
			eggGroupId = cursor.getInt(0);
		}
		
		cursor.close();
		
		//Now that we have the egg group int ID again, get all of the matching pokes
		cursor = db.rawQuery(PokemonQueries.GET_ALL_POKEMON_IN_EGG_GROUP, new String [] { Integer.toString(eggGroupId), Integer.toString(eggGroupId) });

		final ArrayList<PokemonInfo> pokemonInfoList = new ArrayList<PokemonInfo>(cursor.getCount());
		
		while(cursor.moveToNext()){
			final PokemonInfo info = new PokemonInfo();
			
			//TODO: Never name Android resources with hyphens, use underscores!
			final int drawableId 			= resources.getIdentifier(cursor.getString(5), "drawable", "com.cd.pokepraiser");
			
			info.setId(cursor.getInt(0));
			info.setDexNo(cursor.getInt(1));
			info.setName(cursor.getString(2));
			info.setTypeOne(cursor.getInt(3));
			info.setTypeTwo(cursor.getInt(4));
			info.setIconDrawable(drawableId);
			
			pokemonInfoList.add(info);
		}
		
		cursor.close();		
		
		return pokemonInfoList;
	}
}
