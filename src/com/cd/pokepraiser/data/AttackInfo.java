package com.cd.pokepraiser.data;

public class AttackInfo {
	private String name;
	private String type;
	private Integer basePower;
	private Integer baseAccuracy;
	private Integer basePp;
	
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
	public Integer getBasePower() {
		return basePower;
	}
	public void setBasePower(Integer basePower) {
		this.basePower = basePower;
	}
	public Integer getBaseAccuracy() {
		return baseAccuracy;
	}
	public void setBaseAccuracy(Integer baseAccuracy) {
		this.baseAccuracy = baseAccuracy;
	}
	public Integer getBasePp() {
		return basePp;
	}
	public void setBasePp(Integer basePp) {
		this.basePp = basePp;
	}
	@Override
	public String toString() {
		return "AttackInfo [name=" + name + ", type=" + type + ", basePower="
				+ basePower + ", baseAccuracy=" + baseAccuracy + ", basePp="
				+ basePp + "]";
	}
}
