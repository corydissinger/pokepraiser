package com.cd.pokepraiser.data;

public class AttackInfo {
	private String name;
	private String type;
	private String basePower;
	private String baseAccuracy;
	private String basePp;
	private int categoryDrawableId; 
	private int attackDbId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public int getAttackDbId() {
		return attackDbId;
	}
	public void setAttackDbId(int attackDbId) {
		this.attackDbId = attackDbId;
	}
}
