package com.cd.pokepraiser.data;

public class AttackInfo {
	private String name;
	private String type;
	private String basePower;
	private String baseAccuracy;
	private String basePp;
	
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
	@Override
	public String toString() {
		return "AttackInfo [name=" + name + ", type=" + type + ", basePower="
				+ basePower + ", baseAccuracy=" + baseAccuracy + ", basePp="
				+ basePp + "]";
	}
}
