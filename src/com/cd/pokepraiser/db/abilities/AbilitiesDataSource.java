package com.cd.pokepraiser.db.abilities;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cd.pokepraiser.data.AbilityDetail;
import com.cd.pokepraiser.data.AbilityInfo;
import com.cd.pokepraiser.db.DatabaseHelper;

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
	
	public AbilityInfo [] getAbilityList(){
		Cursor cursor = db.rawQuery(AbilitiesQueries.GET_ALL_ABILITY_NAMES, null);

		final AbilityInfo [] abilityNameList = new AbilityInfo[cursor.getCount()];
		int i = 0;
		
		while(cursor.moveToNext()){
			final AbilityInfo info = new AbilityInfo();
			info.setName(cursor.getString(0));
			info.setAbilityDbId(cursor.getInt(1));
			abilityNameList[i++] = info;
		}
		
		cursor.close();
		return abilityNameList;
	}
	
	public AbilityDetail getAbilityDetail(int abilityDbId){
		Cursor cursor = db.rawQuery(AbilitiesQueries.GET_ABILITY_DETAIL, new String [] { Integer.toString(abilityDbId) });

		final AbilityDetail abilityDetail = new AbilityDetail();
		cursor.moveToFirst();
		
		abilityDetail.setName(cursor.getString(0));
		abilityDetail.setBattleEffect(cursor.getString(1));
		abilityDetail.setWorldEffect(cursor.getString(2));		
		
		cursor.close();
		return abilityDetail;
	}

}
