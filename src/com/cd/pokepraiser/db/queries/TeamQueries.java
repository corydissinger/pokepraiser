package com.cd.pokepraiser.db.queries;

public class TeamQueries {
	public static final String CREATE_TABLE_TEAMS 			= "CREATE TABLE TEAMS ( _id integer primary key autoincrement, NAME text not null);";
	
	public static final String CREATE_TABLE_TEAMS_MEMBERS 	= "CREATE TABLE TEAMS_MEMBERS ( _id integer primary key autoincrement, TEAM_ID integer not null, MEMBER_ID integer not null)";
	
	public static final String CREATE_TABLE_MEMBERS 		= "CREATE TABLE MEMBERS ( _id integer primary key autoincrement, NATURE TEXT, HP INTEGER NOT NULL  DEFAULT 0, ATK INTEGER NOT NULL  DEFAULT 0, DEF INTEGER NOT NULL  DEFAULT 0, SPATK INTEGER NOT NULL  DEFAULT 0, SPDEF INTEGER NOT NULL  DEFAULT 0, SPE INTEGER NOT NULL  DEFAULT 0, MOVE_ONE INTEGER NOT NULL  DEFAULT 0, MOVE_TWO INTEGER NOT NULL  DEFAULT 0, MOVE_THREE INTEGER NOT NULL  DEFAULT 0, MOVE_FOUR INTEGER NOT NULL  DEFAULT 0, ABILITY INTEGER NOT NULL  DEFAULT 0, POKEMON_ID INTEGER NOT NULL  DEFAULT 0, ITEM TEXT)";
	
	public static final String GET_TEAM_INFO = "SELECT TEAMS.NAME, (SELECT COUNT(TEMP.MEMBER_ID) FROM TEAMS_MEMBERS AS TEMP WHERE TEMP.TEAM_ID = TEAMS._id), TEAMS._id FROM TEAMS;";
	public static final String GET_TEAM_MEMBER_IDS = "SELECT MEMBER_ID FROM TEAMS_MEMBERS WHERE TEAM_ID = ?;";
	public static final String GET_TEAM_EXISTS = "SELECT 1 FROM TEAMS WHERE _id = ?;";	
	
	public static final String GET_TEAM_MEMBERS =
				"SELECT MEM.HP,"
		        +	  	" MEM.ATK,"
		        +	  	" MEM.DEF,"
		        +		" MEM.SPATK,"
		        +       " MEM.SPDEF,"
		        +       " MEM.SPE,"
		        +       " MEM.MOVE_ONE,"
		        +       " MEM.MOVE_TWO,"
		        +       " MEM.MOVE_THREE,"
		        +       " MEM.MOVE_FOUR,"
		        +       " MEM.ABILITY,"
		        +   	" MEM.POKEMON_ID,"
		        +	    " MEM.ITEM,"
		        +	    " MEM._id"		        
				+ " FROM MEMBERS AS MEM"
				+ " JOIN TEAMS_MEMBERS AS TEAMS"
				+ " ON TEAMS.MEMBER_ID = MEM._id"
				+ " WHERE TEAMS.TEAM_ID = ?;";

	public static final String GET_TEAM_MEMBER =
			"SELECT MEM.HP,"
	        +	  	" MEM.ATK,"
	        +	  	" MEM.DEF,"
	        +		" MEM.SPATK,"
	        +       " MEM.SPDEF,"
	        +       " MEM.SPE,"
	        +       " MEM.MOVE_ONE,"
	        +       " MEM.MOVE_TWO,"
	        +       " MEM.MOVE_THREE,"
	        +       " MEM.MOVE_FOUR,"
	        +       " MEM.ABILITY,"
	        +   	" MEM.POKEMON_ID,"
	        +	    " MEM.ITEM,"
	        +	    " MEM.NATURE,"	        
	        +	    " MEM._id"		        
			+ " FROM MEMBERS AS MEM"
			+ " WHERE MEM._id = ?;";	
	
	public static final String TABLE_TEAMS 			= "TEAMS";
	public static final String TABLE_TEAMS_MEMBERS	= "TEAMS_MEMBERS";	
	public static final String TABLE_MEMBERS	 	= "MEMBERS";	
	
	public static final String COLUMN_NAME 			= "NAME";
	public static final String COLUMN_POKE_ID 		= "POKEMON_ID";
	public static final String COLUMN_MEMBER_ID 	= "MEMBER_ID";
	public static final String COLUMN_TEAM_ID 		= "TEAM_ID";	
	
	//TEAM_MEMBER columns
	public static final String COLUMN_NATURE 			= "NATURE";
	public static final String COLUMN_HP		 		= "HP";
	public static final String COLUMN_ATK		 		= "ATK";
	public static final String COLUMN_DEF		 		= "DEF";
	public static final String COLUMN_SPATK		 		= "SPATK";
	public static final String COLUMN_SPDEF		 		= "SPDEF";
	public static final String COLUMN_SPE		 		= "SPE";
	public static final String COLUMN_MOVE_ONE	 		= "MOVE_ONE";
	public static final String COLUMN_MOVE_TWO	 		= "MOVE_TWO";
	public static final String COLUMN_MOVE_THREE 		= "MOVE_THREE";
	public static final String COLUMN_MOVE_FOUR 		= "MOVE_FOUR";
	public static final String COLUMN_ABILITY 			= "ABILITY";
	public static final String COLUMN_ITEM 				= "ITEM";	
}
