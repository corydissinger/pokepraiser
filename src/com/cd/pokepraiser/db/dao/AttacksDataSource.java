package com.cd.pokepraiser.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cd.pokepraiser.data.AttackAttributes;
import com.cd.pokepraiser.data.AttackInfo;
import com.cd.pokepraiser.data.PokemonAttackInfo;
import com.cd.pokepraiser.data.TypeInfo;
import com.cd.pokepraiser.db.DatabaseHelper;
import com.cd.pokepraiser.db.queries.AttacksQueries;
import com.cd.pokepraiser.db.util.AttacksHelper;
import com.cd.pokepraiser.util.TypeUtils;

public class AttacksDataSource {
	
	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;
	
	public AttacksDataSource(DatabaseHelper dbHelper){
		this.dbHelper = dbHelper;
	}
	
	public void open() throws SQLException {
		db = dbHelper.getReadableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}	
	
	public ArrayList<AttackInfo> getAttackInfoList(){
		Cursor cursor = db.rawQuery(AttacksQueries.GET_ALL_ATTACK_INFO, null);

		final ArrayList<AttackInfo> attackInfoList = new ArrayList<AttackInfo>(cursor.getCount());
		
		while(cursor.moveToNext()){
			final AttackInfo attack = cursorToAttackInfo(cursor);
			attackInfoList.add(attack);
		}
		
		cursor.close();
		return attackInfoList;
	}
	
	private AttackInfo cursorToAttackInfo(Cursor cursor){
		final AttackInfo attack = new AttackInfo();
		attack.setName(cursor.getString(0));
		attack.setType(cursor.getString(1));
		attack.setBasePower(Integer.toString(cursor.getInt(2)));
		attack.setBaseAccuracy(Integer.toString(cursor.getInt(3)));
		attack.setBasePp(Integer.toString(cursor.getInt(4)));
		attack.setCategoryDrawableId(AttacksHelper.getCategoryDrawableResource(cursor.getInt(5)));
		attack.setAttackDbId(cursor.getInt(6));
		attack.setTypeDrawableId(TypeUtils.getTypeDrawableId(cursor.getInt(7)));		
		return attack;
	}	
	
	public AttackAttributes getAttackDetail(int attackDbId){
		Cursor cursor = db.rawQuery(AttacksQueries.GET_ATTACK_DETAIL, new String [] { Integer.toString(attackDbId) });

		final AttackAttributes attackDetail = new AttackAttributes();
		cursor.moveToFirst();
		
		attackDetail.setName(cursor.getString(0));
		attackDetail.setType(cursor.getString(1));
		attackDetail.setBasePower(Integer.toString(cursor.getInt(2)));
		attackDetail.setBaseAccuracy(Integer.toString(cursor.getInt(3)));
		attackDetail.setBasePp(Integer.toString(cursor.getInt(4)));
		attackDetail.setBattleEffectDesc(cursor.getString(5));
		attackDetail.setSecondaryEffectDesc(cursor.getString(6));
		attackDetail.setContacts((cursor.getInt(7) == 1) ? true : false);
		attackDetail.setSound((cursor.getInt(8) == 1) ? true : false);
		attackDetail.setPunch((cursor.getInt(9) == 1) ? true : false);
		attackDetail.setSnatchable((cursor.getInt(10) == 1) ? true : false);
		attackDetail.setGravity((cursor.getInt(11) == 1) ? true : false);
		attackDetail.setDefrosts((cursor.getInt(12) == 1) ? true : false);
		attackDetail.setHitsOpponentTriples((cursor.getInt(13) == 1) ? true : false);
		attackDetail.setReflectable((cursor.getInt(14) == 1) ? true : false);
		attackDetail.setBlockable((cursor.getInt(15) == 1) ? true : false);
		attackDetail.setMirrorable((cursor.getInt(16) == 1) ? true : false);
		attackDetail.setPriority(cursor.getInt(17));	
		attackDetail.setEffectPct(cursor.getInt(18));
		attackDetail.setAttackDbId(attackDbId);
		
		cursor.close();
		return attackDetail;
	}
	
	public ArrayList<PokemonAttackInfo> getPokemonAttackInfoList(int dexNo, int altForm){
		Cursor cursor = db.rawQuery(AttacksQueries.GET_ALL_LEARNED_ATTACKS_ONE +
				 					Integer.toString(dexNo) +
			 						AttacksQueries.GET_ALL_LEARNED_ATTACKS_TWO +
			 						Integer.toString(altForm) +
			 						AttacksQueries.GET_ALL_LEARNED_ATTACKS_THREE, null);

		final ArrayList<PokemonAttackInfo> attackInfoList = new ArrayList<PokemonAttackInfo>(cursor.getCount());
		
		while(cursor.moveToNext()){
			final PokemonAttackInfo attack = cursorToPokemonAttackInfo(cursor);
			attackInfoList.add(attack);
		}
		
		cursor.close();
		return attackInfoList;
	}
	
	private PokemonAttackInfo cursorToPokemonAttackInfo(Cursor cursor){
		final PokemonAttackInfo attack = new PokemonAttackInfo();
		
		attack.setAttackDbId(cursor.getInt(0));		
		attack.setName(cursor.getString(1));
		attack.setTypeDrawableId(TypeUtils.getTypeDrawableId(cursor.getInt(2)));
		attack.setBasePower(Integer.toString(cursor.getInt(3)));
		attack.setBaseAccuracy(Integer.toString(cursor.getInt(4)));
		attack.setBasePp(Integer.toString(cursor.getInt(5)));
		attack.setCategoryDrawableId(AttacksHelper.getCategoryDrawableResource(cursor.getInt(6)));
		attack.setLearnedType(cursor.getInt(7));
		attack.setLvlOrTm(cursor.getInt(8));		
		
		return attack;
	}	
	
	public List<TypeInfo> getAllTypeInfo(){
		Cursor cursor = db.rawQuery(AttacksQueries.GET_ALL_TYPES, null);

		final List<TypeInfo> allTypeInfo = new ArrayList<TypeInfo>(cursor.getCount());		
		
		while(cursor.moveToNext()){
			final TypeInfo type = new TypeInfo();
			
			type.setDbId(cursor.getInt(0));
			type.setName(cursor.getString(1));
			
			allTypeInfo.add(type);
		}
		
		cursor.close();
		return allTypeInfo;
	}

}
