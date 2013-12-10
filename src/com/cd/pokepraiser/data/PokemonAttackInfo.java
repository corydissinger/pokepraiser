package com.cd.pokepraiser.data;

import java.io.Serializable;

public class PokemonAttackInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3447481293673377857L;
	
	private String name;
	private String basePower;
	private String baseAccuracy;
	private String basePp;
	private int categoryDrawableId; 
	private int typeDrawableId;
	private int attackDbId;
	private int learnedType;
	private int lvlOrTm;	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBasePower() {
		return basePower;
	}
	public void setBasePower(String basePower) {
		this.basePower = basePower;
	}
	public String getBaseAccuracy() {
		return baseAccuracy;
	}
	public void setBaseAccuracy(String baseAccuracy) {
		this.baseAccuracy = baseAccuracy;
	}
	public String getBasePp() {
		return basePp;
	}
	public void setBasePp(String basePp) {
		this.basePp = basePp;
	}
	public int getCategoryDrawableId() {
		return categoryDrawableId;
	}
	public void setCategoryDrawableId(int categoryDrawableId) {
		this.categoryDrawableId = categoryDrawableId;
	}
	public int getTypeDrawableId() {
		return typeDrawableId;
	}
	public void setTypeDrawableId(int typeDrawableId) {
		this.typeDrawableId = typeDrawableId;
	}
	public int getAttackDbId() {
		return attackDbId;
	}
	public void setAttackDbId(int attackDbId) {
		this.attackDbId = attackDbId;
	}
	public int getLearnedType() {
		return learnedType;
	}
	public void setLearnedType(int learnedType) {
		this.learnedType = learnedType;
	}
	public int getLvlOrTm() {
		return lvlOrTm;
	}
	public void setLvlOrTm(int lvlOrTm) {
		this.lvlOrTm = lvlOrTm;
	}
}
