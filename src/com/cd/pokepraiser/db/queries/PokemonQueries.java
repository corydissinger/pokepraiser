package com.cd.pokepraiser.db.queries;

public class PokemonQueries {

	public static final String GET_ALL_POKEMON_INFO = 
			"SELECT PKMN._id,"
		  + "		PKMN.DEX_NO,"
		  + "		PKMN.NAME,"
		  + "		PKMN.TYPE_ONE,"
		  + "		PKMN.TYPE_TWO,"
		  + "		PKMN.ICON_PATH"
		  + " FROM POKEMON AS PKMN;";
	
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
		  + "		PKMN.EGG_GROUP_ONE,"
		  + "		PKMN.EGG_GROUP_TWO,"
		  + "		PKMN.IMG_PATH,"
		  + "		PKMN.ICON_PATH,"
		  + "		PKMN.ALT_FORM"
		  + " FROM POKEMON AS PKMN"
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
}
