package com.cd.pokepraiser.db.dao;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cd.pokepraiser.data.ItemDetail;
import com.cd.pokepraiser.data.ItemInfo;
import com.cd.pokepraiser.db.PokeDbHelper;
import com.cd.pokepraiser.db.queries.ItemQueries;

public class ItemsDataSource {

	private SQLiteDatabase db;
	private PokeDbHelper dbHelper;	
	
	public ItemsDataSource(PokeDbHelper dbHelper){
		this.dbHelper = dbHelper;
	}
	
	public void open() throws SQLException {
		db = dbHelper.getReadableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}	
	
	public ArrayList<ItemInfo> getAllItemInfo(){
		Cursor cursor = db.rawQuery(ItemQueries.GET_ALL_ITEM_INFO, null);

		final ArrayList<ItemInfo> itemInfoList = new ArrayList<ItemInfo>(cursor.getCount());
		
		while(cursor.moveToNext()){
			final ItemInfo item = new ItemInfo();
			
			item.setId(cursor.getInt(0));
			item.setName(cursor.getString(1));
			
			itemInfoList.add(item);
		}
		
		cursor.close();
		return itemInfoList;		
	}

	public ItemDetail getItemDetail(int itemId) {
		Cursor cursor = db.rawQuery(ItemQueries.GET_ITEM_DETAIL, new String [] { Integer.toString(itemId) });
		
		cursor.moveToFirst();
		
		final ItemDetail detail = new ItemDetail();
		
		detail.setId(cursor.getInt(0));
		detail.setName(cursor.getString(1));
		detail.setDesc(cursor.getString(2));
		
		cursor.close();
		return detail;
	}
	
}
