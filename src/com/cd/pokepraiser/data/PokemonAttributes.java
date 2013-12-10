package com.cd.pokepraiser.data;

import java.io.Serializable;


public class PokemonAttributes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8438825784796138421L;
	
	private int dexNo;
	private String name;
	private int typeOne;
	private int typeTwo;
	private int bsHp;
	private int bsAtk;
	private int bsDef;
	private int bsSpatk;
	private int bsSpdef;
	private int bsSpd;
	private int abOne;
	private int abTwo;
	private int abHa;
	private int imgDrawable;
	private int iconDrawable;
	private int eggOne;
	private int eggTwo;
	private int pokemonId;
	private int altForm;
	
	public int getDexNo() {
		return dexNo;
	}
	public void setDexNo(int dexNo) {
		this.dexNo = dexNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getBsHp() {
		return bsHp;
	}
	public void setBsHp(int bsHp) {
		this.bsHp = bsHp;
	}
	public int getBsAtk() {
		return bsAtk;
	}
	public void setBsAtk(int bsAtk) {
		this.bsAtk = bsAtk;
	}
	public int getBsDef() {
		return bsDef;
	}
	public void setBsDef(int bsDef) {
		this.bsDef = bsDef;
	}
	public int getBsSpatk() {
		return bsSpatk;
	}
	public void setBsSpatk(int bsSpatk) {
		this.bsSpatk = bsSpatk;
	}
	public int getBsSpdef() {
		return bsSpdef;
	}
	public void setBsSpdef(int bsSpdef) {
		this.bsSpdef = bsSpdef;
	}
	public int getBsSpd() {
		return bsSpd;
	}
	public void setBsSpd(int bsSpd) {
		this.bsSpd = bsSpd;
	}
	public int getAbOne() {
		return abOne;
	}
	public void setAbOne(int abOne) {
		this.abOne = abOne;
	}
	public int getAbTwo() {
		return abTwo;
	}
	public void setAbTwo(int abTwo) {
		this.abTwo = abTwo;
	}
	public int getAbHa() {
		return abHa;
	}
	public void setAbHa(int abHa) {
		this.abHa = abHa;
	}
	public int getEggOne() {
		return eggOne;
	}
	public void setEggOne(int eggOne) {
		this.eggOne = eggOne;
	}
	public int getEggTwo() {
		return eggTwo;
	}
	public void setEggTwo(int eggTwo) {
		this.eggTwo = eggTwo;
	}
	public int getImgDrawable() {
		return imgDrawable;
	}
	public void setImgDrawable(int imgDrawable) {
		this.imgDrawable = imgDrawable;
	}
	public int getIconDrawable() {
		return iconDrawable;
	}
	public void setIconDrawable(int iconDrawable) {
		this.iconDrawable = iconDrawable;
	}
	public int getPokemonId() {
		return pokemonId;
	}
	public void setPokemonId(int pokemonId) {
		this.pokemonId = pokemonId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PokemonDetail [dexNo=");
		builder.append(dexNo);
		builder.append(", name=");
		builder.append(name);
		builder.append(", typeOne=");
		builder.append(typeOne);
		builder.append(", typeTwo=");
		builder.append(typeTwo);
		builder.append(", bsHp=");
		builder.append(bsHp);
		builder.append(", bsAtk=");
		builder.append(bsAtk);
		builder.append(", bsDef=");
		builder.append(bsDef);
		builder.append(", bsSpatk=");
		builder.append(bsSpatk);
		builder.append(", bsSpdef=");
		builder.append(bsSpdef);
		builder.append(", bsSpd=");
		builder.append(bsSpd);
		builder.append(", abOne=");
		builder.append(abOne);
		builder.append(", abTwo=");
		builder.append(abTwo);
		builder.append(", abHa=");
		builder.append(abHa);
		builder.append(", imgDrawable=");
		builder.append(imgDrawable);
		builder.append(", iconDrawable=");
		builder.append(iconDrawable);
		builder.append(", eggOne=");
		builder.append(eggOne);
		builder.append(", eggTwo=");
		builder.append(eggTwo);
		builder.append(", pokemonId=");
		builder.append(pokemonId);
		builder.append("]");
		return builder.toString();
	}
	public int getAltForm() {
		return altForm;
	}
	public void setAltForm(int altForm) {
		this.altForm = altForm;
	}
	
}
