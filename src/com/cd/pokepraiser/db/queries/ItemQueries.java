package com.cd.pokepraiser.db.queries;

public class ItemQueries {

	public static final String GET_ALL_ITEM_INFO = "SELECT _id, NAME FROM ITEMS;";
	public static final String GET_ITEM_DETAIL = "SELECT _id, NAME, DESC FROM ITEMS WHERE _id = ?;";	

}
