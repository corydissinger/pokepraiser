package com.cd.pokepraiser.db.queries;

public class AttacksQueries {

	public static final String GET_ALL_ATTACK_INFO = 
			"SELECT ATKS.NAME,"
		  + "		TYPES.NAME,"
		  + "		ATKS.B_POWER,"
		  + "		ATKS.B_ACCURACY,"
		  + "		ATKS.B_PP,"
		  + "		ATKS.CATEGORY,"
		  + "		ATKS._id,"
		  + "		ATKS.TYPE"		  
		  + " FROM ATTACKS AS ATKS"
		  + " JOIN TYPE_NAMES AS TYPES"
		  + " ON ATKS.TYPE = TYPES._id;";

	public static final String GET_ATTACK_DETAIL =
				"SELECT ATKS.NAME,"
           	 + 			"TYPES.NAME,"
           	 +			"ATKS.B_POWER,"
		     +           "ATKS.B_ACCURACY,"
		     +           "ATKS.B_PP,"
		     +           "ATKS.BATTLE_EFFECT,"
		     +           "ATKS.SECOND_EFFECT,"
		     +           "ATKS.CONTACT,"
		     +           "ATKS.SOUND,"
		     +           "ATKS.PUNCH,"
		     +           "ATKS.SNATCH,"
		     +           "ATKS.GRAVITY,"
		     +           "ATKS.DEFROSTS,"
		     +           "ATKS.HITS_OPP_TRIPLES,"
		     +           "ATKS.REFLECTED,"
		     +           "ATKS.BLOCKED,"
		     +           "ATKS.MIRRORABLE,"
		     +           "ATKS.PRIORITY,"
		     +			 "ATKS.B_EFF_PCT"
			 +	" FROM ATTACKS AS ATKS"
			 +	" JOIN TYPE_NAMES AS TYPES"
			 +	" ON ATKS.TYPE = TYPES._id"
			 +	" WHERE ATKS._id = ?;";
	
	public static final String GET_ALL_LEARNED_ATTACKS_ONE = 
				"SELECT PKMN_ATKS.ATTACK_NO,"
			+		"ATKS.NAME,"
		   	+		"ATKS.TYPE,"			
		   	+		"ATKS.B_POWER,"
		   	+		"ATKS.B_ACCURACY,"
		   	+		"ATKS.B_PP,"
		   	+		"ATKS.CATEGORY,"
			+		"PKMN_ATKS.LEARNED_TYPE,"
			+		"PKMN_ATKS.LVL_OR_TM"
		    +	" FROM POKEMON_ATTACKS AS PKMN_ATKS"
			+	" JOIN ATTACKS AS ATKS"
			+	" ON PKMN_ATKS.ATTACK_NO = ATKS._id"
		    +	" WHERE PKMN_ATKS.DEX_NO ="; //Shitty hack, thands Android SQLite apis!
	
	public static final String GET_ALL_LEARNED_ATTACKS_TWO =
			" AND PKMN_ATKS.ALT_FORM =";
	
	public static final String GET_ALL_LEARNED_ATTACKS_THREE =	
			" ORDER BY PKMN_ATKS.LEARNED_TYPE, PKMN_ATKS.LVL_OR_TM;";	
}
