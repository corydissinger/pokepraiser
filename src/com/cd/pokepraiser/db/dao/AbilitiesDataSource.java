package com.cd.pokepraiser.db.dao;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cd.pokepraiser.data.AbilityAttributes;
import com.cd.pokepraiser.data.AbilityInfo;
import com.cd.pokepraiser.db.DatabaseHelper;
import com.cd.pokepraiser.db.queries.AbilitiesQueries;

public class AbilitiesDataSource {
	
	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;
	
	public AbilitiesDataSource(DatabaseHelper dbHelper){
		this.dbHelper = dbHelper;
	}
	
	public void open() throws SQLException {
		db = dbHelper.getReadableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}	
	
	public ArrayList<AbilityInfo> getAbilityList(){
		Cursor cursor = db.rawQuery(AbilitiesQueries.GET_ALL_ABILITY_NAMES, null);

		final ArrayList<AbilityInfo> abilityNameList = new ArrayList<AbilityInfo>(cursor.getCount());
		
		while(cursor.moveToNext()){
			final AbilityInfo info = new AbilityInfo();
			info.setName(cursor.getString(0));
			info.setAbilityDbId(cursor.getInt(1));
			abilityNameList.add(info);
		}
		
		cursor.close();
		return abilityNameList;
	}
	
	public AbilityAttributes getAbilityAttributes(int abilityDbId){
		Cursor cursor = db.rawQuery(AbilitiesQueries.GET_ABILITY_DETAIL, new String [] { Integer.toString(abilityDbId) });

		final AbilityAttributes abilityDetail = new AbilityAttributes();
		cursor.moveToFirst();
		
		abilityDetail.setName(cursor.getString(0));
		abilityDetail.setBattleEffect(cursor.getString(1));
		abilityDetail.setWorldEffect(cursor.getString(2));		
		
		cursor.close();
		return abilityDetail;
	}

	public AbilityInfo [] getAbilitiesLearnedBy(int [] abilitiesLearnedIds) {
		if(abilitiesLearnedIds[1] == 0 && abilitiesLearnedIds[2] == 0){
			abilitiesLearnedIds = new int []{abilitiesLearnedIds[0]};
		}else if(abilitiesLearnedIds[1] == 0){
			abilitiesLearnedIds = new int []{abilitiesLearnedIds[0], abilitiesLearnedIds[2]};
		}

		final AbilityInfo [] abilityInfoList = new AbilityInfo[abilitiesLearnedIds.length];
		int i = 0;
		
		for(int abilityId : abilitiesLearnedIds){
			Cursor cursor = db.rawQuery(AbilitiesQueries.GET_ABILITY, 
					new String [] { Integer.toString(abilityId)});
			
			final AbilityInfo info = new AbilityInfo();
			
			cursor.moveToFirst();
			info.setAbilityDbId(cursor.getInt(0));			
			info.setName(cursor.getString(1));

			abilityInfoList[i++] = info;
			cursor.close();			
		}
		
		return abilityInfoList;
	}

}
