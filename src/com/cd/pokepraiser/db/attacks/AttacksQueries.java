package com.cd.pokepraiser.db.attacks;

public class AttacksQueries {

	public static final String GET_ALL_ATTACK_INFO = 
			"SELECT ATKS.NAME,"
		  + "		TYPES.NAME,"
		  + "		ATKS.B_POWER,"
		  + "		ATKS.B_ACCURACY,"
		  + "		ATKS.B_PP"
		  + " FROM ATTACKS AS ATKS"
		  + " JOIN TYPE_NAMES AS TYPES"
		  + " ON ATKS.TYPE = TYPES._id;";
	
}
