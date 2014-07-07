package com.cd.pokepraiser.data;

import java.io.Serializable;

public class AbilityAttributes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6755710345320858571L;
	
	public String name;
	public String battleEffect;
	public String worldEffect;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBattleEffect() {
		return battleEffect;
	}
	public void setBattleEffect(String battleEffect) {
		this.battleEffect = battleEffect;
	}
	public String getWorldEffect() {
		return worldEffect;
	}
	public void setWorldEffect(String worldEffect) {
		this.worldEffect = worldEffect;
	}
}
