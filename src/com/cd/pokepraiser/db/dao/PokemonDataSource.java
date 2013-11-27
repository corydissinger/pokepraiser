package com.cd.pokepraiser.db.dao;

import java.util.ArrayList;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cd.pokepraiser.data.PokemonDetail;
import com.cd.pokepraiser.data.PokemonInfo;
import com.cd.pokepraiser.db.DatabaseHelper;
import com.cd.pokepraiser.db.queries.PokemonQueries;

public class PokemonDataSource {
	
	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;
	
	public PokemonDataSource(DatabaseHelper dbHelper){
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
			final String safeResourceName	= cursor.getString(5).replace("-", "_");
			final int drawableId 			= resources.getIdentifier(safeResourceName, "drawable", "com.cd.pokepraiser");
			
			info.setPokemonId(cursor.getInt(0));
			info.setDexNo(cursor.getInt(1));
			info.setPokemonName(cursor.getString(2));
			info.setTypeOne(cursor.getInt(3));
			info.setTypeTwo(cursor.getInt(4));
			info.setIconDrawable(drawableId);
			
			pokemonInfoList.add(info);
		}
		
		cursor.close();
		return pokemonInfoList;
	}
	
	public PokemonDetail getPokemonDetail(int pokemonId, Resources resources){
		Cursor cursor = db.rawQuery(PokemonQueries.GET_POKEMON_DETAIL, new String [] { Integer.toString(pokemonId) });

		final PokemonDetail pokemonDetail = new PokemonDetail();
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
		pokemonDetail.setEggOne(cursor.getInt(14));
		pokemonDetail.setEggTwo(cursor.getInt(15));

		final int imgDrawableId 		= resources.getIdentifier("p" + cursor.getString(16), "drawable", "com.cd.pokepraiser");
		
		final String safeResourceName	= cursor.getString(17).replace("-", "_");		
		final int icnDrawableId 		= resources.getIdentifier(safeResourceName, "drawable", "com.cd.pokepraiser");		
		
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
			final String safeResourceName	= cursor.getString(5).replace("-", "_");
			final int drawableId 			= resources.getIdentifier(safeResourceName, "drawable", "com.cd.pokepraiser");
			
			info.setPokemonId(cursor.getInt(0));
			info.setDexNo(cursor.getInt(1));
			info.setPokemonName(cursor.getString(2));
			info.setTypeOne(cursor.getInt(3));
			info.setTypeTwo(cursor.getInt(4));
			info.setIconDrawable(drawableId);
			
			pokemonInfoList.add(info);
		}
		
		cursor.close();
		return pokemonInfoList;
	}
	
	public ArrayList<PokemonInfo> getPokemonLearningAttack(final int theAttackId, Resources resources){
		Cursor cursor = db.rawQuery(PokemonQueries.GET_POKEMON_LEARNING_ATTACK, 
										new String []{ Integer.toString(theAttackId)});

		final ArrayList<PokemonInfo> pokemonInfoList = new ArrayList<PokemonInfo>(cursor.getCount());
		
		while(cursor.moveToNext()){
			final PokemonInfo info = new PokemonInfo();
			
			//TODO: Never name Android resources with hyphens, use underscores!
			final String safeResourceName	= cursor.getString(5).replace("-", "_");
			final int drawableId 			= resources.getIdentifier(safeResourceName, "drawable", "com.cd.pokepraiser");
			
			info.setPokemonId(cursor.getInt(0));
			info.setDexNo(cursor.getInt(1));
			info.setPokemonName(cursor.getString(2));
			info.setTypeOne(cursor.getInt(3));
			info.setTypeTwo(cursor.getInt(4));
			info.setIconDrawable(drawableId);
			
			pokemonInfoList.add(info);
		}
		
		cursor.close();
		return pokemonInfoList;
	}	
}
