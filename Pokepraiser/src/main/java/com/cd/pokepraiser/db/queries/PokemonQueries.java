package com.cd.pokepraiser.db.queries;

public class PokemonQueries {

	public static final String GET_POKEMON_NAME = "SELECT NAME FROM POKEMON WHERE _id = ?;";
	
	public static final String GET_ALL_POKEMON_INFO = 
			"SELECT PKMN._id,"
		  + "		PKMN.DEX_NO,"
		  + "		PKMN.NAME,"
		  + "		PKMN.TYPE_ONE,"
		  + "		PKMN.TYPE_TWO,"
		  + "		PKMN.ICON_PATH"
		  + " FROM POKEMON AS PKMN"
		  + " ORDER BY PKMN.DEX_NO ASC, PKMN.ALT_FORM ASC;";
	
	public static final String GET_POKEMON_DETAIL = 
			"SELECT PKMN._id,"
		  + "		PKMN.DEX_NO,"
		  + "		PKMN.NAME,"
		  + "		PKMN.TYPE_ONE,"
		  + "		PKMN.TYPE_TWO,"
		  + "		PKMN.BS_HP,"
		  + "		PKMN.BS_ATK,"
		  + "		PKMN.BS_DEF,"
		  + "		PKMN.BS_SPATK,"
		  + "		PKMN.BS_SPDEF,"
		  + "		PKMN.BS_SPD,"
		  + "		PKMN.AB_ONE,"
		  + "		PKMN.AB_TWO,"
		  + "		PKMN.AB_HA,"
		  + "		EGG1.NAME,"
		  + "		EGG2.NAME,"
		  + "		PKMN.IMG_PATH,"
		  + "		PKMN.ICON_PATH,"
		  + "		PKMN.ALT_FORM"
		  + " FROM POKEMON AS PKMN"
		  + " LEFT JOIN EGG_GROUPS AS EGG1"
		  + " ON PKMN.EGG_GROUP_ONE = EGG1._id"
		  + " LEFT JOIN EGG_GROUPS AS EGG2"
		  + " ON PKMN.EGG_GROUP_TWO = EGG2._id"
		  + " WHERE PKMN._id = ?;";
	
	public static final String GET_POKEMON_LEARNING_ABILITY =
			"SELECT PKMN._id,"
			+	" PKMN.DEX_NO,"
			+	" PKMN.NAME,"
			+	" PKMN.TYPE_ONE,"
			+	" PKMN.TYPE_TWO,"
			+	" PKMN.ICON_PATH"
			+" FROM POKEMON AS PKMN"
			+" WHERE PKMN.AB_ONE = ?"
			+	" UNION"
			+" SELECT PKMN._id,"
			+	" PKMN.DEX_NO,"
			+	" PKMN.NAME,"
			+	" PKMN.TYPE_ONE,"
			+	" PKMN.TYPE_TWO,"
			+	" PKMN.ICON_PATH"
			+" FROM POKEMON AS PKMN"
			+" WHERE PKMN.AB_TWO = ?"
			+" UNION"
			+" SELECT PKMN._id,"
			+	" PKMN.DEX_NO,"
			+	" PKMN.NAME,"
			+	" PKMN.TYPE_ONE,"
			+	" PKMN.TYPE_TWO,"
			+	" PKMN.ICON_PATH"
			+" FROM POKEMON AS PKMN"
			+" WHERE PKMN.AB_HA = ?"
			+" ORDER BY PKMN.DEX_NO;";

	public static final String GET_POKEMON_LEARNING_ATTACK =	
				"SELECT PKMN._id,"
			+		" PKMN.DEX_NO,"
			+		" PKMN.NAME,"
			+		" PKMN.TYPE_ONE,"
			+		" PKMN.TYPE_TWO,"
			+		" PKMN.ICON_PATH"
			+	" FROM ATTACKS AS ATKS"
			+	" JOIN POKEMON_ATTACKS AS PKMN_ATKS"
			+	" ON PKMN_ATKS.ATTACK_NO = ATKS._id"
			+	" JOIN POKEMON AS PKMN"
			+	" ON PKMN.DEX_NO = PKMN_ATKS.DEX_NO"
			+	" WHERE ATKS._id = ?;";
	
	public static final String POKEMON_SEARCH_QUERY_PT1 = 
			"SELECT DISTINCT PKMN._id,"
		  + "		PKMN.DEX_NO,"
		  + "		PKMN.NAME,"
		  + "		PKMN.TYPE_ONE,"
		  + "		PKMN.TYPE_TWO,"
		  + "		PKMN.ICON_PATH"
		  + " FROM POKEMON AS PKMN ";		  
	
	public static final String ABILITY_FILTER =
		"(PKMN.AB_ONE = ? OR PKMN.AB_TWO = ? OR PKMN.AB_HA = ?) ";
	
	public static final String MONO_TYPE_FILTER =
		"(PKMN.TYPE_ONE = ? OR PKMN.TYPE_TWO = ?) ";
	
	public static final String DUAL_TYPE_FILTER =
		"(PKMN.TYPE_ONE = ? AND PKMN.TYPE_TWO = ?) ";	
	
	public static final String ATTACK_JOIN =
		"JOIN POKEMON_ATTACKS AS PKMN_ATKS0 ON PKMN.DEX_NO = PKMN_ATKS0.DEX_NO AND PKMN.ALT_FORM = PKMN_ATKS0.ALT_FORM ";
	
	public static final String ATTACK_WHERE = 
		"PKMN_ATKS0.ATTACK_NO = ?";
	
	//TABLES, COLUMNS
	
	public static final String TABLE_POKEMON 	= "POKEMON";
	
	public static final String COLUMN_NAME		= "NAME";
	public static final String COLUMN_ID		= "_id";

	public static final String GET_ALL_NATURE_INFO = "SELECT _id, NAME, PLUS, MINUS FROM NATURES ORDER BY NAME;";
	
	public static final String GET_EGG_GROUP_ID = "SELECT _id FROM EGG_GROUPS WHERE NAME = ?;";	

	public static final String GET_ALL_POKEMON_IN_EGG_GROUP = 
			"SELECT PKMN._id,"
		  + "		PKMN.DEX_NO,"
		  + "		PKMN.NAME,"
		  + "		PKMN.TYPE_ONE,"
		  + "		PKMN.TYPE_TWO,"
		  + "		PKMN.ICON_PATH"
		  + " FROM POKEMON AS PKMN"
		  + " WHERE EGG_GROUP_ONE = ?"
		  + " OR EGG_GROUP_TWO = ?"
		  + " ORDER BY PKMN.DEX_NO ASC, PKMN.ALT_FORM ASC;";	
}
