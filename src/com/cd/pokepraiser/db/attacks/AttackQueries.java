package com.cd.pokepraiser.db.attacks;

public class AttackQueries {

	public static final String GET_ALL_ATTACK_INFO = 
			"SELECT ATKS.NAME,"
		  + "		TYPES.NAME,"
		  + "		ATKS.B_POWER,"
		  + "		ATKS.B_ACCURACY,"
		  + "		ATKS.B_PP,"
		  + "		ATKS.CATEGORY,"
		  + "		ATKS._id"		  
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
		     +           "ATKS.PRIORITY"
			 +	" FROM ATTACKS AS ATKS"
			 +	" JOIN TYPE_NAMES AS TYPES"
			 +	" ON ATKS.TYPE = TYPES._id"
			 +	" WHERE ATKS._id = ?;";
}
