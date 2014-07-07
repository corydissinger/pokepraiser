package com.cd.pokepraiser.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cd.pokepraiser.data.TeamInfo;
import com.cd.pokepraiser.data.TeamMemberAttributes;
import com.cd.pokepraiser.db.TeamDbHelper;
import com.cd.pokepraiser.db.queries.TeamQueries;

public class TeamDataSource {
	
	private SQLiteDatabase db;
	private TeamDbHelper dbHelper;
	
	public TeamDataSource(TeamDbHelper dbHelper){
		this.dbHelper = dbHelper;
	}
	
	public void openRead() throws SQLException {
		db = dbHelper.getReadableDatabase();
	}
	
	public void openWrite() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}	
	
	public void close() {
		dbHelper.close();
	}	

	public List<TeamInfo> getTeamInfo(){
		Cursor cursor = db.rawQuery(TeamQueries.GET_TEAM_INFO, null);

		final List<TeamInfo> teamInfoList = new ArrayList<TeamInfo>(cursor.getCount());
		
		if(cursor.getCount() == 0){
			cursor.close();
			return teamInfoList;
		}
		
		while(cursor.moveToNext()){
			final TeamInfo info = new TeamInfo();
		
			info.setName(cursor.getString(0));
			info.setCount(cursor.getInt(1));
			info.setDbId(cursor.getInt(2));
			
			teamInfoList.add(info);
		}
		
		cursor.close();
		return teamInfoList;		
	}

	public TeamInfo addTeam(String teamName) {
		final ContentValues values = new ContentValues();
		values.put(TeamQueries.COLUMN_NAME, teamName);
		
		final int dbId = (int) db.insert(TeamQueries.TABLE_TEAMS, null, values);

		final TeamInfo newTeam = new TeamInfo();
		newTeam.setCount(0);
		newTeam.setName(teamName);
		newTeam.setDbId(dbId);
		
		return newTeam;
	}
	
	public void deleteTeam(TeamInfo team){
		db.delete(TeamQueries.TABLE_TEAMS, "_id = " + team.getDbId(), null);
		
		Cursor cursor = db.rawQuery(TeamQueries.GET_TEAM_MEMBER_IDS, new String[] { Integer.toString(team.getDbId()) } );

		List<Integer> teamMemberIds = new ArrayList<Integer>(cursor.getCount());
		
		if(teamMemberIds.size() != 0){
			while(cursor.moveToNext()){
				teamMemberIds.add(cursor.getInt(0));
			}
		}
		
		cursor.close();		
		
		db.delete(TeamQueries.TABLE_TEAMS_MEMBERS, "TEAM_ID = " + team.getDbId(), null);
		
		for(Integer memberId : teamMemberIds){
			db.delete(TeamQueries.TABLE_MEMBERS, "_id = " + memberId, null);
		}
	}
	
	public void deleteTeamMember(final int memberId){
		db.delete(TeamQueries.TABLE_TEAMS_MEMBERS, "MEMBER_ID = " + memberId, null);
		
		db.delete(TeamQueries.TABLE_MEMBERS, "_id = " + memberId, null);
	}	

	public boolean checkTeamExists(int dbId) {
		Cursor cursor = db.rawQuery(TeamQueries.GET_TEAM_EXISTS, new String[] { Integer.toString(dbId)} );
		
		final boolean teamExists = cursor.getCount() != 0 ? true : false;
		
		cursor.close();
		
		return teamExists;
	}

	public ArrayList<TeamMemberAttributes> getTeamMembers(int dbId) {
		Cursor cursor = db.rawQuery(TeamQueries.GET_TEAM_MEMBERS, new String[] { Integer.toString(dbId)} );
		final ArrayList<TeamMemberAttributes> teamMemberList = new ArrayList<TeamMemberAttributes>(cursor.getCount());
		
		if(cursor.getCount() == 0){
			cursor.close();
			return teamMemberList;
		}
		
		while(cursor.moveToNext()){
			final TeamMemberAttributes member = new TeamMemberAttributes();
		
			member.setHp(cursor.getInt(0));
			member.setAtk(cursor.getInt(1));
			member.setDef(cursor.getInt(2));
			member.setSpatk(cursor.getInt(3));
			member.setSpdef(cursor.getInt(4));
			member.setSpe(cursor.getInt(5));
			
			member.setMoveOne(cursor.getInt(6));
			member.setMoveTwo(cursor.getInt(7));
			member.setMoveThree(cursor.getInt(8));
			member.setMoveFour(cursor.getInt(9));			
			
			member.setAbility(cursor.getInt(10));
			member.setPokemonId(cursor.getInt(11));
			member.setItem(cursor.getString(12));
			member.setNature(cursor.getString(13));			
			
			member.setId(cursor.getInt(14));
			
			teamMemberList.add(member);
		}
		
		cursor.close();
		return teamMemberList;
	}
	
	public TeamMemberAttributes getTeamMember(int dbId) {
		Cursor cursor = db.rawQuery(TeamQueries.GET_TEAM_MEMBER, new String[] { Integer.toString(dbId)} );
		
		if(cursor.getCount() == 0){
			cursor.close();
			return null;
		}
		
		cursor.moveToNext();
		
		final TeamMemberAttributes member = new TeamMemberAttributes();
	
		member.setHp(cursor.getInt(0));
		member.setAtk(cursor.getInt(1));
		member.setDef(cursor.getInt(2));
		member.setSpatk(cursor.getInt(3));
		member.setSpdef(cursor.getInt(4));
		member.setSpe(cursor.getInt(5));
		
		member.setMoveOne(cursor.getInt(6));
		member.setMoveTwo(cursor.getInt(7));
		member.setMoveThree(cursor.getInt(8));
		member.setMoveFour(cursor.getInt(9));			
		
		member.setAbility(cursor.getInt(10));
		member.setPokemonId(cursor.getInt(11));
		member.setItem(cursor.getString(12));
		member.setNature(cursor.getString(13));		
		
		member.setId(cursor.getInt(14));
		
		cursor.close();
		return member;
	}	

	public int addTeamMember(int selectedPokeId, int teamId) {
		final ContentValues values = new ContentValues();
		values.put(TeamQueries.COLUMN_POKE_ID, selectedPokeId);
		
		final int memberId = (int) db.insert(TeamQueries.TABLE_MEMBERS, null, values);

		final ContentValues teamMembersValues = new ContentValues();
		teamMembersValues.put(TeamQueries.COLUMN_TEAM_ID, teamId);
		teamMembersValues.put(TeamQueries.COLUMN_MEMBER_ID, memberId);
		
		db.insert(TeamQueries.TABLE_TEAMS_MEMBERS, null, teamMembersValues);		
		
		return memberId;
	}
	
	public void updateTeamMember(final TeamMemberAttributes memberAttributes){
		final ContentValues values = new ContentValues();

		values.put(TeamQueries.COLUMN_ABILITY, memberAttributes.getAbility());		
		values.put(TeamQueries.COLUMN_ITEM, memberAttributes.getItem());
		values.put(TeamQueries.COLUMN_NATURE, memberAttributes.getNature());
		
		values.put(TeamQueries.COLUMN_HP, memberAttributes.getHp());
		values.put(TeamQueries.COLUMN_ATK, memberAttributes.getAtk());		
		values.put(TeamQueries.COLUMN_DEF, memberAttributes.getDef());
		values.put(TeamQueries.COLUMN_SPATK, memberAttributes.getSpatk());
		values.put(TeamQueries.COLUMN_SPDEF, memberAttributes.getSpdef());
		values.put(TeamQueries.COLUMN_SPE, memberAttributes.getSpe());
		
		values.put(TeamQueries.COLUMN_MOVE_ONE, memberAttributes.getMoveOne());
		values.put(TeamQueries.COLUMN_MOVE_TWO, memberAttributes.getMoveTwo());
		values.put(TeamQueries.COLUMN_MOVE_THREE, memberAttributes.getMoveThree());		
		values.put(TeamQueries.COLUMN_MOVE_FOUR, memberAttributes.getMoveFour());
		
		db.update(TeamQueries.TABLE_MEMBERS, values, "_id = ?", new String [] { Integer.toString(memberAttributes.getId()) });
	}
}
