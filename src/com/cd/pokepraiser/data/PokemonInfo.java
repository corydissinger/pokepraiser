package com.cd.pokepraiser.data;

public class PokemonInfo implements Cloneable {

	private int pokemonId;
	private int dexNo;
	private String pokemonName;
	private int typeOne;
	//Evil hack,  most pokes have normal listed as a first type.
	private int typeTwo = 0;
	private int iconDrawable;
	
	public int getPokemonId() {
		return pokemonId;
	}
	public void setPokemonId(int pokemonId) {
		this.pokemonId = pokemonId;
	}
	public int getDexNo() {
		return dexNo;
	}
	public void setDexNo(int dexNo) {
		this.dexNo = dexNo;
	}
	public String getPokemonName() {
		return pokemonName;
	}
	public void setPokemonName(String pokemonName) {
		this.pokemonName = pokemonName;
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
		cloned.setPokemonId(pokemonId);
		cloned.setDexNo(dexNo);
		cloned.setPokemonName(pokemonName);
		cloned.setTypeOne(typeOne);
		cloned.setTypeTwo(typeTwo);
		cloned.setIconDrawable(iconDrawable);
		
		return cloned;
	}	
}
