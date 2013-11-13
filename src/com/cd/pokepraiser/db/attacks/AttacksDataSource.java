package com.cd.pokepraiser.db.attacks;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cd.pokepraiser.data.AttackDetail;
import com.cd.pokepraiser.data.AttackInfo;
import com.cd.pokepraiser.db.DatabaseHelper;

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
	
	public AttackInfo [] getAttackInfoList(){
		Cursor cursor = db.rawQuery(AttackQueries.GET_ALL_ATTACK_INFO, null);

		final AttackInfo [] attackInfoList = new AttackInfo[cursor.getCount()];
		int i = 0;
		
		while(cursor.moveToNext()){
			final AttackInfo attack = cursorToAttackInfo(cursor);
			attackInfoList[i++] = attack;
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
		return attack;
	}	
	
	public AttackDetail getAttackDetail(int attackDbId){
		Cursor cursor = db.rawQuery(AttackQueries.GET_ATTACK_DETAIL, new String [] { Integer.toString(attackDbId) });

		final AttackDetail attackDetail = new AttackDetail();
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
		
		cursor.close();
		return attackDetail;
	}

}
