package com.cd.pokepraiser.data;

import java.io.Serializable;

public class AbilityInfo implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5852164010711735624L;
	
	public String name;
	public int abilityDbId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAbilityDbId() {
		return abilityDbId;
	}
	public void setAbilityDbId(int abilityDbId) {
		this.abilityDbId = abilityDbId;
	}
	
	public AbilityInfo clone() {
		AbilityInfo ability;
		
		try{
			ability = (AbilityInfo) super.clone();
			return ability;
		}catch(CloneNotSupportedException ce){
			ce.printStackTrace();
			throw new RuntimeException();
		}
	}
}
