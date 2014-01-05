package com.cd.pokepraiser.data;

import java.io.Serializable;

public class PokemonInfo implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5625306042321119539L;
	
	private int id;
	private int dexNo;
	private String name;
	private int typeOne;
	//Evil hack,  most pokes have normal listed as a first type.
	private int typeTwo = 0;
	private int iconDrawable;
	
	public int getId() {
		return id;
	}
	public void setId(int pokemonId) {
		this.id = pokemonId;
	}
	public int getDexNo() {
		return dexNo;
	}
	public void setDexNo(int dexNo) {
		this.dexNo = dexNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String pokemonName) {
		this.name = pokemonName;
	}
	public int getTypeOne() {
		return typeOne;
	}
	public void setTypeOne(int typeOne) {
		this.typeOne = typeOne;
	}
	public int getTypeTwo() {
		return typeTwo;
	}
	public void setTypeTwo(int typeTwo) {
		this.typeTwo = typeTwo;
	}
	public int getIconDrawable() {
		return iconDrawable;
	}
	public void setIconDrawable(int iconDrawable) {
		this.iconDrawable = iconDrawable;
	}
	
	public PokemonInfo clone() {
		final PokemonInfo cloned = new PokemonInfo();
		cloned.setId(id);
		cloned.setDexNo(dexNo);
		cloned.setName(name);
		cloned.setTypeOne(typeOne);
		cloned.setTypeTwo(typeTwo);
		cloned.setIconDrawable(iconDrawable);
		
		return cloned;
	}	
}
