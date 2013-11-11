package com.cd.pokepraiser.db.attacks;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cd.pokepraiser.data.AttackInfo;
import com.cd.pokepraiser.db.DataBaseHelper;

public class AttacksDataSource {
	
	private SQLiteDatabase db;
	private DataBaseHelper dbHelper;
	
	public AttacksDataSource(Context context){
		this.dbHelper = new DataBaseHelper(context);
	}
	
	public void open() throws SQLException {
		db = dbHelper.getReadableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}	
	
	public AttackInfo [] getAttackInfoList(){
		Cursor cursor = db.rawQuery(AttacksQueries.GET_ALL_ATTACK_INFO, null);

		final AttackInfo [] attackInfoList = new AttackInfo[cursor.getCount()];
		int i = 0;
		
		while(!cursor.isAfterLast()){
			final AttackInfo attack = cursorToAttackInfo(cursor);
			attackInfoList[i++] = attack;
			cursor.moveToNext();
		}
		
		cursor.close();
		return attackInfoList;
	}
	
	private AttackInfo cursorToAttackInfo(Cursor cursor){
		final AttackInfo attack = new AttackInfo();
		attack.setName(cursor.getString(0));
		attack.setType(cursor.getString(1));
		attack.setBasePower(cursor.getInt(2));
		attack.setBaseAccuracy(cursor.getInt(3));
		attack.setBasePp(cursor.getInt(4));
		return attack;
	}
}
