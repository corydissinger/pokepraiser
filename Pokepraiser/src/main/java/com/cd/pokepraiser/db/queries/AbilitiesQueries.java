package com.cd.pokepraiser.db.queries;

public class AbilitiesQueries {
	public static final String GET_ALL_ABILITY_NAMES =
			"SELECT NAME,"
		  + 		"_id"
		  + " FROM ABILITIES;";
	
	public static final String GET_ABILITY_DETAIL =
			"SELECT NAME,"
		+	"		BATTLE_EFFECT,"
		+ 	"		WORLD_EFFECT"
		+	" FROM ABILITIES"
		+	" WHERE _id = ?;";
	
	public static final String GET_ABILITY =	
			"SELECT _id, NAME"
			+ " FROM ABILITIES ABLTS"
			+ " WHERE ABLTS._id = ?;";
}
