package com.cd.pokepraiser.data;

import java.io.Serializable;

public class TeamMemberAttributes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4486018187447427521L;
	
	private int id;
	
	private int hp;
	private int atk;
	private int def;
	private int spatk;
	private int spdef;
	private int spe;
	
	private String nature;
	
	private int moveOne = -1;
	private int moveTwo = -1;
	private int moveThree = -1;
	private int moveFour = -1;
	
	private int ability = -1;
	
	private int pokemonId;
	private String pokemonName;
	private String item;
	
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getAtk() {
		return atk;
	}
	public void setAtk(int atk) {
		this.atk = atk;
	}
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}
	public int getSpatk() {
		return spatk;
	}
	public void setSpatk(int spatk) {
		this.spatk = spatk;
	}
	public int getSpdef() {
		return spdef;
	}
	public void setSpdef(int spdef) {
		this.spdef = spdef;
	}
	public int getSpe() {
		return spe;
	}
	public void setSpe(int spe) {
		this.spe = spe;
	}
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public int getMoveOne() {
		return moveOne;
	}
	public void setMoveOne(int moveOne) {
		this.moveOne = moveOne;
	}
	public int getMoveTwo() {
		return moveTwo;
	}
	public void setMoveTwo(int moveTwo) {
		this.moveTwo = moveTwo;
	}
	public int getMoveThree() {
		return moveThree;
	}
	public void setMoveThree(int moveThree) {
		this.moveThree = moveThree;
	}
	public int getMoveFour() {
		return moveFour;
	}
	public void setMoveFour(int moveFour) {
		this.moveFour = moveFour;
	}
	public int getAbility() {
		return ability;
	}
	public void setAbility(int ability) {
		this.ability = ability;
	}
	public int getPokemonId() {
		return pokemonId;
	}
	public void setPokemonId(int pokemonId) {
		this.pokemonId = pokemonId;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getPokemonName() {
		return pokemonName;
	}
	public void setPokemonName(String pokemonName) {
		this.pokemonName = pokemonName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
